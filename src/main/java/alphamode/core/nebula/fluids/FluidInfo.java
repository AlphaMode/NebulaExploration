package alphamode.core.nebula.fluids;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;

public record FluidInfo(Fluid still, Fluid flowing, Block fluidBlock) {
}
