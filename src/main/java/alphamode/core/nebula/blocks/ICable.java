package alphamode.core.nebula.blocks;

import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;

import java.util.Map;

import net.minecraft.state.State;

public interface ICable<T> {
    Map<String,T> get();
    void add(T value);
    void add(String id,T value);
    void setParent(ICable parent);
    boolean isParent();
}
