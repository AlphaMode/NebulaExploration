package alphamode.core.nebula.blocks;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.fluid.FixedFluidInv;
import alexiil.mc.lib.attributes.fluid.volume.FluidKey;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;

import net.minecraft.block.Block;

public class DefaultFluidTank extends Block {
    public DefaultFluidTank(Settings settings, int tankCount) {
        super(settings);
    }
}
