package alphamode.core.nebula.gases;

import alphamode.core.nebula.NebulaRegistry;
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class GasVariant implements TransferVariant<Gas> {

    public GasVariant(Gas gas, NbtCompound nbt) {
        Objects.requireNonNull(gas, "Need to provide a gas!");
        this.gas = gas;
        this.nbt = nbt != null ? nbt.copy() : null;
    }

    private Gas gas;
    private NbtCompound nbt;

    @Override
    public boolean isBlank() {
        return gas == NebulaGases.EMPTY;
    }

    @Override
    public Gas getObject() {
        return gas;
    }

    @Override
    public @Nullable NbtCompound getNbt() {
        return nbt;
    }

    @Override
    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("gas", NebulaRegistry.GAS.getId(gas).toString());
        if(nbt != null)
            nbt.put("tag", nbt.copy());
        return nbt;
    }

    public static GasVariant fromNbt(NbtCompound compound) {
        Gas gas = NebulaRegistry.GAS.get(new Identifier(compound.getString("gas")));
        NbtCompound nbt = compound.contains("tag") ? compound.getCompound("tag") : null;
        return new GasVariant(gas, nbt);
    }

    @Override
    public void toPacket(PacketByteBuf buf) {
        buf.writeVarInt(NebulaRegistry.GAS.getRawId(gas));
        if(nbt != null)
            buf.writeNbt(nbt.copy());
    }

    /**
     * Read a variant from a packet byte buffer, assuming it was serialized using {@link #toPacket}.
     */
    static GasVariant fromPacket(PacketByteBuf buf) {
        Gas gas = NebulaRegistry.GAS.get(buf.readVarInt());
        NbtCompound nbt = buf.readNbt();
        return new GasVariant(gas, nbt);
    }
}
