// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.17
// Paste this class into your mod and generate all required imports

package alphamode.core.nebula.entitys.rocket;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;

public class rocket extends SinglePartEntityModel<Entity> {
    private final ModelPart root;

    //private final ModelPart bb_main;

    public rocket(ModelPart root) {
        this.root = root;

        // TODO define your model parts here - 'this.body = root.getChild("body");' etc
        //this.body = root.getChild("body");
    }

    public static TexturedModelData getTexturedModelData() {
        // TODO replace 'undefined' with 'root'

        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData bb_main = root.addChild(
		    "main",
		    ModelPartBuilder.create()
		        .uv(0, 0)
		        .mirrored(false)
		        .cuboid(-0.5F, -4.0F, -7.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 0)
		        .mirrored(false)
		        .cuboid(-0.5F, -6.0F, -6.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)),
		    ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F)
		);

        return TexturedModelData.of(data, 16, 16);
    }

    @Override
    public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    
}