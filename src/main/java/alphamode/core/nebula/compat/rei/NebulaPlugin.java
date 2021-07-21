package alphamode.core.nebula.compat.rei;

import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.GasVolume;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.EntryType;
import me.shedaniel.rei.api.common.entry.type.EntryTypeRegistry;
import static alphamode.core.nebula.NebulaMod.id;

public class NebulaPlugin implements REIClientPlugin {

    public static final EntryType<GasVolume> GAS = EntryType.deferred(id("gas"));

    @Override
    public String getPluginProviderName() {
        return "nebula";
    }

    @Override
    public void registerEntryTypes(EntryTypeRegistry registry) {
        registry.register(GAS, new GasEntryDefinition());
    }

    @Override
    public void registerEntries(EntryRegistry registry) {
        for (Gas gas : NebulaRegistry.GAS) {
            registry.addEntry(EntryStack.of(GAS,new GasVolume(gas, 1000)));
        }
    }
}
