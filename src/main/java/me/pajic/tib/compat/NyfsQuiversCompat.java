package me.pajic.tib.compat;

import net.minecraft.client.player.LocalPlayer;
import org.apache.commons.lang3.tuple.MutableTriple;
//? if <= 1.21.1 {
import com.nyfaria.nyfsquiver.init.ItemInit;
import com.nyfaria.nyfsquiver.menu.QuiverContainer;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import me.pajic.tib.TIBUtil;
//?}

import java.util.Optional;

public class NyfsQuiversCompat {
    public static void addArrowsFromQuiver(LocalPlayer player, MutableTriple<Integer, Integer, Boolean> arrows) {
        //? if <= 1.21.1 {
        Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
        if (ac.isPresent()) {
            SlotEntryReference quiver = ac.get().getFirstEquipped(ItemInit.QUIVER.get());
            if (quiver != null) TIBUtil.addArrowsFromContainer(new QuiverContainer(quiver.stack()), arrows);
        }
        //?}
    }
}
