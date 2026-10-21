package me.pajic.zombieimprovements.util;

import me.pajic.zombieimprovements.ZombieImprovements;
import net.minecraft.resources.Identifier;

import java.util.Map;

public class LeaderTextures {

	private static final Map<Identifier, Identifier> TEXTURES = Map.ofEntries(
			leaderTexture("zombie/zombie"),
			leaderTexture("zombie/zombie_baby"),
			leaderTexture("zombie/husk"),
			leaderTexture("zombie/husk_baby"),
			leaderTexture("zombie/drowned"),
			leaderTexture("zombie/drowned_baby"),
			leaderTexture("zombie_villager/zombie_villager"),
			leaderTexture("zombie_villager/zombie_villager_baby"),
			leaderTexture("piglin/zombified_piglin"),
			leaderTexture("piglin/zombified_piglin_baby")
	);

	private static Map.Entry<Identifier, Identifier> leaderTexture(String path) {
		int slash = path.lastIndexOf('/');
		return Map.entry(
				Identifier.withDefaultNamespace("textures/entity/" + path + ".png"),
				ZombieImprovements.id("textures/entity/" + path.substring(0, slash + 1) + "leader_" + path.substring(slash + 1) + ".png")
		);
	}

	public static Identifier get(Identifier original) {
		return TEXTURES.getOrDefault(original, original);
	}
}
