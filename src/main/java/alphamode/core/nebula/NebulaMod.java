package alphamode.core.nebula;

import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.items.NebulaItems;
import alphamode.core.nebula.screen.NebulaScreens;
import alphamode.core.nebula.util.CreativeTabBuilder;
import net.fabricmc.api.ModInitializer;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;

public class NebulaMod implements ModInitializer {

    public static final String MOD_ID = "nebula";

    public static final CreativeModeTab SPACE_TAB = CreativeTabBuilder.createTab(id("space_misc"), () -> new ItemStack(NebulaItems.BASIC_OXYGEN_TANK));
    public static final CreativeModeTab SPACE_MACHINES = CreativeTabBuilder.createTab(id("space_machines"), () -> new ItemStack(NebulaItems.Condenser));

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MOD_ID, id);
    }

    private final ResourceKey<LevelStem> DIMENSION_KEY = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY,id("outter_space"));

    private ResourceKey<Level> LEVEL_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY, DIMENSION_KEY.location());

    @Override
    public void onInitialize() {
        NebulaItems.init();
        NebulaBlocks.init();
        NebulaScreens.init();
    }
}
