package alphamode.core.nebula.blocks;

import alphamode.core.nebula.blocks.entity.GasCableBlockEntity;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class BasicGasCable extends BlockWithEntity {

    protected BasicGasCable(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GasCableBlockEntity(pos,state);
    }
}
