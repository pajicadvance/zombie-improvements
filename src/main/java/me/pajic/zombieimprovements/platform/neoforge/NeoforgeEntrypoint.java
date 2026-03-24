package me.pajic.zombieimprovements.platform.neoforge;

//? neoforge {

/*import me.pajic.zombieimprovements.ZombieImprovements;
import me.pajic.zombieimprovements.util.AttachmentUtil;
import me.pajic.zombieimprovements.util.ModSoundEvents;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(ZombieImprovements.MOD_ID)
@EventBusSubscriber(modid = ZombieImprovements.MOD_ID)
public class NeoforgeEntrypoint {

	@SubscribeEvent
	private static void register(RegisterEvent event) {
		AttachmentUtil.init();
		ModSoundEvents.init();
		event.register(
				NeoForgeRegistries.ATTACHMENT_TYPES.key(),
				registry -> registry.register(
						ZombieImprovements.id("leader"), AttachmentUtil.LEADER
				)
		);
		event.register(
				Registries.SOUND_EVENT,
				registry -> {
					registry.register(ModSoundEvents.AMBIENT_ID, ModSoundEvents.AMBIENT);
					registry.register(ModSoundEvents.HURT_ID, ModSoundEvents.HURT);
					registry.register(ModSoundEvents.DEATH_ID, ModSoundEvents.DEATH);
				}
		);
	}
}
*///?}
