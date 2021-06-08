package alphamode.core.nebula.util;

import alphamode.core.nebula.config.Config;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class UnitUtils {

    public static Text temperature(int temperature) {
        return switch (Config.temperatureUnit) {
            case KELVIN -> new TranslatableText("unit.temperature.kelvin", temperature + 273.15);
            case CELSIUS -> new TranslatableText("unit.temperature.celsius", temperature);
            case FAHRENHEIT -> new TranslatableText("unit.temperature.fahrenheit", temperature * 1.8 + 32);
        };
    }

    public static Text pressure(int pressure) {
        return switch (Config.pressureUnit) {
            case ATM -> new TranslatableText("unit.pressure.atm", pressure);
            case PA -> new TranslatableText("unit.pressure.pa", pressure * 101325);
            case MMHG -> new TranslatableText("unit.pressure.mmhg", pressure * 760);
            case TORR -> new TranslatableText("unit.pressure.torr", pressure * 760);
        };
    }

}
