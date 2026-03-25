package me.pajic.tillitbreaks.platform;

public interface Platform {

	boolean isModLoaded(String modId);

	boolean isDevelopmentEnvironment();

	default boolean isDebug() {
		return isDevelopmentEnvironment();
	}
}
