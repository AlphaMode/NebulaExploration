package alphamode.core.nebula.transfer;

import net.minecraft.util.math.BlockPos;

public class PowerPath {
    public BlockPos currentPos;

    public PowerPath(BlockPos currentPos) {
        this.currentPos = currentPos;
    }
    PowerPath(BlockPos pos, PowerPath path) {
        this.currentPos = pos;
    }
    @Override
    public String toString() {
        return "Position : "+ this.currentPos.toString() + "]";
    }
}
