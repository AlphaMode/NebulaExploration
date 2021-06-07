package alphamode.core.nebula.packet;


import alphamode.core.nebula.client.screen.CondenserHandledScreen;
import alphamode.core.nebula.gases.GasVolume;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import static alphamode.core.nebula.NebulaMod.id;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class GasTankS2CPacket {
    public static final Identifier ID = id("condenser_packet");

    public static void onPacket(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        NbtCompound data = buf.readNbt();
        List<GasVolume> gases = new ArrayList<>();
        NbtList gasesList = data.getList("gases", 10);
        for(int i = 0; i<gasesList.size();i++) {
            gases.add(GasVolume.fromTag(gasesList.getCompound(i)));
        }

        gases.sort( (a,b) -> b.getAmount() - a.getAmount() );

        client.execute(() -> {
            if(client.currentScreen instanceof CondenserHandledScreen) {
                CondenserHandledScreen screen = (CondenserHandledScreen) client.currentScreen;
                screen.updateTank(gases);
            }
        });
    }

    public static Packet<?> create(List<GasVolume> gases) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        NbtList gasesList = new NbtList();
        for(GasVolume gas : gases) {
            gasesList.add(gas.toTag(new NbtCompound()));
        }
        NbtCompound data = new NbtCompound();
        data.put("gases", gasesList);
        buf.writeNbt(data);
        return ServerPlayNetworking.createS2CPacket(ID,buf);
    }

}
