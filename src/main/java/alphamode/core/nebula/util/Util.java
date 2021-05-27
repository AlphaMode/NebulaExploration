package alphamode.core.nebula.util;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidKey;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.dimensions.NebulaDimensions;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.GasVolume;
import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.rei.api.ClientHelper;
import me.shedaniel.rei.utils.FormattingUtils;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import org.lwjgl.opengl.GL11;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public class Util {


    //Check if fabric api is installed
    public static ItemGroup createTab(Identifier id, Supplier<ItemStack> tab) {
        return FabricItemGroupBuilder.build(id,tab);
    }

    private static void buildSquare(Matrix4f matrix, BufferBuilder builder, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2) {
        builder.vertex(matrix, x1, y2, z).texture(u1, v2).next();
        builder.vertex(matrix, x2, y2, z).texture(u2, v2).next();
        builder.vertex(matrix, x2, y1, z).texture(u2, v1).next();
        builder.vertex(matrix, x1, y1, z).texture(u1, v1).next();
    }

    public static void renderTiledTextureAtlas(MatrixStack matrices, Rectangle bounds, Sprite sprite, int x, int y, int width, int height, int depth, boolean upsideDown) {
        // start drawing sprites
        MinecraftClient.getInstance().getTextureManager().bindTexture(sprite.getAtlas().getId());
        BufferBuilder builder = Tessellator.getInstance().getBuffer();
        builder.begin(GL11.GL_QUADS, VertexFormats.POSITION_TEXTURE);

        // tile vertically
        float u1 = sprite.getMinU();
        float v1 = sprite.getMinV();
        int spriteHeight = sprite.getHeight();
        int spriteWidth = sprite.getWidth();
        int startX = x + bounds.x;
        int startY = y + bounds.y;
        do {
            int renderHeight = Math.min(spriteHeight, height);
            height -= renderHeight;
            float v2 = sprite.getFrameV((16f * renderHeight) / spriteHeight);

            // we need to draw the quads per width too
            int x2 = startX;
            int widthLeft = width;
            Matrix4f matrix = matrices.peek().getModel();
            // tile horizontally
            do {
                int renderWidth = Math.min(spriteWidth, widthLeft);
                widthLeft -= renderWidth;

                float u2 = sprite.getFrameU((16f * renderWidth) / spriteWidth);
                if (upsideDown) {
                    // FIXME: I think this causes tiling errors, look into it
                    buildSquare(matrix, builder, x2, x2 + renderWidth, startY, startY + renderHeight, depth, u1, u2, v2, v1);
                } else {
                    buildSquare(matrix, builder, x2, x2 + renderWidth, startY, startY + renderHeight, depth, u1, u2, v1, v2);
                }
                x2 += renderWidth;
            } while (widthLeft > 0);

            startY += renderHeight;
        } while (height > 0);

        // finish drawing sprites
        builder.end();
        RenderSystem.enableAlphaTest();
        BufferRenderer.draw(builder);
    }

    public static String getModFromModId(String modid) {
        if (modid == null)
            return "";
        String s = FabricLoader.getInstance().getModContainer(modid).map(ModContainer::getMetadata).map(ModMetadata::getName).orElse(modid);
        return s;
    }

    public static List<FluidVolume> toFluidVolumeArray(List<GasVolume> gasVolumes) {
        List<FluidVolume> fluidsGases = new ArrayList<>();
        for(GasVolume gas : gasVolumes) {
            fluidsGases.add(gas.toFluidVolume());
        }
        return fluidsGases;
    }

    public static List<Text> appendModIdToTooltips(List<Text> components, String modId) {
        final String modName = ClientHelper.getInstance().getModFromModId(modId);
        boolean alreadyHasMod = false;
        for (Text s : components)
            if (FormattingUtils.stripFormatting(s.getString()).equalsIgnoreCase(modName)) {
                alreadyHasMod = true;
                break;
            }
        if (!alreadyHasMod)
            components.add(ClientHelper.getInstance().getFormattedModFromModId(modId));
        return components;
    }

    public static Map<Gas, Integer> getAtmosphereGas(PlayerEntity playerEntity) {
        for(Identifier id: NebulaRegistry.ATMOSPHERE.getIds()) {
            if(NebulaRegistry.ATMOSPHERE.get(id).getDimension().equals(playerEntity.world.getRegistryKey().getValue())) {
                return NebulaRegistry.ATMOSPHERE.get(id).getAstmospherGases();
            }
        }
        //playerEntity.world.getRegistryKey().getValue().equals(NebulaRegistry.ATMOSPHERE.));
        return NebulaDimensions.SPACE.getAstmospherGases();
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

}
