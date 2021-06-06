package alphamode.core.nebula.lib.client.gui;

import net.minecraft.client.util.math.MatrixStack;

public interface Widget {

    void render(MatrixStack matrixStack);

    void setX(int x);

    void setY(int y);
}
