package me.pajic.tillitbreaks.compat;

import me.pajic.tillitbreaks.TIB;

public class CompatFlags {
    public static final boolean ACCESSORIES_LOADED = TIB.xplat().isModLoaded("accessories");
    public static final boolean INFINITY_FIX_PRESENT =
			TIB.xplat().isModLoaded("bowinfinityfix") ||
			TIB.xplat().isModLoaded("infinities") ||
			TIB.xplat().isModLoaded("rearm");
}
