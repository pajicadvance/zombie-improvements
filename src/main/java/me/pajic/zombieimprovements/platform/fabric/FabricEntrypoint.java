package me.pajic.zombieimprovements.platform.fabric;

//? fabric {

import me.pajic.zombieimprovements.ZombieImprovements;
import me.pajic.zombieimprovements.util.AttachmentUtil;import net.fabricmc.api.ModInitializer;

@SuppressWarnings("unused")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		AttachmentUtil.init();
		ZombieImprovements.onInitialize();
	}
}
//?}
