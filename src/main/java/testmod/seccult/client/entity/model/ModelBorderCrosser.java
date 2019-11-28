package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import testmod.seccult.entity.EntityBorderCrosser;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * ModelBorderCrosser - RogoShum
 * Created using Tabula 7.0.1
 */
public class ModelBorderCrosser extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer cycle;
    public ModelRenderer ring_1;
    public ModelRenderer ring_2;
    public ModelRenderer ring_3;
    public ModelRenderer cycle_2;

    public ModelBorderCrosser() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.ring_1 = new ModelRenderer(this, 0, 24);
        this.ring_1.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.ring_1.addBox(-8.0F, -8.0F, -8.0F, 16, 3, 16, 0.0F);
        this.setRotateAngle(ring_1, 0.0F, 0.5235987755982988F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.body.addBox(-6.0F, -7.0F, -6.0F, 12, 12, 12, 0.0F);
        this.cycle_2 = new ModelRenderer(this, 0, 43);
        this.cycle_2.setRotationPoint(0.0F, 8.4F, 0.0F);
        this.cycle_2.addBox(-6.0F, -0.5F, -6.0F, 12, 1, 12, 0.0F);
        this.setRotateAngle(cycle_2, 0.0F, 0.7853981633974483F, 0.0F);
        this.ring_3 = new ModelRenderer(this, 0, 24);
        this.ring_3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ring_3.addBox(-8.0F, -1.5F, -8.0F, 16, 3, 16, 0.0F);
        this.setRotateAngle(ring_3, 0.0F, 0.5235987755982988F, 1.5707963267948966F);
        this.cycle = new ModelRenderer(this, 0, 43);
        this.cycle.setRotationPoint(0.0F, 8.4F, 0.0F);
        this.cycle.addBox(-6.0F, -0.5F, -6.0F, 12, 1, 12, 0.0F);
        this.ring_2 = new ModelRenderer(this, 0, 24);
        this.ring_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ring_2.addBox(-8.0F, -1.5F, -8.0F, 16, 3, 16, 0.0F);
        this.setRotateAngle(ring_2, 0.0F, 1.0471975511965976F, 0.0F);
        this.body.addChild(this.ring_1);
        this.body.addChild(this.cycle_2);
        this.body.addChild(this.ring_3);
        this.body.addChild(this.cycle);
        this.body.addChild(this.ring_2);
    }

    @Override
    public void render(Entity entity, float x, float y, float z, float f3, float f4, float f5) { 
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.body.offsetX, this.body.offsetY, this.body.offsetZ);
        GlStateManager.translate(this.body.rotationPointX * f5, this.body.rotationPointY * f5, this.body.rotationPointZ * f5);
        GlStateManager.scale(4.8D, 4.4D, 4.8D);
        GlStateManager.translate(-this.body.offsetX, -this.body.offsetY, -this.body.offsetZ);
        GlStateManager.translate(-this.body.rotationPointX * f5, -this.body.rotationPointY * f5, -this.body.rotationPointZ * f5);
        
        int tickE = entity.ticksExisted % 360;
        float tick = (float)tickE / 360;
        tick *= 5;
        
    	int size = entity.ticksExisted * 2;
    	if(size > 180)
    		size = 180;

        
        if(entity instanceof EntityBorderCrosser)
        {
        	EntityBorderCrosser crosser = (EntityBorderCrosser) entity;
        	 this.ring_1.rotateAngleY = crosser.getTICK() * -0.5F + 0.5235987755982988F + tick * 2;
             this.ring_2.rotateAngleY = crosser.getTICK() * -0.5F + 1.0471975511965976F + tick * -2;
             this.ring_2.rotateAngleZ = crosser.getTICK() * -0.5F + 1.0471975511965976F + tick * -2;
             this.ring_3.rotateAngleY = crosser.getTICK() * 0.1F + 0.5235987755982988F + tick;
             this.ring_3.rotateAngleZ = crosser.getTICK() * 0.2F + 1.5707963267948966F + tick;
             this.cycle_2.rotateAngleY = crosser.getTICK() * -0.9F + tick * -1.2F;
             this.cycle.rotateAngleY = crosser.getTICK() * -0.6F + tick;
             this.body.rotateAngleY = crosser.getTICK() * 0.5F + tick;
             this.body.rotateAngleZ = (float)size * 0.017453292F;
             this.body.render(f5);
        }
       
        GlStateManager.popMatrix();
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
