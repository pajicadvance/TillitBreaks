package me.pajic.tillitbreaks.platform.neoforge;

//? neoforge {

/*import me.pajic.tillitbreaks.platform.Platform;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

public class NeoforgePlatform implements Platform {

	@Override
	public boolean isModLoaded(String modId) {
		return ModList.get().isLoaded(modId);
	}

	@Override
	public boolean isDebug() {
		return !FMLLoader/^? if > 1.21.1 {^/.getCurrent()/^?}^/.isProduction();
	}
}
*///?}
