package alphamode.core.nebula.lib.client.gui;

import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public abstract class Widget {

    protected List<Widget> nestedWidgets;
    protected int startX, startY;
    protected static final MinecraftClient client = MinecraftClient.getInstance();

    public Widget(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
    }

    public void render(MatrixStack matrixStack) {
        for (Widget nested : nestedWidgets) {
            nested.render(matrixStack);
        }
    }

    public abstract void renderTooltip(MatrixStack matrixStack, int mouseX, int mouseY);
}
