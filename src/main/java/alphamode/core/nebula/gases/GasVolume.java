package alphamode.core.nebula.gases;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidKey;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import alexiil.mc.lib.attributes.fluid.volume.SimpleFluidKey;
import alphamode.core.nebula.NebulaRegistry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import alphamode.core.nebula.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

//Wrapper for fluidvolume
public final class GasVolume {

    private final Gas owner;
    private long gasAmount;
    private final SimpleFluidKey key;

    public GasVolume(Gas gas, long gasAmount) {
        this.owner = gas;
        this.gasAmount = gasAmount;
        key = new SimpleFluidKey(new FluidKey.FluidKeyBuilder(NebulaRegistry.GAS.getId(gas))
                .setGas()
                .setRenderColor(gas.getColor())
                .setName(gas.getName()));
    }


    public Gas getGas() {
        return this.owner;
    }


    /**
     * @return Returns the amount in mB
     */
    public int getAmount() {
        return toFluidVolume().getAmount_F().asInt(1000);
    }

    public static GasVolume fromTag(NbtCompound tag) {
        return new GasVolume(NebulaRegistry.GAS.get(new Identifier(tag.getString("id"))),tag.getLong("amount"));
    }

    public NbtCompound toTag(NbtCompound tag) {
        tag.putString("id", NebulaRegistry.GAS.getId(owner).toString());
        tag.putLong("amount", gasAmount);
        return tag;
    }

    public static GasVolume fromPacket(PacketByteBuf buf) {
        return new GasVolume(NebulaRegistry.GAS.get(buf.readIdentifier()), buf.readLong());
    }

    public PacketByteBuf toPacket(PacketByteBuf buf) {
        buf.writeIdentifier(NebulaRegistry.GAS.getId(owner));
        buf.writeLong(gasAmount);
        return buf;
    }

    public FluidVolume toFluidVolume() {
        return key.withAmount(FluidAmount.of(gasAmount, 1000));
    }

    public void setAmount(long l) {
        gasAmount = l;
    }

    @Environment(EnvType.CLIENT)
    public List<Text> getTooltip() {
        return getTooltip(new ArrayList<>());
    }

    @Environment(EnvType.CLIENT)
    public List<Text> getTooltip(List<Text> tooltip) {
        tooltip.add(owner.getName());
        tooltip.add(new TranslatableText("gui.nebula.concentration", gasAmount).formatted(Formatting.GRAY));
        if(MinecraftClient.getInstance().options.advancedItemTooltips)
            tooltip.add(new LiteralText(owner.getId().toString()).formatted(Formatting.DARK_GRAY));
        tooltip.add(Util.gasModToolTip(owner));
        return tooltip;
    }

    @Override
    public boolean equals(Object obj) {
        if(NebulaRegistry.GAS.getId(this.getGas()).equals(NebulaRegistry.GAS.getId(((GasVolume)obj).getGas())) & ((GasVolume)obj).gasAmount == gasAmount)
            return true;
        return false;
    }
}
