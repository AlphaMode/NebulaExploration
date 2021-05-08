package alphamode.core.nebula.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.material.Fluids;

import static alphamode.core.nebula.NebulaMod.id;

public class CondenserContainerScreen extends AbstractContainerScreen<AbstractContainerMenu> {
    private static final ResourceLocation TEXTURE = id("textures/gui/condenser.png");

    public CondenserContainerScreen(AbstractContainerMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float f, int i, int j) {

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        FluidRenderHandler fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER.getSource());
        TextureAtlasSprite[] sprites = fluidRenderHandler.getFluidSprites(minecraft.level, minecraft.level == null ? null : BlockPos.ZERO, Fluids.WATER.getSource().defaultFluidState());
        renderTiledTextureAtlas(poseStack, this, sprites[0],11,16, 20, 52, 100, false);
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        minecraft.getTextureManager().bind(TEXTURE);
        blit(poseStack, x+11, y+16, 0, 166, 20, 59);
        blit(poseStack, x+39, y+16, 0, 166, 20, 59);
        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }
    public static void renderTiledTextureAtlas(PoseStack matrices, AbstractContainerScreen<?> screen, TextureAtlasSprite sprite, int x, int y, int width, int height, int depth, boolean upsideDown) {
        // start drawing sprites
        screen.minecraft.getTextureManager().bind(sprite.atlas().location());
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormat.POSITION_TEX);

        // tile vertically
        float u1 = sprite.getU0();
        float v1 = sprite.getV0();
        int spriteHeight = sprite.getHeight();
        int spriteWidth = sprite.getWidth();
        int startX = x + screen.leftPos;
        int startY = y + screen.topPos;
        do {
            int renderHeight = Math.min(spriteHeight, height);
            height -= renderHeight;
            float v2 = sprite.getV((16f * renderHeight) / spriteHeight);

            // we need to draw the quads per width too
            int x2 = startX;
            int widthLeft = width;
            Matrix4f matrix = matrices.last().pose();
            // tile horizontally
            do {
                int renderWidth = Math.min(spriteWidth, widthLeft);
                widthLeft -= renderWidth;

                float u2 = sprite.getU((16f * renderWidth) / spriteWidth);
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
        BufferUploader.end(builder);
    }

    private static void buildSquare(Matrix4f matrix, BufferBuilder builder, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2) {
        builder.vertex(matrix, x1, y2, z).uv(u1, v2).endVertex();
        builder.vertex(matrix, x2, y2, z).uv(u2, v2).endVertex();
        builder.vertex(matrix, x2, y1, z).uv(u2, v1).endVertex();
        builder.vertex(matrix, x1, y1, z).uv(u1, v1).endVertex();
    }

    @Override
    public void render(PoseStack poseStack, int x, int y, float delta) {
        //blit(poseStack, ax, ay, 0, 0, imageWidth, imageHeight);
        renderBackground(poseStack);
        super.render(poseStack, x, y, delta);
        renderTooltip(poseStack, x, y);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleLabelX = 7;//(imageWidth - font.width(title)) / 2;
        titleLabelY = 5;
    }
}
