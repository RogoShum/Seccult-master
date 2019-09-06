package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Arrow - rogoshum
 * Created using Tabula 7.0.1
 */
public class ModelArrow extends ModelBase {
    public ModelRenderer shape1;
    public ModelRenderer shape1_1;
    public ModelRenderer shape1_2;

    public ModelArrow() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.shape1_1 = new ModelRenderer(this, 0, 0);
        this.shape1_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1_1.addBox(-4.0F, -2.5F, 0.0F, 16, 5, 0, 0.0F);
        this.setRotateAngle(shape1_1, -1.5707963267948966F, 0.0F, 0.0F);
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0.0F, 16.5F, 0.0F);
        this.shape1.addBox(-4.0F, -2.5F, 0.0F, 16, 5, 0, 0.0F);
        this.setRotateAngle(shape1, 0.0F, 1.5707963267948966F, 0.0F);
        this.shape1_2 = new ModelRenderer(this, 0, 5);
        this.shape1_2.setRotationPoint(-3F, 0.0F, 0.0F);
        this.shape1_2.addBox(-2.5F, -2.5F, 0.0F, 5, 5, 0, 0.0F);
        this.setRotateAngle(shape1_2, 0.0F, 1.5707963267948966F, 0.0F);
        this.shape1.addChild(this.shape1_1);
        this.shape1_1.addChild(this.shape1_2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.shape1.render(f5);
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
