package schauweg.tillitbreaks.compat;

import com.nyfaria.nyfsquiver.init.ItemInit;
import com.nyfaria.nyfsquiver.menu.QuiverContainer;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Optional;

public class NyfsQuiverCompat {

    public static Optional<Triple<Integer, Integer, Boolean>> readQuiverInventory(ClientPlayerEntity player) {
        Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
        if (ac.isPresent()) {
            SlotEntryReference quiver = ac.get().getFirstEquipped(ItemInit.QUIVER.get());
            if (quiver != null) {
                int arrowCounter = 0;
                int specialArrowCounter = 0;
                boolean hasNormalArrows = false;
                QuiverContainer container = new QuiverContainer(quiver.stack());
                for (int i = 0; i < container.size(); i++) {
                    ItemStack is = container.getStack(i);
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
