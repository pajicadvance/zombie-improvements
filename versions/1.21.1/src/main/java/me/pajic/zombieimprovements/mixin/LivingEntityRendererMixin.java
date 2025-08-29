package me.pajic.zombieimprovements.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.pajic.zombieimprovements.Main;
import me.pajic.zombieimprovements.util.ZombieData;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>>  {

    @Shadow public abstract M getModel();

    @Shadow protected abstract float getBob(T livingBase, float partialTick);

    @ModifyExpressionValue(
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;isSpectator()Z"
            )
    )
    private boolean renderZombiePowerLayer(
            boolean original,
            @Local(argsOnly = true) T entity,
            @Local(argsOnly = true) PoseStack poseStack,
            @Local(argsOnly = true) MultiBufferSource bufferSource,
            @Local(argsOnly = true, ordinal = 1) float partialTick,
            @Local(argsOnly = true) int packedLight,
            @Local(ordinal = 9) float limbSwing,
            @Local(ordinal = 8) float limbSwingAmount,
            @Local(ordinal = 4) float netHeadYaw,
            @Local(ordinal = 5) float headPitch
    ) {
        if (Main.CONFIG.leaderRedAura.get() && entity instanceof Zombie zombie && zombie.getEntityData().get(ZombieData.LEADER)) {
            float f = zombie.tickCount + partialTick;
            EntityModel<T> entityModel = getModel();
            entityModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.energySwirl(ZombieData.ZOMBIE_POWER_LAYER, (f * 0.01F) % 1.0F, f * 0.01F % 1.0F));
            entityModel.setupAnim(entity, limbSwing, limbSwingAmount, getBob(entity, partialTick), netHeadYaw, headPitch);
            entityModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, -8355712);
        }
        return original;
    }
}
