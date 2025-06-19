package schauweg.tillitbreaks.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import org.apache.commons.lang3.tuple.Triple;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import schauweg.tillitbreaks.compat.AccessorifyCompat;
import schauweg.tillitbreaks.config.TIBConfig;
import schauweg.tillitbreaks.config.TIBConfigManager;

import java.util.Optional;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {

    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract void drawText(TextRenderer textRenderer, Text text, int x, int y, int color, boolean shadow);

    @Shadow @Final private Matrix3x2fStack matrices;

    @Inject(method = "drawStackOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawStackCount(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V"))
    public void onDrawItemInSlot(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {

        TIBConfig config = TIBConfigManager.getConfig();

        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (player == null || player.currentScreenHandler instanceof CreativeInventoryScreen.CreativeScreenHandler)
            return;

        float scale = config.getTextSize() / 100F * 0.5F;

        if (stack.isDamageable()) {
            matrices.pushMatrix();
            matrices.translate(x, y);
            matrices.scale(scale, scale);
            int fontHeight = client.textRenderer.fontHeight;

            if (config.isShowDurabilityNumber()) {
                if (config.isShowDurabilityNumIfFull() && !stack.isDamaged() || stack.isDamaged()) {
                    String curDur = String.valueOf(stack.getMaxDamage() - stack.getDamage());
                    int textWidth = client.textRenderer.getWidth(curDur);
                    float barOffset = config.isShowDurabilityBar() ? 2.5F / scale : 0;

                    int color = -1;
                    if (config.isColorDurabilityNumber() && !(config.isColorDurabilityNumWhiteIfFull() && !stack.isDamaged())) {
                        color = ColorHelper.fullAlpha(stack.getItemBarColor());
                    }

                    drawText(textRenderer, Text.literal(curDur), (int)(16 / scale - textWidth + (scale * 0.33F)), (int)(16 / scale - fontHeight - barOffset + scale), color, config.isTextShadow());
                }
            }

            if (config.isShowArrowCount() && (stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem)) {

                PlayerInventory inventory = player.getInventory();
                int arrowCounter = 0;
                int specialArrowCounter = 0;
                boolean hasNormalArrows = false;
                if (FabricLoader.getInstance().isModLoaded("accessories")) {
                    if (FabricLoader.getInstance().isModLoaded("accessorify")) {
                        Optional<Triple<Integer, Integer, Boolean>> arrowData = AccessorifyCompat.readArrowSlots(player);
                        if (arrowData.isPresent()) {
                            arrowCounter += arrowData.get().getLeft();
                            specialArrowCounter += arrowData.get().getMiddle();
                            hasNormalArrows = arrowData.get().getRight();
                        }
                    }
                }
                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack is = inventory.getStack(i);
                    if (is.getItem() == Items.ARROW || is.getItem() == Items.SPECTRAL_ARROW || is.getItem() == Items.TIPPED_ARROW) {
                        arrowCounter += is.getCount();
                        if (is.getItem() == Items.ARROW) hasNormalArrows = true;
                        else specialArrowCounter += is.getCount();
                    }
                }
                String totalArrows = String.valueOf(arrowCounter);

                if (EnchantmentHelper.getLevel(player.getWorld().getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.INFINITY), stack) > 0) {
                    boolean isBowInfinityFixLoaded = FabricLoader.getInstance().isModLoaded("bowinfinityfix");
                    boolean isInfinitiesLoaded = FabricLoader.getInstance().isModLoaded("infinities");
                    boolean isReArmLoaded = FabricLoader.getInstance().isModLoaded("rearm");
                    if (isBowInfinityFixLoaded || isInfinitiesLoaded || isReArmLoaded) {
                        if (arrowCounter == 0) {
                            totalArrows = "∞";
                        }
                        else if (arrowCounter > 0) {
                            if (specialArrowCounter > 0) {
                                totalArrows = "∞+" + specialArrowCounter;
                            }
                            else {
                                totalArrows = "∞";
                            }
                        }
                    }
                    else if (arrowCounter > 0 && hasNormalArrows) {
                        if (specialArrowCounter > 0) {
                            totalArrows = "∞+" + specialArrowCounter;
                        }
                        else {
                            totalArrows = "∞";
                        }
                    }
                }
                int textWidth = client.textRenderer.getWidth(totalArrows);
                drawText(textRenderer, Text.literal(totalArrows), (int)(16 / scale - textWidth + (scale * 0.33F)), (int)(0.5F / scale), -1, config.isTextShadow());
            }
            matrices.popMatrix();
        }
    }
}
