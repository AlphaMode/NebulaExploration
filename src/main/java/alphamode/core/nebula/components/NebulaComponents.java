package alphamode.core.nebula.components;

import alphamode.core.nebula.blocks.entity.CondenserBlockEntity;
import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.block.BlockComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import static alphamode.core.nebula.NebulaMod.id;

public class NebulaComponents implements BlockComponentInitializer {
    public static final ComponentKey<FluidVolumeCompound> GAS_COMPONENT = ComponentRegistry.getOrCreate(id("gas_component"), FluidVolumeCompound.class);

    @Override
    public void registerBlockComponentFactories(BlockComponentFactoryRegistry registry) {
        registry.registerFor(CondenserBlockEntity.class, GAS_COMPONENT, FluidVolumeComponent::new);
    }
}
