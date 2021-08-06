package alphamode.core.nebula.packet;


import alphamode.core.nebula.client.gui.screens.CondenserHandledScreen;
import alphamode.core.nebula.gases.GasVolume;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import static alphamode.core.nebula.NebulaMod.LOGGER;
import static alphamode.core.nebula.NebulaMod.id;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class GasTankS2CPacket {
    public static final Identifier ID = id("gas_tank");

    public static void onPacket(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        List<GasVolume> gases = new ArrayList<>();
        int id = buf.readInt();
        int temp = buf.readInt();
        for (int i = 0; i < temp; i++) {
            gases.add(GasVolume.fromPacket(buf));
        }
        gases.sort((a, b) -> b.getAmount() - a.getAmount());
        Type type = buf.readEnumConstant(Type.class);
        client.execute(() -> {
            LOGGER.info(client.player.currentScreenHandler.syncId + " " + id);
            if (client.player.currentScreenHandler.syncId == id) {
                LOGGER.info(client.player.currentScreenHandler);
                ((CondenserHandledScreen)client.currentScreen).updateTank(gases, type);
            }
        });
    }

    public static Packet<?> create(int id, List<GasVolume> gases, Type type) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(id);
        buf.writeInt(gases.size());
        for (GasVolume gas : gases) {
            gas.toPacket(buf);
        }
        buf.writeEnumConstant(type);
        return ServerPlayNetworking.createS2CPacket(ID, buf);
    }

    public enum Type {
        INFO,
        TANK
    }
}
