package alphamode.core.nebula.blocks.entity;

import alexiil.mc.lib.attributes.fluid.FluidExtractable;
import alexiil.mc.lib.attributes.fluid.render.FluidVolumeRenderer;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import alphamode.core.nebula.blocks.ICable;
import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.gases.GasState;


import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

public class GasCableBlockEntity extends BlockEntity implements ICable<GasState>, Tickable {

    private boolean isParent;
    private ICable<GasState> parent;
    private Map<String, GasState> gases;

    public GasCableBlockEntity() {
        super(NebulaBlocks.GAS_CABLE_BLOCK_ENTITY_BLOCK);
        this.isParent = true;
    }

    @Override
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
    public Map<String,GasState> get() {
        return this.gases;
    }

    @Override
    public void add(GasState gasState) {
        gasState.getGas();
    }

    @Override
    public void add(String id, GasState value) {

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
