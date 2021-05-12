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
        EMPTY = Registry.register(Registry.FLUID, id("empty"), new Gas());
        OXYGEN = Registry.register(Registry.FLUID, id("oxygen"),new Gas());
        NITROGEN = Registry.register(Registry.FLUID, id("nitrogen"), new Gas(new Color(0xA6A6EC).getRGB()));
    }
}
