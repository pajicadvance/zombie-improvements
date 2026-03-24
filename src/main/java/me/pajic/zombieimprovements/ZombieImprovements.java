package me.pajic.zombieimprovements;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.zombieimprovements.config.ModConfig;
import me.pajic.zombieimprovements.platform.Platform;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? fabric {
import me.pajic.zombieimprovements.platform.fabric.FabricPlatform;
//?} neoforge {
/*import me.pajic.zombieimprovements.platform.neoforge.NeoforgePlatform;
 *///?}

@SuppressWarnings("LoggingSimilarMessage")
public class ZombieImprovements {

	public static final String MOD_ID = /*$ mod_id*/ "zombieimprovements";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final Platform PLATFORM = createPlatformInstance();
	public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);

	public static void onInitialize() {}

	private static Platform createPlatformInstance() {
		//? fabric {
		return new FabricPlatform();
		//?} neoforge {
		/*return new NeoforgePlatform();
		 *///?}
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static void debugLog(String message, Object ... args) {
		if (PLATFORM.isDebug()) LOGGER.info(message, args);
	}
}
