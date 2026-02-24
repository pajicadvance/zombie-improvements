package me.pajic.zombieimprovements.config;

import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedExpression;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.pajic.zombieimprovements.ZombieImprovements;

import java.util.Set;

@Version(version = 1)
public class ModConfig extends Config {
	public ModConfig() {
		super(ZombieImprovements.CONFIG_RL);
	}

	public ValidatedBoolean leaderRedAura = new ValidatedBoolean(true);
	public ValidatedBoolean leaderIncreasedDamage = new ValidatedBoolean(true);
	public ValidatedExpression damageIncreaseFormula = new ValidatedExpression("r*1.5+3", Set.of('r'));
	public ValidatedDouble leaderSizeIncrease = new ValidatedDouble(0.125, 1, 0);
	public ValidatedBoolean leaderUniqueTexture = new ValidatedBoolean(true);
	public ValidatedBoolean leaderUniqueSounds = new ValidatedBoolean(true);
	public ValidatedBoolean leaderNamePrefix = new ValidatedBoolean(true);
	public ValidatedBoolean reinforcementSpawnSounds = new ValidatedBoolean(true);
	public ValidatedFloat spawnSoundVolume = new ValidatedFloat(1, 10, 1);
	public ValidatedBoolean onlyLeaderSpawnsReinforcements = new ValidatedBoolean(false);
	public ValidatedBoolean noLeaderFromSpawners = new ValidatedBoolean(false);
	public ValidatedBoolean noReinforcementsFromSpawners = new ValidatedBoolean(false);
	public ValidatedFloat leaderMaxSpawnChance = new ValidatedFloat(0.05F, 1, 0);
}
