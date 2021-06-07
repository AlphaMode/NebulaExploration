package alphamode.core.nebula.dimensions;

import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.gases.NebulaGases;
import com.google.common.collect.ImmutableMap;
import static alphamode.core.nebula.NebulaMod.id;

import java.util.List;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;

public class NebulaDimensions {

    public static final Atmosphere SPACE;
    public static final Atmosphere OUTTER_SPACE;
    public static final Atmosphere EARTH_ATMOSPHERE;

    public static void init() {
    }

    static {
        SPACE = Registry.register(NebulaRegistry.ATMOSPHERE, id("space"), new Atmosphere(id("space"), List.of(new GasVolume(NebulaGases.EMPTY,0))));
        OUTTER_SPACE = Registry.register(NebulaRegistry.ATMOSPHERE, id("outter_space"), new Atmosphere(id("outter_space"), List.of(new GasVolume(NebulaGases.NITROGEN, 100))));
        EARTH_ATMOSPHERE = Registry.register(NebulaRegistry.ATMOSPHERE, id("earth"),new Atmosphere(DimensionType.OVERWORLD_ID, List.of(new GasVolume(NebulaGases.OXYGEN,1001),new GasVolume(NebulaGases.NITROGEN,3027))));
    }

}
