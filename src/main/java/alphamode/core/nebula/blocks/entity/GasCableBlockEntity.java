package alphamode.core.nebula.blocks.entity;

import alphamode.core.nebula.blocks.ICable;
import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.gases.GasVolume;
import java.util.Map;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class GasCableBlockEntity extends BlockEntity implements ICable<GasVolume> {

    private boolean isParent;
    private ICable<GasVolume> parent;
    private Map<String, GasVolume> gases;

    public GasCableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NebulaBlocks.GAS_CABLE_BLOCK_ENTITY_BLOCK,blockPos,blockState);
        this.isParent = true;
    }

    //@Override
    public void tick() {
        for(Direction direction : Direction.values()) {
            BlockState blockState = getWorld().getBlockState(this.pos.offset(direction));
            if(blockState.getBlock() instanceof ICable) {
                if (isParent) {
                    ICable cable = (ICable) blockState.getBlock();
                    cable.setParent(this);
                }
            }
        }

    }



    @Override
    public Map<String, GasVolume> get() {
        return this.gases;
    }

    @Override
    public void add(GasVolume gasState) {
        gasState.getGas();
    }

    @Override
    public void add(String id, GasVolume value) {

    }

    @Override
    public void setParent(ICable parent) {
        this.parent = parent;
    }

    @Override
    public boolean isParent() {
        return isParent;
    }
}
