package alphamode.core.nebula.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import static alphamode.core.nebula.NebulaMod.id;

import net.minecraft.world.inventory.MenuType;

public class NebulaScreens {
    public static final MenuType<CondenserContainerMenu> CONDENSER_MENU;

    public static void init() {
    }

    static {
        CONDENSER_MENU = ScreenHandlerRegistry.registerSimple(id("condenser"), CondenserContainerMenu::new);
    }

}
