package me.pajic.tib.compat;

import net.minecraft.client.player.LocalPlayer;
import org.apache.commons.lang3.tuple.MutableTriple;
//? if <= 1.21.4 {
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import me.pajic.tib.TIBUtil;
//?}

import java.util.Optional;

public class AccessorifyCompat {
    public static void addArrowsFromArrowSlots(LocalPlayer player, MutableTriple<Integer, Integer, Boolean> arrows) {
        //? if <= 1.21.4 {
        Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
        if (ac.isPresent()) {
            AccessoriesContainer container = ac.get().getContainer(new SlotTypeReference("arrow"));
            if (container != null) TIBUtil.addArrowsFromContainer(container.getAccessories(), arrows);
        }
        //?}
    }
}
