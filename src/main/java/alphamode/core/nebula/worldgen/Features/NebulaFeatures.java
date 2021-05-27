package alphamode.core.nebula.worldgen.Features;

import alphamode.core.nebula.blocks.NebulaBlocks;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

import static alphamode.core.nebula.NebulaMod.id;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class NebulaFeatures {

    public static ConfiguredFeature<?,?> ALUMINUM_ORE;

    public static void init() {
        RegistryKey<ConfiguredFeature<?,?>> aluminumOreKey = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, id("aluminum_ore_overworld"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, aluminumOreKey.getValue(), ALUMINUM_ORE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,aluminumOreKey);
    }

    static {
        ALUMINUM_ORE = Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, NebulaBlocks.ALUMINUM_ORE.getDefaultState(), 5)).decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0,0,30)).repeat(10)).spreadHorizontally();
    }
}
