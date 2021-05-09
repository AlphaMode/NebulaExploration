package alphamode.core.nebula.dimensions;

import alphamode.core.nebula.gases.Gas;

import java.awt.*;
import java.util.Map;

import net.minecraft.util.Identifier;

public class Atmosphere {

    private Map<Gas,Integer> astmospherGases;

    private final Identifier dimension;

    public Atmosphere(Identifier dimensionId,Map gases) {
        dimension = dimensionId;
        astmospherGases = gases;
    }

        public Map<Gas,Integer> getAstmospherGases() {
        return astmospherGases;
    }

    public Identifier getDimension() {
        return dimension;
    }
}
