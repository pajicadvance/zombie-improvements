package me.pajic.zombieimprovements.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.pajic.zombieimprovements.Main;
import me.pajic.zombieimprovements.util.HumanoidRenderStateExtension;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {

    @Shadow public abstract M getModel();

    @ModifyExpressionValue(
            method = "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;shouldRenderLayers(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;)Z"
            )
    )
    private boolean renderZombiePowerLayer(
            boolean original,
            @Local(argsOnly = true) S renderState,
            @Local(argsOnly = true) PoseStack poseStack,
            @Local(argsOnly = true) MultiBufferSource bufferSource,
            @Local(argsOnly = true) int packedLight
    ) {
        if (Main.CONFIG.leaderRedAura.get() && renderState.entityType.is(EntityTypeTags.ZOMBIES) && ((HumanoidRenderStateExtension) renderState).zi$isLeader()) {
            float f = renderState.ageInTicks;
            M entityModel = getModel();
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.energySwirl(Main.ZOMBIE_POWER_LAYER, (f * 0.01F) % 1.0F, f * 0.01F % 1.0F));
            entityModel.setupAnim(renderState);
            entityModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, -8355712);
        }
        return original;
    }
}
