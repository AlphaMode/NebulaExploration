package alphamode.core.nebula.gases;

import alphamode.core.nebula.api.NebulaID;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class GasStorage {

    public static final BlockApiLookup<Storage<GasVariant>, Direction> SIDED =
            BlockApiLookup.get(new NebulaID("sided_gas_storage"), Storage.asClass(), Direction.class);

}
