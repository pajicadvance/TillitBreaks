package schauweg.tillitbreaks.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import schauweg.tillitbreaks.config.TIBConfig;
import schauweg.tillitbreaks.config.TIBConfigManager;

@Mixin(DrawContext.class)
public class DrawContextMixin {

    @Shadow @Final private MatrixStack matrices;

    @Shadow @Final private MinecraftClient client;

    @Shadow @Final private VertexConsumerProvider.Immediate vertexConsumers;

    @Inject(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At("TAIL"))
    public void onDrawItemInSlot(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {

        TIBConfig config = TIBConfigManager.getConfig();

        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (player == null || player.currentScreenHandler instanceof CreativeInventoryScreen.CreativeScreenHandler)
            return;

        float scale = config.getTextSize() / 100F * 0.5F;

        if (stack.isDamageable()) {
            MatrixStack matrixTextInfo = new MatrixStack();
            matrixTextInfo.push();
            matrixTextInfo.multiplyPositionMatrix(matrices.peek().getPositionMatrix());
            matrixTextInfo.translate(x, y, 300.0F);
            matrixTextInfo.scale(scale, scale, 0F);
            int fontHeight = client.textRenderer.fontHeight;

            if (config.isShowDurabilityNumber()) {
                if (config.isShowDurabilityNumIfFull() && !stack.isDamaged() || stack.isDamaged()) {
                    String curDur = String.valueOf(stack.getMaxDamage() - stack.getDamage());
                    int textWidth = client.textRenderer.getWidth(curDur);
                    float barOffset = config.isShowDurabilityBar() ? 2.5F / scale : 0;

                    int color = -1;
                    if (config.isColorDurabilityNumber() && !(config.isColorDurabilityNumWhiteIfFull() && !stack.isDamaged())) {
                        color = stack.getItemBarColor();
                    }

                    client.textRenderer.draw(curDur, 16 / scale - textWidth + (scale * 0.33F), 16 / scale - fontHeight - barOffset + scale, color, false, matrixTextInfo.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.SEE_THROUGH, 0, 0);
                }
            }

            if (config.isShowArrowCount() && (stack.getItem() == Items.BOW || stack.getItem() == Items.CROSSBOW)) {

                PlayerInventory inventory = player.getInventory();
                int arrowCounter = 0;
                int specialArrowCounter = 0;
                boolean hasNormalArrows = false;
                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack is = inventory.getStack(i);
                    if (is.getItem() == Items.ARROW || is.getItem() == Items.SPECTRAL_ARROW || is.getItem() == Items.TIPPED_ARROW) {
                        arrowCounter += is.getCount();
                        if (is.getItem() == Items.ARROW) hasNormalArrows = true;
                        else specialArrowCounter += is.getCount();
                    }
                }
                String totalArrows = String.valueOf(arrowCounter);

                if (EnchantmentHelper.getLevel(player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).entryOf(Enchantments.INFINITY), stack) > 0) {
                    boolean isBowInfinityFixLoaded = FabricLoader.getInstance().isModLoaded("bowinfinityfix");
                    boolean isInfinitiesLoaded = FabricLoader.getInstance().isModLoaded("infinities");
                    if (isBowInfinityFixLoaded || isInfinitiesLoaded) {
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
                client.textRenderer.draw(totalArrows, 16 / scale - textWidth + (scale * 0.33F), 0.5F / scale, -1, false, matrixTextInfo.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.SEE_THROUGH, 0, 0);
            }
            matrixTextInfo.push();
        }
    }
}
