package alphamode.core.nebula.gases;

import alphamode.core.nebula.NebulaRegistry;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.util.registry.Registry;

import static alphamode.core.nebula.NebulaMod.id;
import java.awt.*;

public class NebulaGases {

    public static final FlowableFluid EMPTY;
    public static final FlowableFluid OXYGEN;
    public static final FlowableFluid NITROGEN;

    public static void init() {
    }

    static {
        EMPTY = Registry.register(Registry.FLUID, id("empty"), new WaterFluid.Still());
        OXYGEN = Registry.register(Registry.FLUID, id("oxygen"),new WaterFluid.Still());
        NITROGEN = Registry.register(Registry.FLUID, id("nitrogen"),new WaterFluid.Still() /*new Gas(new Color(0xA6A6EC).getRGB())*/);
    }
}
