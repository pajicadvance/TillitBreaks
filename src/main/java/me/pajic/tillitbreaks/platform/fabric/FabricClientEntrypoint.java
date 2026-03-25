package me.pajic.tillitbreaks.platform.fabric;

//? fabric {

import me.pajic.tillitbreaks.TIB;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		TIB.onInitializeClient();
	}
}
//?}
