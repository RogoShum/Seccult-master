package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelBubble - Undefined
 * Created using Tabula 7.0.1
 */
public class ModelBubble extends ModelBase {
    public ModelRenderer shape1;

    public ModelBubble() {
        this.textureWidth = 32;
        this.textureHeight = 16;
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.shape1.addBox(-3.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.shape1.render(f5);
        this.shape1.rotateAngleX = entity.rotationPitch * 0.017453292F;
        this.shape1.rotateAngleY = entity.rotationYaw * 0.017453292F;
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
