package me.pajic.zombieimprovements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.pajic.zombieimprovements.ZombieImprovements;
import me.pajic.zombieimprovements.util.AttachmentUtil;
import me.pajic.zombieimprovements.util.ModSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.entity.monster.zombie.Husk;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.monster.zombie.ZombieVillager;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({Zombie.class, Husk.class, Drowned.class, ZombieVillager.class, ZombifiedPiglin.class})
public class ZombieSoundsMixin {

	@ModifyReturnValue(
			method = {"getAmbientSound", "getHurtSound", "getDeathSound"},
			at = @At("RETURN")
	)
	private SoundEvent modifyLeaderSound(SoundEvent original) {
		return ZombieImprovements.CONFIG.leaderUniqueSounds.get() && AttachmentUtil.isLeader((Entity) (Object) this) ?
				ModSoundEvents.getLeaderVariant(original) : original;
	}
}
