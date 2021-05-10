package alphamode.core.nebula.items;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.blocks.NebulaBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import static alphamode.core.nebula.NebulaMod.id;

public class NebulaItems {
    private static final Item.Settings generalItem = new Item.Settings().maxCount(1).group(NebulaMod.SPACE_TAB);

    public static final OxygenTankItem BASIC_OXYGEN_TANK;
    public static final Item MUSTARD_BOMB;
    public static final Item FAN;

    //BLOCK ITEMS
    public static final BlockItem CONDENSER;
    public static final BlockItem BASIC_GAS_CABLE;
    public static final BlockItem ALUMINUM_ORE;

    //Load the class
    public static void init() {

    }
    static {
        BASIC_OXYGEN_TANK = Registry.register(Registry.ITEM, id("basic_oxygen_tank"), new OxygenTankItem(generalItem,100));
        FAN = Registry.register(Registry.ITEM,id("fan"), new Item(generalItem.maxCount(64)));
        MUSTARD_BOMB = Registry.register(Registry.ITEM,id("mustard_bomb"),new MustardGasBomb(generalItem.maxCount(16)));

        CONDENSER = Registry.register(Registry.ITEM, id("condenser"), new BlockItem(NebulaBlocks.CONDENSER_BLOCK, new Item.Settings().group(NebulaMod.SPACE_MACHINES)));
        BASIC_GAS_CABLE = Registry.register(Registry.ITEM, id("basic_gas_cable"), new BlockItem(NebulaBlocks.BASIC_GAS_CABLE, new Item.Settings().group(NebulaMod.SPACE_MACHINES)));
        ALUMINUM_ORE = Registry.register(Registry.ITEM, id("aluminum_ore"), new BlockItem(NebulaBlocks.ALUMINUM_ORE, new Item.Settings().group(NebulaMod.SPACE_MACHINES)));
    }
}
