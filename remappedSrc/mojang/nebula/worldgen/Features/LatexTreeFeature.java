package alphamode.core.nebula.worldgen.Features;

import alphamode.core.nebula.worldgen.Features.config.LatexConfig;
import com.mojang.serialization.Codec;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class LatexTreeFeature extends Feature<LatexConfig> {

    public LatexTreeFeature(Codec<LatexConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(WorldGenLevel worldGenLevel, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, LatexConfig featureConfiguration) {
        return false;
    }
}
