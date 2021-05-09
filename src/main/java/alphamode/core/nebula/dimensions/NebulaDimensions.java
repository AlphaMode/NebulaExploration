package alphamode.core.nebula.dimensions;

import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.NebulaGases;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;

import static alphamode.core.nebula.NebulaMod.id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NebulaDimensions {

    public static final Atmosphere SPACE;
    public static final Atmosphere OUTTER_SPACE;
    public static final Atmosphere EARTH_ATMOSPHERE;

    public static void init() {
    }

    static {
        SPACE = Registry.register(NebulaRegistry.ATMOSPHERE, id("space"), new Atmosphere(id("space"), Map.of(NebulaGases.EMPTY,0)));
        OUTTER_SPACE = Registry.register(NebulaRegistry.ATMOSPHERE, id("outter_space"), new Atmosphere(id("outter_space"), Map.of(NebulaGases.NITROGEN,52)));
        EARTH_ATMOSPHERE = Registry.register(NebulaRegistry.ATMOSPHERE, id("earth"),new Atmosphere(DimensionType.OVERWORLD_ID, Map.of(NebulaGases.OXYGEN,27,NebulaGases.NITROGEN,11)));

    }

}
