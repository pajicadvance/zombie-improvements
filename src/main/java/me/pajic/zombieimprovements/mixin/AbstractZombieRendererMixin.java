package me.pajic.zombieimprovements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.zombieimprovements.ZombieImprovements;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if < 1.21.11 {
/*import me.pajic.zombieimprovements.util.AttachmentUtil;
import net.minecraft.world.entity.monster.zombie.Zombie;
*///?} else {
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import me.pajic.zombieimprovements.util.HumanoidRenderStateExtension;
//?}

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(AbstractZombieRenderer.class)
public class AbstractZombieRendererMixin {

	//? if < 1.21.11 {
	/*@ModifyReturnValue(
			method = "getTextureLocation(Lnet/minecraft/world/entity/monster/zombie/Zombie;)Lnet/minecraft/resources/Identifier;",
			at = @At("RETURN")
	)
	private Identifier init(Identifier original, @Local(argsOnly = true) Zombie zombie) {
		return ZombieImprovements.CONFIG.leaderUniqueTexture.get() && AttachmentUtil.isLeader(zombie) ?
				ZombieImprovements.id("textures/entity/zombie/leader_zombie.png") : original;
	}
	*///?} else {
	@ModifyReturnValue(
			method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/ZombieRenderState;)Lnet/minecraft/resources/Identifier;",
			at = @At("RETURN")
	)
	private <S extends ZombieRenderState> Identifier init(Identifier original, @Local(argsOnly = true) S renderState) {
		return ZombieImprovements.CONFIG.leaderUniqueTexture.get() && ((HumanoidRenderStateExtension) renderState).zi$isLeader() ?
				ZombieImprovements.id("textures/entity/zombie/leader_zombie.png") : original;
	}
	//?}
}
