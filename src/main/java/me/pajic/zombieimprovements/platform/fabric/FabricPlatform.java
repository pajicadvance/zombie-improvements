package me.pajic.zombieimprovements.platform.fabric;

//? fabric {

import me.pajic.zombieimprovements.platform.Platform;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatform implements Platform {

	@Override
	public boolean isDebug() {
		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}
}
//?}
