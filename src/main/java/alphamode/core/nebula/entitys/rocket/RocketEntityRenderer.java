package alphamode.core.nebula.entitys.rocket;

import alphamode.core.nebula.api.NebulaID;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class RocketEntityRenderer extends EntityRenderer<RocketBase> {
    public RocketEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(RocketBase entity) {
        return new NebulaID("textures/block/aluminum_ore.png");
    }
}
