package alphamode.core.nebula.lib.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public class BaseGui<T extends ScreenHandler> extends HandledScreen {

    public List<Widget> widgets = new ArrayList<>();

    public BaseGui(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        for(Widget widget : widgets) {
            widget.render(matrices);
        }
    }
}
