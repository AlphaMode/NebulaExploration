package alphamode.core.nebula.biomes;

import alphamode.core.nebula.api.NebulaID;
import java.awt.*;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;

public class NebulaBiomes {

    public static final RegistryKey<Biome> SPACE_KEY = RegistryKey.of(Registry.BIOME_KEY, new NebulaID("space"));

    public static final Biome SPACE;

    private static Biome createSpace() {
        return new Biome.Builder()
                .category(Biome.Category.NONE)
                .scale(.5f)
                .temperature(-1f)
                .effects(new BiomeEffects.Builder()
                .skyColor(new Color(0, 0, 0).getRGB()).build())
                .generationSettings(new GenerationSettings.Builder().build())
                .build();
    }

    static {
        SPACE = Registry.register(BuiltinRegistries.BIOME, SPACE_KEY.getValue(), createSpace());
    }

    public static void init() {
    }
}
