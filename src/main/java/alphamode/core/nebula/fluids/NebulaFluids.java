package alphamode.core.nebula.fluids;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import static alphamode.core.nebula.NebulaMod.id;

public class NebulaFluids {

    public static final UpsideDownFluid STILL_ROCKET_FUEL;
    public static final UpsideDownFluid FLOWING_ROCKET_FUEL;



    public static void init() {
    }

    static {
        STILL_ROCKET_FUEL = null;//Registry.register(Registry.FLUID, id("rocket_fuel"), new RocketFuelFluid.Still());
        FLOWING_ROCKET_FUEL = null;//Registry.register(Registry.FLUID, id("flowing_rocket_fuel"), new RocketFuelFluid.Flowing());


    }

}
