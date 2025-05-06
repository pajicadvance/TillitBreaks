package schauweg.tillitbreaks.compat;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Optional;

public class AccessorifyCompat {

    public static Optional<Triple<Integer, Integer, Boolean>> readArrowSlots(ClientPlayerEntity player) {
        Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
        if (ac.isPresent()) {
            AccessoriesContainer container = ac.get().getContainer(new SlotTypeReference("arrow"));
            if (container != null) {
                ExpandedSimpleContainer arrows = container.getAccessories();
                int arrowCounter = 0;
                int specialArrowCounter = 0;
                boolean hasNormalArrows = false;
                for (int i = 0; i < arrows.size(); i++) {
                    ItemStack is = arrows.getStack(i);
                    if (is.getItem() == Items.ARROW || is.getItem() == Items.SPECTRAL_ARROW || is.getItem() == Items.TIPPED_ARROW) {
                        arrowCounter += is.getCount();
                        if (is.getItem() == Items.ARROW) hasNormalArrows = true;
                        else specialArrowCounter += is.getCount();
                    }
                }
                return Optional.of(Triple.of(arrowCounter, specialArrowCounter, hasNormalArrows));
            }
        }
        return Optional.empty();
    }
}
