package me.pajic.tillitbreaks.platform.fabric;

//? fabric {

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import me.pajic.tillitbreaks.TIB;
import net.fabricmc.api.ClientModInitializer;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		TIB.onInitializeClient();
	}
}
//?}
