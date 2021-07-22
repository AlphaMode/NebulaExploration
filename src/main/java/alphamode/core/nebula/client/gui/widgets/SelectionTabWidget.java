package alphamode.core.nebula.client.gui.widgets;

import alphamode.core.nebula.lib.client.gui.Widget;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class SelectionTabWidget extends Widget {
    protected Identifier TAB_TEXTURES = new Identifier("textures/gui/container/creative_inventory/tabs.png");
    protected ItemStack icon;
    protected int index;
    protected int xOffset, yOffset;
    protected boolean isSelected = true;

    public SelectionTabWidget(Item icon, int xOffset, int yOffset, int index) {
        super(0, 0);
        this.icon = new ItemStack(icon);
        if (index != 0) {
            this.xOffset = 28 * index;
        } else {
            this.xOffset = xOffset;
        }
        this.yOffset = yOffset;
        this.index = index;
    }

    @Override
    public void render(MatrixStack matrixStack, int posX, int posY, float delta) {
        int x = posX + xOffset;
        int y = posY + yOffset;
        matrixStack.push();
        MinecraftClient.getInstance().getItemRenderer().renderGuiItemIcon(icon, x + 6, y - 20);
        RenderSystem.setShaderTexture(0, TAB_TEXTURES);
        if (isSelected) {
            if (index > 0) {
                drawTexture(matrixStack, x, y - 28, 28, 32, 27, 32);
            } else {
                drawTexture(matrixStack, x, y - 28, 0, 32, 27, 32);
            }
        } else {
            drawTexture(matrixStack, x, y - 28, 0, 2, 27, 32);
        }
        matrixStack.pop();
    }

    @Override
    public void renderTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {

    }
}
