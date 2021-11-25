package alphamode.core.nebula.fluids;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;

public class SimpleSingleFluidStorage extends SingleFluidStorage {

    private long maxCapacity;

    public SimpleSingleFluidStorage(long max) {
        this.maxCapacity = max;
    }

    public SimpleSingleFluidStorage(FluidVariant fluidVariant, long amount) {
        this.fluidVariant = fluidVariant;
        this.amount = amount;
        this.maxCapacity = Long.MAX_VALUE;
    }

    @Override
    protected long getCapacity(FluidVariant fluidVariant) {
        return this.maxCapacity;
    }
}
