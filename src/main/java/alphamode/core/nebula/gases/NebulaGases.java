package alphamode.core.nebula.gases;

import alphamode.core.nebula.NebulaRegistry;

import net.minecraft.util.registry.Registry;

import static alphamode.core.nebula.NebulaMod.id;
import java.awt.*;

public class NebulaGases {

    public static final Gas EMPTY;
    public static final Gas OXYGEN;
    public static final Gas NITROGEN;

    public static void init() {
    }

    static {
        EMPTY = Registry.register(NebulaRegistry.GAS, id("empty"), new Gas());
        OXYGEN = Registry.register(NebulaRegistry.GAS, id("oxygen"),new Gas());
        NITROGEN = Registry.register(NebulaRegistry.GAS, id("nitrogen"), new Gas(new Color(0xA6A6EC).getRGB()));
    }
}
