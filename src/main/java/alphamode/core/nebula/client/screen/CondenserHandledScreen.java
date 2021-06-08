package alphamode.core.nebula.client.screen;

import alphamode.core.nebula.NebulaRegistry;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.gases.NebulaGases;
import alphamode.core.nebula.screen.CondenserScreenHandler;
import alphamode.core.nebula.util.Util;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import static alphamode.core.nebula.NebulaMod.id;
import alphamode.core.nebula.lib.client.GuiUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
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

import java.util.ArrayList;
import java.util.List;

public class CondenserHandledScreen extends HandledScreen<CondenserScreenHandler> {
    private static final Identifier TEXTURE = id("textures/gui/condenser.png");
    private static Sprite fluidSprite;

    private List<GasVolume> tankGases;
    private List<GasVolume> atmosphericGases;
    private final List<Pair<GasVolume, Integer>> tankRenderCache = new ArrayList<>();
    private final List<Pair<GasVolume, Integer>> atmosphereRenderCache = new ArrayList<>();

    //Gas storaged in millibuckets
    private final int maxAmount = 8000;

    public CondenserHandledScreen(CondenserScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        fluidSprite = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER.getStill())
                .getFluidSprites(inventory.player.world, BlockPos.ORIGIN, Fluids.WATER.getStill().getDefaultState())[0];
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = 7;//(imageWidth - font.width(title)) / 2;
        titleY = 5;

        updateTank(List.of(new GasVolume(NebulaGases.NITROGEN, 40), new GasVolume(NebulaGases.OXYGEN, 12)));
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
        renderGases(matrixStack);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrixStack, this.x, this.y, 0, 0, backgroundWidth, backgroundHeight);
    }

    public void renderGases(MatrixStack matrixStack) {
        int offset = 67;
        for (var entry : atmosphereRenderCache) {
            int height = entry.getRight();
            GuiUtil.setColorARGB(entry.getLeft().getGas().getColor());
            GuiUtil.renderTiledTextureAtlas(matrixStack, this, fluidSprite, 11, offset - height, 20, height, 0, false);
            offset -= height;
        }

        offset = 67;
        for (var entry : tankRenderCache) {
            int height = entry.getRight();
            GuiUtil.setColorARGB(entry.getLeft().getGas().getColor());
            GuiUtil.renderTiledTextureAtlas(matrixStack, this, fluidSprite, 39, offset - height, 20, height, 0, true);
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
                renderTooltip(matrixStack, entry.getLeft().getTooltip(), mouseX, mouseY);
            offset -= entry.getRight();
        }

    }

    private void renderGasTooltip(MatrixStack matrixStack, int x, int y, GasVolume gas) {
        List<Text> tooltip = new ArrayList<>();
        tooltip.add(gas.getGas().getName());
        tooltip.add(new TranslatableText("gui.nebula.concentration", gas.getAmount()).formatted(Formatting.GRAY));
        if (this.client.options.advancedItemTooltips)
            tooltip.add(new LiteralText(NebulaRegistry.GAS.getId(gas.getGas()).toString()).formatted(Formatting.DARK_GRAY));
        tooltip.add(Util.gasModToolTip(gas.getGas()));
        renderTooltip(matrixStack, tooltip, x, y);
    }

    public void updateTank(List<GasVolume> gases) {
        if (tankGases != null && tankGases.equals(gases))
            return;

        tankGases = gases;
        tankRenderCache.clear();

        for (GasVolume gas : gases)
            tankRenderCache.add(new Pair<>(gas, Math.max(2, (int) (52.0 * gas.getAmount() / maxAmount))));
    }

    public void updateAtmosphere(List<GasVolume> gases) {
        if (atmosphericGases != null && atmosphericGases.equals(gases))
            return;

        atmosphericGases = gases;
        atmosphereRenderCache.clear();

        double sumVolume = gases.stream().mapToInt(GasVolume::getAmount).sum();
        int pixelsAvailable = 52 - gases.size() * 2;

        for (GasVolume gas : gases)
            atmosphereRenderCache.add(new Pair<>(gas, (int) (gas.getAmount() / sumVolume * pixelsAvailable + 2)));
    }

}