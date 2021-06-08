package alphamode.core.nebula.util;

import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.dimensions.NebulaPlanets;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.GasVolume;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class Util {


    //Check if fabric api is installed
    public static ItemGroup createTab(Identifier id, Supplier<ItemStack> tab) {
        return FabricItemGroupBuilder.build(id,tab);
    }

    public static String getModFromModId(String modid) {
        if (modid == null)
            return "";
        return FabricLoader.getInstance().getModContainer(modid).map(ModContainer::getMetadata).map(ModMetadata::getName).orElse(modid);
    }

    public static Text modToolTip(String modId) {
        return new LiteralText(getModFromModId(modId)).formatted(Formatting.BLUE,Formatting.ITALIC);
    }

    public static Text gasModToolTip(Gas gas) {
        return Util.modToolTip(NebulaRegistry.GAS.getId(gas).getNamespace());
    }

    public static List<GasVolume> getAtmosphereGas(PlayerEntity playerEntity) {
        for(Identifier id: NebulaRegistry.PLANET.getIds()) {
            if(NebulaRegistry.PLANET.get(id).dimension().equals(playerEntity.world.getRegistryKey().getValue())) {
                return NebulaRegistry.PLANET.get(id).atmosphereGases();
            }
        }
        //playerEntity.world.getRegistryKey().getValue().equals(NebulaRegistry.ATMOSPHERE.));
        return NebulaPlanets.SPACE.atmosphereGases();
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

}
