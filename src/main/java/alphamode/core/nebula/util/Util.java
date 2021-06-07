package alphamode.core.nebula.util;

import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.dimensions.NebulaDimensions;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.GasVolume;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Util {


    //Check if fabric api is installed
    public static ItemGroup createTab(Identifier id, Supplier<ItemStack> tab) {
        return FabricItemGroupBuilder.build(id,tab);
    }

    public static String getModFromModId(String modid) {
        if (modid == null)
            return "";
        String s = FabricLoader.getInstance().getModContainer(modid).map(ModContainer::getMetadata).map(ModMetadata::getName).orElse(modid);
        return s;
    }

    public static List<Text> appendModIdToTooltips(List<Text> components, String modId) {
        components.add(new LiteralText(modId));
        return components;
    }

    public static List<GasVolume> getAtmosphereGas(PlayerEntity playerEntity) {
        for(Identifier id: NebulaRegistry.ATMOSPHERE.getIds()) {
            if(NebulaRegistry.ATMOSPHERE.get(id).getDimension().equals(playerEntity.world.getRegistryKey().getValue())) {
                return NebulaRegistry.ATMOSPHERE.get(id).getAstmosphereGases();
            }
        }
        //playerEntity.world.getRegistryKey().getValue().equals(NebulaRegistry.ATMOSPHERE.));
        return NebulaDimensions.SPACE.getAstmosphereGases();
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

}
