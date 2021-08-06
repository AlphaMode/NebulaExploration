package alphamode.core.nebula.compat.rei;

import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.gases.NebulaGases;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.fluid.FluidStack;
import me.shedaniel.rei.api.client.ClientHelper;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.comparison.FluidComparatorRegistry;
import me.shedaniel.rei.api.common.entry.type.EntryType;
import me.shedaniel.rei.api.common.entry.type.EntryTypeRegistry;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.fluid.FluidSupportProvider;
import me.shedaniel.rei.api.common.util.EntryStacks;
import static alphamode.core.nebula.NebulaMod.LOGGER;
import static alphamode.core.nebula.NebulaMod.id;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class NebulaPlugin implements REIClientPlugin {

    public static final EntryType<GasVolume> GAS = EntryType.deferred(id("gas"));

    @Override
    public String getPluginProviderName() {
        return "nebula";
    }

    @Override
    public void registerEntryTypes(EntryTypeRegistry registry) {
        registry.register(GAS, new GasEntryDefinition());
        registry.registerBridge(VanillaEntryTypes.ITEM, GAS, input -> {
            if(input.getIdentifier().getNamespace().equals("nebula"))
                LOGGER.info(input.getIdentifier());
            if (!input.isEmpty()) {
                return CompoundEventResult.pass();
            } else {
                return CompoundEventResult.interruptTrue(null);
            }
        });
    }

    @Override
    public void registerEntries(EntryRegistry registry) {
        for (Gas gas : NebulaRegistry.GAS) {
            registry.addEntry(EntryStack.of(GAS, new GasVolume(gas, 1000)));
        }
        for(Identifier fluid : Registry.FLUID.getIds()) {
            for(Identifier gas : NebulaRegistry.GAS.getIds()) {
                if (fluid.equals(gas)) {
                    registry.removeEntry(EntryStacks.of(Registry.FLUID.get(fluid)));
                }
            }
        }

    }

    @Override
    public void registerFluidComparators(FluidComparatorRegistry registry) {
    }

}
