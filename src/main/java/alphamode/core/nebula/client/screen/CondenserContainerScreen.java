package alphamode.core.nebula.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import org.lwjgl.opengl.GL11;
import static alphamode.core.nebula.NebulaMod.id;

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
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;

public class CondenserContainerScreen extends HandledScreen<ScreenHandler> {
    private static final Identifier TEXTURE = id("textures/gui/condenser.png");

    public CondenserContainerScreen(ScreenHandler abstractContainerMenu, PlayerInventory inventory, Text component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    protected void drawBackground(MatrixStack poseStack, float f, int i, int j) {

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        FluidRenderHandler fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER.getStill());
        Sprite[] sprites = fluidRenderHandler.getFluidSprites(client.world, client.world == null ? null : BlockPos.ORIGIN, Fluids.WATER.getStill().getDefaultState());
        renderTiledTextureAtlas(poseStack, this, sprites[0],11,16, 20, 52, 100, false);
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        client.getTextureManager().bindTexture(TEXTURE);
        drawTexture(poseStack, x+11, y+16, 0, 166, 20, 59);
        drawTexture(poseStack, x+39, y+16, 0, 166, 20, 59);
        drawTexture(poseStack, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }
    public static void renderTiledTextureAtlas(MatrixStack matrices, HandledScreen<?> screen, Sprite sprite, int x, int y, int width, int height, int depth, boolean upsideDown) {
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
                if(upsideDown) {
                    // FIXME: I think this causes tiling errors, look into it
                    buildSquare(matrix, builder, x2, x2 + renderWidth, startY, startY + renderHeight, depth, u1, u2, v2, v1);
                } else {
                    buildSquare(matrix, builder, x2, x2 + renderWidth, startY, startY + renderHeight, depth, u1, u2, v1, v2);
                }
                x2 += renderWidth;
            } while(widthLeft > 0);

            startY += renderHeight;
        } while(height > 0);

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
    public void render(MatrixStack poseStack, int x, int y, float delta) {
        //blit(poseStack, ax, ay, 0, 0, imageWidth, imageHeight);
        renderBackground(poseStack);
        super.render(poseStack, x, y, delta);
        drawMouseoverTooltip(poseStack, x, y);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = 7;//(imageWidth - font.width(title)) / 2;
        titleY = 5;
    }
}
