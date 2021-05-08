package alphamode.core.nebula.gases;

import alphamode.core.nebula.NebulaRegistry;

import net.minecraft.util.registry.Registry;

import static alphamode.core.nebula.NebulaMod.id;

public class NebulaGases {
    public static final Gas OXYGEN;

    public static void init() {
    }

    static {
        OXYGEN = Registry.register(NebulaRegistry.GAS, id("oxygen"),new Gas());
    }
}
