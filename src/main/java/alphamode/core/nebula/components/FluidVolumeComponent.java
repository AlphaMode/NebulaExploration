package alphamode.core.nebula.components;

import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class FluidVolumeComponent implements FluidVolumeCompound {

    @Nullable
    private List<FluidVolume> gases;
    private final Object provider;

    public FluidVolumeComponent(Object provider) {
        this.provider = provider;
    }

    @Nullable
    @Override
    public List<FluidVolume> getFluids() {
        return gases;
    }

    @Override
    public void setFluids(List<FluidVolume> gases) {
        this.gases = gases;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        List<FluidVolume> temp = new ArrayList<>();
        ListTag gasesTag = tag.getList("gases", 0);
        for(int i = 0; i < gasesTag.size(); ++i) {
            //temp.add(FluidVolume.fromTag((CompoundTag) gasesTag.get(i)));
        }
        gases = temp;
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        ListTag gasesTag = new ListTag();
        if(gases == null) {
            tag.put("gases", gasesTag);
            return;
        }
        for(FluidVolume gas : gases) {
            gasesTag.add(gas.toTag());
        }
        tag.put("gases", gasesTag);
    }


    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return player == this.provider;
    }
}
