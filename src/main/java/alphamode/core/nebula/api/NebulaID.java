package alphamode.core.nebula.api;

import alphamode.core.nebula.NebulaMod;

import net.minecraft.util.Identifier;

//Wrapper for Identifier because static imports are lame
public class NebulaID extends Identifier {
    public NebulaID(String path) {
        super(NebulaMod.MOD_ID, path);
    }
}
