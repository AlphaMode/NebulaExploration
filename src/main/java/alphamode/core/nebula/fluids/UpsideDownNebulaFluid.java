package alphamode.core.nebula.fluids;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class UpsideDownNebulaFluid extends UpsideDownFluid {
    protected Fluid still;
    protected Fluid flowing;
    protected boolean isInfinite;
    protected Item bucket;

    public UpsideDownNebulaFluid(boolean infinite) {
        this.isInfinite = infinite;
    }

    public UpsideDownNebulaFluid() {
        this.isInfinite = false;
    }

    /**
     * @return whether the given fluid an instance of this fluid
     */
    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    @Override
    public Fluid getFlowing() {
        return this.flowing;
    }

    public void setFlowing(Fluid flowing) {
        this.flowing = flowing;
    }

    @Override
    public Fluid getStill() {
        return this.still;
    }

    public void setStill(Fluid still) {
        this.still = still;
    }

    @Override
    protected boolean isInfinite() {
        return this.isInfinite;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Override
    protected int getFlowSpeed(WorldView world) {
        return 4;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }


    @Override
    public Item getBucketItem() {
        return bucket;
    }

    public void setBucket(Item bucket) {
        this.bucket = bucket;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    public int getTickRate(WorldView world) {
        return 5;
    }

    @Override
    protected float getBlastResistance() {
        return 100;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return Registry.BLOCK.get(Registry.FLUID.getId(state.getFluid())).getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
        //return still.getDefaultState().getBlockState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    public static class Still extends UpsideDownNebulaFluid {
        public Still(boolean infinite) {
            super(infinite);
        }

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }
    public static class Flowing extends UpsideDownNebulaFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }
    }
}
