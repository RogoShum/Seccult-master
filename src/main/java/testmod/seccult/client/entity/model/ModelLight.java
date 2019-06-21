package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import testmod.seccult.entity.EntityLight;
import net.minecraft.client.renderer.GlStateManager;

/**
 * ModelSlime - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
public class ModelLight extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer core;
    public ModelRenderer ring1;
    public ModelRenderer ring2;
    public ModelRenderer ring3;
    public ModelRenderer ring4;
    public ModelRenderer ring5;
    public ModelRenderer ring6;
	public ModelRenderer core2;

    public ModelLight(int QAQ) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        if(QAQ > 0) {
        this.core = new ModelRenderer(this, 35, 4);
        this.core.setRotationPoint(0.1F, 18.5F, 0.2F);
        this.core.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(core, 0.0F, 2.6406831582674206F, 0.0F);
        this.ring4 = new ModelRenderer(this, 0, 22);
        this.ring4.setRotationPoint(-1.9F, 0.0F, -5.0F);
        this.ring4.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.ring6 = new ModelRenderer(this, 0, 22);
        this.ring6.setRotationPoint(6.0F, 0.0F, 1.2F);
        this.ring6.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.setRotateAngle(ring6, 0.0F, -2.0943951023931953F, 0.0F);
        this.ring3 = new ModelRenderer(this, 0, 22);
        this.ring3.setRotationPoint(-2.5F, 0.0F, -3.7F);
        this.ring3.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.setRotateAngle(ring3, 0.0F, -2.0943951023931953F, 0.0F);
        this.ring2 = new ModelRenderer(this, 0, 22);
        this.ring2.setRotationPoint(-3.4F, 0.0F, 5.3F);
        this.ring2.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.setRotateAngle(ring2, 0.0F, 2.0943951023931953F, 0.0F);
        this.ring5 = new ModelRenderer(this, 0, 22);
        this.ring5.setRotationPoint(5.0F, 0.0F, 0.0F);
        this.ring5.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.setRotateAngle(ring5, 0.0F, 2.0943951023931953F, 0.0F);
        this.ring1 = new ModelRenderer(this, 0, 22);
        this.ring1.setRotationPoint(-1.6F, 0.0F, 5.0F);
        this.ring1.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.core.addChild(this.ring4);
        this.core.addChild(this.ring6);
        this.core.addChild(this.ring3);
        this.core.addChild(this.ring2);
        this.core.addChild(this.ring5);
        this.core.addChild(this.ring1);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 10.0F, -10.3F);
        this.body.addBox(-4.4F, 16.0F, -4.0F, 8, 8, 8, 0.0F);
        this.setRotateAngle(body, 1.0471975511965976F, 0.5235987755982988F, 0.6632251157578453F);
        this.core2 = new ModelRenderer(this, 35, 4);
        this.core2.setRotationPoint(0.1F, 18.5F, 0.2F);
        this.core2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(core2, 0.0F, 2.6406831582674206F, 0.0F);
        }
        else
        {
        this.core2 = new ModelRenderer(this, 35, 4);
        this.core2.setRotationPoint(0.1F, 18.5F, 0.2F);
        this.core2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(core2, 0.0F, 2.6406831582674206F, 0.0F);
        }
    }
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	EntityLight light = (EntityLight) entity;
        this.core2.render(f5);
    	
    	if(this.body != null) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.body.offsetX, this.body.offsetY, this.body.offsetZ);
        GlStateManager.translate(this.body.rotationPointX * f5, this.body.rotationPointY * f5, this.body.rotationPointZ * f5);
        GlStateManager.scale(0.7D, 0.7D, 0.7D);
        GlStateManager.translate(-this.body.offsetX, -this.body.offsetY, -this.body.offsetZ);
        GlStateManager.translate(-this.body.rotationPointX * f5, -this.body.rotationPointY * f5, -this.body.rotationPointZ * f5);
        this.body.render(f5);
        GlStateManager.popMatrix();
        if(light.getRenderSpeed() == 1) 
        {
        	this.setRotateAngle(core, 0.0F, (2.6406831582674206F + f2 ) + 360 / 12, 0.0F);
        }
        else
        {
        	this.setRotateAngle(core, 0.0F, f2 / 12, 0.0F);
        }
        this.core.render(f5);
    	}
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
