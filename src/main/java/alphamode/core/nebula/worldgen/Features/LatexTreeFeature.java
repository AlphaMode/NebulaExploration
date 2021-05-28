package alphamode.core.nebula.worldgen.Features;

import alphamode.core.nebula.worldgen.Features.config.LatexConfig;
import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class LatexTreeFeature extends Feature<LatexConfig> {


    public LatexTreeFeature(Codec<LatexConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<LatexConfig> context) {
        return false;
    }


}
