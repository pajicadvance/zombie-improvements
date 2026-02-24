package me.pajic.zombieimprovements.mixin.modern;

//? if > 1.21.1 {

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.zombieimprovements.ZombieImprovements;
import me.pajic.zombieimprovements.util.HumanoidRenderStateExtension;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.tags.EntityTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
//? if >= 1.21.11
import net.minecraft.client.renderer.rendertype.RenderTypes;
//? if < 1.21.11
//import net.minecraft.client.renderer.rendertype.RenderType;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<S extends LivingEntityRenderState, M extends EntityModel<? super S>> {

    @Shadow public abstract M getModel();

    @SuppressWarnings("ConstantValue")
	@ModifyExpressionValue(
            method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;shouldRenderLayers(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;)Z"
            )
    )
    private boolean renderZombiePowerLayer(
            boolean original,
            @Local(argsOnly = true) S renderState,
            @Local(argsOnly = true) PoseStack poseStack,
            @Local(argsOnly = true) SubmitNodeCollector submitNodeCollector
    ) {
        if (ZombieImprovements.CONFIG.leaderRedAura.get() && renderState.entityType != null && renderState.entityType.is(EntityTypeTags.ZOMBIES) && renderState instanceof HumanoidRenderState && ((HumanoidRenderStateExtension) renderState).zi$isLeader()) {
            float h = renderState.ageInTicks;
            M entityModel = getModel();
            submitNodeCollector.order(1)
                    .submitModel(
                            entityModel,
                            renderState,
                            poseStack,
							/*? if < 1.21.11 {*//*RenderType*//*?} else {*/RenderTypes/*?}*/.energySwirl(
									ZombieImprovements.id("textures/entity/zombie/leader_zombie_aura.png"),
									(h * 0.01F) % 1.0F, h * 0.01F % 1.0F
							),
                            renderState.lightCoords,
                            OverlayTexture.NO_OVERLAY,
                            -8355712,
                            null,
                            renderState.outlineColor,
                            null
                    );
        }
        return original;
    }
}
//?}
