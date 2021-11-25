package alphamode.core.nebula.fluids;

import alphamode.core.nebula.api.NebulaID;
import alphamode.core.nebula.blocks.UpsideDownFluidBlock;
import alphamode.core.nebula.items.NebulaBucket;
import alphamode.core.nebula.items.NebulaItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class NebulaFluids {

    public static final FluidInfo ROCKET_FUEL = registerFluid("rocket_fuel", false, true, true);

    public static void init() {
    }

    public static FluidInfo registerFluid(String id, boolean infinite, boolean addBlock, boolean upsideDown) {
        Fluid still;
        Fluid flowing;
        if(upsideDown) {
            still = Registry.register(Registry.FLUID, new NebulaID(id), new UpsideDownNebulaFluid.Still(infinite));
            flowing = Registry.register(Registry.FLUID, new NebulaID(id + "_flowing"), new UpsideDownNebulaFluid.Flowing());
        } else {
            still = Registry.register(Registry.FLUID, new NebulaID(id), new NebulaFluid.Still(infinite));
            flowing = Registry.register(Registry.FLUID, new NebulaID(id + "_flowing"), new NebulaFluid.Flowing());
        }

        Block fluidBlock = null;
        BucketItem bucket = Registry.register(Registry.ITEM, new NebulaID(id + "_bucket"), new NebulaBucket(still, NebulaItems.generalItem));
        try {
            still.getClass().getMethod("setFlowing", Fluid.class).invoke(still, flowing);
            still.getClass().getMethod("setStill", Fluid.class).invoke(still, still);
            still.getClass().getMethod("setBucket", Item.class).invoke(still, bucket);
            flowing.getClass().getMethod("setFlowing", Fluid.class).invoke(flowing, flowing);
            flowing.getClass().getMethod("setStill", Fluid.class).invoke(flowing, still);
            flowing.getClass().getMethod("setBucket", Item.class).invoke(flowing, bucket);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        still.setFlowing(flowing);
//        still.setStill(still);
//        still.setBucket(bucket);
//        flowing.setStill(still);
//        flowing.setFlowing(flowing);
//        flowing.setBucket(bucket);
        if (addBlock)
            if(upsideDown)
                fluidBlock = Registry.register(Registry.BLOCK, new NebulaID(id), new UpsideDownFluidBlock((UpsideDownFluid) still, FabricBlockSettings.copy(Blocks.WATER)));
            else
                fluidBlock = Registry.register(Registry.BLOCK, new NebulaID(id), new FluidBlock((FlowableFluid) still, FabricBlockSettings.copy(Blocks.WATER)));
        return new FluidInfo(still, flowing, fluidBlock);
    }
}
