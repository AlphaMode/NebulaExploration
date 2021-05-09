package alphamode.core.nebula;

import alphamode.core.nebula.dimensions.Atmosphere;
import alphamode.core.nebula.dimensions.NebulaDimensions;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.NebulaGases;
import com.mojang.serialization.Lifecycle;
import static alphamode.core.nebula.NebulaMod.id;

import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class NebulaRegistry {

    public static final RegistryKey<Registry<Gas>> GAS_KEY = RegistryKey.ofRegistry(id("gas"));
    public static final DefaultedRegistry<Gas> GAS = Registry.create(GAS_KEY, new DefaultedRegistry("empty",GAS_KEY, Lifecycle.stable()), () -> NebulaGases.EMPTY, Lifecycle.stable());

    public static final RegistryKey<Registry<Atmosphere>> ATMOSPHERE_KEY = RegistryKey.ofRegistry(id("atmosphere"));
    public static final DefaultedRegistry<Atmosphere> ATMOSPHERE = Registry.create(ATMOSPHERE_KEY, new DefaultedRegistry("empty",ATMOSPHERE_KEY, Lifecycle.stable()), () -> NebulaDimensions.EARTH_ATMOSPHERE, Lifecycle.stable());

    public static void init() {
    }
}
