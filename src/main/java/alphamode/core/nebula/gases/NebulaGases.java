package alphamode.core.nebula.gases;

import alexiil.mc.lib.attributes.fluid.volume.FluidKey;
import alexiil.mc.lib.attributes.fluid.volume.FluidUnit;
import alexiil.mc.lib.attributes.fluid.volume.SimpleFluidKey;
import static alphamode.core.nebula.NebulaMod.id;

import net.minecraft.block.NetherWartBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class NebulaGases {

    public static final Gas EMPTY = register(id("empty"), new Gas());
    public static final Gas OXYGEN = register(id("oxygen"), new Gas());
    public static final Gas NITROGEN = register(id("nitrogen"), new Gas(0xFFA6A6EC));

    public static final FluidKey EMPTY_KEY = createFluidKey(EMPTY);
    public static final FluidKey OXYGEN_KEY = createFluidKey(OXYGEN);
    public static final FluidKey NITROGEN_KEY = createFluidKey(NITROGEN);

    public static void init() {}

    public static Gas register(Identifier id, Gas gas) {
        return Registry.register(Registry.FLUID, id, gas);
    }

    public static FluidKey createFluidKey(Gas gas) {
        FluidKey.FluidKeyBuilder builder = new FluidKey.FluidKeyBuilder(gas)
                .setName(gas.getName())
                .setGas()
                .setRenderColor(gas.getColor());
        return new SimpleFluidKey(builder);
    }
}
