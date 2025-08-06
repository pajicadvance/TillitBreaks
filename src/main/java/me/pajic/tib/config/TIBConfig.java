package me.pajic.tib.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TIBConfig {

    public static ConfigClassHandler<TIBConfig> HANDLER = ConfigClassHandler.createBuilder(TIBConfig.class)
            .id(ResourceLocation.fromNamespaceAndPath("tillitbreaks", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("tillitbreaks.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build()
            ).build();

    @SerialEntry public static boolean showDurabilityBar = true;
    @SerialEntry public static boolean showDurabilityNumber = true;
    @SerialEntry public static boolean showDurabilityBarIfFull = false;
    @SerialEntry public static boolean showDurabilityNumberIfFull = false;
    @SerialEntry public static float durabilityBarColorSaturation = 1F;
    @SerialEntry public static float durabilityNumberColorSaturation = 1F;
    @SerialEntry public static boolean showArrowCount = true;
    @SerialEntry public static boolean textShadow = true;
    @SerialEntry public static float textScale = 1F;

    public static Screen makeScreen(Screen parentScreen) {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> builder
                .title(Component.translatable("tillitbreaks.config"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("tillitbreaks.config"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("tillitbreaks.config.showDurabilityBar"))
                                .description(OptionDescription.of(Component.translatable("tillitbreaks.config.showDurabilityBar")))
                                .binding(showDurabilityBar, () -> showDurabilityBar, newValue -> showDurabilityBar = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("tillitbreaks.config.showDurabilityNumber"))
                                .description(OptionDescription.of(Component.translatable("tillitbreaks.config.showDurabilityNumber")))
                                .binding(showDurabilityNumber, () -> showDurabilityNumber, newValue -> showDurabilityNumber = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("tillitbreaks.config.showDurabilityBarIfFull"))
                                .description(OptionDescription.of(Component.translatable("tillitbreaks.config.showDurabilityBarIfFull")))
                                .binding(showDurabilityBarIfFull, () -> showDurabilityBarIfFull, newValue -> showDurabilityBarIfFull = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("tillitbreaks.config.showDurabilityNumberIfFull"))
                                .description(OptionDescription.of(Component.translatable("tillitbreaks.config.showDurabilityNumberIfFull")))
                                .binding(showDurabilityNumberIfFull, () -> showDurabilityNumberIfFull, newValue -> showDurabilityNumberIfFull = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Component.translatable("tillitbreaks.config.durabilityBarColorSaturation"))
                                .description(OptionDescription.of(Component.translatable("tillitbreaks.config.durabilityBarColorSaturation.desc")))
                                .binding(durabilityBarColorSaturation, () -> durabilityBarColorSaturation, newValue -> durabilityBarColorSaturation = newValue)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0F, 1F).step(0.01F))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Component.translatable("tillitbreaks.config.durabilityNumberColorSaturation"))
                                .description(OptionDescription.of(Component.translatable("tillitbreaks.config.durabilityNumberColorSaturation.desc")))
                                .binding(durabilityNumberColorSaturation, () -> durabilityNumberColorSaturation, newValue -> durabilityNumberColorSaturation = newValue)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0F, 1F).step(0.01F))
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("tillitbreaks.config.showArrowCount"))
                                .description(OptionDescription.of(Component.translatable("tillitbreaks.config.showArrowCount")))
                                .binding(showArrowCount, () -> showArrowCount, newValue -> showArrowCount = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("tillitbreaks.config.textShadow"))
                                .description(OptionDescription.of(Component.translatable("tillitbreaks.config.textShadow")))
                                .binding(textShadow, () -> textShadow, newValue -> textShadow = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Component.translatable("tillitbreaks.config.textScale"))
                                .description(OptionDescription.of(Component.translatable("tillitbreaks.config.textScale.desc")))
                                .binding(textScale, () -> textScale, newValue -> textScale = newValue)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.5F, 1.5F).step(0.1F))
                                .build())
                        .build()
                )
        ).generateScreen(parentScreen);
    }
}
