package alphamode.core.nebula.client.gui.screens;

import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.gases.NebulaGases;
import alphamode.core.nebula.lib.client.gui.GasTankWidget;
import alphamode.core.nebula.screen.CondenserScreenHandler;
import alphamode.core.nebula.util.Util;
import com.mojang.blaze3d.systems.RenderSystem;

import static alphamode.core.nebula.NebulaMod.id;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class CondenserHandledScreen extends HandledScreen<CondenserScreenHandler> {
    private static final Identifier TEXTURE = id("textures/gui/condenser.png");

    private List<GasVolume> tankGases;
    private List<GasVolume> atmosphericGases;

    private GasTankWidget atmosphereTank;
    public GasTankWidget itemTank;

    public CondenserHandledScreen(CondenserScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = 7;//(imageWidth - font.width(title)) / 2;
        titleY = 5;

        atmosphericGases = Util.getAtmosphereGas(client.player);
        tankGases = List.of(new GasVolume(NebulaGases.NITROGEN, 40), new GasVolume(NebulaGases.OXYGEN, 12));

        atmosphereTank = new GasTankWidget(this, 11, 15, atmosphericGases, -1);
        itemTank = new GasTankWidget(this, 39, 15, tankGases, 8000);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float delta) {
        renderBackground(matrixStack);
        super.render(matrixStack, x, y, delta);
        atmosphereTank.renderTooltip(matrixStack, x, y);
        itemTank.renderTooltip(matrixStack, x, y);
        drawMouseoverTooltip(matrixStack, x, y);
    }

    @Override
    protected void drawBackground(MatrixStack matrixStack, float delta, int x, int y) {
        atmosphereTank.render(matrixStack);
        itemTank.render(matrixStack);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrixStack, this.x, this.y, 0, 0, backgroundWidth, backgroundHeight);
    }

    public void updateTank(List<GasVolume> gases) {
        tankGases = gases;
        itemTank.setGases(gases);
    }

}