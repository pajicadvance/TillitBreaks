package me.pajic.tillitbreaks.compat;

import me.pajic.tillitbreaks.platform.MultiLoaderUtil;

public class CompatFlags {

    public static final boolean INFINITY_FIX_PRESENT =
			MultiLoaderUtil.INSTANCE.isModLoaded("bowinfinityfix") ||
            MultiLoaderUtil.INSTANCE.isModLoaded("infinities") ||
            MultiLoaderUtil.INSTANCE.isModLoaded("rearm");
	public static final boolean TOOL_POUCH_LOADED = MultiLoaderUtil.INSTANCE.isModLoaded("toolpouch");
}
