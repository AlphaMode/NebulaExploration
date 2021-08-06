package alphamode.core.nebula.items;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.api.NebulaID;
import alphamode.core.nebula.blocks.NebulaBlocks;

import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import static alphamode.core.nebula.NebulaMod.SPACE_TAB;
import static alphamode.core.nebula.NebulaMod.id;

public class NebulaItems {
    public static final Item.Settings generalItem = new Item.Settings().maxCount(1).group(NebulaMod.SPACE_TAB);

    public static final GasTankItem GAS_TANK;
    public static final Item MUSTARD_BOMB;
    public static final Item FAN;
    public static final GasHelmet GAS_HELMET;
    public static final LaserCannon LASER_CANNON;

    //BLOCK ITEMS
    public static final BlockItem CONDENSER;
    public static final BlockItem BASIC_GAS_CABLE;
    public static final BlockItem ALUMINUM_ORE;

    //Load the class
    public static void init() {

    }
    static {
        GAS_TANK = Registry.register(Registry.ITEM, new NebulaID("gas_tank"), new GasTankItem(generalItem.group(null), 1000));
        FAN = Registry.register(Registry.ITEM,id("fan"), new Item(generalItem.maxCount(64).group(SPACE_TAB)));
        MUSTARD_BOMB = Registry.register(Registry.ITEM,id("mustard_bomb"),new MustardGasBomb(generalItem.maxCount(16)));
        GAS_HELMET = Registry.register(Registry.ITEM, new NebulaID("gas_helmet"), new GasHelmet(ArmorMaterials.NETHERITE, generalItem.maxCount(1)));
        LASER_CANNON = Registry.register(Registry.ITEM, new NebulaID("laser_cannon"), new LaserCannon(generalItem));

        CONDENSER = Registry.register(Registry.ITEM, id("condenser"), new BlockItem(NebulaBlocks.CONDENSER_BLOCK, new Item.Settings().group(NebulaMod.SPACE_MACHINES)));
        BASIC_GAS_CABLE = Registry.register(Registry.ITEM, id("basic_gas_cable"), new BlockItem(NebulaBlocks.BASIC_GAS_CABLE, new Item.Settings().group(NebulaMod.SPACE_MACHINES)));
        ALUMINUM_ORE = Registry.register(Registry.ITEM, id("aluminum_ore"), new BlockItem(NebulaBlocks.ALUMINUM_ORE, new Item.Settings().group(NebulaMod.SPACE_MACHINES)));
    }
}
