package me.pajic.tillitbreaks.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.tillitbreaks.renderer.ItemTextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.joml.Matrix3x2fStack;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {

    @Shadow @Final Minecraft minecraft;
    @Shadow @Final private Matrix3x2fStack pose;

    @Inject(
            method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
            at = @At("TAIL")
    )
    private void onRenderItemDecorations(Font font, ItemStack stack, int x, int y, String text, CallbackInfo ci) {
        ItemTextRenderer.render((GuiGraphics) (Object) this, minecraft, pose, font, stack, x, y);
    }
}
