package alphamode.core.nebula.blocks.entity;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.api.CableNetwork;
import alphamode.core.nebula.api.Node;
import alphamode.core.nebula.blocks.NebulaBlocks;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WirelessTransferNode extends BlockEntity implements Node {
    private CableNetwork network = NebulaMod.GLOBAL_NETWORK;

    public WirelessTransferNode(BlockPos pos, BlockState state) {
        super(NebulaBlocks.TRANSFER_NODE_BLOCK_ENTITY, pos, state);
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, T blockEntity) {

    }

    @Nullable
    @Override
    public Node getNodeProvider() {
        return null;
    }

    @Deprecated
    @Override
    public void setNodeProvider(Node provider) {}

    @Deprecated
    @Override
    public boolean isParent() {
        return true;
    }

    @Deprecated
    @Override
    public void setParent(boolean bool) {}

    @Override
    public CableNetwork getNetwork() {
        return network;
    }

    @Override
    public void setNetwork(CableNetwork network) {
        this.network = network;
    }


}
