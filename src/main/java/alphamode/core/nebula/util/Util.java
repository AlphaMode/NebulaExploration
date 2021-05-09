package alphamode.core.nebula.util;

import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.dimensions.Atmosphere;
import alphamode.core.nebula.dimensions.NebulaDimensions;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.NebulaGases;
import alphamode.core.nebula.items.NebulaItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;
import java.util.function.Supplier;

public class Util {


    //Check if fabric api is installed
    public static ItemGroup createTab(Identifier id, Supplier<ItemStack> tab) {
        return FabricItemGroupBuilder.build(id,tab);
    }

    public static Map<Gas,Integer> getAtmosphereGas(PlayerEntity playerEntity) {
        for(Identifier id: NebulaRegistry.ATMOSPHERE.getIds()) {
            if(NebulaRegistry.ATMOSPHERE.get(id).getDimension().equals(playerEntity.world.getRegistryKey().getValue())) {
                return NebulaRegistry.ATMOSPHERE.get(id).getAstmospherGases();
            }
        }
        //playerEntity.world.getRegistryKey().getValue().equals(NebulaRegistry.ATMOSPHERE.));
        return NebulaDimensions.SPACE.getAstmospherGases();
    }

}
