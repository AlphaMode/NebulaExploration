package alphamode.core.nebula.entitys;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.items.Throwables.MustardBomb;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static alphamode.core.nebula.NebulaMod.MOD_ID;
import static alphamode.core.nebula.NebulaMod.id;

public class NebulaEntities {
    public static final EntityType<MustardBomb> MUSTARD_BOMB;


    static {
        MUSTARD_BOMB = Registry.register(Registry.ENTITY_TYPE, id("mustard_bomb_entity"),EntityType.Builder.<MustardBomb>of(MustardBomb::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(MOD_ID+"mustard_bomb_entity"));
    }
}
