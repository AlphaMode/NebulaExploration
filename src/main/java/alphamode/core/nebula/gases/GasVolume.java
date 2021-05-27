package alphamode.core.nebula.gases;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.mixin.impl.FluidBlockMixin;
import alexiil.mc.lib.attributes.fluid.volume.FluidKey;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import alexiil.mc.lib.attributes.fluid.volume.SimpleFluidKey;
import alexiil.mc.lib.attributes.fluid.volume.SimpleFluidVolume;
import alphamode.core.nebula.NebulaRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public final class GasVolume  {

    private Gas owner;
    private int gasAmount;
    private SimpleFluidKey key;

    public GasVolume(Gas gas, int gasAmount) {
        this.owner = gas;
        this.gasAmount = gasAmount;
        key = new SimpleFluidKey(new FluidKey.FluidKeyBuilder(NebulaRegistry.GAS.getId(gas)).setGas().setRenderColor(gas.getColor()).setName(gas.getName()));
    }

    public Gas getGas() {
        return this.owner;
    }


    /**
     * @return Returns the amount in mB
     */
    public int getAmount() {
        return this.gasAmount;
    }

    public static GasVolume fromTag(NbtCompound tag) {
        return new GasVolume(NebulaRegistry.GAS.get(new Identifier(tag.getString("id"))),tag.getInt("amount"));
    }

    public NbtCompound toTag(NbtCompound tag) {
        tag.putString("id", NebulaRegistry.GAS.getId(owner).toString());
        tag.putInt("amount", gasAmount);
        return tag;
    }

    public FluidVolume toFluidVolume() {
        return key.withAmount(FluidAmount.of(gasAmount,1000));
    }

}
