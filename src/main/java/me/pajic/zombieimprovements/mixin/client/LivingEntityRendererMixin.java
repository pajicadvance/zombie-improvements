package me.pajic.zombieimprovements.mixin.client;

//? >=26.1 {

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import me.pajic.zombieimprovements.ZombieImprovements;
import me.pajic.zombieimprovements.util.HumanoidRenderStateExtension;
import me.pajic.zombieimprovements.util.LeaderAuraModelProvider;
import me.pajic.zombieimprovements.util.LeaderTextures;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<S extends LivingEntityRenderState, M extends EntityModel<? super S>> {

    @Unique private static final float zi$AURA_SCALE = 1.1F;

    @Shadow public abstract M getModel();

    @SuppressWarnings({"ConstantValue", "deprecation", "unchecked"})
	@ModifyExpressionValue(
            method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;shouldRenderLayers(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;)Z"
            )
    )
    private boolean renderZombiePowerLayer(
            boolean original,
            @Local(argsOnly = true) S state,
            @Local(argsOnly = true) PoseStack poseStack,
            @Local(argsOnly = true) SubmitNodeCollector submitNodeCollector
    ) {
        if (
				ZombieImprovements.CONFIG.leaderRedAura.get() && state.entityType != null &&
				state.entityType.builtInRegistryHolder().is(EntityTypeTags.ZOMBIES) &&
				state instanceof HumanoidRenderState &&
				((HumanoidRenderStateExtension) state).zi$isLeader()
		) {
            float h = state.ageInTicks;
            M entityModel = (Object) this instanceof LeaderAuraModelProvider provider ?
                    (M) provider.zi$getAuraModel(state.isBaby) : getModel();
            float center = 1.501F - state.boundingBoxHeight / state.scale / 2;
            poseStack.pushPose();
            poseStack.translate(0, center, 0);
            poseStack.scale(zi$AURA_SCALE, zi$AURA_SCALE, zi$AURA_SCALE);
            poseStack.translate(0, -center, 0);
            submitNodeCollector.order(1)
                    .submitModel(
                            entityModel,
							state,
                            poseStack,
							RenderTypes.energySwirl(
									ZombieImprovements.id("textures/entity/zombie/leader_zombie_aura.png"),
									(h * 0.01F) % 1.0F, h * 0.01F % 1.0F
							),
                            state.lightCoords,
                            OverlayTexture.NO_OVERLAY,
                            -8355712,
                            null,
                            state.outlineColor
                            //? <=26.2
                            //, null
                    );
            poseStack.popPose();
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "getRenderType",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getTextureLocation(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;)Lnet/minecraft/resources/Identifier;"
            )
    )
    private Identifier modifyLeaderTexture(Identifier original, @Local(argsOnly = true) S state) {
        return ZombieImprovements.CONFIG.leaderUniqueTexture.get() &&
                state instanceof HumanoidRenderState &&
                ((HumanoidRenderStateExtension) state).zi$isLeader() ?
                LeaderTextures.get(original) : original;
    }
}
//?}
