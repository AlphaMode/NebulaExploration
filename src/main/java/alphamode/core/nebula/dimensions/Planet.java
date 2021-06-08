package alphamode.core.nebula.dimensions;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.gases.NebulaGases;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public record Planet (
        Identifier dimension,
        int radius,

        int atmosphereHeight,
        List<GasVolume> atmosphereGases,

        Function<Integer,Double> heightToPressure,
        Function<Integer,Double> heightToTemperature
)   {

    public static class Builder {

        private static final Function<Integer,Double> EMPTY_FUNCTION = y -> 0.0;

        private final Identifier dimension;
        private int radius = 0;

        private int atmosphereHeight = 0;
        private final List<GasVolume> atmosphereGases = new ArrayList<>();

        private Function<Integer,Double> heightToPressure = EMPTY_FUNCTION;
        private Function<Integer,Double> heightToTemperature = EMPTY_FUNCTION;

        public Builder(Identifier dimension) {
            this.dimension = dimension;
        }

        public Builder radius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder atmosphere(int height, Object... gases) {
            atmosphereHeight = height;
            atmosphereGases.clear();
            for (int i = 0; i < gases.length; i+=2) {
                if (!(gases[i] instanceof Gas))
                    NebulaMod.LOGGER.error("Object at " + i + " was '" + gases[i].getClass().getSimpleName() + "' expected 'Gas'");
                else if (!(gases[i+1] instanceof Integer))
                    NebulaMod.LOGGER.error("Object at " + i + " was '" + gases[i].getClass().getSimpleName() + "' expected 'int`");
                else
                    atmosphereGases.add(new GasVolume((Gas)gases[i], (Integer)gases[i+1]));
            }
            return this;
        }

        public Builder heightToPressure(Function<Integer,Double> heightToPressure) {
            this.heightToPressure = heightToPressure;
            return this;
        }

        public Builder heightToTemperature(Function<Integer,Double> heightToTemperature) {
            this.heightToTemperature = heightToTemperature;
            return this;
        }

        public Planet build() {
            if (atmosphereGases.isEmpty())
                atmosphereGases.add(new GasVolume(NebulaGases.EMPTY, 0));
            return new Planet(dimension, radius, atmosphereHeight, atmosphereGases, heightToPressure, heightToTemperature);
        }

        public Planet register() {
            return Registry.register(NebulaRegistry.PLANET, dimension, build());
        }

    }


}
