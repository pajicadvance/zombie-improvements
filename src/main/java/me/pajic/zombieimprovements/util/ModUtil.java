package me.pajic.zombieimprovements.util;

import net.minecraft.world.entity.EntitySpawnReason;

public class ModUtil {

	public static boolean isFromSpawner(EntitySpawnReason spawnType) {
		return EntitySpawnReason.isSpawner(spawnType);
	}
}
