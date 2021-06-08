package alphamode.core.nebula.config;

public class Config {

    public enum TemperatureUnit { KELVIN, CELSIUS, FAHRENHEIT}
    public enum PressureUnit { ATM, PA, MMHG, TORR}

    public static TemperatureUnit temperatureUnit = TemperatureUnit.CELSIUS;
    public static PressureUnit pressureUnit = PressureUnit.ATM;

}
