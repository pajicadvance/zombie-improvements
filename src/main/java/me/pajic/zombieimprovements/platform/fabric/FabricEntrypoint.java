package me.pajic.zombieimprovements.platform.fabric;

//? fabric {

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import me.pajic.zombieimprovements.ZombieImprovements;
import me.pajic.zombieimprovements.util.ModSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

@Entrypoint("main")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		ZombieImprovements.onInitialize();
        ModSoundEvents.register((id, sound) -> Registry.register(BuiltInRegistries.SOUND_EVENT, id, sound));
	}
}
//?}
