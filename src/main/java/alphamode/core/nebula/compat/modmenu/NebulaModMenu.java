package alphamode.core.nebula.compat.modmenu;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.config.Config;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import net.minecraft.text.TranslatableText;

public class NebulaModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setTitle(new TranslatableText("modmenu.config.title"))
                    .setParentScreen(parent);
            ConfigCategory temp = builder.getOrCreateCategory(new TranslatableText("modmenu.config.temperature"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            //TODO: Prob change this to a translatable text later
            temp.addEntry(entryBuilder.startEnumSelector(new TranslatableText("modmenu.config.temperature.unit"), Config.TemperatureUnit.class, Config.TemperatureUnit.CELSIUS)
                    .setDefaultValue(Config.TemperatureUnit.CELSIUS)
                    //.setTooltip(new TranslatableText("modmenu.config.temperature.unit.info"))
                    .setSaveConsumer(temperatureUnit -> Config.temperatureUnit = temperatureUnit)
                    .build());
            builder.setSavingRunnable(() -> {
                NebulaMod.LOGGER.info("Saving config");
            });
            return builder.build();
        };
    }
}
