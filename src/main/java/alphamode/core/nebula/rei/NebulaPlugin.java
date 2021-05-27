package alphamode.core.nebula.rei;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.NebulaGases;
import me.shedaniel.rei.RoughlyEnoughItemsCore;
import me.shedaniel.rei.RoughlyEnoughItemsState;
import me.shedaniel.rei.api.BuiltinPlugin;
import me.shedaniel.rei.api.EntryRegistry;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import me.shedaniel.rei.impl.ItemEntryStack;

import static alphamode.core.nebula.NebulaMod.id;

import net.minecraft.util.Identifier;

public class NebulaPlugin implements REIPluginV0 {

    @Override
    public Identifier getPluginIdentifier() {
        return id("nebula_plugin");
    }

    @Override
    public void registerEntries(EntryRegistry entryRegistry) {
        NebulaMod.LOGGER.info("Registering Entries");
        for(Gas gas : NebulaRegistry.GAS) {
            entryRegistry.registerEntry(new GasEntryStack(gas));
        }
    }
}
