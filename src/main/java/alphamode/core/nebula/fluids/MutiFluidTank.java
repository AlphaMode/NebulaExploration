package alphamode.core.nebula.fluids;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.DefaultedList;

public class MutiFluidTank {
    private List<SingleFluidStorage> tanks;

    public MutiFluidTank() {
        this(new ArrayList());
    }

    public MutiFluidTank(List tanks) {
        this.tanks = tanks;
    }

    public MutiFluidTank(int max, List tanks) {
        this.tanks = DefaultedList.ofSize(max);
        tanks.addAll(tanks);
    }

    /**
     * @return {@code true} if the passed non-blank fluid variant can be inserted, {@code false} otherwise.
     */
    protected boolean canInsert(FluidVariant fluidVariant) {
        return true;
    }

    /**
     * @return {@code true} if the passed non-blank fluid variant can be extracted, {@code false} otherwise.
     */
    protected boolean canExtract(FluidVariant fluidVariant) {
        return true;
    }

    public long extract(int tank, FluidVariant fluidVariant, int maxAmount, TransactionContext transactionContext) {
        if(canExtract(fluidVariant))
            return tanks.get(tank).extract(fluidVariant, maxAmount, transactionContext);
        return 0;
    }

    public long insert(int tank, FluidVariant fluidVariant, int maxAmount, TransactionContext transactionContext) {
        if(canInsert(fluidVariant))
            return tanks.get(tank).insert(fluidVariant, maxAmount, transactionContext);
        return 0;
    }

    public PacketByteBuf toPacket() {
        return toPacket(new PacketByteBuf(Unpooled.buffer()));
    }

    public PacketByteBuf toPacket(PacketByteBuf buf) {
        buf.writeInt(tanks.size());
        for(SingleFluidStorage tank : tanks) {
            tank.fluidVariant.toPacket(buf);
            buf.writeLong(tank.amount);
        }
        return buf;
    }

    public static MutiFluidTank fromPacket(PacketByteBuf buf) {
        int size = buf.readInt();
        List<SingleFluidStorage> tanks = new ArrayList<>();
        for(int i  = 0; i < size; i++) {
             FluidVariant fluid = FluidVariant.fromPacket(buf);
             long amount = buf.readLong();
             tanks.add(new SimpleSingleFluidStorage(fluid, amount));
        }
        return new MutiFluidTank(tanks);
    }
}
