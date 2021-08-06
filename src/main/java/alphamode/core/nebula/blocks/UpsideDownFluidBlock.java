package alphamode.core.nebula.blocks;

import alphamode.core.nebula.fluids.UpsideDownFluid;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;

public class UpsideDownFluidBlock extends Block implements FluidDrainable {
    public static final IntProperty LEVEL;
    protected final UpsideDownFluid fluid;
    private final List<FluidState> statesByLevel;
    public static final VoxelShape COLLISION_SHAPE;
    public static final ImmutableList<Direction> field_34006;

    public UpsideDownFluidBlock(UpsideDownFluid fluid, AbstractBlock.Settings settings) {
        super(settings);
        this.fluid = fluid;
        this.statesByLevel = Lists.newArrayList();
        this.statesByLevel.add(fluid.getStill(false));

        for (int i = 1; i < 8; ++i) {
            this.statesByLevel.add(fluid.getFlowing(8 - i, false));
        }

        this.statesByLevel.add(fluid.getFlowing(8, true));
        this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 0));
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return context.isAbove(COLLISION_SHAPE, pos, true) && state.get(LEVEL) == 0 && false ? COLLISION_SHAPE : VoxelShapes.empty();
    }

    public boolean hasRandomTicks(BlockState state) {
        return state.getFluidState().hasRandomTicks();
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        state.getFluidState().onRandomTick(world, pos, random);
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return !this.fluid.isIn(FluidTags.LAVA);
    }

    public FluidState getFluidState(BlockState state) {
        int i = state.get(LEVEL);
        return this.statesByLevel.get(Math.min(i, 8));
    }

    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.getFluidState().getFluid().matchesType(this.fluid);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        return Collections.emptyList();
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (this.receiveNeighborFluids(world, pos, state)) {
            world.getFluidTickScheduler().schedule(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }

    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.getFluidState().isStill() || neighborState.getFluidState().isStill()) {
            world.getFluidTickScheduler().schedule(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (this.receiveNeighborFluids(world, pos, state)) {
            world.getFluidTickScheduler().schedule(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }

    }

    private boolean receiveNeighborFluids(World world, BlockPos pos, BlockState state) {
        if (this.fluid.isIn(FluidTags.LAVA)) {
            boolean bl = world.getBlockState(pos.down()).isOf(Blocks.SOUL_SOIL);
            UnmodifiableIterator var5 = field_34006.iterator();

            while (var5.hasNext()) {
                Direction direction = (Direction) var5.next();
                BlockPos blockPos = pos.offset(direction.getOpposite());
                if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
                    Block block = world.getFluidState(pos).isStill() ? Blocks.OBSIDIAN : Blocks.COBBLESTONE;
                    world.setBlockState(pos, block.getDefaultState());
                    this.playExtinguishSound(world, pos);
                    return false;
                }

                if (bl && world.getBlockState(blockPos).isOf(Blocks.BLUE_ICE)) {
                    world.setBlockState(pos, Blocks.BASALT.getDefaultState());
                    this.playExtinguishSound(world, pos);
                    return false;
                }
            }
        }

        return true;
    }

    private void playExtinguishSound(WorldAccess world, BlockPos pos) {
        world.syncWorldEvent(WorldEvents.LAVA_EXTINGUISHED, pos, 0);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        if (state.get(LEVEL) == 0) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            return new ItemStack(this.fluid.getBucketItem());
        } else {
            return ItemStack.EMPTY;
        }
    }

    public Optional<SoundEvent> getBucketFillSound() {
        return this.fluid.getBucketFillSound();
    }

    static {
        LEVEL = Properties.LEVEL_15;
        COLLISION_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        field_34006 = ImmutableList.of(Direction.UP, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST);
    }
}
