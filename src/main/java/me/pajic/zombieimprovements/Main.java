package me.pajic.zombieimprovements;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.zombieimprovements.config.ModConfig;
import me.pajic.zombieimprovements.util.ZombieData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
    public static final String MOD_ID = "zombieimprovements";
    private static final Logger LOGGER = LoggerFactory.getLogger("Zombie Improvements");
    private static final boolean DEBUG = FabricLoader.getInstance().isDevelopmentEnvironment();
    public static final ResourceLocation CONFIG_RL = withModNamespace("config");
    public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);

    @Override
    public void onInitialize() {
        ZombieData.init();
    }

    public static ResourceLocation withModNamespace(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void debugLog(String message, Object ... args) {
        if (DEBUG) LOGGER.info(message, args);
    }
}
