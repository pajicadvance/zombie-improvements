package me.pajic.zombieimprovements.config;

import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.pajic.zombieimprovements.Main;

@Version(version = 1)
public class ModConfig extends Config {
    public ModConfig() {
        super(Main.CONFIG_RL);
    }

    public ValidatedBoolean leaderRedAura = new ValidatedBoolean(true);
    public ValidatedBoolean reinforcementSpawnSounds = new ValidatedBoolean(true);
    public ValidatedBoolean onlyLeaderSpawnsReinforcements = new ValidatedBoolean(false);
    public ValidatedBoolean noLeaderFromSpawners = new ValidatedBoolean(false);
    public ValidatedBoolean noReinforcementsFromSpawners = new ValidatedBoolean(false);
}
