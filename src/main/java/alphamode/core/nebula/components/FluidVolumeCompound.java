package alphamode.core.nebula.components;

import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import java.util.List;

public interface FluidVolumeCompound extends Component, AutoSyncedComponent {

    static <T> FluidVolumeCompound get(Object provider) {
        return NebulaComponents.GAS_COMPONENT.get(provider);
    }

    List<FluidVolume> getFluids();

    void setFluids(List<FluidVolume> gases);
}
