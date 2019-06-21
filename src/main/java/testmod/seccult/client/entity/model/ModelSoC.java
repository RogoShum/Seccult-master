package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelSoC - RogoShum
 * Created using Tabula 7.0.0
 */
public class ModelSoC extends ModelBase {
    public ModelRenderer tail;
    public ModelRenderer head;

    public ModelSoC() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.head = new ModelRenderer(this, 0, 16);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.2F, 18.3F, -4.0F, 8, 8, 8, -2.5F);
        this.tail = new ModelRenderer(this, 0, 0);
        this.tail.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tail.addBox(-4.2F, 18.3F, -1.2F, 8, 8, 8, -2.5F);
        this.head.addChild(this.tail);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	this.setRotateAngle(this.head, f3, f4);
    	this.setRotateAngle(this.tail, f3, f4);
        this.head.render(f5);
        this.tail.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float f3, float f4) {
        modelRenderer.rotateAngleX = f3 * 0.017453292F;
        modelRenderer.rotateAngleY = f4 * 0.017453292F;	
    }
}
