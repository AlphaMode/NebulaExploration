package alphamode.core.nebula.dimensions;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.gases.NebulaGases;
import net.minecraft.world.dimension.DimensionType;

//TODO: Probably make this data-driven instead
public class NebulaPlanets {

    public static void init() {}

    public static final Planet SPACE = new Planet.Builder(NebulaMod.id("space")).register();

    public static final Planet EARTH = new Planet.Builder(DimensionType.OVERWORLD_ID)
            .radius(63)
            .atmosphere(193, NebulaGases.OXYGEN, 15, NebulaGases.NITROGEN, 5)
            .heightToTemperature(y -> 256.0 - y)
            .heightToPressure(y -> 256.0 - y)
            .register();

    public static final Planet NETHER = new Planet.Builder(DimensionType.THE_NETHER_ID)
            .atmosphere(193, NebulaGases.HYDROGEN, 10, NebulaGases.OXYGEN, 10)
            .register();

}
