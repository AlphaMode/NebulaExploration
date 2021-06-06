package alphamode.core.nebula.blocks.entity;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.api.*;
import alphamode.core.nebula.blocks.NebulaBlocks;
import alphamode.core.nebula.gases.GasApis;
import alphamode.core.nebula.gases.GasVolume;
import alphamode.core.nebula.lib.client.GuiUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiCache;
import java.awt.*;
import java.util.Map;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
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

    public static void tick(World world, BlockPos pos, BlockState state, GasCableBlockEntity blockEntity) {
        if(blockEntity.getParentReference() != null) {
            if (((GasCableBlockEntity) blockEntity.getParentReference()).removed) {

                if (blockEntity.getNetwork() != null) {
                    blockEntity.getNetwork().removeNode(blockEntity.getParentReference());
                }
                blockEntity.setParentReference(null);
            }
        }
        if (blockEntity.isParent) {
            world.addParticle(DustParticleEffect.DEFAULT, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, 0, 0, 0);
        } else if (blockEntity.getParentReference() != null) {
            world.addParticle(new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(new Color(0, 255, 255).getRGB())), 1.f), pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, 0, 0, 0);
        }
        for (Direction direction : Direction.values()) {
            if (world.getBlockEntity(pos.offset(direction)) instanceof Node) {
                Node cable = (Node) world.getBlockEntity(pos.offset(direction));
                if (blockEntity.isParent()) {
                    cable.setParentReference(blockEntity);
                } else {
                    if (blockEntity.getParentReference() != null) {
                        cable.setParentReference(blockEntity.getParentReference());
                    }
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
    public void setParentReference(Node child) {
        this.parent = (GasCableBlockEntity) child;
    }

    @Override
    public Node getParentReference() {
        return parent;
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
