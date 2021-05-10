package alphamode.core.nebula.blocks;

import alphamode.core.nebula.blocks.entity.GasCableBlockEntity;

import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class BasicGasCable extends BlockWithEntity {

    protected BasicGasCable(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new GasCableBlockEntity();
    }
}
