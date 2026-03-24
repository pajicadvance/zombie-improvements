package me.pajic.zombieimprovements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.zombieimprovements.ZombieImprovements;
import me.pajic.zombieimprovements.util.HumanoidRenderStateExtension;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(AbstractZombieRenderer.class)
public class AbstractZombieRendererMixin {

	@ModifyReturnValue(
			method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/ZombieRenderState;)Lnet/minecraft/resources/Identifier;",
			at = @At("RETURN")
	)
	private <S extends ZombieRenderState> Identifier init(Identifier original, @Local(argsOnly = true) S renderState) {
		return ZombieImprovements.CONFIG.leaderUniqueTexture.get() && ((HumanoidRenderStateExtension) renderState).zi$isLeader() ?
				ZombieImprovements.id("textures/entity/zombie/leader_zombie.png") : original;
	}
}
