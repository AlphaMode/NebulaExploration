package alphamode.core.nebula.client.screen;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.screen.CondenserScreenHandler;
import alphamode.core.nebula.util.Util;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import static alphamode.core.nebula.NebulaMod.id;
import alphamode.core.nebula.lib.client.GuiUtil;

import java.util.*;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.SimplePairList;

public class CondenserHandledScreen extends HandledScreen<CondenserScreenHandler> {
    private static final Identifier TEXTURE = id("textures/gui/condenser.png");

    private List<GasVolume> tankGases;
    private List<GasVolume> atmosphericGases;
    private final List<Pair<GasVolume, Integer>> tankRenderCache = new ArrayList<>();
    private final List<Pair<GasVolume, Integer>> atmosphereRenderCache = new ArrayList<>();

    //Gas storaged in millibuckets
    private final int maxAmount = 8000;

    public CondenserHandledScreen(CondenserScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = 7;//(imageWidth - font.width(title)) / 2;
        titleY = 5;

        updateTank(new ArrayList<>());
        updateAtmosphere(Util.getAtmosphereGas(client.player));
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float delta) {
        renderBackground(matrixStack);
        super.render(matrixStack, x, y, delta);
        renderGasTooltips(matrixStack, x, y);
        drawMouseoverTooltip(matrixStack, x, y);
    }

    @Override
    protected void drawBackground(MatrixStack matrixStack, float delta, int x, int y) {
        int ax = (width - backgroundWidth) / 2;
        int ay = (height - backgroundHeight) / 2;
        renderGases(matrixStack);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.disableDepthTest();
        drawTexture(matrixStack, ax + 11, ay + 16, 0, 166, 20, 59);
        drawTexture(matrixStack, ax + 39, ay + 16, 0, 166, 20, 59);
        drawTexture(matrixStack, ax, ay, 0, 0, backgroundWidth, backgroundHeight);
        RenderSystem.enableDepthTest();
    }

    public void renderGases(MatrixStack matrixStack) {
        FluidRenderHandler fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER.getStill());
        Sprite[] sprites = fluidRenderHandler.getFluidSprites(client.world, client.world == null ? null : BlockPos.ORIGIN, Fluids.WATER.getStill().getDefaultState());

        int offset = 67;
        for (var entry : atmosphereRenderCache) {
            int height = entry.getRight();
            GuiUtil.setColorRGBA(entry.getLeft().getGas().getColor());
            GuiUtil.renderTiledTextureAtlas(matrixStack, this, sprites[0], 11, offset - height, 20, height, 100, false);
            offset -= height;
        }

        offset = 67;
        for (var entry : tankRenderCache) {
            int height = entry.getRight();
            GuiUtil.setColorRGBA(entry.getLeft().getGas().getColor());
            GuiUtil.renderTiledTextureAtlas(matrixStack, this, sprites[0], 39, offset - height, 20, height, 100, true);
            offset -= entry.getRight();
        }
    }

    private void renderGasTooltips(MatrixStack matrixStack, int mouseX, int mouseY) {
        int checkX = mouseX - this.x;
        int checkY = mouseY - this.y;

        int offset = 67;
        for (var entry : atmosphereRenderCache) {
            if (checkX >= 11 && checkX <= 31 && checkY >= offset - entry.getRight() && checkY < offset)
                renderGasTooltip(matrixStack, mouseX, mouseY, entry.getLeft());
            offset -= entry.getRight();
        }

        offset = 67;
        for (var entry : tankRenderCache) {
            if (checkX >= 39 && checkX <= 59 && checkY >= offset - entry.getRight() && checkY < offset)
                renderGasTooltip(matrixStack, mouseX, mouseY, entry.getLeft());
            offset -= entry.getRight();
        }

    }

    private void renderGasTooltip(MatrixStack matrixStack, int x, int y, GasVolume gas) {
        List<Text> tooltip = new ArrayList<>();
        tooltip.add(gas.getGas().getName());
        tooltip.add(new TranslatableText("gui.nebula.concentration").append(": " + gas.getAmount() + " mB/tick").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText(NebulaMod.MOD_ID).formatted(Formatting.DARK_GRAY, Formatting.ITALIC));
        renderTooltip(matrixStack, tooltip, x, y);

    }

    public void updateTank(List<GasVolume> gases) {
        if (tankGases == null || !tankGases.equals(gases)) {
            tankGases = gases;
            updatePixelsPerGas(tankGases, tankRenderCache);
        }
    }

    public void updateAtmosphere(List<GasVolume> gases) {
        if (atmosphericGases == null || !atmosphericGases.equals(gases)) {
            atmosphericGases = gases;
            updatePixelsPerGas(atmosphericGases, atmosphereRenderCache);
        }
    }

    private void updatePixelsPerGas(List<GasVolume> gases, List<Pair<GasVolume, Integer>> renderCache) {
        renderCache.clear();
        int numGasses = gases.size();
        double sumVolume = gases.stream().mapToInt(GasVolume::getAmount).sum();
        int pixelsAvailable = 52 - numGasses*2;

        for (GasVolume gas : gases)
            renderCache.add(new Pair<>(gas, (int) (gas.getAmount() / sumVolume * pixelsAvailable + 2)));
    }




}