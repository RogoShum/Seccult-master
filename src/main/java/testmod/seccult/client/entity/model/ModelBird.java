package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.livings.EntityBase;
import testmod.seccult.entity.livings.flying.EntityBird;

/**
 * ModelParrot - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ModelBird extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer left_wing;
    public ModelRenderer tail;
    public ModelRenderer head;
    public ModelRenderer right_leg;
    public ModelRenderer right_wing;
    public ModelRenderer left_leg;
    public ModelRenderer cover_head;
    public ModelRenderer face;
    public ModelRenderer mouth;
    public ModelRenderer hair;
    public ModelRenderer face_;
    
    private ModelBird.State state = ModelBird.State.AWAKING;

    public ModelBird() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.right_wing = new ModelRenderer(this, 19, 8);
        this.right_wing.setRotationPoint(-1.5F, 18.84F, -1.56F);
        this.right_wing.addBox(-0.5F, 0.0F, -1.5F, 1, 5, 3, 0.0F);
        this.setRotateAngle(right_wing, -1.0471975511965976F, -3.141592653589793F, 0.08726646259971647F);
        this.tail = new ModelRenderer(this, 22, 1);
        this.tail.setRotationPoint(0.0F, 20.17F, 3.16F);
        this.tail.addBox(-1.5F, -1.0F, -1.0F, 3, 4, 1, 0.0F);
        this.setRotateAngle(tail, 1.4114477660878142F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 2, 2);
        this.head.setRotationPoint(0.0F, 17.19F, -1.86F);
        this.head.addBox(-1.0F, -1.5F, -1.0F, 2, 3, 2, -0.2F);
        this.setRotateAngle(head, 0.5462880558742251F, 0.0F, 0.0F);
        this.mouth = new ModelRenderer(this, 16, 7);
        this.mouth.setRotationPoint(0.0F, -0.75F, -1.15F);
        this.mouth.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(mouth, -2.0032889154390916F, 0.0F, 0.0F);
        this.face_ = new ModelRenderer(this, 11, 7);
        this.face_.setRotationPoint(-0.2F, -0.5F, -1.2F);
        this.face_.addBox(-0.5F, -1.0F, -0.5F, 1, 2, 1, 0.0F);
        this.right_leg = new ModelRenderer(this, 14, 18);
        this.right_leg.setRotationPoint(-1.0F, 22.0F, 0.25F);
        this.right_leg.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(right_leg, 0.17453292519943295F, 0.0F, 0.0F);
        this.hair = new ModelRenderer(this, 2, 18);
        this.hair.setRotationPoint(0.0F, -0.15F, 0.15F);
        this.hair.addBox(0.0F, -4.0F, -2.0F, 0, 5, 4, 0.0F);
        this.setRotateAngle(hair, -1.0471975511965976F, 0.0F, 0.0F);
        this.left_leg = new ModelRenderer(this, 14, 18);
        this.left_leg.setRotationPoint(1.0F, 22.0F, 0.25F);
        this.left_leg.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(left_leg, 0.17453292519943295F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 2, 8);
        this.body.setRotationPoint(0.0F, 17.7F, -2.6F);
        this.body.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
        this.setRotateAngle(body, 0.9560913642424937F, 0.0F, 0.0F);
        this.face = new ModelRenderer(this, 11, 7);
        this.face.setRotationPoint(0.2F, -0.5F, -1.2F);
        this.face.addBox(-0.5F, -1.0F, -0.5F, 1, 2, 1, 0.0F);
        this.left_wing = new ModelRenderer(this, 19, 8);
        this.left_wing.setRotationPoint(1.5F, 18.84F, -1.56F);
        this.left_wing.addBox(-0.5F, 0.0F, -1.5F, 1, 5, 3, 0.0F);
        this.setRotateAngle(left_wing, -1.0471975511965976F, -3.141592653589793F, -0.08726646259971647F);
        this.cover_head = new ModelRenderer(this, 10, 0);
        this.cover_head.setRotationPoint(0.0F, -1.3F, 0.0F);
        this.cover_head.addBox(-1.0F, -0.5F, -2.0F, 2, 1, 4, 0.0F);
        this.setRotateAngle(cover_head, -0.36425021489121656F, 0.0F, 0.0F);
        this.head.addChild(this.mouth);
        this.head.addChild(this.face_);
        this.head.addChild(this.hair);
        this.head.addChild(this.face);
        this.head.addChild(this.cover_head);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    { 
    	
        this.right_wing.render(scale);
        this.tail.render(scale);
        this.head.render(scale);
        this.right_leg.render(scale);
        this.left_leg.render(scale);
        this.body.render(scale);
        this.left_wing.render(scale);
    }

    @Override
    	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
    			float headPitch, float scaleFactor, Entity entityIn) {
    		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    		
    		EntityBird bird = (EntityBird) entityIn;
    		if(this.state == ModelBird.State.SLEEPING)
    		{

    			this.head.rotateAngleX = bird.headRotationX;
            	this.head.rotateAngleY = 0;
            	right_leg.rotateAngleX = 0.17453292519943295F;
    			left_leg.rotateAngleX = 0.17453292519943295F;

    			this.left_wing.rotateAngleY = -3.141592653589793F;
    			this.left_wing.rotateAngleZ = -0.08726646259971647F;

    			this.right_wing.rotateAngleY = -3.141592653589793F;
    			this.right_wing.rotateAngleZ = 0.08726646259971647F;
    			
                this.tail.rotateAngleX = 1.4114477660878142F;
    		}
    		
    		if(this.state ==  ModelBird.State.FLYING)
    		{
    			this.head.rotateAngleX = 0.5462880558742251F + (headPitch * 0.017453292F);
    			this.head.rotateAngleY = netHeadYaw * 0.017453292F;
    			this.tail.rotateAngleX = 1.4114477660878142F + (float)(-bird.motionY);
    			right_leg.rotateAngleX = 0.9F;
    			left_leg.rotateAngleX = 0.9F;
    			this.left_wing.rotateAngleY = -3.141592653589793F + (bird.getSwing() * 0.017453292F / 2);
    			this.left_wing.rotateAngleZ = -0.08726646259971647F - (bird.getSwing() * 0.017453292F);

    			this.right_wing.rotateAngleY = -3.141592653589793F - (bird.getSwing() * 0.017453292F / 2);
    			this.right_wing.rotateAngleZ = 0.08726646259971647F + (bird.getSwing() * 0.017453292F);
    		}

    		if(this.state == ModelBird.State.AWAKING)
    		{
    			this.head.rotateAngleX = 0.5462880558742251F + (headPitch * 0.017453292F);
    			this.head.rotateAngleY = netHeadYaw * 0.017453292F;
    			this.tail.rotateAngleX = 1.4114477660878142F;
    			right_leg.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount) * 3;
    			left_leg.rotateAngleX =  (MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount) * 3;

    			this.left_wing.rotateAngleY = -3.141592653589793F;
    			this.left_wing.rotateAngleZ = -0.08726646259971647F;

    			this.right_wing.rotateAngleY = -3.141592653589793F;
    			this.right_wing.rotateAngleZ = 0.08726646259971647F;
    		}
    	}
    
    @Override
    public void setLivingAnimations(EntityLivingBase entityliving, float limbSwing, float limbSwingAmount,
    		float partialTickTime) {
    	super.setLivingAnimations(entityliving, limbSwing, limbSwingAmount, partialTickTime);
    	
    	EntityBird bird = (EntityBird) entityliving;
        if(bird.onGround)
        {
            if(bird.getIsSleeping())
            {
            	this.head.setRotationPoint(0.0F, 17.49F, -2.16F);
            	this.state = ModelBird.State.SLEEPING;
            }
            else
            {
            	this.head.setRotationPoint(0.0F, 17.19F, -1.86F);
            	this.state = ModelBird.State.AWAKING;
            }
        }
        else
        {
        	this.head.setRotationPoint(0.0F, 17.19F, -1.86F);
        	this.state = ModelBird.State.FLYING;
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
    
    @SideOnly(Side.CLIENT)
    static enum State
    {
        FLYING,
        SLEEPING,
        AWAKING
    }
}
