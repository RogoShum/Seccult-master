package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import testmod.seccult.entity.livings.water.EntityFish;

/**
 * ModelGuardian - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ModelFish extends ModelBase {
    public ModelRenderer Body;
    public ModelRenderer fin_up;
    public ModelRenderer fin_down;
    public ModelRenderer fin_left;
    public ModelRenderer fin_right;
    public ModelRenderer head;
    public ModelRenderer tail0;
    public ModelRenderer tail1;
    public ModelRenderer tail2;

    public ModelFish() 
    {
        this.textureWidth = 32;
        this.textureHeight = 32;
     
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.setRotationPoint(0.0F, 5.6F, 0.0F);
        this.Body.addBox(-1.5F, 13.5F, -4.8F, 3, 5, 8, -0.8F);
        
        this.head = new ModelRenderer(this, 8, 14);
        this.head.setRotationPoint(0.4F, -0.5F, -5.65F);
        this.head.addBox(-1.0F, 15.0F, 0.0F, 1, 3, 2, 0.0F);
        
        ////fin////
        
        this.fin_left = new ModelRenderer(this, 0, 0);
        
        this.fin_left.setRotationPoint(0.5F, 14.93F, -2.2F);
        this.fin_left.addBox(0.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);

        this.fin_down = new ModelRenderer(this, 0, 1);
        this.fin_down.setRotationPoint(1.0F, 15.89F, -0.91F);
        this.fin_down.addBox(-1.0F, -4.5F, -1.0F, 0, 3, 2, 0.0F);
        
        this.fin_right = new ModelRenderer(this, 0, 0);
        this.fin_right.mirror = true;
        this.fin_right.setRotationPoint(0.0F, 16.73F, -2.5F);
        this.fin_right.addBox(0.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        
        this.fin_up = new ModelRenderer(this, 0, 1);
        this.fin_up.setRotationPoint(-1.0F, 15.7F, -3.12F);
        this.fin_up.addBox(-1.0F, -4.5F, -1.0F, 0, 4, 2, 0.0F);
        
        ////tail////
        
        this.tail1 = new ModelRenderer(this, 0, 20);
        this.tail1.setRotationPoint(0.0F, 0.5F, 0.8F);
        this.tail1.addBox(-0.5F, -1.2F, 0.0F, 1, 2, 1, 0.0F);
        
        this.tail2 = new ModelRenderer(this, 0, 12);
        this.tail2.setRotationPoint(0.0F, -0.5F, 0.0F);
        this.tail2.addBox(0.0F, 0.0F, 0.0F, 0, 3, 3, 0.0F);
        
        this.tail0 = new ModelRenderer(this, 16, 0);
        this.tail0.setRotationPoint(0.0F, 15.7F, 2.4F);
        this.tail0.addBox(-0.5F, -1.2F, 0.0F, 1, 3, 1, 0.0F);
        
        
        
        this.tail1.addChild(this.tail2);
        
        this.Body.addChild(this.fin_left);
        this.Body.addChild(this.head);
        
        this.tail0.addChild(this.tail1);
        
        this.Body.addChild(this.fin_down);
        this.Body.addChild(this.fin_right);
        this.Body.addChild(this.fin_up);
        this.Body.addChild(this.tail0);
        
        this.setRotateAngle(fin_up, 0.7285004297824331F, 3.141592653589793F, 0.0F);
        this.setRotateAngle(fin_down, -2.504198410761464F, 0.0F, 0.0F);
        
        this.setRotateAngle(tail1, 0.0F, 0.07295476273336297F, 0.0F);
        this.setRotateAngle(tail2, 0.5009094953223726F, 0.091106186954104F, 0.0F);
        this.setRotateAngle(tail0, 0.0F, 0.045553093477052F, 0.0F);
        
        this.setRotateAngle(fin_left, 2.6179938779914944F, 0.2617993877991494F, 1.8325957145940461F);
        this.setRotateAngle(fin_right, 2.6179938779914944F, -0.2617993877991494F, -1.833468379220043F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Body.render(f5);
        EntityFish fish = (EntityFish) entity;
        int swing = fish.getSwing();

        this.setRotateAngle(Body, entity.rotationPitch, f4, 0);
        this.setRotateAngles(tail0, 0.045553093477052F, swing, 0);
        this.setRotateAngles(tail1, 0.5009094953223726F, swing, 0);
        this.setRotateAngles(tail2, 0, swing, 0);
        this.setRotateAngles(fin_left, 2.6179938779914944F, swing, 1);
        
        this.setRotateAngles(fin_left, 2.6179938779914944F, swing, 1);
        this.setRotateAngles(fin_right, 2.6179938779914944F, swing, 1);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    public void setRotateAngles(ModelRenderer modelRenderer, float y, float swing, int mode) {
    	if(mode == 0)
    	{
    		modelRenderer.rotateAngleY = (swing + y) * 0.017453292F;
    	}
    	else
    		modelRenderer.rotateAngleX = (swing + y) * 0.017453292F;
    }
}
