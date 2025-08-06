package me.pajic.tib;

import me.pajic.tib.config.TIBConfig;
import net.fabricmc.api.ModInitializer;

public class TIB implements ModInitializer {
    @Override
    public void onInitialize() {
        TIBConfig.HANDLER.load();
    }
}
