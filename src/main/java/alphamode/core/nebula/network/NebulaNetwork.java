package alphamode.core.nebula.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NebulaNetwork {

    private static Map<Identifier, Consumer<PacketByteBuf>> handlers;

    @Environment(EnvType.CLIENT)
    public static void registerEntityPacket(Identifier id, Consumer<PacketByteBuf> entityConsumer) {
        handlers.put(id, entityConsumer);
    }

    public static void handleEntity(PacketByteBuf buf) {
        handlers.forEach((id, handler) -> {
            if(buf.readIdentifier().equals(id)) {
                handler.accept(buf);
            }
        });
    }

    public static void init() {
        handlers = new HashMap<>();
    }

}
