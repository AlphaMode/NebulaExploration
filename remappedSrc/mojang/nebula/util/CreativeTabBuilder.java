package alphamode.core.nebula.util;

import alphamode.core.nebula.items.NebulaItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeTabBuilder {


    //Check if fabric api is installed
    public static CreativeModeTab createTab(ResourceLocation id, Supplier<ItemStack> tab) {
        return FabricItemGroupBuilder.build(id,tab);
    }

}
