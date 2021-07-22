package alphamode.core.nebula.mixin.client;

import alphamode.core.nebula.client.gui.screens.InventoryScreenExtension;
import alphamode.core.nebula.client.gui.widgets.InventoryTabWidget;
import alphamode.core.nebula.client.gui.widgets.SelectionTabWidget;
import alphamode.core.nebula.items.NebulaItems;
import alphamode.core.nebula.lib.client.gui.Widget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin implements InventoryScreenExtension {

    @Unique
    private List<Widget> widgets = new ArrayList<>();

    public void addWidget(Widget widget) {
        assert widget instanceof InventoryTabWidget;
        widgets.add(widget);
    }

    public void removeWidget(Widget widget) {
        assert widget instanceof InventoryTabWidget;
        widgets.remove(widget);
    }

    @Inject(method = "init", at = @At("HEAD"))
    public void init(CallbackInfo ci) {
        widgets.add(new InventoryTabWidget(Items.CHEST, 0, 0));
        widgets.add(new SelectionTabWidget(NebulaItems.FAN, 28, 0, widgets.size()));
    }

    @Inject(method = "render", at = @At(value = "TAIL", target = "Lnet/minecraft/client/gui/screen/Screen;renderBackground(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    public void renderTabs(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        //28, 32
        for (Widget widget : widgets) {
            widget.render(matrices, ((HandledScreenAccessor) this).getX(), ((HandledScreenAccessor) this).getY(), delta);
        }

        //((DrawableHelper)(Object)this).drawTexture(matrices, ((HandledScreenAccessor) this).getX(), 20, 0, 2, 28, 32);
    }
}
