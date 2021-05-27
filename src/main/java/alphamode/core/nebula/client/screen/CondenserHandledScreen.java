package alphamode.core.nebula.client.screen;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.screen.CondenserScreenHandler;
import alphamode.core.nebula.util.Util;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import org.lwjgl.opengl.GL11;
import static alphamode.core.nebula.NebulaMod.id;
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
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;

public class CondenserHandledScreen extends HandledScreen<CondenserScreenHandler> {
    private static final Identifier TEXTURE = id("textures/gui/condenser.png");
    private List<GasVolume> tank;
    //Gas storaged in millibuckets
    private final int maxAmount = 8000;

    public void updateTank(List<GasVolume> gases) {
        this.tank = gases;
    }

    /**
     * Calculate the rendering heights for all the liquids
     *
     * @param liquids  The liquids
     * @param capacity Max capacity of smeltery, to calculate how much height one liquid takes up
     * @param height   Maximum height, basically represents how much height full capacity is
     * @param min      Minimum amount of height for a fluid. A fluid can never have less than this value height returned
     * @return Array with heights corresponding to input-list liquids
     */
    public static int[] calcLiquidHeights(List<FluidVolume> liquids, FluidAmount capacity, int height, int min) {
        int[] fluidHeights = new int[liquids.size()];

        FluidAmount totalFluidAmount = FluidAmount.ZERO;
        if (liquids.size() > 0) {
            for(int i = 0; i < liquids.size(); i++) {
                FluidVolume liquid = liquids.get(i);

                float h = (float) liquid.getAmount_F().div(capacity).asInexactDouble();
                totalFluidAmount.add(liquid.amount());
                fluidHeights[i] = Math.max(min, (int) Math.ceil(h * (float) height));
            }

            // if not completely full, leave a few pixels for the empty tank display
            if(totalFluidAmount.isLessThan(capacity)) {
                height -= min;
            }

            // check if we have enough height to render everything, if not remove pixels from the tallest liquid
            int sum;
            do {
                sum = 0;
                int biggest = -1;
                int m = 0;
                for(int i = 0; i < fluidHeights.length; i++) {
                    sum += fluidHeights[i];
                    if(fluidHeights[i] > biggest) {
                        biggest = fluidHeights[i];
                        m = i;
                    }
                }

                // we can't get a result without going negative
                if(fluidHeights[m] == 0) {
                    break;
                }

                // remove a pixel from the biggest one
                if(sum > height) {
                    fluidHeights[m]--;
                }
            } while(sum > height);
        }

        return fluidHeights;
    }

    public void renderGases(MatrixStack matrixStack) {
        FluidRenderHandler fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER.getStill());
        Sprite[] sprites = fluidRenderHandler.getFluidSprites(client.world, client.world == null ? null : BlockPos.ORIGIN, Fluids.WATER.getStill().getDefaultState());
        //Atmosphere gases
        int aoffset = 67;
        int oa = 0;
        List<FluidVolume> gases = new ArrayList<>();
        for (Map.Entry<Gas, Integer> cursed : Util.getAtmosphereGas(client.player).entrySet()) {
            gases.add(new GasVolume(cursed.getKey(), cursed.getValue()).toFluidVolume());
            setColorRGBA(cursed.getKey().getColor());
            //oa =+ calcGasHeight(cursed.getValue(),oa);


            aoffset -= cursed.getValue();
        }
        for(int gas: calcLiquidHeights(gases,FluidAmount.ofWhole(8000),52,0)) {
            //NebulaMod.LOGGER.info(gas);
            renderTiledTextureAtlas(matrixStack, this, sprites[0], 11, aoffset - gas, 20, gas, 100, false);
        }
        //Tank Gases
        int boffset = 67;
        for (GasVolume gas : tank) {
            setColorRGBA(gas.getGas().getColor());
            renderTiledTextureAtlas(matrixStack, this, sprites[0], 39, boffset - gas.getAmount(), 20, gas.getAmount(), 100, false);
        }
    }

    private void renderTankTooltip(MatrixStack matrixStack, int x, int y) {

    }

    private void renderAtmosphericGasTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        int offset = 67;
        for (Map.Entry<Gas, Integer> cursed : Util.getAtmosphereGas(client.player).entrySet()) {
            int checkX = mouseX - this.x;
            int checkY = mouseY - this.y;

            List<Text> UwU = new ArrayList<>();

            renderTooltip(matrixStack, UwU, mouseX, mouseY);
            int temp = Util.clamp(cursed.getValue(), 0, 52);

            if (checkX >= 11 && checkY >= offset - temp && checkX < 11 + 20 && checkY < offset - temp + temp) {
                List<Text> tooltip = new ArrayList<>();

                tooltip.add(cursed.getKey().getName());

                tooltip.add(new TranslatableText("gui.nebula.concentration").append(": " + Util.getAtmosphereGas(client.player).get(cursed.getKey()) + " mB/tick").formatted(Formatting.GRAY));
                Util.appendModIdToTooltips(tooltip, NebulaMod.MOD_ID);
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


    public CondenserHandledScreen(CondenserScreenHandler abstractContainerMenu, PlayerInventory inventory, Text component) {
        super(abstractContainerMenu, inventory, component);
        tank = new ArrayList<>();

    }

    @Override
    protected void drawBackground(MatrixStack matrixStack, float delta, int x, int y) {
        int ax = (width - backgroundWidth) / 2;
        int ay = (height - backgroundHeight) / 2;
        renderGases(matrixStack);
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
        renderTankTooltip(matrixStack, x, y);
        renderAtmosphericGasTooltip(matrixStack, x, y);
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
