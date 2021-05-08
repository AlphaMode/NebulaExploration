package alphamode.core.nebula.client;

import alphamode.core.nebula.client.screen.CondenserContainerScreen;
import alphamode.core.nebula.items.NebulaItems;
import alphamode.core.nebula.screen.NebulaScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;

import static alphamode.core.nebula.NebulaMod.id;

import net.minecraft.world.item.Items;

@Environment(EnvType.CLIENT)
public class NebulaModClient implements ClientModInitializer {
    private int getStage(int ammount) {
        return 0;
    }
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(NebulaScreens.CONDENSER_MENU, CondenserContainerScreen::new);
        FabricModelPredicateProviderRegistry.register(NebulaItems.BASIC_OXYGEN_TANK, id("ammount"),(itemStack, clientLevel, livingEntity) -> {
            if(livingEntity == null) {
                return 0;
            }
            return itemStack.getOrCreateTag().getInt("ammount") == 0 ? 0 : getStage(itemStack.getOrCreateTag().getInt("ammount"));
        });
    }
}
