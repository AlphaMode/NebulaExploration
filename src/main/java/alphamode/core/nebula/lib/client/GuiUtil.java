package alphamode.core.nebula.lib.client;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

//Helper methods for gui
public class GuiUtil {
    public static void setColorARGB(int color) {
        float a = alpha(color) / 255.0F;
        float r = red(color) / 255.0F;
        float g = green(color) / 255.0F;
        float b = blue(color) / 255.0F;
        RenderSystem.setShaderColor(r, g, b, a);
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

    public static void renderTiledTextureAtlas(MatrixStack matrices, HandledScreen<?> screen, Sprite sprite, int x, int y, int width, int height, int depth, boolean upsideDown) {
        // start drawing sprites
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, sprite.getAtlas().getId());
        //screen.client.getTextureManager().bindTexture(sprite.getAtlas().getId());
        BufferBuilder builder = Tessellator.getInstance().getBuffer();
        builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

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
        //RenderSystem
        //RenderSystem.enableAlphaTest();
        BufferRenderer.draw(builder);
    }

    public static void buildSquare(Matrix4f matrix, BufferBuilder builder, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2) {
        builder.vertex(matrix, x1, y2, z).texture(u1, v2).next();
        builder.vertex(matrix, x2, y2, z).texture(u2, v2).next();
        builder.vertex(matrix, x2, y1, z).texture(u2, v1).next();
        builder.vertex(matrix, x1, y1, z).texture(u1, v1).next();
    }
}
