package alphamode.core.nebula.blocks;

import alphamode.core.nebula.blocks.entity.CondenserBlockEntity;
import alphamode.core.nebula.blocks.entity.GasCableBlockEntity;
import alphamode.core.nebula.blocks.entity.WirelessTransferNode;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import static alphamode.core.nebula.NebulaMod.id;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public class NebulaBlocks {

    private static final FabricBlockSettings generalProperties = FabricBlockSettings.of(Material.STONE)
            .requiresTool()
            .strength(3.f, 4.f);

    //BLOCKS
    public static final CondenserBlock CONDENSER_BLOCK;
    public static final BasicGasCable BASIC_GAS_CABLE;
    public static final OreBlock ALUMINUM_ORE;
    public static final Block WIRELESS_FLUID_TRANSFER_NODE;

    //BLOCK ENTITIES
    public static final BlockEntityType<CondenserBlockEntity> CONDENSER_BLOCK_ENTITY;
    public static final BlockEntityType<GasCableBlockEntity> GAS_CABLE_BLOCK_ENTITY_BLOCK;
    public static final BlockEntityType<WirelessTransferNode> TRANSFER_NODE_BLOCK_ENTITY;

    public static void init() {
    }

    static {
        CONDENSER_BLOCK = Registry.register(Registry.BLOCK, id("condenser"), new CondenserBlock(generalProperties.nonOpaque().breakByTool(FabricToolTags.PICKAXES)));
        CONDENSER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("condenser"), BlockEntityType.Builder.create(CondenserBlockEntity::new, CONDENSER_BLOCK).build(null));
        BASIC_GAS_CABLE = Registry.register(Registry.BLOCK, id("basic_gas_cable"), new BasicGasCable(generalProperties));
        GAS_CABLE_BLOCK_ENTITY_BLOCK = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("gas_cable"), BlockEntityType.Builder.create(GasCableBlockEntity::new, BASIC_GAS_CABLE).build(null));
        ALUMINUM_ORE = Registry.register(Registry.BLOCK, id("aluminum_ore"), new OreBlock(generalProperties.strength(4.5f, 5.f).sounds(BlockSoundGroup.STONE).breakByTool(FabricToolTags.PICKAXES, 2)));
        WIRELESS_FLUID_TRANSFER_NODE = Registry.register(Registry.BLOCK, id("wireless_fluid_transfer_node"), new WirelessFluidNode(generalProperties));
        TRANSFER_NODE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("transfer_node"), BlockEntityType.Builder.create(WirelessTransferNode::new, WIRELESS_FLUID_TRANSFER_NODE).build(null));
    }


}
