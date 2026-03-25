package me.pajic.tillitbreaks.util;

import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.apache.commons.lang3.tuple.MutableTriple;

public class TIBUtil {

    public static boolean shouldRenderText(LocalPlayer player, ItemStack stack) {
        return player != null &&
                !(player.containerMenu instanceof CreativeModeInventoryScreen.ItemPickerMenu) &&
                stack.isDamageableItem();
    }

    public static void addArrowsFromContainer(Container container, MutableTriple<Integer, Integer, Boolean> arrows) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.is(ItemTags.ARROWS)) {
                arrows.setLeft(arrows.getLeft() + stack.getCount());
                if (stack.getItem() == Items.ARROW) arrows.setRight(true);
                else arrows.setMiddle(arrows.getMiddle() + stack.getCount());
            }
        }
    }

    @SuppressWarnings("resource")
	public static boolean hasInfinity(LocalPlayer player, ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(
                player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.INFINITY),
                stack
        ) > 0;
    }
}
