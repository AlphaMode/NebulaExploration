package alphamode.core.nebula.worldgen.Features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class LatexConfig implements FeatureConfiguration {
    public static final Codec<LatexConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.INT.fieldOf("height").orElse(5).forGetter((latexConfig) -> latexConfig.height)).apply(instance,LatexConfig::new);
    });

    public int height;

    public LatexConfig(int height) {
        this.height = height;
    }
}
