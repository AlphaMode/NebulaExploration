package alphamode.core.nebula.blocks;

import alphamode.core.nebula.blocks.entity.CondenserBlockEntity;
import static alphamode.core.nebula.NebulaMod.id;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class NebulaBlocks {

    private static final BlockBehaviour.Properties generalProperties = BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.f, 4.f);

    //BLOCKS
    public static final CondenserBlock Condenser_BLOCK;

    //BLOCK ENTITIES
    public static final BlockEntityType<CondenserBlockEntity> Condenser_BLOCK_ENTITY;

    public static void init() {
    }

    static {
        Condenser_BLOCK = Registry.register(Registry.BLOCK,id("condenser"),new CondenserBlock(generalProperties));
        Condenser_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,id("condenser"),BlockEntityType.Builder.of(CondenserBlockEntity::new, Condenser_BLOCK).build(null));
    }


}
