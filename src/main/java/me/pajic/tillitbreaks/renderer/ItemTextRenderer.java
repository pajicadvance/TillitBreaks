package me.pajic.tillitbreaks.renderer;

import me.pajic.tillitbreaks.TIB;
import me.pajic.tillitbreaks.util.TIBUtil;
import me.pajic.tillitbreaks.compat.AccessoriesCompat;
import me.pajic.tillitbreaks.compat.CompatFlags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.joml.Matrix3x2fStack;
import net.minecraft.util.ARGB;

public class ItemTextRenderer {

    public static void render(
            GuiGraphics guiGraphics,
            Minecraft minecraft,
            Matrix3x2fStack pose,
            Font font,
            ItemStack stack,
            int x,
            int y
    ) {
        if (TIBUtil.shouldRenderText(minecraft.player, stack)) {
            float scale = TIB.CONFIG.textScale.get() * 0.5F;

            pose.pushMatrix();
            pose.translate(x, y);
            pose.scale(scale, scale);

            if (shouldRenderDurability(stack)) renderDurability(guiGraphics, font, scale, stack);
            if (shouldRenderArrowCounter(stack)) renderArrowCounter(minecraft, guiGraphics, font, scale, stack);

            pose.popMatrix();
        }
    }

    private static void renderDurability(GuiGraphics guiGraphics, Font font, float scale, ItemStack stack) {
        String durability = String.valueOf(stack.getMaxDamage() - stack.getDamageValue());
        float offset = TIB.CONFIG.showDurabilityBar.get() ? 2.5F / scale : 0;
        int i = stack.getMaxDamage();
        float f = Math.max(0.0F, ((float) i - stack.getDamageValue()) / i);
        int color = TIB.CONFIG.showDurabilityNumberIfFull.get() && stack.getDamageValue() == 0 ?
                -1 : Mth.hsvToRgb(f / 3.0F, TIB.CONFIG.durabilityNumberColorSaturation.get(), 1.0F);

        guiGraphics.drawString(
                font,
                durability,
                (int) (16 / scale - font.width(durability) + (scale * 0.33F)),
                (int) (16 / scale - font.lineHeight - offset + scale),
                ARGB.opaque(color),
				TIB.CONFIG.textShadow.get()
        );
    }

    private static void renderArrowCounter(Minecraft minecraft, GuiGraphics guiGraphics, Font font, float scale, ItemStack stack) {
		//noinspection DataFlowIssue
		Inventory inventory = minecraft.player.getInventory();
        MutableTriple<Integer, Integer, Boolean> arrows = MutableTriple.of(0, 0, false);

        if (CompatFlags.ACCESSORIES_LOADED) AccessoriesCompat.addArrowsFromArrowSlots(minecraft.player, arrows);
        TIBUtil.addArrowsFromContainer(inventory, arrows);

        int arrowCounter = arrows.getLeft();
        int specialArrowCounter = arrows.getMiddle();
        boolean hasNormalArrows = arrows.getRight();
        String totalArrows = String.valueOf(arrowCounter);

        if (TIBUtil.hasInfinity(minecraft.player, stack)) {
            if (CompatFlags.INFINITY_FIX_PRESENT) {
                if (arrowCounter == 0) totalArrows = "∞";
                else if (arrowCounter > 0) {
                    if (specialArrowCounter > 0) totalArrows = "∞+" + specialArrowCounter;
                    else totalArrows = "∞";
                }
            } else if (arrowCounter > 0 && hasNormalArrows) {
                if (specialArrowCounter > 0) totalArrows = "∞+" + specialArrowCounter;
                else totalArrows = "∞";
            }
        }

        guiGraphics.drawString(
                font,
                totalArrows,
                (int) (16 / scale - font.width(totalArrows) + (scale * 0.33F)),
                (int) (0.5F / scale),
                -1,
				TIB.CONFIG.textShadow.get()
        );
    }

    private static boolean shouldRenderDurability(ItemStack stack) {
        return TIB.CONFIG.showDurabilityNumber.get() &&
                ((TIB.CONFIG.showDurabilityNumberIfFull.get() && !stack.isDamaged()) || stack.isDamaged());
    }

    private static boolean shouldRenderArrowCounter(ItemStack stack) {
        return TIB.CONFIG.showArrowCount.get() &&
                (stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem);
    }
}
