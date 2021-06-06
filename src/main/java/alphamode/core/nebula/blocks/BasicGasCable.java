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

    private static final VoxelShape NODE = Block.createCuboidShape(5, 5, 5, 10, 10, 10);
    private static final VoxelShape N_UP = Block.createCuboidShape(5.5, 5.5, 5.5, 9.5, 16, 9.5);
    private static final VoxelShape N_DOWN = Block.createCuboidShape(5.5, 0, 5.5, 9.5, 5.5, 9.5);
    private static final VoxelShape N_NORTH = Block.createCuboidShape(5.5, 5.5, 0, 9.5, 9.5, 5.5);
    private static final VoxelShape N_SOUTH = Block.createCuboidShape(5.5, 5.5, 5.5, 9.5, 9.5, 16);

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

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = getDefaultState();
        for (Direction dir : Direction.values()) {
            if (ctx.getWorld().getBlockEntity(ctx.getBlockPos().offset(dir)) instanceof Machine || ctx.getWorld().getBlockEntity(ctx.getBlockPos().offset(dir)) instanceof Node) {
                BlockState neighbor = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(dir));
                state = state.with(getFacing(dir), true);
                neighbor = neighbor.with(getFacing(dir.getOpposite()), true);
            }
        }
        return state;
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (world.getBlockEntity(neighborPos) instanceof Machine || world.getBlockEntity(neighborPos) instanceof Node)
            return state.with(getFacing(direction),true );
        return state;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape nodeShape = NODE;
        if(state.get(Properties.UP))
            nodeShape = VoxelShapes.combineAndSimplify(nodeShape, N_UP, BooleanBiFunction.OR);
        if(state.get(Properties.DOWN))
            nodeShape = VoxelShapes.combineAndSimplify(nodeShape, N_DOWN, BooleanBiFunction.OR);
        if(state.get(Properties.NORTH))
            nodeShape = VoxelShapes.combineAndSimplify(nodeShape, N_NORTH, BooleanBiFunction.OR);
        if(state.get(Properties.SOUTH))
            nodeShape = VoxelShapes.combineAndSimplify(nodeShape, N_SOUTH, BooleanBiFunction.OR);
        return nodeShape;
    }
}
