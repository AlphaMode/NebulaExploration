package alphamode.core.nebula.blocks.entity;

import alphamode.core.nebula.blocks.ICable;
import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.gases.GasState;
import net.fabricmc.fabric.api.lookup.v1.custom.ApiLookupMap;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;

public class GasCableBlockEntity extends BlockEntity implements ICable<GasState>, Tickable {

    private GasState[] gasStates;

    public GasCableBlockEntity() {
        super(NebulaBlocks.GAS_CABLE_BLOCK_ENTITY_BLOCK);
    }

    @Override
    public void tick() {

    }

    @Override
    public GasState[] getStates() {
        return this.gasStates;
    }

    @Override
    public boolean isParent() {
        return false;
    }
}
