package me.pajic.zombieimprovements.util;

import me.pajic.zombieimprovements.ZombieImprovements;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntitySpawnReason;

public class ModUtil {
	public static Identifier ZOMBIE_POWER_LAYER = ZombieImprovements.id("textures/entity/zombie/zombie_leader.png");

	public static boolean isFromSpawner(EntitySpawnReason spawnType) {
		return EntitySpawnReason.isSpawner(spawnType);
	}
}
