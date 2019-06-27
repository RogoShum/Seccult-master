package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import testmod.seccult.entity.livings.EntityNotoriousBIG;

/**
 * ModelBIG - rogoshum
 * Created using Tabula 7.0.1
 */
public class ModelBIG extends ModelBase {
    public ModelRenderer CoreBody;
    public ModelRenderer shell1;
    public ModelRenderer RightHandCore;
    public ModelRenderer LiftHandCore;
    public ModelRenderer CoreTail;
    public ModelRenderer Eye;
    public ModelRenderer KnotCore;
    public ModelRenderer shell2;
    public ModelRenderer shell3;
    public ModelRenderer shell4;
    public ModelRenderer RightHandArm;
    public ModelRenderer RightHand;
    public ModelRenderer RightHandWheel;
    public ModelRenderer LiftHandArm;
    public ModelRenderer LiftHand;
    public ModelRenderer LiftHandWheel;
    public ModelRenderer Knot;
    public ModelRenderer Knot1;
    public ModelRenderer Knot2;
    public ModelRenderer Knot3;
    public ModelRenderer Knot4;

    public ModelBIG() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.shell4 = new ModelRenderer(this, 26, 52);
        this.shell4.setRotationPoint(0.8F, 4.6F, 3.5F);
        this.shell4.addBox(0.0F, 0.0F, 0.0F, 3, 4, 8, 0.0F);
        this.setRotateAngle(shell4, 1.8668041679331349F, 0.0F, 0.0F);
        this.Knot3 = new ModelRenderer(this, 4, 14);
        this.Knot3.setRotationPoint(2.9F, -1.0F, 4.3F);
        this.Knot3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.Knot = new ModelRenderer(this, 4, 14);
        this.Knot.setRotationPoint(1.8F, -0.9F, 0.1F);
        this.Knot.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.Knot1 = new ModelRenderer(this, 4, 14);
        this.Knot1.setRotationPoint(0.9F, -1.0F, 2.5F);
        this.Knot1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.shell2 = new ModelRenderer(this, 0, 38);
        this.shell2.setRotationPoint(1.0F, -1.1F, 0.6F);
        this.shell2.addBox(0.0F, 0.0F, 0.0F, 5, 4, 10, 0.0F);
        this.setRotateAngle(shell2, 0.22759093446006054F, 0.0F, 0.0F);
        this.CoreTail = new ModelRenderer(this, 41, 1);
        this.CoreTail.setRotationPoint(1.5F, 1.8F, 8.0F);
        this.CoreTail.addBox(0.0F, 0.0F, 0.0F, 6, 6, 5, 0.0F);
        this.Eye = new ModelRenderer(this, 0, 0);
        this.Eye.setRotationPoint(6.0F, 3.0F, -1.0F);
        this.Eye.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.RightHandCore = new ModelRenderer(this, 40, 0);
        this.RightHandCore.setRotationPoint(-4.0F, 1.6F, 2.5F);
        this.RightHandCore.addBox(0.0F, 0.0F, 0.0F, 6, 6, 6, 0.0F);
        this.LiftHandCore = new ModelRenderer(this, 40, 0);
        this.LiftHandCore.mirror = true;
        this.LiftHandCore.setRotationPoint(6.8F, 1.6F, 2.5F);
        this.LiftHandCore.addBox(0.0F, 0.0F, 0.0F, 6, 6, 6, 0.0F);
        this.LiftHand = new ModelRenderer(this, 40, 21);
        this.LiftHand.mirror = true;
        this.LiftHand.setRotationPoint(-1.8F, -0.9F, -2.1F);
        this.LiftHand.addBox(0.0F, 0.0F, 0.0F, 4, 5, 5, 0.0F);
        this.setRotateAngle(LiftHand, 0.05235987755982988F, 0.3490658503988659F, 0.0F);
        this.Knot4 = new ModelRenderer(this, 4, 14);
        this.Knot4.setRotationPoint(1.7F, -1.2F, 6.6F);
        this.Knot4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.RightHand = new ModelRenderer(this, 40, 21);
        this.RightHand.setRotationPoint(0.8F, -0.9F, -2.7F);
        this.RightHand.addBox(0.0F, 0.0F, 0.0F, 4, 5, 5, 0.0F);
        this.setRotateAngle(RightHand, 0.05235987755982988F, -0.3490658503988659F, 0.0F);
        this.Knot2 = new ModelRenderer(this, 4, 14);
        this.Knot2.setRotationPoint(2.9F, -1.0F, 4.3F);
        this.Knot2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.shell3 = new ModelRenderer(this, 0, 52);
        this.shell3.setRotationPoint(1.0F, -2.6F, 2.4F);
        this.shell3.addBox(0.0F, 0.0F, 0.0F, 4, 4, 8, 0.0F);
        this.setRotateAngle(shell3, -0.5462880558742251F, 0.0F, 0.0F);
        this.RightHandArm = new ModelRenderer(this, 40, 12);
        this.RightHandArm.setRotationPoint(-1.3F, 0.8F, -5.0F);
        this.RightHandArm.addBox(0.0F, 0.0F, 0.0F, 3, 3, 6, 0.0F);
        this.setRotateAngle(RightHandArm, -0.17453292519943295F, 0.2617993877991494F, 0.0F);
        this.LiftHandWheel = new ModelRenderer(this, 40, 31);
        this.LiftHandWheel.mirror = true;
        this.LiftHandWheel.setRotationPoint(1.0F, 2.0F, 0.5F);
        this.LiftHandWheel.addBox(0.0F, 0.0F, 0.0F, 2, 4, 4, 0.0F);
        this.RightHandWheel = new ModelRenderer(this, 40, 31);
        this.RightHandWheel.setRotationPoint(1.0F, 2.0F, 0.5F);
        this.RightHandWheel.addBox(0.0F, 0.0F, 0.0F, 2, 4, 4, 0.0F);
        this.LiftHandArm = new ModelRenderer(this, 40, 12);
        this.LiftHandArm.mirror = true;
        this.LiftHandArm.setRotationPoint(4.1F, 0.8F, -5.0F);
        this.LiftHandArm.addBox(0.0F, 0.0F, 0.0F, 3, 3, 6, 0.0F);
        this.setRotateAngle(LiftHandArm, -0.17453292519943295F, -0.2617993877991494F, 0.0F);
        this.shell1 = new ModelRenderer(this, 0, 18);
        this.shell1.setRotationPoint(-1.0F, -1.0F, -1.0F);
        this.shell1.addBox(0.0F, 0.0F, 0.0F, 6, 8, 12, 0.0F);
        this.CoreBody = new ModelRenderer(this, 0, 0);
        this.CoreBody.setRotationPoint(-4.2F, 16.0F, -5.0F);
        this.CoreBody.addBox(0.0F, 0.0F, 0.0F, 10, 8, 10, 0.0F);
        this.KnotCore = new ModelRenderer(this, 4, 14);
        this.KnotCore.setRotationPoint(5.5F, 0.4F, -0.4F);
        this.KnotCore.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.shell3.addChild(this.shell4);
        this.KnotCore.addChild(this.Knot3);
        this.KnotCore.addChild(this.Knot);
        this.KnotCore.addChild(this.Knot1);
        this.shell1.addChild(this.shell2);
        this.CoreBody.addChild(this.CoreTail);
        this.CoreBody.addChild(this.Eye);
        this.CoreBody.addChild(this.RightHandCore);
        this.CoreBody.addChild(this.LiftHandCore);
        this.LiftHandArm.addChild(this.LiftHand);
        this.KnotCore.addChild(this.Knot4);
        this.RightHandArm.addChild(this.RightHand);
        this.KnotCore.addChild(this.Knot2);
        this.shell2.addChild(this.shell3);
        this.RightHandCore.addChild(this.RightHandArm);
        this.LiftHand.addChild(this.LiftHandWheel);
        this.RightHand.addChild(this.RightHandWheel);
        this.LiftHandCore.addChild(this.LiftHandArm);
        this.CoreBody.addChild(this.shell1);
        this.CoreBody.addChild(this.KnotCore);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    	EntityNotoriousBIG big = (EntityNotoriousBIG) entity;
    	float max = (float) big.Max(big.motionX, big.motionY, big.motionZ);
    	float swing = max * big.getSwing();
    	if(swing > 45)
    		swing = 45;
    	if(swing < -45)
    		swing = -45;
    	this.setRotateAngleQ(LiftHandCore, swing, 1);
    	this.setRotateAngleQ(RightHandCore, swing, 0);
    	this.setRotateAngles(CoreBody, f3, f4, big.getMySize());
        this.CoreBody.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    private void setRotateAngleQ(ModelRenderer modelRenderer, float f1, float f3) {
    	if(f3 == 0)
            modelRenderer.rotateAngleX = f1 * 0.017453292F;
    	else
            modelRenderer.rotateAngleX = -f1 * 0.017453292F;
    }
    
    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    private void setRotateAngles(ModelRenderer modelRenderer, float yaw, float pitch, float size) {
        modelRenderer.rotateAngleX = pitch * (1 / size) * 0.017453292F;
        //modelRenderer.rotateAngleY = yaw * 0.017453292F;	
    }
}
