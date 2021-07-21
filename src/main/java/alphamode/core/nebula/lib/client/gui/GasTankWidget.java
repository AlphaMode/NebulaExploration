package alphamode.core.nebula.lib.client.gui;

import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.lib.client.GuiUtil;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class GasTankWidget extends Widget {

    private static Sprite fluidSprite = null;

    private final int x, y;
    private List<GasVolume> gases;
    private int maxVolume;
    private final HandledScreen<?> screen;
    private final List<Pair<GasVolume, Integer>> renderCache = new ArrayList<>();

    public GasTankWidget(HandledScreen<?> screen, int x, int y, List<GasVolume> gases, int maxVolume) {
        super(screen.x + x, screen.y + y);
        this.screen = screen;
        this.x = screen.x + x;
        this.y = screen.y + y;

        this.gases = gases;
        this.maxVolume = maxVolume;

        update();

        if (fluidSprite == null)
            fluidSprite = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER.getStill())
                    .getFluidSprites(client.player.world, BlockPos.ORIGIN, Fluids.WATER.getStill().getDefaultState())[0];
    }

    public void render(MatrixStack matrixStack) {
        int offset = y + 52; //67;
        for (var entry : renderCache) {
            int height = entry.getRight();
            GuiUtil.setColorARGB(entry.getLeft().getGas().getColor());
            GuiUtil.renderTiledTextureAtlas(matrixStack, fluidSprite, x, offset - height, 20, height, 0);
            offset -= height;
        }
    }

    public void renderTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        int offset = y + 52;
        for (var entry : renderCache) {
            if (mouseX >= x && mouseX <= x + 20 && mouseY >= offset - entry.getRight() && mouseY < offset)
                screen.renderTooltip(matrixStack, entry.getLeft().getTooltip(), mouseX, mouseY);
            offset -= entry.getRight();
        }

    }

    public void setGases(List<GasVolume> gases) {
        if (!this.gases.equals(gases)) {
            this.gases = gases;
            update();
        }
    }

    public void setMaxVolume(int maxVolume) {
        if (this.maxVolume != maxVolume) {
            this.maxVolume = maxVolume;
            update();
        }
    }

    private void update() {
        renderCache.clear();
        if (maxVolume == -1) {
            double sumVolume = gases.stream().mapToInt(GasVolume::getAmount).sum();
            int pixelsAvailable = 52 - gases.size() * 2;

            for (GasVolume gas : gases)
                renderCache.add(new Pair<>(gas, (int) (gas.getAmount() / sumVolume * pixelsAvailable + 2)));
        } else {
            for (GasVolume gas : gases)
                renderCache.add(new Pair<>(gas, Math.max(2, (int) (52.0 * gas.getAmount() / maxVolume))));
        }
    }
}
