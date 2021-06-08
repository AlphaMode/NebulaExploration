package alphamode.core.nebula.dimensions;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.gases.NebulaGases;
import net.minecraft.world.dimension.DimensionType;

public class NebulaPlanets {

    public static void init() {}

    public static final Planet SPACE = new Planet.Builder(NebulaMod.id("space")).register();

    public static final Planet EARTH = new Planet.Builder(DimensionType.OVERWORLD_ID)
            .radius(63)
            .atmosphere(193, NebulaGases.OXYGEN, 2000, NebulaGases.NITROGEN, 1000)
            .heightToTemperature(y -> 256.0 - y)
            .heightToPressure(y -> 256.0 - y)
            .register();

}
