package alphamode.core.nebula.entitys;

import alphamode.core.nebula.api.NebulaID;
import alphamode.core.nebula.entitys.rocket.CreativeRocket;
import alphamode.core.nebula.items.Throwables.MustardBomb;
import static alphamode.core.nebula.NebulaMod.MOD_ID;
import static alphamode.core.nebula.NebulaMod.id;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class NebulaEntities {
    public static final EntityType<MustardBomb> MUSTARD_BOMB;
    public static final EntityType<CreativeRocket> CREATIVE_ROCKET;
    public static final EntityType<LaserEntity> LASER_ENTITY;

    static {
        MUSTARD_BOMB = Registry.register(Registry.ENTITY_TYPE, id("mustard_bomb_entity"),EntityType.Builder.<MustardBomb>create(MustardBomb::new, SpawnGroup.MISC).setDimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10).build(MOD_ID+":mustard_bomb_entity"));
        CREATIVE_ROCKET = Registry.register(Registry.ENTITY_TYPE, new NebulaID("creative_rocket"), EntityType.Builder.<CreativeRocket>create(CreativeRocket::new ,SpawnGroup.MISC).build(MOD_ID+":creative_rocket"));
        LASER_ENTITY = Registry.register(Registry.ENTITY_TYPE, new NebulaID("laser"), EntityType.Builder.create((EntityType<LaserEntity> type, World world) -> new LaserEntity(world), SpawnGroup.MISC).setDimensions(1, .5f).build(MOD_ID+":laser"));
    }

    public static void init() {
    }
}
