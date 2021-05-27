package alphamode.core.nebula;

import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.dimensions.NebulaDimensions;
import alphamode.core.nebula.fluids.NebulaFluids;
import alphamode.core.nebula.gases.NebulaGases;
import alphamode.core.nebula.items.NebulaItems;
import alphamode.core.nebula.rei.NebulaPlugin;
import alphamode.core.nebula.screen.NebulaScreens;
import alphamode.core.nebula.util.Util;
import alphamode.core.nebula.worldgen.Features.NebulaFeatures;
import me.shedaniel.rei.RoughlyEnoughItemsCore;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;

public class NebulaMod implements ModInitializer {

    public static final String MOD_ID = "nebula";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final ItemGroup SPACE_TAB = Util.createTab(id("space_misc"), () -> new ItemStack(NebulaItems.BASIC_OXYGEN_TANK));
    public static final ItemGroup SPACE_MACHINES = Util.createTab(id("space_machines"), () -> new ItemStack(NebulaItems.CONDENSER));

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    private final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(Registry.DIMENSION_KEY,id("outter_space"));

    private RegistryKey<World> LEVEL_KEY = RegistryKey.of(Registry.WORLD_KEY, DIMENSION_KEY.getValue());

    @Override
    public void onInitialize() {
        NebulaRegistry.init();
        NebulaItems.init();
        NebulaBlocks.init();
        NebulaScreens.init();
        NebulaGases.init();
        NebulaFeatures.init();
        NebulaFluids.init();
        NebulaDimensions.init();
        if(FabricLoader.getInstance().isModLoaded("roughlyenoughitems")) {
            LOGGER.info("Loading REI plugin.");
            RoughlyEnoughItemsCore.registerPlugin(new NebulaPlugin());
        }
    }
}
