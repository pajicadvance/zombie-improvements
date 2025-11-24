package me.pajic.zombieimprovements.platform.fabric;

//? fabric {

import me.pajic.zombieimprovements.ZombieImprovements;
import net.fabricmc.api.ModInitializer;

@SuppressWarnings("unused")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		ZombieImprovements.onInitialize();
	}
}
//?}
