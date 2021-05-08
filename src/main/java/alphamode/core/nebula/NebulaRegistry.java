package alphamode.core.nebula;

import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.NebulaGases;
import com.mojang.serialization.Lifecycle;

import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import static alphamode.core.nebula.NebulaMod.id;

public class NebulaRegistry {
    public static final RegistryKey<Registry<Gas>> GAS_KEY = RegistryKey.ofRegistry(id("gas"));
    public static final DefaultedRegistry<Gas> GAS = Registry.create(GAS_KEY, new DefaultedRegistry("empty",GAS_KEY, Lifecycle.stable()), () -> NebulaGases.OXYGEN, Lifecycle.stable());
    public static void init() {
    }
}
