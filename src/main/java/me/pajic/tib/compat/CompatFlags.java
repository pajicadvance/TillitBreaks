package me.pajic.tib.compat;

import net.fabricmc.loader.api.FabricLoader;

public class CompatFlags {
    public static final boolean ACCESSORIES_LOADED = FabricLoader.getInstance().isModLoaded("accessories");
    public static final boolean ACCESSORIFY_LOADED = ACCESSORIES_LOADED && FabricLoader.getInstance().isModLoaded("accessorify");
    public static final boolean NYFS_QUIVERS_LOADED = ACCESSORIES_LOADED && FabricLoader.getInstance().isModLoaded("nyfsquiver");
    public static final boolean INFINITY_FIX_PRESENT =
            FabricLoader.getInstance().isModLoaded("bowinfinityfix") ||
            FabricLoader.getInstance().isModLoaded("infinities") ||
            FabricLoader.getInstance().isModLoaded("rearm");
}
