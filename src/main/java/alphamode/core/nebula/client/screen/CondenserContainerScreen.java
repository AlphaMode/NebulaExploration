package alphamode.core.nebula.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;

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
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        minecraft.getTextureManager().bind(TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(PoseStack poseStack, int x, int y, float delta) {
        FluidRenderHandler fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER.getSource());
        TextureAtlasSprite[] sprites = fluidRenderHandler.getFluidSprites(minecraft.level, minecraft.level == null ? null : BlockPos.ZERO, Fluids.WATER.getSource().defaultFluidState());
        minecraft.getTextureManager().bind(sprites[0].getName());
        int ax = (width - imageWidth) / 2;
        int ay = (height - imageHeight) / 2;
        blit(poseStack, ax, ay, 0, 0, imageWidth, imageHeight);
        renderBackground(poseStack);
        super.render(poseStack, x, y, delta);
        renderTooltip(poseStack, x, y);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleLabelX = (imageWidth - font.width(title)) / 2;
    }
}
