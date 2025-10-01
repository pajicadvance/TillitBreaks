package me.pajic.tib.compat;

import io.wispforest.accessories.data.SlotTypeLoader;
import net.minecraft.client.player.LocalPlayer;
import org.apache.commons.lang3.tuple.MutableTriple;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import me.pajic.tib.TIBUtil;

import java.util.Optional;

public class AccessorifyCompat {
    public static void addArrowsFromArrowSlots(LocalPlayer player, MutableTriple<Integer, Integer, Boolean> arrows) {
        Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
        if (ac.isPresent()) {
            AccessoriesContainer container = ac.get().getContainer(SlotTypeLoader.getSlotType(player, "arrow"));
            if (container != null) TIBUtil.addArrowsFromContainer(container.getAccessories(), arrows);
        }
    }
}
