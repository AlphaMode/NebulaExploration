package alphamode.core.nebula.blocks;

import alphamode.core.nebula.blocks.entity.CondenserBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import static alphamode.core.nebula.NebulaMod.id;

public class NebulaBlocks {

    private static final AbstractBlock.Settings generalProperties = AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.f, 4.f);

    //BLOCKS
    public static final CondenserBlock Condenser_BLOCK;

    //BLOCK ENTITIES
    public static final BlockEntityType<CondenserBlockEntity> Condenser_BLOCK_ENTITY;

    public static void init() {
    }

    static {
        Condenser_BLOCK = Registry.register(Registry.BLOCK,id("condenser"),new CondenserBlock(generalProperties));
        Condenser_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,id("condenser"),BlockEntityType.Builder.create(CondenserBlockEntity::new, Condenser_BLOCK).build(null));
    }


}
