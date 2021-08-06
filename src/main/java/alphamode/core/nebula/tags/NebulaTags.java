package alphamode.core.nebula.tags;

import alphamode.core.nebula.api.NebulaID;
import net.fabricmc.fabric.api.tag.TagRegistry;

import net.minecraft.block.Block;
import net.minecraft.tag.Tag;

public class NebulaTags {

    public static final Tag<Block> PIPE_CONNECTS_TO = TagRegistry.block(new NebulaID("nebula_pipe_connects_to"));
    public static final Tag<Block> GENERATOR = TagRegistry.block(new NebulaID("nebula_generator"));
    public static final Tag<Block> RECEIVER = TagRegistry.block(new NebulaID("nebula_receiver"));
    public static final Tag<Block> PIPE = TagRegistry.block(new NebulaID("nebula_valid_pipe"));
    public static void init() {

    }
}
