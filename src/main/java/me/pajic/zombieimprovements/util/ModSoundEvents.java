package me.pajic.zombieimprovements.util;

import me.pajic.zombieimprovements.ZombieImprovements;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {

	public static final Identifier AMBIENT_ID = ZombieImprovements.id("entity.zombie.leader.ambient");
	public static final Identifier HURT_ID = ZombieImprovements.id("entity.zombie.leader.hurt");
	public static final Identifier DEATH_ID = ZombieImprovements.id("entity.zombie.leader.death");

	public static final SoundEvent AMBIENT = SoundEvent.createVariableRangeEvent(AMBIENT_ID);
	public static final SoundEvent HURT = SoundEvent.createVariableRangeEvent(HURT_ID);
	public static final SoundEvent DEATH = SoundEvent.createVariableRangeEvent(DEATH_ID);

	public static void init() {}
}
