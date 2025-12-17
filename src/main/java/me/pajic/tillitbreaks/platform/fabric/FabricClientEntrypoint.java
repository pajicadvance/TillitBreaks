package me.pajic.tillitbreaks.platform.fabric;

//? fabric {

import me.pajic.tillitbreaks.TIB;
import net.fabricmc.api.ClientModInitializer;

@SuppressWarnings("unused")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		TIB.onInitializeClient();
	}
}
//?}
