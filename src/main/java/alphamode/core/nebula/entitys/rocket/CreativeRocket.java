package alphamode.core.nebula.entitys.rocket;

import alphamode.core.nebula.entitys.NebulaEntities;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class CreativeRocket extends RocketBase {
    public CreativeRocket(World world) {
        super(NebulaEntities.CREATIVE_ROCKET, world);
    }

    public CreativeRocket(EntityType<CreativeRocket> creativeRocketEntityType, World world) {
        super(creativeRocketEntityType, world);
    }
}
