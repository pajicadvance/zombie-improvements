package me.pajic.zombieimprovements.platform.neoforge;

//? neoforge {

/*import me.pajic.zombieimprovements.ZombieImprovements;
import me.pajic.zombieimprovements.util.AttachmentUtil;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(ZombieImprovements.MOD_ID)
@EventBusSubscriber(modid = ZombieImprovements.MOD_ID)
public class NeoforgeEntrypoint {

	@SubscribeEvent
	private static void onCommonSetup(FMLCommonSetupEvent event) {
		ZombieImprovements.onInitialize();
	}

	@SubscribeEvent
	private static void registerDataAttachments(RegisterEvent event) {
		AttachmentUtil.init();
		event.register(
				NeoForgeRegistries.ATTACHMENT_TYPES.key(),
				registry -> registry.register(
						ZombieImprovements.id("leader"), AttachmentUtil.LEADER
				)
		);
	}
}
*///?}
