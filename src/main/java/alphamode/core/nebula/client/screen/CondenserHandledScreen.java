package alphamode.core.nebula.client.screen;

import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.NebulaGases;
import alphamode.core.nebula.util.Util;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import static alphamode.core.nebula.NebulaMod.id;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;

public class CondenserHandledScreen extends HandledScreen<ScreenHandler> {
    private static final Identifier TEXTURE = id("textures/gui/condenser.png");

    private void renderGasTooltip(MatrixStack matrixStack, Gas gas, int mouseX, int mouseY) {
        int offset = 67;
        for(Map.Entry<Gas, Integer> cursed:Util.getAtmosphereGas(client.player).entrySet()) {

            int checkX = mouseX - this.x;
            int checkY = mouseY - this.y;

            if(checkX >= 11 && checkY >= offset-cursed.getValue() && checkX < 11 + 20 && checkY < offset-cursed.getValue() + cursed.getValue()) {
                List<Text> tooltip = new ArrayList<>();
                tooltip.add(cursed.getKey().getName());
                //tooltip.add(new LiteralText(" "));
                String name = NebulaRegistry.GAS.getId(gas).getNamespace();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                tooltip.add(new TranslatableText("gui.nebula.concentration").append(": "+Util.getAtmosphereGas(client.player).get(cursed.getKey())+" / 52").formatted(Formatting.GRAY));
                tooltip.add(new LiteralText(name).formatted(Formatting.BLUE));
                renderTooltip(matrixStack, tooltip, mouseX, mouseY);
            }
            offset -= cursed.getValue();
        }

    }
    public static void setColorRGBA(int color) {
        float a = alpha(color) / 255.0F;
        float r = red(color) / 255.0F;
        float g = green(color) / 255.0F;
        float b = blue(color) / 255.0F;

        RenderSystem.color4f(r, g, b, a);
    }

    public static int alpha(int c) {
        return (c >> 24) & 0xFF;
    }

    public static int red(int c) {
        return (c >> 16) & 0xFF;
    }

    public static int green(int c) {
        return (c >> 8) & 0xFF;
    }

    public static int blue(int c) {
        return (c) & 0xFF;
    }

    public void renderGases(MatrixStack matrixStack,int mouseX,int mouseY) {
        FluidRenderHandler fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER.getStill());
        Sprite[] sprites = fluidRenderHandler.getFluidSprites(client.world, client.world == null ? null : BlockPos.ORIGIN, Fluids.WATER.getStill().getDefaultState());

        int offset = 67;
        for(Map.Entry<Gas, Integer> cursed:Util.getAtmosphereGas(client.player).entrySet()) {
            setColorRGBA(cursed.getKey().getColor());
            renderTiledTextureAtlas(matrixStack, this, sprites[0], 11, offset-cursed.getValue(), 20, cursed.getValue(), 100, false);
            offset -= cursed.getValue();
        }

    }

    public CondenserHandledScreen(ScreenHandler abstractContainerMenu, PlayerInventory inventory, Text component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    protected void drawBackground(MatrixStack matrixStack, float delta, int x, int y) {
        int ax = (width - backgroundWidth) / 2;
        int ay = (height - backgroundHeight) / 2;
        renderGases(matrixStack,x,y);
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        client.getTextureManager().bindTexture(TEXTURE);
        RenderSystem.disableDepthTest();
        drawTexture(matrixStack, ax + 11, ay + 16, 0, 166, 20, 59);
        drawTexture(matrixStack, ax + 39, ay + 16, 0, 166, 20, 59);
        drawTexture(matrixStack, ax, ay, 0, 0, backgroundWidth, backgroundHeight);
        RenderSystem.enableDepthTest();
    }

    //Cursed tinker's method
    private static void renderTiledTextureAtlas(MatrixStack matrices, HandledScreen<?> screen, Sprite sprite, int x, int y, int width, int height, int depth, boolean upsideDown) {
        // start drawing sprites
        screen.client.getTextureManager().bindTexture(sprite.getAtlas().getId());
        BufferBuilder builder = Tessellator.getInstance().getBuffer();
        builder.begin(GL11.GL_QUADS, VertexFormats.POSITION_TEXTURE);

        // tile vertically
        float u1 = sprite.getMinU();
        float v1 = sprite.getMinV();
        int spriteHeight = sprite.getHeight();
        int spriteWidth = sprite.getWidth();
        int startX = x + screen.x;
        int startY = y + screen.y;
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

    private static void buildSquare(Matrix4f matrix, BufferBuilder builder, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2) {
        builder.vertex(matrix, x1, y2, z).texture(u1, v2).next();
        builder.vertex(matrix, x2, y2, z).texture(u2, v2).next();
        builder.vertex(matrix, x2, y1, z).texture(u2, v1).next();
        builder.vertex(matrix, x1, y1, z).texture(u1, v1).next();
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float delta) {
        //blit(matrixStack, ax, ay, 0, 0, imageWidth, imageHeight);
        renderBackground(matrixStack);
        super.render(matrixStack, x, y, delta);

        renderGasTooltip(matrixStack, NebulaGases.OXYGEN, x, y);
        drawMouseoverTooltip(matrixStack, x, y);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = 7;//(imageWidth - font.width(title)) / 2;
        titleY = 5;
    }
}
