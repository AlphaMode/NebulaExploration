package alphamode.core.nebula.util;

import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.dimensions.NebulaPlanets;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.transfer.Side;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class Util {

    public static final EnumProperty<Side> SIDE_NORTH;
    public static final EnumProperty<Side> SIDE_SOUTH;
    public static final EnumProperty<Side> SIDE_EAST;
    public static final EnumProperty<Side> SIDE_WEST;
    public static final EnumProperty<Side> SIDE_UP;
    public static final EnumProperty<Side> SIDE_DOWN;
    public static final Set<EnumProperty<Side>> SIDE_DIRECTIONS;

    static {
        SIDE_NORTH = EnumProperty.of("side_north", Side.class);
        SIDE_SOUTH = EnumProperty.of("side_south", Side.class);
        SIDE_EAST = EnumProperty.of("side_east", Side.class);
        SIDE_WEST = EnumProperty.of("side_west", Side.class);
        SIDE_UP = EnumProperty.of("side_up", Side.class);
        SIDE_DOWN = EnumProperty.of("side_down", Side.class);

        HashSet<EnumProperty<Side>> temp2 = new HashSet<>();
        setConnectedSides(temp2);
        SIDE_DIRECTIONS = temp2;
    }

    private static void setConnectedSides(HashSet<EnumProperty<Side>> temp) {
        temp.add(SIDE_NORTH);
        temp.add(SIDE_SOUTH);
        temp.add(SIDE_UP);
        temp.add(SIDE_DOWN);
        temp.add(SIDE_EAST);
        temp.add(SIDE_WEST);
    }

    //Check if fabric api is installed
    public static ItemGroup createTab(Identifier id, Supplier<ItemStack> tab) {

        return FabricItemGroupBuilder.build(id, tab);
    }

    public static ItemGroup createTab(Identifier id, Supplier<ItemStack> tab, Consumer<List<ItemStack>> append) {
        return FabricItemGroupBuilder.create(id).appendItems(append).icon(tab).build();
    }

    public static String getModFromModId(String modid) {
        if (modid == null)
            return "";
        return FabricLoader.getInstance().getModContainer(modid).map(ModContainer::getMetadata).map(ModMetadata::getName).orElse(modid);
    }

    public static Text modToolTip(String modId) {
        return new LiteralText(getModFromModId(modId)).formatted(Formatting.BLUE, Formatting.ITALIC);
    }

    public static Text gasModToolTip(Gas gas) {
        return Util.modToolTip(NebulaRegistry.GAS.getId(gas).getNamespace());
    }

    public static List<GasVolume> getAtmosphereGas(World world) {
        for (Identifier id : NebulaRegistry.PLANET.getIds()) {
            if (NebulaRegistry.PLANET.get(id).dimension().equals(world.getRegistryKey().getValue())) {
                return NebulaRegistry.PLANET.get(id).atmosphereGases();
            }
        }
        //playerEntity.world.getRegistryKey().getValue().equals(NebulaRegistry.ATMOSPHERE.));
        return NebulaPlanets.SPACE.atmosphereGases();
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static BooleanProperty getFacing(Direction dir) {
        return switch (dir) {
            case UP -> Properties.UP;
            case DOWN -> Properties.DOWN;
            case NORTH -> Properties.NORTH;
            case SOUTH -> Properties.SOUTH;
            case EAST -> Properties.EAST;
            case WEST -> Properties.WEST;
        };
    }

    public static EnumProperty<Side> getSideFromDirection(Direction direction) {
        return switch (direction) {
            case UP -> SIDE_UP;
            case DOWN -> SIDE_DOWN;
            case WEST -> SIDE_WEST;
            case EAST -> SIDE_EAST;
            case NORTH -> SIDE_NORTH;
            case SOUTH -> SIDE_SOUTH;
        };
    }


}
