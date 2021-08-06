package alphamode.core.nebula.client.render.entity;

import alphamode.core.nebula.entitys.LaserEntity;
import org.apache.logging.log4j.LogManager;

import java.awt.*;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

public class LaserEntityRender<T extends LaserEntity> extends EntityRenderer<T> {
    public LaserEntityRender(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getLightning());
        matrices.push();
        matrices.translate(-.25, 0, -.5);
        Matrix4f model = matrices.peek().getModel();
        //matrices.multiply(new Quaternion(0, entity.getYaw() - entity.getYaw() - entity.getYaw() , 0, true));
        Color color = new Color(entity.getColor());
        createBullet(model, buffer, light, color.darker().darker().darker());
        matrices.scale(.8f, .8f, .8f);
        matrices.translate(.0625, .0625, .125);
        createBullet(model, buffer, light, color);
        matrices.pop();
    }

    public void createBullet(Matrix4f model, VertexConsumer buffer, int light, Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int a = 255;
        //LogManager.getLogger().info(r+" "+ g+" " + b);
        buffer.vertex(model, 0f, 0f, 0f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, .5f, 0f, 0f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, .5f, 0f, 1f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, 0f, 0f, 1f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();

        buffer.vertex(model, .5f, .5f, 0f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, 0f, .5f, 0f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, 0f, .5f, 1f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, .5f, .5f, 1f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();

        buffer.vertex(model, 0f, .5f, 0f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, 0f, 0f, 0f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, 0f, 0f, 1f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, 0f, .5f, 1f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        //
        buffer.vertex(model, .5f, 0f, 0f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, .5f, .5f, 0f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, .5f, .5f, 1f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, .5f, 0f, 1f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();

        buffer.vertex(model, .5f, 0f, 0f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, 0f, 0f, 0f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, 0f, .5f, 0f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, .5f, .5f, 0f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();

        buffer.vertex(model, 0f, 0f, 1f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, .5f, 0f, 1f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, .5f, .5f, 1f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
        buffer.vertex(model, 0f, .5f, 1f).light(light).color(r, g, b, a).overlay(OverlayTexture.DEFAULT_UV).next();
    }

    @Override
    public Identifier getTexture(LaserEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }

    @Override
    public boolean shouldRender(T entity, Frustum frustum, double x, double y, double z) {
        return true;
    }
}
