package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * NewProject - Undefined
 * Created using Tabula 7.0.1
 */
public class ModelLightingThing extends ModelBase {
    public ModelRenderer ball;
    public ModelRenderer ball1;

    public ModelLightingThing() {
        this.textureWidth = 16;
        this.textureHeight = 16;
        this.ball = new ModelRenderer(this, 0, 0);
        this.ball.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.ball.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
        this.ball1 = new ModelRenderer(this, 0, 6);
        this.ball1.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.ball1.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.ball.render(f5);
        this.ball1.render(f5);
        
        this.ball.rotateAngleX = entity.rotationPitch * 0.017453292F;
        this.ball.rotateAngleY = entity.rotationYaw * 0.017453292F;
        
        this.ball1.rotateAngleX = entity.rotationPitch * 0.017453292F;
        this.ball1.rotateAngleY = entity.rotationYaw * 0.017453292F;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
