package me.pajic.zombieimprovements.mixin.client;

//? >=26.1 {

import me.pajic.zombieimprovements.util.AttachmentUtil;
import me.pajic.zombieimprovements.util.HumanoidRenderStateExtension;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.zombie.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidMobRenderer.class)
public class HumanoidMobRendererMixin {

    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/Mob;Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;F)V",
            at = @At("HEAD")
    )
    private void extendRenderState(Mob entity, HumanoidRenderState state, float partialTicks, CallbackInfo ci) {
        if (entity instanceof Zombie) ((HumanoidRenderStateExtension) state).zi$setLeader(AttachmentUtil.isLeader(entity));
    }
}
//?}
