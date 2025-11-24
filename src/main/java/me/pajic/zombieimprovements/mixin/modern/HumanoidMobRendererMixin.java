package me.pajic.zombieimprovements.mixin.modern;

//? if > 1.21.1 {

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.zombieimprovements.util.HumanoidRenderStateExtension;
import me.pajic.zombieimprovements.util.ZombieExtension;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(HumanoidMobRenderer.class)
public class HumanoidMobRendererMixin {

    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/Mob;Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;F)V",
            at = @At("HEAD")
    )
    private void extendRenderState(Mob mob, HumanoidRenderState humanoidRenderState, float f, CallbackInfo ci) {
        if (mob instanceof Zombie) ((HumanoidRenderStateExtension) humanoidRenderState).zi$setLeader(((ZombieExtension) mob).zi$isLeader());
    }
}
//?}
