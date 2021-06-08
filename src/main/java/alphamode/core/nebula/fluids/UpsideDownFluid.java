package alphamode.core.nebula.fluids;

import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class UpsideDownFluid extends Fluid {

    private static final BooleanProperty FLOWING_UP = BooleanProperty.of("flowing_up");
    private static final IntProperty LEVEL = Properties.LEVEL_1_8;

    private final Item BUCKET;

    public UpsideDownFluid(Item bucket) {
        this.BUCKET = bucket;
    }

    public UpsideDownFluid() {
        BUCKET = null;
    }


    protected static int getBlockStateLevel(FluidState state) {
        return state.isStill() ? 0 : 8 - Math.min(state.getLevel(), 8) + (state.get(FLOWING_UP) ? 8 : 0);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
        builder.add(FLOWING_UP, LEVEL);
    }

//    public void tickFluid(WorldAccess world, BlockPos pos, FluidState fluidState, BlockState blockState) {
//        int i = fluidState.getLevel() - this.getLevelDecreasePerBlock(world);
//        if (fluidState.get(FLOWING_UP)) {
//            i = 7;
//        }
//        if (i > 0) {
//            Map<Direction, FluidState> map = this.getSpread(world, pos, blockState);
//            Iterator var7 = map.entrySet().iterator();
//
//            while (var7.hasNext()) {
//                Map.Entry<Direction, FluidState> entry = (Map.Entry) var7.next();
//                Direction direction = entry.getKey();
//                FluidState fluidState2 = entry.getValue();
//                BlockPos blockPos = pos.offset(direction);
//                BlockState blockState2 = world.getBlockState(blockPos);
//                if (this.canFlow(world, pos, blockState, direction, blockPos, blockState2, world.getFluidState(blockPos), fluidState2.getFluid())) {
//                    this.flow(world, blockPos, blockState2, direction, fluidState2);
//                }
//            }
//
//        }
//    }
//
//    private boolean checkFluid(BlockView world, Fluid fluid, BlockPos pos, BlockState state, BlockPos fromPos, BlockState fromState) {
//        if (!this.receivesFlow(Direction.UP, world, pos, state, fromPos, fromState)) {
//            return false;
//        } else {
//            return fromState.getFluidState().getFluid().matchesType(this) ? true : this.canFill(world, fromPos, fromState, fluid);
//        }
//    }
//
//    public final boolean receivesFlow(Direction face, BlockView world, BlockPos pos, BlockState state, BlockPos fromPos, BlockState fromState) {
//        Object2ByteLinkedOpenHashMap object2ByteLinkedOpenHashMap2;
//        if (!state.getBlock().hasDynamicBounds() && !fromState.getBlock().hasDynamicBounds()) {
//            object2ByteLinkedOpenHashMap2 = (Object2ByteLinkedOpenHashMap)field_15901.get();
//        } else {
//            object2ByteLinkedOpenHashMap2 = null;
//        }
//
//        Block.NeighborGroup neighborGroup2;
//        if (object2ByteLinkedOpenHashMap2 != null) {
//            neighborGroup2 = new Block.NeighborGroup(state, fromState, face);
//            byte b = object2ByteLinkedOpenHashMap2.getAndMoveToFirst(neighborGroup2);
//            if (b != 127) {
//                return b != 0;
//            }
//        } else {
//            neighborGroup2 = null;
//        }
//
//        VoxelShape voxelShape = state.getCollisionShape(world, pos);
//        VoxelShape voxelShape2 = fromState.getCollisionShape(world, fromPos);
//        boolean bl = !VoxelShapes.adjacentSidesCoverSquare(voxelShape, voxelShape2, face);
//        if (object2ByteLinkedOpenHashMap2 != null) {
//            if (object2ByteLinkedOpenHashMap2.size() == 200) {
//                object2ByteLinkedOpenHashMap2.removeLastByte();
//            }
//
//            object2ByteLinkedOpenHashMap2.putAndMoveToFirst(neighborGroup2, (byte)(bl ? 1 : 0));
//        }
//
//        return bl;
//    }
//
//    protected boolean canFlow(BlockView world, BlockPos fluidPos, BlockState fluidBlockState, Direction flowDirection, BlockPos flowTo, BlockState flowToBlockState, FluidState fluidState, Fluid fluid) {
//        return fluidState.canBeReplacedWith(world, flowTo, fluid, flowDirection) && this.receivesFlow(flowDirection, world, fluidPos, fluidBlockState, flowTo, flowToBlockState) && this.canFill(world, flowTo, flowToBlockState, fluid);
//    }
//
//    @Override
//    protected void tryFlow(WorldAccess world, BlockPos fluidPos, FluidState state) {
//        if (!state.isEmpty()) {
//            BlockState blockState = world.getBlockState(fluidPos);
//            BlockPos blockPos = fluidPos.down();
//            BlockState blockState2 = world.getBlockState(blockPos);
//            FluidState fluidState = this.getUpdatedState(world, blockPos, blockState2);
//            if (this.canFlow(world, fluidPos, blockState, Direction.DOWN, blockPos, blockState2, world.getFluidState(blockPos), fluidState.getFluid())) {
//                this.flow(world, blockPos, blockState2, Direction.DOWN, fluidState);
//                if (this.method_15740(world, fluidPos) >= 3) {
//                    this.tickFluid(world, fluidPos, state, blockState);
//                }
//            } else if (state.isStill() || !this.checkFluid(world, fluidState.getFluid(), fluidPos, blockState, blockPos, blockState2)) {
//                this.tickFluid(world, fluidPos, state, blockState);
//            }
//
//        }
//    }
//
//    @Override
//    public Item getBucketItem() {
//        return BUCKET;
//    }
//
//    @Override
//    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
//        return false;
//    }
//
//    @Override
//    protected boolean isInfinite() {
//        return false;
//    }
//
//
//    @Override
//    protected int getFlowSpeed(WorldView world) {
//        return 4;
//    }
//
//    @Override
//    protected int getLevelDecreasePerBlock(WorldView world) {
//        return 1;
//    }
//
//    @Override
//    public int getTickRate(WorldView world) {
//        return 5;
//    }
//
//    @Override
//    protected float getBlastResistance() {
//        return 0;
//    }
//
//    @Override
//    public float getHeight(FluidState state, BlockView world, BlockPos pos) {
//        return state.getFluid().matchesType(world.getFluidState(pos.up()).getFluid()) ? 1.0F : state.getHeight();
//    }
//
//    @Override
//    public float getHeight(FluidState state) {
//        return (float) state.getLevel() / 9.0F;
//    }
//
//    @Override
//    public boolean isStill(FluidState state) {
//        return false;
//    }
//
//    @Override
//    public int getLevel(FluidState state) {
//        return 16;
//    }
//
//    @Override
//    public VoxelShape getShape(FluidState state, BlockView world, BlockPos pos) {
//        return VoxelShapes.cuboid(0, 0, 0, 16, 16, 16);
//    }
}
