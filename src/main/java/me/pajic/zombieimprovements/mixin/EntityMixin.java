package me.pajic.zombieimprovements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.pajic.zombieimprovements.ZombieImprovements;
import me.pajic.zombieimprovements.util.AttachmentUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {

	@ModifyReturnValue(
			method = "getTypeName",
			at = @At("RETURN")
	)
	private Component addLeaderToName(Component original) {
		return AttachmentUtil.isLeader((Entity) (Object) this) && ZombieImprovements.CONFIG.leaderNamePrefix.get() ?
				Component.translatable("entity.zombieimprovements.leader", original) : original;
	}
}
