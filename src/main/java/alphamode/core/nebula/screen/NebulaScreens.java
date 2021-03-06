package alphamode.core.nebula.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

import static alphamode.core.nebula.NebulaMod.id;

public class NebulaScreens {
    public static final ScreenHandlerType<CondenserScreenHandler> CONDENSER_MENU;

    public static void init() {
    }

    static {
        CONDENSER_MENU = ScreenHandlerRegistry.registerSimple(id("condenser"), CondenserScreenHandler::new);
    }

}
