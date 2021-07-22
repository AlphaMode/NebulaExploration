package alphamode.core.nebula.client.gui.screens;

import alphamode.core.nebula.client.gui.widgets.InventoryTabWidget;
import alphamode.core.nebula.lib.client.gui.Widget;
import java.util.ArrayList;
import java.util.List;

//Wrapper so other devs can add tabs
public interface InventoryScreenExtension {
    void addWidget(Widget widget);

    void removeWidget(Widget widget);
}
