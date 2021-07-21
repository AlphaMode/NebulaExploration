package alphamode.core.nebula.blocks.entity;

import alphamode.core.nebula.api.*;
import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.gases.GasVolume;
import java.awt.*;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class GasCableBlockEntity extends BlockEntity implements Node, Insertable<GasVolume>, Extractable<GasVolume> {
    public GasCableBlockEntity(BlockPos pos, BlockState state) {
        super(NebulaBlocks.GAS_CABLE_BLOCK_ENTITY_BLOCK, pos, state);
    }

    private GasCableBlockEntity parent;
    private CableNetwork network;
    private boolean isParent = false;

    //New tick
    public static void tick2(World world, BlockPos pos, BlockState state, GasCableBlockEntity blockEntity) {

    }

    public static void tick(World world, BlockPos pos, BlockState state, GasCableBlockEntity blockEntity) {
        /*if (blockEntity.getNetwork() != null) {
            Iterator<Node> it = blockEntity.getNetwork().getNodes().iterator();
            while (it.hasNext()) {
                Node cable = it.next();
                NebulaMod.LOGGER.info(cable.isParent());
                if(cable.getNodeProvider() != null) {
                    if (((GasCableBlockEntity) cable.getNodeProvider()).removed) {
                        cable.getNetwork().removeNode(cable);
                        cable.setNodeProvider(null);
                    }
                }
            }
        }else if(blockEntity.getNodeProvider() != null) {
            if(blockEntity.removed) {
                blockEntity.getNetwork().removeNode(blockEntity);
                blockEntity.setNetwork(null);
            }
        }*/
        if (blockEntity.isParent) {
            world.addParticle(DustParticleEffect.DEFAULT, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, 0, 0, 0);
        } else if (blockEntity.getNodeProvider() != null) {
            world.addParticle(new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(new Color(0, 255, 255).getRGB())), 1.f), pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, 0, 0, 0);
        }
        for (Direction direction : Direction.values()) {
            if (world.getBlockEntity(pos.offset(direction)) instanceof Node) {
                Node cable = (Node) world.getBlockEntity(pos.offset(direction));
                if (blockEntity.isParent()) {
                    cable.setNetwork(blockEntity.getNetwork());
                    cable.setNodeProvider(blockEntity);
                } else {
                    //if (blockEntity.getNodeProvider() != null) {
                        //cable.setParentReference(blockEntity.getParentReference());
                        cable.setNetwork(blockEntity.getNetwork());
                        cable.setNodeProvider(blockEntity);
                    //}
                }
            } else if (world.getBlockEntity(pos.offset(direction)) instanceof Machine) {
                blockEntity.setParent(true);
                if (blockEntity.getNetwork() == null) {
                    blockEntity.setNetwork(new CableNetwork());
                }
                blockEntity.getNetwork().addNode(blockEntity);
            }
        }
    }

    @Override
    public Node getNodeProvider() {
        return parent;
    }

    @Override
    public void setNodeProvider(Node provider) {
        parent = (GasCableBlockEntity) provider;
    }

    @Override
    public boolean isParent() {
        return isParent;
    }

    @Override
    public void setParent(boolean bool) {
        isParent = bool;
    }

    @Override
    public CableNetwork getNetwork() {
        return network;
    }

    @Override
    public void setNetwork(CableNetwork network) {
        this.network = network;
    }
}
