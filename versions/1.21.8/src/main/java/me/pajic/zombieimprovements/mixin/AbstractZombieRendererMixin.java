package me.pajic.zombieimprovements.mixin;

import me.pajic.zombieimprovements.util.ZombieExtension;
import me.pajic.zombieimprovements.util.ZombieRenderStateExtension;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractZombieRenderer.class)
public class AbstractZombieRendererMixin {

    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/monster/Zombie;Lnet/minecraft/client/renderer/entity/state/ZombieRenderState;F)V",
            at = @At("HEAD")
    )
    private void extendRenderState(Zombie zombie, ZombieRenderState zombieRenderState, float f, CallbackInfo ci) {
        ((ZombieRenderStateExtension) zombieRenderState).zi$setLeader(((ZombieExtension) zombie).zi$isLeader());
    }
}
