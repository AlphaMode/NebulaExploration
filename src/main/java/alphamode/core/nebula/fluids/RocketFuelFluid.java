package alphamode.core.nebula.fluids;

import alphamode.core.nebula.blocks.NebulaBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public abstract class RocketFuelFluid extends UpsideDownFluid {

    public Fluid getFlowing() {
        return NebulaFluids.FLOWING_ROCKET_FUEL;
    }

    public Fluid getStill() {
        return NebulaFluids.STILL_ROCKET_FUEL;
    }

    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {

    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return NebulaBlocks.ROCKET_FUEL.getDefaultState().with(Properties.LEVEL_1_8, getBlockStateLevel(state));
    }

    public abstract static class Flowing extends RocketFuelFluid {

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public abstract static class Still extends RocketFuelFluid {

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
