package me.pajic.zombieimprovements;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.zombieimprovements.config.ModConfig;
import me.pajic.zombieimprovements.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//? if 1.21.1 {
/*import net.minecraft.world.entity.MobSpawnType;
*///?} else {
import net.minecraft.world.entity.EntitySpawnReason;
//?}

//? fabric {
import me.pajic.zombieimprovements.platform.fabric.FabricPlatform;
//?} neoforge {
/*import me.pajic.zombieimprovements.platform.neoforge.NeoforgePlatform;
*///?}

@SuppressWarnings("LoggingSimilarMessage")
public class ZombieImprovements {

	public static final String MOD_ID = /*$ mod_id*/ "zombieimprovements";
	public static final String MOD_VERSION = /*$ mod_version*/ "1.0.6";
	public static final String MOD_FRIENDLY_NAME = /*$ mod_name*/ "Zombie Improvements";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ResourceLocation CONFIG_RL = id("config");
	public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);
	private static final Platform PLATFORM = createPlatformInstance();

	public static ResourceLocation ZOMBIE_POWER_LAYER = id("textures/entity/zombie/zombie_leader.png");

	public static void onInitialize() {
	}

	public static void onInitializeClient() {
	}

	public static boolean isFromSpawner(/*? if < 1.21.8 {*//*MobSpawnType*//*?} else {*/EntitySpawnReason/*?}*/ spawnType) {
		return /*? if < 1.21.8 {*//*MobSpawnType*//*?} else {*/EntitySpawnReason/*?}*/.isSpawner(spawnType);
	}

	public static Platform xplat() {
		return PLATFORM;
	}

	private static Platform createPlatformInstance() {
		//? fabric {
		return new FabricPlatform();
		//?} neoforge {
		/*return new NeoforgePlatform();
		*///?}
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	public static void debugLog(String message, Object ... args) {
		if (PLATFORM.isDebug()) LOGGER.info(message, args);
	}
}
