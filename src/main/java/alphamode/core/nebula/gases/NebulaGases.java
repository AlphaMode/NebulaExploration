package alphamode.core.nebula.gases;

import alphamode.core.nebula.NebulaRegistry;
import static alphamode.core.nebula.NebulaMod.id;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class NebulaGases {

    public static final Gas EMPTY = register(id("empty"), new Gas());
    public static final Gas OXYGEN = register(id("oxygen"), new Gas());
    public static final Gas NITROGEN = register(id("nitrogen"), new Gas(0xFFA6A6EC));

    public static void init() {}

    public static Gas register(Identifier id, Gas gas) {
        return Registry.register(NebulaRegistry.GAS, id, gas);
    }
}
