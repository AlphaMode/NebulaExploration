package alphamode.core.nebula.worldgen.Features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;

public class LatexConfig implements FeatureConfig {
    public static final Codec<LatexConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.INT.fieldOf("height").orElse(5).forGetter((latexConfig) -> latexConfig.height)).apply(instance,LatexConfig::new);
    });

    public int height;

    public LatexConfig(int height) {
        this.height = height;
    }
}
