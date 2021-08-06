package alphamode.core.nebula.gases;

import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.api.NebulaID;
import alphamode.core.nebula.fluids.NebulaFluid;
import alphamode.core.nebula.fluids.NebulaFluids;
import alphamode.core.nebula.items.GasTankItem;
import alphamode.core.nebula.items.NebulaItems;
import net.devtech.arrp.json.lang.JLang;
import static alphamode.core.nebula.NebulaMod.RESOURCE_PACK;
import static alphamode.core.nebula.NebulaMod.id;

import java.awt.*;

import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class NebulaGases {
    //REACTICE
    public static final Gas EMPTY = register(id("empty"), new Gas());
    public static final Gas OXYGEN = register(id("oxygen"), new Gas());
    public static final Gas NITROGEN = register(id("nitrogen"), new Gas(0xFFA6A6EC));
    public static final Gas HYDROGEN = register(new NebulaID("hydrogen"), new Gas(new Color(0, 255, 227).getRGB()));
    public static final Gas CHLORINE = register(new NebulaID("chlorine"), new Gas(new Color(170, 147, 2).getRGB()));
    public static final Gas FLUORINE = register(new NebulaID("fluorine"), new Gas(new Color(215, 211, 84).getRGB()));
    public static final Gas PHOSPHINE = register(new NebulaID("phosphine"), new Gas());
    //NOBLE GASES
    public static final Gas ARGON = register(new NebulaID("argon"), new Gas(new Color(203, 183, 248).getRGB()));

    //COMPOUND GASES

    public static void init() {}

    public static Gas register(Identifier id, Gas gas) {
        //Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), id.getPath()+"_gas_tank"), new GasTankItem(NebulaItems.generalItem, gas, 1000));
        Registry.register(Registry.FLUID, id, new WaterFluid.Still());
        return Registry.register(NebulaRegistry.GAS, id, gas);
    }
}
