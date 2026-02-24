package me.pajic.zombieimprovements.platform.fabric;

//? fabric {

import me.pajic.zombieimprovements.util.AttachmentUtil;
import me.pajic.zombieimprovements.util.ModSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

@SuppressWarnings("unused")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		AttachmentUtil.init();
		ModSoundEvents.init();
		registerSounds();
	}

	private static void registerSounds() {
		Registry.register(BuiltInRegistries.SOUND_EVENT, ModSoundEvents.AMBIENT_ID, ModSoundEvents.AMBIENT);
		Registry.register(BuiltInRegistries.SOUND_EVENT, ModSoundEvents.HURT_ID, ModSoundEvents.HURT);
		Registry.register(BuiltInRegistries.SOUND_EVENT, ModSoundEvents.DEATH_ID, ModSoundEvents.DEATH);
	}
}
//?}
