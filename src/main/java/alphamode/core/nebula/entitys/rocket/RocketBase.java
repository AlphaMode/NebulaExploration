package alphamode.core.nebula.entitys.rocket;

import alphamode.core.nebula.entitys.NebulaEntities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class RocketBase<T extends RocketBase> extends Entity {

    private int tier;

    public RocketBase(EntityType<T> type, World world, int tier) {
        super(type, world);
        this.tier = tier;
    }

    public RocketBase(EntityType<?> type, World world) {
        super(type, world);
        this.tier = 0;
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.tier = nbt.getInt("Tier");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("Tier", this.tier);
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        player.startRiding(this);
        LOGGER.info(player);
        return super.interact(player, hand);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
