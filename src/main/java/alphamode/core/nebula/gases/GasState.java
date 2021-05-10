package alphamode.core.nebula.gases;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;

import java.util.Map;

import net.minecraft.block.BlockState;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;

//I don't think we need this yet :thinking: maybe now we do?
public final class GasState extends State<Gas,GasState> {

    private int gasAmmount;

    protected GasState(Gas owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<GasState> codec) {
        super(owner, entries, codec);
        this.gasAmmount = 0;
    }

    protected GasState(Gas owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<GasState> codec,int ammount) {
        super(owner, entries, codec);
        this.gasAmmount = ammount;
    }

    public int getAmmount() {
        return this.gasAmmount;
    }

}
