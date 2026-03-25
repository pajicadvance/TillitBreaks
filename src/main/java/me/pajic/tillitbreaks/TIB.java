package me.pajic.tillitbreaks;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.pajic.tillitbreaks.config.TIBConfig;
import me.pajic.tillitbreaks.platform.Platform;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? fabric {
import me.pajic.tillitbreaks.platform.fabric.FabricPlatform;
//?} neoforge {
/*import me.pajic.tillitbreaks.platform.neoforge.NeoforgePlatform;
 *///?}

@SuppressWarnings("LoggingSimilarMessage")
public class TIB {

	public static final String MOD_ID = /*$ mod_id*/ "tillitbreaks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final Platform PLATFORM = createPlatformInstance();
	public static TIBConfig CONFIG = ConfigApiJava.registerAndLoadConfig(TIBConfig::new, RegisterType.CLIENT);

	public static void onInitializeClient() {
	}

	public static Platform xplat() {
		return PLATFORM;
	}

	private static Platform createPlatformInstance() {
		//? fabric {
		return new FabricPlatform();
		//?} neoforge {
		/*return new NeoforgePlatform();
		 *///?}
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static void debugLog(String message, Object ... args) {
		if (PLATFORM.isDebug()) LOGGER.info(message, args);
	}
}
