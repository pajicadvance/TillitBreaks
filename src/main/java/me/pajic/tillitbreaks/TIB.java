package me.pajic.tillitbreaks;

import me.pajic.tillitbreaks.config.TIBConfig;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import net.neoforged.fml.common.Mod;

@Mod(TIB.MOD_ID)
public class TIB {
    public static final String MOD_ID = "tillitbreaks";

    public TIB() {
        TIBConfig.HANDLER.load();
        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (client, parent) -> TIBConfig.makeScreen(parent)
        );
    }
}
