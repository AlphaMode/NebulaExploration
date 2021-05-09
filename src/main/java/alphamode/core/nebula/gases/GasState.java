package alphamode.core.nebula.gases;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;

import java.util.Map;

import net.minecraft.block.BlockState;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;

//I don't think we need this yet :thinking:
public class GasState extends State<Gas,GasState> {


    protected GasState(Gas owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<GasState> codec) {
        super(owner, entries, codec);
    }

}
