package alphamode.core.nebula.dimensions;

import alphamode.core.nebula.gases.Gas;
import java.util.Map;

import net.minecraft.util.Identifier;

public class Atmosphere {

    private Map<Gas, Integer> astmosphereGases;

    private final Identifier dimension;

    public Atmosphere(Identifier dimensionId,Map gases) {
        dimension = dimensionId;
        astmosphereGases = gases;
    }

        public Map<Gas, Integer> getAstmospherGases() {
        return astmosphereGases;
    }

    public Identifier getDimension() {
        return dimension;
    }
}
