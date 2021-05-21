package alphamode.core.nebula.client;

import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import alphamode.core.nebula.client.screen.CondenserHandledScreen;
import alphamode.core.nebula.items.NebulaItems;
import alphamode.core.nebula.screen.NebulaScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;

import static alphamode.core.nebula.NebulaMod.id;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

@Environment(EnvType.CLIENT)
public class NebulaModClient implements ClientModInitializer {
    private int getStage(int ammount) {
        return 0;
    }
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(NebulaScreens.CONDENSER_MENU, CondenserHandledScreen::new);
        FabricModelPredicateProviderRegistry.register(NebulaItems.BASIC_OXYGEN_TANK, id("ammount"),(itemStack, clientLevel, livingEntity) -> {
            if(livingEntity == null) {
                return 0;
            }
            return itemStack.getOrCreateTag().getInt("ammount") == 0 ? 0 : getStage(itemStack.getOrCreateTag().getInt("ammount"));
        });
        ClientPlayNetworking.registerGlobalReceiver(id("condenser_update"),(client, handler, buf, responseSender) -> {
            client.execute(() -> {
                List<FluidVolume> gases = new ArrayList<>();
                ListTag gasesTag = buf.readCompoundTag().getList("gases", 0);
                for(int v = 0; v < gasesTag.size(); ++v) {
                    gases.add(FluidVolume.fromTag((CompoundTag) gasesTag.get(v)));
                }
                //tank = gases;
            });
        });
    }
}
