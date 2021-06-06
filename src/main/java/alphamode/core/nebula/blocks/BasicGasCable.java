package alphamode.core.nebula.blocks;

import alphamode.core.nebula.api.Node;
import alphamode.core.nebula.blocks.entity.GasCableBlockEntity;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BasicGasCable extends BlockWithEntity {

    protected BasicGasCable(Settings settings) {
        super(settings);
        //BlockApiLookup.get()
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GasCableBlockEntity(pos,state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, NebulaBlocks.GAS_CABLE_BLOCK_ENTITY_BLOCK, GasCableBlockEntity::tick);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    /*@Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        for(Direction dir : Direction.values()) {
            if(ctx.getWorld().getBlockState(ctx.getBlockPos().offset(dir)).getBlock() instanceof Node) {

            }
        }
        return ctx.getWorld().getBlockState(ctx.getBlockPos());
    }*/

}
