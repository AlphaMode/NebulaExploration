package alphamode.core.nebula.blocks;

import alphamode.core.nebula.blocks.entity.CondenserBlockEntity;
import alphamode.core.nebula.blocks.entity.GasCableBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

import static alphamode.core.nebula.NebulaMod.id;

public class NebulaBlocks {

    private static final FabricBlockSettings generalProperties = FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.f, 4.f);

    //BLOCKS
    public static final CondenserBlock CONDENSER_BLOCK;
    public static final BasicGasCable BASIC_GAS_CABLE;
    public static final OreBlock ALUMINUM_ORE;

    //BLOCK ENTITIES
    public static final BlockEntityType<CondenserBlockEntity> CONDENSER_BLOCK_ENTITY;
    public static final BlockEntityType<GasCableBlockEntity> GAS_CABLE_BLOCK_ENTITY_BLOCK;

    public static void init() {
    }

    static {
        CONDENSER_BLOCK = Registry.register(Registry.BLOCK,id("condenser"),new CondenserBlock(generalProperties.nonOpaque()));
        CONDENSER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,id("condenser"),BlockEntityType.Builder.create(CondenserBlockEntity::new, CONDENSER_BLOCK).build(null));
        BASIC_GAS_CABLE = Registry.register(Registry.BLOCK, id("basic_gas_cable"), new BasicGasCable(generalProperties));
        GAS_CABLE_BLOCK_ENTITY_BLOCK = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("gas_cable"), BlockEntityType.Builder.create(GasCableBlockEntity::new, BASIC_GAS_CABLE).build(null));
        ALUMINUM_ORE = Registry.register(Registry.BLOCK,id("aluminum_ore"), new OreBlock(generalProperties.strength(4.5f, 5.f).sounds(BlockSoundGroup.STONE).breakByTool(FabricToolTags.PICKAXES, 2)));
    }


}
