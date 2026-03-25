package me.pajic.tillitbreaks.config;

import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.pajic.tillitbreaks.TIB;

@Version(version = 1)
public class TIBConfig extends Config {

    public TIBConfig() {
        super(TIB.id("config"));
    }

	public ValidatedBoolean showDurabilityBar = new ValidatedBoolean(true);
	public ValidatedBoolean showDurabilityNumber = new ValidatedBoolean(true);
	public ValidatedBoolean showDurabilityBarIfFull = new ValidatedBoolean(false);
	public ValidatedBoolean showDurabilityNumberIfFull = new ValidatedBoolean(false);
	public ValidatedFloat durabilityBarColorSaturation = new ValidatedFloat(1, 1, 0);
	public ValidatedFloat durabilityNumberColorSaturation = new ValidatedFloat(1, 1, 0);
	public ValidatedBoolean showArrowCount = new ValidatedBoolean(true);
	public ValidatedBoolean textShadow = new ValidatedBoolean(true);
	public ValidatedFloat textScale = new ValidatedFloat(1, 1.5F, 0.5F);
}
