package me.pajic.tillitbreaks.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.tillitbreaks.renderer.ItemTextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(GuiGraphicsExtractor.class)
public abstract class GuiGraphicsExtractorMixin {

    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private Matrix3x2fStack pose;

    @Inject(
            method = "itemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
            at = @At("TAIL")
    )
    private void onRenderItemDecorations(Font font, ItemStack stack, int x, int y, String text, CallbackInfo ci) {
        ItemTextRenderer.render((GuiGraphicsExtractor) (Object) this, minecraft, pose, font, stack, x, y);
    }
}
