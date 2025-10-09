package me.pajic.zombieimprovements;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.zombieimprovements.config.ModConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//? if < 1.21.8
/*import net.minecraft.world.entity.MobSpawnType;*/
//? if >= 1.21.8
import net.minecraft.world.entity.EntitySpawnReason;

public class Main implements ModInitializer {
    public static final String MOD_ID = "zombieimprovements";
    private static final Logger LOGGER = LoggerFactory.getLogger("Zombie Improvements");
    private static final boolean DEBUG = FabricLoader.getInstance().isDevelopmentEnvironment();
    public static final ResourceLocation CONFIG_RL = withModNamespace("config");
    public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);
    public static ResourceLocation ZOMBIE_POWER_LAYER = withModNamespace("textures/entity/zombie/zombie_leader.png");

    @Override
    public void onInitialize() {}

    public static boolean isFromSpawner(/*? if < 1.21.8 {*//*MobSpawnType*//*?} else {*/EntitySpawnReason/*?}*/ spawnType) {
        return /*? if < 1.21.8 {*//*MobSpawnType*//*?} else {*/EntitySpawnReason/*?}*/.isSpawner(spawnType);
    }

    public static ResourceLocation withModNamespace(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void debugLog(String message, Object ... args) {
        if (DEBUG) LOGGER.info(message, args);
    }
}
