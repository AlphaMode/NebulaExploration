package alphamode.core.nebula.gases;

import alphamode.core.nebula.api.Extractable;
import alphamode.core.nebula.api.Insertable;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import org.jetbrains.annotations.NotNull;
import static alphamode.core.nebula.NebulaMod.id;

import net.minecraft.util.math.Direction;

public class GasApis {

    public static final BlockApiLookup<Insertable, @NotNull Direction> INSERTABLE =
            BlockApiLookup.get(id("gas_insertable"), Insertable.class, Direction.class);
    public static final BlockApiLookup<Extractable, @NotNull Direction> EXTRACTABLE =
            BlockApiLookup.get(id("gas_extractable"), Extractable.class, Direction.class);

    private GasApis() {
    }
}
