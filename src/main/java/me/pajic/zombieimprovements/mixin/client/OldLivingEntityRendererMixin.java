package me.pajic.zombieimprovements.mixin.client;

//? <26.1 {

/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.zombieimprovements.ZombieImprovements;
import me.pajic.zombieimprovements.util.AttachmentUtil;
import me.pajic.zombieimprovements.util.LeaderTextures;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.zombie.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(LivingEntityRenderer.class)
public abstract class OldLivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>>  {

    @Unique private static final float zi$AURA_SCALE = 1.1F;

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
        if (ZombieImprovements.CONFIG.leaderRedAura.get() && entity instanceof Zombie zombie && AttachmentUtil.isLeader(zombie)) {
            float f = zombie.tickCount + partialTick;
            EntityModel<T> entityModel = getModel();
            entityModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.energySwirl(
					ZombieImprovements.id("textures/entity/zombie/leader_zombie_aura.png"),
					(f * 0.01F) % 1.0F, f * 0.01F % 1.0F)
			);
            entityModel.setupAnim(entity, limbSwing, limbSwingAmount, getBob(entity, partialTick), netHeadYaw, headPitch);
            ModelPart hat = entityModel instanceof ZombieVillagerModel<?> villagerModel ? villagerModel.hat : null;
            boolean hatVisible = hat != null && hat.visible;
            if (hat != null) hat.visible = false;
            float center = 1.501F - entity.getBbHeight() / entity.getScale() / 2;
            poseStack.pushPose();
            poseStack.translate(0, center, 0);
            poseStack.scale(zi$AURA_SCALE, zi$AURA_SCALE, zi$AURA_SCALE);
            poseStack.translate(0, -center, 0);
            entityModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, -8355712);
            poseStack.popPose();
            if (hat != null) hat.visible = hatVisible;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "getRenderType",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getTextureLocation(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/resources/Identifier;"
            )
    )
    private Identifier modifyLeaderTexture(Identifier original, @Local(argsOnly = true) T entity) {
        return ZombieImprovements.CONFIG.leaderUniqueTexture.get() && entity instanceof Zombie && AttachmentUtil.isLeader(entity) ?
                LeaderTextures.get(original) : original;
    }
}
*///?}
