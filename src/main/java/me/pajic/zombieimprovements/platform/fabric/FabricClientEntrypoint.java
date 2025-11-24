package me.pajic.zombieimprovements.platform.fabric;

//? fabric {

import me.pajic.zombieimprovements.ZombieImprovements;
import net.fabricmc.api.ClientModInitializer;

@SuppressWarnings("unused")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ZombieImprovements.onInitializeClient();
	}
}
//?}
