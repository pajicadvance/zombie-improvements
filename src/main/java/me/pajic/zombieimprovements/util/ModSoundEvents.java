package me.pajic.zombieimprovements.util;

import me.pajic.zombieimprovements.ZombieImprovements;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ModSoundEvents {

	private static final Map<SoundEvent, SoundEvent> LEADER_VARIANTS = new IdentityHashMap<>();

	static {
		leaderVariant(SoundEvents.ZOMBIE_AMBIENT, "entity.zombie.leader.ambient");
		leaderVariant(SoundEvents.ZOMBIE_HURT, "entity.zombie.leader.hurt");
		leaderVariant(SoundEvents.ZOMBIE_DEATH, "entity.zombie.leader.death");
		leaderVariant(SoundEvents.HUSK_AMBIENT, "entity.husk.leader.ambient");
		leaderVariant(SoundEvents.HUSK_HURT, "entity.husk.leader.hurt");
		leaderVariant(SoundEvents.HUSK_DEATH, "entity.husk.leader.death");
		leaderVariant(SoundEvents.DROWNED_AMBIENT, "entity.drowned.leader.ambient");
		leaderVariant(SoundEvents.DROWNED_AMBIENT_WATER, "entity.drowned.leader.ambient_water");
		leaderVariant(SoundEvents.DROWNED_HURT, "entity.drowned.leader.hurt");
		leaderVariant(SoundEvents.DROWNED_HURT_WATER, "entity.drowned.leader.hurt_water");
		leaderVariant(SoundEvents.DROWNED_DEATH, "entity.drowned.leader.death");
		leaderVariant(SoundEvents.DROWNED_DEATH_WATER, "entity.drowned.leader.death_water");
		leaderVariant(SoundEvents.ZOMBIE_VILLAGER_AMBIENT, "entity.zombie_villager.leader.ambient");
		leaderVariant(SoundEvents.ZOMBIE_VILLAGER_HURT, "entity.zombie_villager.leader.hurt");
		leaderVariant(SoundEvents.ZOMBIE_VILLAGER_DEATH, "entity.zombie_villager.leader.death");
		leaderVariant(SoundEvents.ZOMBIFIED_PIGLIN_AMBIENT, "entity.zombified_piglin.leader.ambient");
		leaderVariant(SoundEvents.ZOMBIFIED_PIGLIN_ANGRY, "entity.zombified_piglin.leader.angry");
		leaderVariant(SoundEvents.ZOMBIFIED_PIGLIN_HURT, "entity.zombified_piglin.leader.hurt");
		leaderVariant(SoundEvents.ZOMBIFIED_PIGLIN_DEATH, "entity.zombified_piglin.leader.death");
	}

	private static void leaderVariant(SoundEvent original, String path) {
		LEADER_VARIANTS.put(original, SoundEvent.createVariableRangeEvent(ZombieImprovements.id(path)));
	}

	public static SoundEvent getLeaderVariant(SoundEvent original) {
		return LEADER_VARIANTS.getOrDefault(original, original);
	}

	public static void register(BiConsumer<Identifier, SoundEvent> registrar) {
		LEADER_VARIANTS.values().forEach(sound -> registrar.accept(
				//? >=26.1
				sound.location(),
				//? <26.1
				//sound.getLocation(),
				sound
		));
	}
}
