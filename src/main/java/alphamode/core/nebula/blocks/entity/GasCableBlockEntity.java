package alphamode.core.nebula.blocks.entity;

import alexiil.mc.lib.attributes.fluid.FluidExtractable;
import alexiil.mc.lib.attributes.fluid.render.FluidVolumeRenderer;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import alphamode.core.nebula.blocks.ICable;
import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.gases.GasState;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;

public class GasCableBlockEntity extends BlockEntity implements ICable<GasState>, Tickable, FluidExtractable {

    private FluidVolume[] gases;

    public GasCableBlockEntity() {
        super(NebulaBlocks.GAS_CABLE_BLOCK_ENTITY_BLOCK);
    }

    @Override
    public void tick() {

    }

    @Override
    public FluidVolume[] getGases() {
        return this.gases;
    }

    @Override
    public boolean isParent() {
        return false;
    }
}
