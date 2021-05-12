package alphamode.core.nebula.blocks;

import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;

import net.minecraft.state.State;

public interface ICable<T> {
    FluidVolume[] getGases();
    boolean isParent();
}
