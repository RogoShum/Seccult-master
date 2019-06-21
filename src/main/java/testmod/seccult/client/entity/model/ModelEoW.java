package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;

/**
 * ModelSlime - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
public class ModelEoW extends ModelBase {
    public ModelRenderer Core;
    public ModelRenderer Head;
    public ModelRenderer Tail;
    public ModelRenderer Tail2;
    public ModelRenderer Tail3;
    public ModelRenderer Tail4;
    public ModelRenderer teeth_small_1;
    public ModelRenderer teeth_small_2;
    public ModelRenderer teeth_small_3;
    public ModelRenderer teeth_small_4;
    public ModelRenderer teeth_large_1;
    public ModelRenderer teeth_large_2;
    public ModelRenderer teeth_large_3;
    public ModelRenderer teeth_large_4;
    public ModelRenderer teeth_large_5;
    public ModelRenderer teeth_large_6;
    public ModelRenderer teeth_large_7;
    public ModelRenderer teeth_large_8;
    public ModelRenderer teeth_small_5;
    public ModelRenderer teeth_small_6;
    public ModelRenderer teeth_large_9;
    public ModelRenderer teeth_large_10;

    public ModelEoW() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Core = new ModelRenderer(this, 0, 12);
        this.Core.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Core.addBox(-5.0F, 5.5F, -5.0F, 10, 10, 10, 8.8F);
        this.teeth_large_6 = new ModelRenderer(this, 32, 0);
        this.teeth_large_6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_large_6.addBox(-23.0F, 11.4F, -27.8F, 4, 4, 6, 0.0F);

        this.teeth_large_1 = new ModelRenderer(this, 30, 0);
        this.teeth_large_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_large_1.addBox(4.0F, 10.0F, -25.0F, 7, 7, 8, 0.0F);

        this.teeth_large_10 = new ModelRenderer(this, 32, 0);
        this.teeth_large_10.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_large_10.addBox(-23.6F, 11.8F, -33.8F, 2, 2, 6, 0.0F);

        this.teeth_small_4 = new ModelRenderer(this, 32, 2);
        this.teeth_small_4.mirror = true;
        this.teeth_small_4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_small_4.addBox(-10.6F, 11.0F, -26.6F, 2, 3, 8, 0.0F);

        this.teeth_small_6 = new ModelRenderer(this, 37, 4);
        this.teeth_small_6.mirror = true;
        this.teeth_small_6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_small_6.addBox(-16.6F, 11.0F, -25.5F, 1, 2, 3, 0.0F);

        this.teeth_large_7 = new ModelRenderer(this, 32, 0);
        this.teeth_large_7.mirror = true;
        this.teeth_large_7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_large_7.addBox(21.3F, 11.7F, -30.4F, 3, 3, 6, 0.0F);

        this.teeth_large_5 = new ModelRenderer(this, 32, 0);
        this.teeth_large_5.mirror = true;
        this.teeth_large_5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_large_5.addBox(18.7F, 11.4F, -27.8F, 4, 4, 6, 0.0F);

        this.Head = new ModelRenderer(this, 0, 20);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-5.0F, 5.0F, -16.0F, 10, 10, 2, 4.0F);
        this.teeth_small_1 = new ModelRenderer(this, 32, 2);
        this.teeth_small_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_small_1.addBox(0.0F, 10.0F, -25.0F, 4, 7, 6, 0.0F);

        this.teeth_small_3 = new ModelRenderer(this, 32, 0);
        this.teeth_small_3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_small_3.addBox(9.5F, 11.0F, -26.6F, 2, 3, 8, 0.0F);

        this.Tail3 = new ModelRenderer(this, 40, 20);
        this.Tail3.mirror = true;
        this.Tail3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Tail3.addBox(-5.0F, 12.0F, 20.0F, 10, 10, 2, 1.0F);
        this.teeth_large_4 = new ModelRenderer(this, 32, 0);
        this.teeth_large_4.mirror = true;
        this.teeth_large_4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_large_4.addBox(-17.0F, 11.0F, -27.0F, 5, 5, 8, 0.0F);

        this.teeth_large_9 = new ModelRenderer(this, 32, 0);
        this.teeth_large_9.mirror = true;
        this.teeth_large_9.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_large_9.addBox(21.4F, 11.8F, -33.8F, 2, 2, 6, 0.0F);

        this.Tail2 = new ModelRenderer(this, 40, 20);
        this.Tail2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Tail2.addBox(-5.0F, 9.3F, 17.0F, 10, 10, 2, 2.5F);
        
        this.teeth_small_2 = new ModelRenderer(this, 32, 2);
        this.teeth_small_2.mirror = true;
        this.teeth_small_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_small_2.addBox(-3.0F, 10.0F, -25.0F, 4, 7, 6, 0.0F);

        this.teeth_small_5 = new ModelRenderer(this, 37, 4);
        this.teeth_small_5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_small_5.addBox(16.5F, 11.0F, -24.9F, 1, 2, 3, 0.0F);

        this.Tail4 = new ModelRenderer(this, 40, 20);
        this.Tail4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Tail4.addBox(-5.0F, 24.0F, 22F, 10, 10, 2, 1.1F);
        
        this.teeth_large_8 = new ModelRenderer(this, 32, 0);
        this.teeth_large_8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_large_8.addBox(-24.5F, 11.7F, -30.4F, 3, 3, 6, 0.0F);

        this.teeth_large_2 = new ModelRenderer(this, 30, 0);
        this.teeth_large_2.mirror = true;
        this.teeth_large_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_large_2.addBox(-11.0F, 10.0F, -25.0F, 7, 7, 8, 0.0F);

        this.Tail = new ModelRenderer(this, 0, 20);
        this.Tail.mirror = true;
        this.Tail.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Tail.addBox(-5.0F, 6.0F, 19.0F, 10, 10, 2, 4.4F);
        this.teeth_large_3 = new ModelRenderer(this, 32, 0);
        this.teeth_large_3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth_large_3.addBox(12.0F, 11.0F, -27.0F, 5, 5, 8, 0.0F);

        this.Head.addChild(this.teeth_large_6);
        this.Head.addChild(this.teeth_large_1);
        this.Head.addChild(this.teeth_large_10);
        this.Head.addChild(this.teeth_small_4);
        this.Head.addChild(this.teeth_small_6);
        this.Head.addChild(this.teeth_large_7);
        this.Head.addChild(this.teeth_large_5);
        this.Head.addChild(this.teeth_small_1);
        this.Head.addChild(this.teeth_small_3);
        this.Head.addChild(this.teeth_large_4);
        this.Head.addChild(this.teeth_large_9);
        this.Head.addChild(this.teeth_small_2);
        this.Head.addChild(this.teeth_small_5);
        this.Head.addChild(this.teeth_large_8);
        this.Head.addChild(this.teeth_large_2);
        this.Head.addChild(this.teeth_large_3);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	
        this.setRotateAngle(teeth_large_6, 0.0F, -0.3490658503988659F, 0.0F);
        this.setRotateAngle(teeth_large_1, 0.0F, -0.2617993877991494F, 0.0F);
        this.setRotateAngle(teeth_large_10, 0.0F, -0.4363323129985824F, 0.0F);
        this.setRotateAngle(teeth_small_4, 0.0F, -0.2617993877991494F, 0.0F);
        this.setRotateAngle(teeth_small_6, 0.0F, -0.5235987755982988F, 0.0F);
        this.setRotateAngle(teeth_large_7, 0.0F, 0.4363323129985824F, 0.0F);
        this.setRotateAngle(teeth_large_5, 0.0F, 0.3490658503988659F, 0.0F);
        this.setRotateAngle(teeth_small_1, 0.0F, -0.08726646259971647F, 0.0F);
        this.setRotateAngle(teeth_small_3, 0.0F, 0.2617993877991494F, 0.0F);
        this.setRotateAngle(teeth_large_4, 0.0F, -0.08726646259971647F, 0.0F);
        this.setRotateAngle(teeth_large_9, 0.0F, 0.4363323129985824F, 0.0F);
        this.setRotateAngle(teeth_small_2, 0.0F, 0.08726646259971647F, 0.0F);
        this.setRotateAngle(teeth_small_5, 0.0F, 0.5235987755982988F, 0.0F);
        this.setRotateAngle(teeth_large_8, 0.0F, -0.4363323129985824F, 0.0F);
        this.setRotateAngle(teeth_large_2, 0.0F, 0.2617993877991494F, 0.0F);
        this.setRotateAngle(teeth_large_3, 0.0F, 0.08726646259971647F, 0.0F);
    	
        this.setRotateAngles(Core, f3, f4);
        this.setRotateAngles(Head, f3, f4);
        this.setRotateAngles(Tail, f3, f4);
        this.setRotateAngles(Tail2, f3, f4);
        this.setRotateAngles(Tail3, f3, f4);
        this.setRotateAngles(Tail4, f3, f4);
        
        this.Core.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.Head.offsetX, this.Head.offsetY, this.Head.offsetZ);
        GlStateManager.translate(this.Head.rotationPointX * f5, this.Head.rotationPointY * f5, this.Head.rotationPointZ * f5);
        GlStateManager.scale(1.0D, 1.0D, 0.9D);
        GlStateManager.translate(-this.Head.offsetX, -this.Head.offsetY, -this.Head.offsetZ);
        GlStateManager.translate(-this.Head.rotationPointX * f5, -this.Head.rotationPointY * f5, -this.Head.rotationPointZ * f5);
        this.Head.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.Tail3.offsetX, this.Tail3.offsetY, this.Tail3.offsetZ);
        GlStateManager.translate(this.Tail3.rotationPointX * f5, this.Tail3.rotationPointY * f5, this.Tail3.rotationPointZ * f5);
        GlStateManager.scale(1.0D, 1.0D, 1.4D);
        GlStateManager.translate(-this.Tail3.offsetX, -this.Tail3.offsetY, -this.Tail3.offsetZ);
        GlStateManager.translate(-this.Tail3.rotationPointX * f5, -this.Tail3.rotationPointY * f5, -this.Tail3.rotationPointZ * f5);
        this.Tail3.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.Tail2.offsetX, this.Tail2.offsetY, this.Tail2.offsetZ);
        GlStateManager.translate(this.Tail2.rotationPointX * f5, this.Tail2.rotationPointY * f5, this.Tail2.rotationPointZ * f5);
        GlStateManager.scale(1.0D, 1.0D, 1.3D);
        GlStateManager.translate(-this.Tail2.offsetX, -this.Tail2.offsetY, -this.Tail2.offsetZ);
        GlStateManager.translate(-this.Tail2.rotationPointX * f5, -this.Tail2.rotationPointY * f5, -this.Tail2.rotationPointZ * f5);
        this.Tail2.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.Tail4.offsetX, this.Tail4.offsetY, this.Tail4.offsetZ);
        GlStateManager.translate(this.Tail4.rotationPointX * f5, this.Tail4.rotationPointY * f5, this.Tail4.rotationPointZ * f5);
        GlStateManager.scale(0.7D, 0.7D, 1.5D);
        GlStateManager.translate(-this.Tail4.offsetX, -this.Tail4.offsetY, -this.Tail4.offsetZ);
        GlStateManager.translate(-this.Tail4.rotationPointX * f5, -this.Tail4.rotationPointY * f5, -this.Tail4.rotationPointZ * f5);
        this.Tail4.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.Tail.offsetX, this.Tail.offsetY, this.Tail.offsetZ);
        GlStateManager.translate(this.Tail.rotationPointX * f5, this.Tail.rotationPointY * f5, this.Tail.rotationPointZ * f5);
        GlStateManager.scale(1.0D, 1.0D, 0.8D);
        GlStateManager.translate(-this.Tail.offsetX, -this.Tail.offsetY, -this.Tail.offsetZ);
        GlStateManager.translate(-this.Tail.rotationPointX * f5, -this.Tail.rotationPointY * f5, -this.Tail.rotationPointZ * f5);
        this.Tail.render(f5);
        GlStateManager.popMatrix();
    }

    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
	}

	private void setRotateAngles(ModelRenderer modelRenderer, float f4, float headPitch) {
        modelRenderer.rotateAngleX = headPitch * 0.017453292F;
        modelRenderer.rotateAngleY = f4 * 0.017453292F;	
	}
	/**
     * This is a helper function from Tabula to set the rotation of model parts
     */
}
