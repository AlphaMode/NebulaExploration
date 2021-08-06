package alphamode.core.nebula.fluids;

import alphamode.core.nebula.api.NebulaID;
import alphamode.core.nebula.blocks.UpsideDownFluidBlock;
import alphamode.core.nebula.items.NebulaBucket;
import alphamode.core.nebula.items.NebulaItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BucketItem;
import net.minecraft.util.registry.Registry;

public class NebulaFluids {

    public static final FluidInfo ROCKET_FUEL = registerFluid("rocket_fuel", false, true);

    public static void init() {
    }

    public static FluidInfo registerFluid(String id, boolean infinite, boolean addBlock) {
        NebulaFluid still = Registry.register(Registry.FLUID, new NebulaID(id), new NebulaFluid.Still(new NebulaID(id), infinite));
        NebulaFluid flowing = Registry.register(Registry.FLUID, new NebulaID(id + "_flowing"), new NebulaFluid.Flowing(new NebulaID(id)));
        Block fluidBlock = null;
        BucketItem bucket = Registry.register(Registry.ITEM, new NebulaID(id + "_bucket"), new NebulaBucket(still, NebulaItems.generalItem));
        still.setFlowing(flowing);
        still.setStill(still);
        still.setBucket(bucket);
        flowing.setStill(still);
        flowing.setFlowing(flowing);
        flowing.setBucket(bucket);
        if (addBlock)
            fluidBlock = Registry.register(Registry.BLOCK, new NebulaID(id), new UpsideDownFluidBlock(still, FabricBlockSettings.copy(Blocks.WATER)));
        return new FluidInfo(still, flowing, fluidBlock);
    }
}
