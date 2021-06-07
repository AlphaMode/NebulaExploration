package alphamode.core.nebula.client.screen;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.gases.Gas;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.screen.CondenserScreenHandler;
import alphamode.core.nebula.util.Util;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import static alphamode.core.nebula.NebulaMod.id;
import alphamode.core.nebula.lib.client.GuiUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.resource.language.I18n;
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


    public void renderGases(MatrixStack matrixStack) {
        FluidRenderHandler fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER.getStill());
        Sprite[] sprites = fluidRenderHandler.getFluidSprites(client.world, client.world == null ? null : BlockPos.ORIGIN, Fluids.WATER.getStill().getDefaultState());
        //Atmosphere gases
        int aoffset = 67;
        int oa = 0;

        List<GasVolume> gasses = Util.getAtmosphereGas(client.player);
        int numGasses = gasses.size();
        double sumVolume = gasses.stream().mapToInt(GasVolume::getAmount).sum();
        int pixelsAvailable = 52 - numGasses*2;

        for (GasVolume gas : Util.getAtmosphereGas(client.player)) {
            int pixels = (int) Math.floor(gas.getAmount() / sumVolume * pixelsAvailable) + 2;

            //TODO: render tooltips here

            GuiUtil.setColorRGBA(gas.getGas().getColor());
            GuiUtil.renderTiledTextureAtlas(matrixStack, this, sprites[0], 11, aoffset - oa - pixels, 20, pixels, 100, true);
            oa += pixels;
        }
//        //Tank Gases
//        int boffset = 67;
//        for (GasVolume gas : tank) {
//            GuiUtil.setColorRGBA(gas.getGas().getColor());
//            GuiUtil.renderTiledTextureAtlas(matrixStack, this, sprites[0], 39, boffset - gas.getAmount(), 20, gas.getAmount(), 100, false);
//        }
    }

    private void renderTankTooltip(MatrixStack matrixStack, int x, int y) {

    }

    private void renderAtmosphericGasTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        int offset = 67;
        for (GasVolume cursed : Util.getAtmosphereGas(client.player)) {
            int checkX = mouseX - this.x;
            int checkY = mouseY - this.y;

            List<Text> UwU = new ArrayList<>();

            renderTooltip(matrixStack, UwU, mouseX, mouseY);
            int temp = Util.clamp(cursed.getAmount(), 0, 52);

            if (checkX >= 11 && checkY >= offset - temp && checkX < 11 + 20 && checkY < offset - temp + temp) {
                List<Text> tooltip = new ArrayList<>();

                tooltip.add(cursed.getGas().getName());

                tooltip.add(new TranslatableText("gui.nebula.concentration").append(": " + Util.getAtmosphereGas(client.player).get(0).getAmount() + " mB/tick").formatted(Formatting.GRAY));
                Util.appendModIdToTooltips(tooltip, NebulaMod.MOD_ID);
                renderTooltip(matrixStack, tooltip, mouseX, mouseY);
            }
            offset -= cursed.getAmount();
        }

    }

    public CondenserHandledScreen(CondenserScreenHandler abstractContainerMenu, PlayerInventory inventory, Text component) {
        super(abstractContainerMenu, inventory, component);
        tank = new ArrayList<>();

    }

    @Override
    protected void drawBackground(MatrixStack matrixStack, float delta, int x, int y) {
        int ax = (width - backgroundWidth) / 2;
        int ay = (height - backgroundHeight) / 2;
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        //client.getTextureManager().bindTexture(TEXTURE);
        RenderSystem.setShaderTexture(0, TEXTURE);
        renderGases(matrixStack);
        RenderSystem.disableDepthTest();
        drawTexture(matrixStack, ax + 11, ay + 16, 0, 166, 20, 59);
        drawTexture(matrixStack, ax + 39, ay + 16, 0, 166, 20, 59);
        drawTexture(matrixStack, ax, ay, 0, 0, backgroundWidth, backgroundHeight);
        RenderSystem.enableDepthTest();
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