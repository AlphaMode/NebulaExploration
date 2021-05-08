package alphamode.core.nebula.entitys;

import alphamode.core.nebula.NebulaMod;
import alphamode.core.nebula.items.Throwables.MustardBomb;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

import static alphamode.core.nebula.NebulaMod.MOD_ID;
import static alphamode.core.nebula.NebulaMod.id;

public class NebulaEntities {
    public static final EntityType<MustardBomb> MUSTARD_BOMB;


    static {
        MUSTARD_BOMB = Registry.register(Registry.ENTITY_TYPE, id("mustard_bomb_entity"),EntityType.Builder.<MustardBomb>create(MustardBomb::new, SpawnGroup.MISC).setDimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10).build(MOD_ID+"mustard_bomb_entity"));
    }
}
