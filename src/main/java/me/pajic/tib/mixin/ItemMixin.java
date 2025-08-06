package me.pajic.tib.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.tib.TIBUtil;
import me.pajic.tib.config.TIBConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Item.class)
public class ItemMixin {

    @ModifyArg(
            method = "getBarColor",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;hsvToRgb(FFF)I"
            ),
            index = 1
    )
    private float modifyDurabilityColorSaturation(float saturation, @Local(argsOnly = true) ItemStack stack) {
        return TIBConfig.showDurabilityBarIfFull && stack.getDamageValue() == 0 ?
                0 : TIBConfig.durabilityBarColorSaturation;
    }

    @ModifyExpressionValue(
            method = "isBarVisible",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;isDamaged()Z"
            )
    )
    private boolean modifyBarVisibleCondition(boolean original, @Local(argsOnly = true) ItemStack stack) {
        return TIBConfig.showDurabilityBar &&
                (TIBConfig.showDurabilityBarIfFull || original) &&
                TIBUtil.shouldRenderText(Minecraft.getInstance().player, stack);
    }
}
