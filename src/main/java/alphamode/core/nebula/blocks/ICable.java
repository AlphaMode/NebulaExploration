package alphamode.core.nebula.blocks;

import net.minecraft.state.State;

public interface ICable<T> {
    State[] getStates();
    boolean isParent();
}
