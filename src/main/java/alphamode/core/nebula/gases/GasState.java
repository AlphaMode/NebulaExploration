package alphamode.core.nebula.gases;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;

import net.minecraft.state.State;
import net.minecraft.state.property.Property;

//I don't think we need this yet :thinking: maybe now we do?
public final class GasState extends State<Gas,GasState> {

    private int gasAmount;

    protected GasState(Gas owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<GasState> codec) {
        super(owner, entries, codec);
        this.gasAmount = 0;
    }

    protected GasState(Gas owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<GasState> codec,int amount) {
        super(owner, entries, codec);
        this.gasAmount = amount;
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

}
