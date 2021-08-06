package alphamode.core.nebula.entitys.rocket.model;

import alphamode.core.nebula.entitys.rocket.RocketBase;
import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;

public class RocketModel extends EntityModel<RocketBase> {

    private final ModelPart base;

    public RocketModel(ModelPart modelPart) {
        this.base = modelPart.getChild(EntityModelPartNames.CUBE);
    }
    @Override
    public void setAngles(RocketBase entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.base).forEach((modelRenderer) -> {
            modelRenderer.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        });
    }


}
