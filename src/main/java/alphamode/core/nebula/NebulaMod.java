package alphamode.core.nebula;

import alphamode.core.nebula.api.CableNetwork;
import alphamode.core.nebula.api.NebulaID;
import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.dimensions.NebulaPlanets;
import alphamode.core.nebula.entitys.NebulaEntities;
import alphamode.core.nebula.fluids.NebulaFluids;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.NebulaGases;
import alphamode.core.nebula.items.NebulaItems;
import alphamode.core.nebula.network.NebulaNetwork;
import alphamode.core.nebula.screen.NebulaScreens;
import alphamode.core.nebula.util.Util;
import alphamode.core.nebula.worldgen.Features.NebulaFeatures;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.ComposterBlock;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

public class NebulaMod implements ModInitializer {

    //TODO: This value is only temporary
    public static final CableNetwork GLOBAL_NETWORK = new CableNetwork();

    public static final String MOD_ID = "nebula";

    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(new NebulaID("resources").toString());

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static ItemGroup SPACE_TAB = Util.createTab(new NebulaID("space_misc"), () -> new ItemStack(NebulaItems.FAN));
    public static ItemGroup SPACE_MACHINES = Util.createTab(new NebulaID("space_machines"), () -> new ItemStack(NebulaItems.CONDENSER));
    public static ItemGroup TANKS = Util.createTab(new NebulaID("tanks"), () -> new ItemStack(NebulaItems.GAS_TANK), (stacks) -> {
        for (Gas gas : NebulaRegistry.GAS) {

            ItemStack stack = new ItemStack(NebulaItems.GAS_TANK);
            LOGGER.info(gas.getName().toString());
            stack.getOrCreateNbt().putString("gas_id", NebulaRegistry.GAS.getId(gas).toString());
            if(gas != NebulaGases.EMPTY)
                stack.getNbt().putInt("gas", 1000);
            stacks.add(stack);
        }
    });

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing");
        NebulaRegistry.init();
        NebulaNetwork.init();
        NebulaItems.init();
        NebulaBlocks.init();
        NebulaScreens.init();
        NebulaGases.init();
        NebulaFeatures.init();
        NebulaFluids.init();
        NebulaPlanets.init();
        NebulaEntities.init();


        LOGGER.info("Finished Initializing");
    }
}
