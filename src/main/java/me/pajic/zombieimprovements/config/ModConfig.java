package me.pajic.zombieimprovements.config;

import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.pajic.zombieimprovements.ZombieImprovements;

@Version(version = 1)
public class ModConfig extends Config {
	public ModConfig() {
		super(ZombieImprovements.CONFIG_RL);
	}

	public ValidatedBoolean leaderRedAura = new ValidatedBoolean(true);
	public ValidatedBoolean reinforcementSpawnSounds = new ValidatedBoolean(true);
	public ValidatedFloat spawnSoundVolume = new ValidatedFloat(1, 10, 1);
	public ValidatedBoolean onlyLeaderSpawnsReinforcements = new ValidatedBoolean(false);
	public ValidatedBoolean noLeaderFromSpawners = new ValidatedBoolean(false);
	public ValidatedBoolean noReinforcementsFromSpawners = new ValidatedBoolean(false);
	public ValidatedFloat leaderMaxSpawnChance = new ValidatedFloat(0.05F, 1, 0);
}
