package alphamode.core.nebula.client.render.entity;

import alphamode.core.nebula.api.NebulaID;
import alphamode.core.nebula.entitys.rocket.RocketBase;
import dev.monarkhes.myron.api.Myron;
import java.util.Random;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class RocketEntityRender extends EntityRenderer<RocketBase> {
    protected RocketEntityRender(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(RocketBase entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        Random random = new Random(entity.getPos().hashCode());
        int r = random.nextInt(100);
        BakedModel model = Myron.getModel(new NebulaID("models/misc/utah_teapot"));

        if (model != null) {
            VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getSolid());

            matrices.push();
            float time = (entity.getEntityWorld() == null ? 0 : entity.getEntityWorld().getTime()) + tickDelta + r;

            float scale = (float) (0.4 + 0.05 * Math.sin(time * 0.025));
            matrices.scale(scale, scale, scale);

            matrices.translate(0.5F / scale, 0.5F / scale, 0.5F / scale);
            matrices.translate(0, 0.125 * Math.sin(time * 0.1), 0);

            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(2 * (time)));

            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(0.8F * time));

            matrices.multiply(Vec3f.NEGATIVE_Z.getDegreesQuaternion(1.4F * time));

            MatrixStack.Entry entry = matrices.peek();

            model.getQuads(null, null, entity.getEntityWorld().random).forEach(quad -> {
                consumer.quad(entry, quad, 1F, 1F, 1F, light, OverlayTexture.DEFAULT_UV);
            });

            matrices.pop();
        }
    }

    @Override
    public boolean shouldRender(RocketBase entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public Identifier getTexture(RocketBase entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
