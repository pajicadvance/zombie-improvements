package me.pajic.zombieimprovements.platform;

public interface Platform {
	boolean isModLoaded(String modId);

	boolean isDebug();

	ModLoader loader();

	String mcVersion();

	enum ModLoader {
		FABRIC, NEOFORGE, FORGE
	}
}
