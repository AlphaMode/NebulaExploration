package alphamode.core.nebula.packet;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.client.screen.CondenserHandledScreen;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.screen.CondenserScreenHandler;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import static alphamode.core.nebula.NebulaMod.id;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;

public class GasTankS2CPacket {
    public static final Identifier ID = id("condenser_packet");

    public static void onPacket(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        CompoundTag data = buf.readCompoundTag();
        List<GasVolume> gases = new ArrayList<>();
        ListTag gasesList = data.getList("gases", 10);
        for(int i = 0; i<gasesList.size();i++) {
            gases.add(GasVolume.fromTag(gasesList.getCompound(i)));
        }

        client.execute(() -> {
            if(client.currentScreen instanceof CondenserHandledScreen) {
                CondenserHandledScreen screen = (CondenserHandledScreen) client.currentScreen;
                screen.updateTank(gases);
            }
        });
    }

    public static Packet<?> create(List<GasVolume> gases) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        ListTag gasesList = new ListTag();
        for(GasVolume gas : gases) {
            gasesList.add(gas.toTag(new CompoundTag()));
        }
        CompoundTag data = new CompoundTag();
        data.put("gases", gasesList);
        buf.writeCompoundTag(data);
        return ServerPlayNetworking.createS2CPacket(ID,buf);
    }

}
