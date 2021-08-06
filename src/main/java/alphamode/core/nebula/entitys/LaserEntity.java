package alphamode.core.nebula.entitys;

import alphamode.core.nebula.api.NebulaID;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import java.awt.*;

import net.minecraft.client.render.entity.LightningEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LaserEntity extends Entity {
    private int color;
    public LaserEntity(World world) {
        super(NebulaEntities.LASER_ENTITY, world);
        color = new Color(255,0,0).getRGB();
    }

    public LaserEntity(World world, Vec3d vec, float pitch, float yaw) {
        super(NebulaEntities.LASER_ENTITY, world);
        this.setPosition(vec);
        this.updateTrackedPosition(vec);
        this.setPitch(pitch);
        this.setYaw(yaw);
        this.color = new Color(255,0,0).getRGB();
    }

    public LaserEntity(World world, Vec3d vec, float pitch, float yaw, int color) {
        super(NebulaEntities.LASER_ENTITY, world);
        this.setPosition(vec);
        this.updateTrackedPosition(vec);
        this.setPitch(pitch);
        this.setYaw(yaw);
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.color = nbt.getInt("color");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("color", this.color);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Environment(EnvType.CLIENT)
    public static void spawn(PacketByteBuf packetByteBuf) {
        LOGGER.info("SPAWNED REEEDSFOPKSD");
    }
}
