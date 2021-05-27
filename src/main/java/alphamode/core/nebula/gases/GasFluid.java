package alphamode.core.nebula.gases;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class GasFluid extends FlowableFluid {

    @Override
    protected boolean isInfinite() {
        return false;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {

    }

    @Override
    protected int getFlowSpeed(WorldView world) {
        return 10;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 0;
    }
}
