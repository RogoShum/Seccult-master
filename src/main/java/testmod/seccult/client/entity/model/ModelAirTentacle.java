package testmod.seccult.client.entity.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import testmod.seccult.entity.livings.EntityBase;
import testmod.seccult.entity.livings.flying.EntityAirTentacle;
import testmod.seccult.entity.livings.water.EntityWaterTentacle;
import net.minecraft.client.renderer.GlStateManager;

/**
 * ModelAirTentacle - RogoShu,
 * Created using Tabula 7.0.1
 */
public class ModelAirTentacle extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer[] leg1 = new ModelRenderer[6];
    public ModelRenderer[] leg2 = new ModelRenderer[6];
    public ModelRenderer[] leg3 = new ModelRenderer[6];
    public ModelRenderer[] leg4 = new ModelRenderer[6];
    public ModelRenderer[] leg5 = new ModelRenderer[2];
    public ModelRenderer[] leg6 = new ModelRenderer[2];
    public ModelRenderer[] leg7 = new ModelRenderer[2];
    public ModelRenderer[] leg8 = new ModelRenderer[2];
    public ModelRenderer head_1;
    public ModelRenderer body_1;
    public ModelRenderer body_2;

    public ModelAirTentacle() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        
        this.leg1[0] = new ModelRenderer(this, 0, 0);
        this.leg1[0].setRotationPoint(3.0F, 1.8F, 3.0F);
        this.leg1[0].addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(leg1[0], 0.0F, 0.7853981633974483F, 0.0F);
        
        this.leg2[0] = new ModelRenderer(this, 0, 0);
        this.leg2[0].setRotationPoint(-3.0F, 1.8F, -3.0F);
        this.leg2[0].addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(leg2[0], 0.0F, 0.7853981633974483F, 0.0F);
        
        this.leg3[0] = new ModelRenderer(this, 0, 0);
        this.leg3[0].setRotationPoint(-3.0F, 1.8F, 3.0F);
        this.leg3[0].addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(leg3[0], 0.0F, -0.7853981633974483F, 0.0F);
        
        this.leg4[0] = new ModelRenderer(this, 0, 0);
        this.leg4[0].setRotationPoint(3.0F, 1.8F, -3.0F);
        this.leg4[0].addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(leg4[0], 0.0F, -0.7853981633974483F, 0.0F);
        
        for(int i = 0; i < 4; ++i)
        {
        	ModelRenderer[] leg = this.leg1;
        	
        	switch(i)
        	{
        	case 0:
        		leg = this.leg1;
        		break;
        	case 1:
        		leg = this.leg2;
        		break;
        	case 2:
        		leg = this.leg3;
        		break;
        	case 3:
        		leg = this.leg4;
        		break;
        	}

            		leg[1] = new ModelRenderer(this, 0, 0);
            		leg[1].setRotationPoint(0.0F, 6.5F, 0.0F);
            		leg[1].addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
            		
            		leg[2] = new ModelRenderer(this, 0, 0);
            		leg[2].setRotationPoint(0.0F, 6.5F, 0.0F);
            		leg[2].addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);

            		leg[3] = new ModelRenderer(this, 10, 0);
            		leg[3].setRotationPoint(0.0F, 6.5F, 0.0F);
            		leg[3].addBox(-0.5F, 0.0F, -1.0F, 1, 8, 2, 0.0F);

            		leg[4] = new ModelRenderer(this, 20, 0);
            		leg[4].setRotationPoint(0.0F, 6.5F, 0.0F);
            		leg[4].addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);

            		leg[5] = new ModelRenderer(this, 20, 0);
            		leg[5].setRotationPoint(0.0F, 6.5F, 0.0F);
            		leg[5].addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        }
        
        this.leg5[0] = new ModelRenderer(this, 20, 0);
        this.leg5[0].setRotationPoint(0.0F, 1.8F, 2.0F);
        this.leg5[0].addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        
        this.leg5[1] = new ModelRenderer(this, 20, 0);
        this.leg5[1].setRotationPoint(0.0F, 6.8F, 0.0F);
        this.leg5[1].addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(leg5[1], 0.0F, 0.7853981633974483F, 0.0F);
        
        this.leg6[0] = new ModelRenderer(this, 20, 0);
        this.leg6[0].setRotationPoint(0.0F, 1.8F, -2.0F);
        this.leg6[0].addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        
        this.leg6[1] = new ModelRenderer(this, 20, 0);
        this.leg6[1].setRotationPoint(0.0F, 6.8F, 0.0F);
        this.leg6[1].addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(leg6[1], 0.0F, 0.7853981633974483F, 0.0F);
        
        this.leg7[0] = new ModelRenderer(this, 20, 0);
        this.leg7[0].setRotationPoint(2.0F, 1.8F, 0.0F);
        this.leg7[0].addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(leg7[0], 0.0F, 1.5707963267948966F, 0.0F);
        
        this.leg7[1] = new ModelRenderer(this, 20, 0);
        this.leg7[1].setRotationPoint(0.0F, 6.8F, 0.0F);
        this.leg7[1].addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(leg7[1], 0.0F, 0.7853981633974483F, 0.0F);

        this.leg8[0] = new ModelRenderer(this, 20, 0);
        this.leg8[0].setRotationPoint(-2.0F, 1.8F, 0.0F);
        this.leg8[0].addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(leg8[0], 0.0F, 1.5707963267948966F, 0.0F);
        
        this.leg8[1] = new ModelRenderer(this, 20, 0);
        this.leg8[1].setRotationPoint(0.0F, 6.8F, 0.0F);
        this.leg8[1].addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(leg8[1], 0.0F, 0.7853981633974483F, 0.0F);
   
        this.head = new ModelRenderer(this, 0, 38);
        this.head.setRotationPoint(0.0F, -8.5F, 0.0F);
        this.head.addBox(-3.0F, -6.0F, -3.0F, 6, 8, 6, 0.0F);    

        this.body = new ModelRenderer(this, 0, 18);
        this.body.setRotationPoint(0.0F, -85.0F, 0.0F);
        this.body.addBox(-4.0F, -7.0F, -4.0F, 8, 12, 8, 0.0F);

        this.head_1 = new ModelRenderer(this, 0, 52);
        this.head_1.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.head_1.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 4, 0.0F);

        this.body_1 = new ModelRenderer(this, 24, 14);
        this.body_1.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.body_1.addBox(0.0F, 0.0F, 0.0F, 20, 12, 0, 0.0F);
        this.setRotateAngle(body_1, 0.0F, 0.0F, -2.0488420089161434F);
        
        this.body_2 = new ModelRenderer(this, 24, 14);
        this.body_2.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.body_2.addBox(0.0F, 0.0F, 0.0F, 20, 12, 0, 0.0F);
        this.setRotateAngle(body_2, -3.141592653589793F, 0.017453292519943295F, -1.0471975511965976F);
        
        this.leg1[0].addChild(this.leg1[1]);
        this.leg1[1].addChild(this.leg1[2]);
        this.leg1[2].addChild(this.leg1[3]);
        this.leg1[3].addChild(this.leg1[4]);
        this.leg1[4].addChild(this.leg1[5]);
        
        
        this.leg2[0].addChild(this.leg2[1]);
        this.leg2[1].addChild(this.leg2[2]);
        this.leg2[2].addChild(this.leg2[3]);
        this.leg2[3].addChild(this.leg2[4]);
        this.leg2[4].addChild(this.leg2[5]);
        
        this.leg3[0].addChild(this.leg3[1]);
        this.leg3[1].addChild(this.leg3[2]);
        this.leg3[2].addChild(this.leg3[3]);
        this.leg3[3].addChild(this.leg3[4]);
        this.leg3[4].addChild(this.leg3[5]);
        
        
        this.leg4[0].addChild(this.leg4[1]);
        this.leg4[1].addChild(this.leg4[2]);
        this.leg4[2].addChild(this.leg4[3]);
        this.leg4[3].addChild(this.leg4[4]);
        this.leg4[4].addChild(this.leg4[5]);
        
        this.leg5[0].addChild(this.leg5[1]);
        this.leg6[0].addChild(this.leg6[1]);
        this.leg7[0].addChild(this.leg7[1]);  
        this.leg8[0].addChild(this.leg8[1]);
   
        this.head.addChild(this.head_1);
        
        this.head.addChild(this.body_1);
        this.head.addChild(this.body_2);
        
        this.body.addChild(this.leg1[0]);
        this.body.addChild(this.leg2[0]);
        this.body.addChild(this.leg3[0]);
        this.body.addChild(this.leg4[0]);
        this.body.addChild(this.leg5[0]);
        this.body.addChild(this.leg6[0]);
        this.body.addChild(this.leg7[0]);
        this.body.addChild(this.leg8[0]);
        
        this.body.addChild(this.head);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.body.offsetX, this.body.offsetY, this.body.offsetZ);
        GlStateManager.translate(this.body.rotationPointX * f5, this.body.rotationPointY * f5, this.body.rotationPointZ * f5);
        GlStateManager.scale(3.0D, 3.0D, 3.0D);
        GlStateManager.translate(-this.body.offsetX, -this.body.offsetY, -this.body.offsetZ);
        GlStateManager.translate(-this.body.rotationPointX * f5, -this.body.rotationPointY * f5, -this.body.rotationPointZ * f5);
        this.body.render(f5);
        GlStateManager.popMatrix();
        EntityBase at = (EntityBase) entity;
        if(at instanceof EntityWaterTentacle)
        	this.body.rotateAngleX = (entity.rotationPitch - 90) * 0.017453292F;
        
        float swing = at.getSwing();
        if(at.getIsSleeping())
        {
            for(int i = 0; i < 4; ++i)
            {
            	ModelRenderer[] leg = this.leg1;
            	
            	switch(i)
            	{
            	case 0:
            		leg = this.leg1;
            		break;
            	case 1:
            		leg = this.leg2;
            		break;
            	case 2:
            		leg = this.leg3;
            		break;
            	case 3:
            		leg = this.leg4;
            		break;
            	}

            	if(i % 2 == 0)
            	{
            		leg[1].rotateAngleX = -10 * 0.017453292F;
            		leg[2].rotateAngleX = -10 * 0.017453292F;
            		leg[3].rotateAngleX = -15 * 0.017453292F;
            		leg[4].rotateAngleX = -15 * 0.017453292F;
            		leg[5].rotateAngleX = -15 * 0.017453292F;
            	}
            	else
            	{
            		leg[1].rotateAngleX = 10 * 0.017453292F;
            		leg[2].rotateAngleX = 10 * 0.017453292F;
            		leg[3].rotateAngleX = 15 * 0.017453292F;
            		leg[4].rotateAngleX = 15 * 0.017453292F;
            		leg[5].rotateAngleX = 15 * 0.017453292F;
            	}
            }
        }
        else
        {
            for(int i = 0; i < 4; ++i)
            {
            	ModelRenderer[] leg = this.leg1;
            	
            	switch(i)
            	{
            	case 0:
            		leg = this.leg1;
            		break;
            	case 1:
            		leg = this.leg2;
            		break;
            	case 2:
            		leg = this.leg3;
            		break;
            	case 3:
            		leg = this.leg4;
            		break;
            	}

            	if(i % 2 == 0)
            	{
            		leg[1].rotateAngleX = swing * 0.017453292F;
            		leg[2].rotateAngleX = swing * 0.017453292F;
            		leg[3].rotateAngleX = swing * 0.017453292F;
            		leg[4].rotateAngleX = swing * 0.017453292F;
            		leg[5].rotateAngleX = swing * 0.017453292F;
            	}
            	else
            	{
            		leg[1].rotateAngleX = -swing * 0.017453292F;
            		leg[2].rotateAngleX = -swing * 0.017453292F;
            		leg[3].rotateAngleX = -swing * 0.017453292F;
            		leg[4].rotateAngleX = -swing * 0.017453292F;
            		leg[5].rotateAngleX = -swing * 0.017453292F;
            	}
            }
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
