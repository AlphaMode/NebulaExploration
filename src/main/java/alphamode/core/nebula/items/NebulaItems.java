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

    //BLOCK ITEMS
    public static final BlockItem Condenser;

    //Load the class
    public static void init() {

    }
    static {
        BASIC_OXYGEN_TANK = Registry.register(Registry.ITEM, id("basic_oxygen_tank"), new OxygenTankItem(generalItem,100));
        MUSTARD_BOMB = Registry.register(Registry.ITEM,id("mustard_bomb"),new MustardGasBomb(generalItem));

        Condenser = Registry.register(Registry.ITEM, id("condenser"), new BlockItem(NebulaBlocks.Condenser_BLOCK, new Item.Settings().group(NebulaMod.SPACE_MACHINES)));
    }
}
