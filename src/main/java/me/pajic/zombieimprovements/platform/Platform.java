package me.pajic.zombieimprovements.platform;

public interface Platform {

	boolean isDevelopmentEnvironment();

	default boolean isDebug() {
		return isDevelopmentEnvironment();
	}
}
