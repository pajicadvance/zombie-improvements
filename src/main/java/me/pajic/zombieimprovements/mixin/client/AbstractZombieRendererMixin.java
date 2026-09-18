package me.pajic.zombieimprovements.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.zombieimprovements.ZombieImprovements;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? >=26.1 {
import me.pajic.zombieimprovements.util.HumanoidRenderStateExtension;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
//?} else {
/*import me.pajic.zombieimprovements.util.AttachmentUtil;
import net.minecraft.world.entity.monster.zombie.Zombie;
*///?}

@Mixin(AbstractZombieRenderer.class)
public class AbstractZombieRendererMixin {

	@ModifyReturnValue(
            //? >=26.1
			method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/ZombieRenderState;)Lnet/minecraft/resources/Identifier;",
            //? <26.1
            //method = "getTextureLocation(Lnet/minecraft/world/entity/monster/zombie/Zombie;)Lnet/minecraft/resources/Identifier;",
			at = @At("RETURN")
	)
	private /*? >=26.1 {*/<S extends ZombieRenderState>/*?}*/ Identifier modifyTexture(
            Identifier original,
            //~ if <26.1 'S zombie' -> 'Zombie zombie'
             @Local(argsOnly = true) S zombie
    ) {
        //~ if <26.1 '((HumanoidRenderStateExtension) zombie).zi$isLeader()' -> 'AttachmentUtil.isLeader(zombie)'
		return ZombieImprovements.CONFIG.leaderUniqueTexture.get() && ((HumanoidRenderStateExtension) zombie).zi$isLeader() ?
				ZombieImprovements.id("textures/entity/zombie/leader_zombie.png") : original;
	}
}
