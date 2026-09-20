package me.pajic.tillitbreaks;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.tillitbreaks.config.TIBConfig;
import me.pajic.tillitbreaks.platform.MultiLoaderUtil;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class TIB {

    public static final String MOD_ID = /*$ mod_id*/ "tillitbreaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static TIBConfig CONFIG = ConfigApiJava.registerAndLoadConfig(TIBConfig::new);
    public static NumberFormat FORMATTER;

    public static void onInitializeClient() {
        FORMATTER = NumberFormat.getCompactNumberInstance(Locale.getDefault(Locale.Category.FORMAT), NumberFormat.Style.SHORT);
        FORMATTER.setParseIntegerOnly(true);
        FORMATTER.setRoundingMode(RoundingMode.DOWN);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void debugLog(String message, Object ... args) {
        if (MultiLoaderUtil.INSTANCE.isDevEnv()) LOGGER.info(message, args);
    }
}
