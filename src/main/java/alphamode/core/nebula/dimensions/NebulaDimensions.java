package alphamode.core.nebula.dimensions;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.gases.NebulaGases;
import com.google.common.collect.ImmutableMap;
import static alphamode.core.nebula.NebulaMod.id;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;

public class NebulaDimensions {

    public static final Atmosphere SPACE;
    public static final Atmosphere OUTTER_SPACE;
    public static final Atmosphere EARTH_ATMOSPHERE;

    static {
        SPACE = register(id("space"), id("space"),NebulaGases.EMPTY, 0);
        OUTTER_SPACE = register(id("outer_space"), id("outer_space"),NebulaGases.NITROGEN, 100);
        EARTH_ATMOSPHERE = register(id("earth"), DimensionType.OVERWORLD_ID, NebulaGases.OXYGEN, 2000, NebulaGases.NITROGEN, 1000, NebulaGases.EMPTY, 1000);
    }

    public static void init() {}


    public static Atmosphere register(Identifier id, Atmosphere atmosphere) {
        return Registry.register(NebulaRegistry.ATMOSPHERE, id, atmosphere);
    }

    public static Atmosphere register(Identifier id, Identifier dimension, GasVolume... volumes) {
        return Registry.register(NebulaRegistry.ATMOSPHERE, id, new Atmosphere(dimension, List.of(volumes)));
    }

    public static Atmosphere register(Identifier id, Identifier dimension, Object... gasses) {

        List<GasVolume> list = new ArrayList<>();

        for (int i = 0; i < gasses.length; i+=2) {
            if (!(gasses[i] instanceof Gas))
                NebulaMod.LOGGER.error("Object at " + i + " was '" + gasses[i].getClass().getSimpleName() + "' expected 'Gas'");
            else if (!(gasses[i+1] instanceof Integer))
                NebulaMod.LOGGER.error("Object at " + i + " was '" + gasses[i].getClass().getSimpleName() + "' expected 'int`");
            else
                list.add(new GasVolume((Gas)gasses[i], (Integer)gasses[i+1]));
        }

        return Registry.register(NebulaRegistry.ATMOSPHERE, id, new Atmosphere(dimension, list));
    }

}
