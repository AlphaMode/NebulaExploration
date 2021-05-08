package alphamode.core.nebula;

import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.items.NebulaItems;
import alphamode.core.nebula.screen.NebulaScreens;
import alphamode.core.nebula.util.CreativeTabBuilder;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;

public class NebulaMod implements ModInitializer {

    public static final String MOD_ID = "nebula";

    public static final ItemGroup SPACE_TAB = CreativeTabBuilder.createTab(id("space_misc"), () -> new ItemStack(NebulaItems.BASIC_OXYGEN_TANK));
    public static final ItemGroup SPACE_MACHINES = CreativeTabBuilder.createTab(id("space_machines"), () -> new ItemStack(NebulaItems.Condenser));

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    private final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(Registry.DIMENSION_OPTIONS,id("outter_space"));

    private RegistryKey<World> LEVEL_KEY = RegistryKey.of(Registry.DIMENSION, DIMENSION_KEY.getValue());

    @Override
    public void onInitialize() {
        NebulaItems.init();
        NebulaBlocks.init();
        NebulaScreens.init();
    }
}
