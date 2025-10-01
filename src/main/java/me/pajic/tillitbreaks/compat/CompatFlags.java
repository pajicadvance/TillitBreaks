package me.pajic.tillitbreaks.compat;

import net.neoforged.fml.ModList;

public class CompatFlags {
    public static final boolean ACCESSORIES_LOADED = ModList.get().isLoaded("accessories");
    public static final boolean ACCESSORIFY_LOADED = ACCESSORIES_LOADED && ModList.get().isLoaded("accessorify");
    public static final boolean INFINITY_FIX_PRESENT =
            ModList.get().isLoaded("bowinfinityfix") ||
            ModList.get().isLoaded("infinities") ||
            ModList.get().isLoaded("rearm");
}
