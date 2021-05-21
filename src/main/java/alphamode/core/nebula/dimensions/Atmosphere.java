package alphamode.core.nebula.dimensions;

import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import alphamode.core.nebula.gases.Gas;
import java.util.Map;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;

public class Atmosphere {

    private Map<Fluid, Integer> astmosphereGases;

    private final Identifier dimension;

    public Atmosphere(Identifier dimensionId,Map gases) {
        dimension = dimensionId;
        astmosphereGases = gases;
    }

        public Map<Fluid, Integer> getAstmospherGases() {
        return astmosphereGases;
    }

    public Identifier getDimension() {
        return dimension;
    }
}
