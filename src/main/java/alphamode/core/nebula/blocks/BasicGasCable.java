package alphamode.core.nebula.blocks;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.api.Machine;
import alphamode.core.nebula.api.Node;
import alphamode.core.nebula.blocks.entity.GasCableBlockEntity;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class BasicGasCable extends BlockWithEntity {

    private static final VoxelShape NODE = Block.createCuboidShape(5, 5, 5, 11, 11, 11);
    private static final VoxelShape N_UP = Block.createCuboidShape(5.5, 5.5, 5.5, 10.5, 16, 10.5);
    private static final VoxelShape N_DOWN = Block.createCuboidShape(5.5, 0, 5.5, 10.5, 5.5, 10.5);
    private static final VoxelShape N_NORTH = Block.createCuboidShape(5.5, 5.5, 0, 10.5, 10.5, 5.5);
    private static final VoxelShape N_SOUTH = Block.createCuboidShape(5.5, 5.5, 5.5, 10.5, 10.5, 16);
    private static final VoxelShape N_EAST = Block.createCuboidShape(5.5, 5.5, 5.5, 16, 10.5, 10.5);
    private static final VoxelShape N_WEST = Block.createCuboidShape(0, 5.5, 5.5, 10.5, 10.5, 10.5);

    protected BasicGasCable(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(Properties.UP, false)
                .with(Properties.DOWN, false)
                .with(Properties.NORTH, false)
                .with(Properties.SOUTH, false)
                .with(Properties.EAST, false)
                .with(Properties.WEST, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.UP, Properties.DOWN, Properties.NORTH, Properties.SOUTH, Properties.EAST, Properties.WEST);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GasCableBlockEntity(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, NebulaBlocks.GAS_CABLE_BLOCK_ENTITY_BLOCK, GasCableBlockEntity::tick);
    }

    public BooleanProperty getFacing(Direction dir) {
        return switch (dir) {
            case UP -> Properties.UP;
            case DOWN -> Properties.DOWN;
            case NORTH -> Properties.NORTH;
            case SOUTH -> Properties.SOUTH;
            case EAST -> Properties.EAST;
            case WEST -> Properties.WEST;
        };
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
//        if (state.hasBlockEntity() && !state.isOf(newState.getBlock())) {
////            if(world.getBlockEntity(pos) instanceof GasCableBlockEntity) {
//            GasCableBlockEntity blockEntity = (GasCableBlockEntity) world.getBlockEntity(pos);
//            for (Direction dir : Direction.values()) {
//                BlockEntity blockEntity1 = world.getBlockEntity(pos.offset(dir));
//                if (blockEntity1 instanceof Node) {
//                    world.setBlockState(blockEntity1.getPos(), world.getBlockState(blockEntity1.getPos()).with(getFacing(dir.getOpposite()), false));
//                }
//            }
//            blockEntity.setNodeProvider(null);
//            if (blockEntity.getNetwork() != null) {
//                blockEntity.getNetwork().removeNode(blockEntity);
//            }
////            }
//            NebulaMod.LOGGER.info("destroyed");
//            world.removeBlockEntity(pos);
//        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = getDefaultState();
        for (Direction dir : Direction.values()) {
            if (ctx.getWorld().getBlockEntity(ctx.getBlockPos().offset(dir)) instanceof Machine || ctx.getWorld().getBlockEntity(ctx.getBlockPos().offset(dir)) instanceof Node) {
                state = state.with(getFacing(dir), true);
            }
        }
        return state;
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.hasBlockEntity()) {
            if (world.getBlockEntity(pos) instanceof GasCableBlockEntity) {
                GasCableBlockEntity blockEntity = (GasCableBlockEntity) world.getBlockEntity(pos);
                blockEntity.setNodeProvider(null);
                if (blockEntity.getNetwork() != null) {
                    blockEntity.getNetwork().removeNode(blockEntity);
                }
            }
        }
        if(world.getBlockEntity(neighborPos) == null) {
            return state.with(getFacing(direction), false);
        }
        if (world.getBlockEntity(neighborPos) instanceof Machine || world.getBlockEntity(neighborPos) instanceof Node)
            return state.with(getFacing(direction), true);
        return state;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape nodeShape = NODE;
        if (state.get(Properties.UP))
            nodeShape = VoxelShapes.combineAndSimplify(nodeShape, N_UP, BooleanBiFunction.OR);
        if (state.get(Properties.DOWN))
            nodeShape = VoxelShapes.combineAndSimplify(nodeShape, N_DOWN, BooleanBiFunction.OR);
        if (state.get(Properties.NORTH))
            nodeShape = VoxelShapes.combineAndSimplify(nodeShape, N_NORTH, BooleanBiFunction.OR);
        if (state.get(Properties.SOUTH))
            nodeShape = VoxelShapes.combineAndSimplify(nodeShape, N_SOUTH, BooleanBiFunction.OR);
        if (state.get(Properties.EAST))
            nodeShape = VoxelShapes.combineAndSimplify(nodeShape, N_EAST, BooleanBiFunction.OR);
        if (state.get(Properties.WEST))
            nodeShape = VoxelShapes.combineAndSimplify(nodeShape, N_WEST, BooleanBiFunction.OR);
        return nodeShape;
    }
}
