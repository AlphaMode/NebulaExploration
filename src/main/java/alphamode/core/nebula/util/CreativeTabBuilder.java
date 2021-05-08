package alphamode.core.nebula.util;

import alphamode.core.nebula.items.NebulaItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import java.util.function.Supplier;

public class CreativeTabBuilder {


    //Check if fabric api is installed
    public static ItemGroup createTab(Identifier id, Supplier<ItemStack> tab) {
        return FabricItemGroupBuilder.build(id,tab);
    }

}
