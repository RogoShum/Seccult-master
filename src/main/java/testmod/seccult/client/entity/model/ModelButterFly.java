package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import testmod.seccult.entity.livings.insect.EntityButterfly;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * ModelBat - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ModelButterFly extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer wing;
    public ModelRenderer tentacle;
    public ModelRenderer wing_1;
    public ModelRenderer leg;

    public ModelButterFly() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.leg = new ModelRenderer(this, 22, 0);
        this.leg.setRotationPoint(-1.0F, -1.1F, -5.0F);
        this.leg.addBox(0.0F, 0.0F, 0.0F, 2, 2, 10, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 23.5F, 0.0F);
        this.body.addBox(-1.0F, 0.0F, -6.0F, 2, 2, 12, -0.5F);
        this.setRotateAngle(body, 3.141592653589793F, 0.0F, 0.0F);
        this.wing_1 = new ModelRenderer(this, 20, 16);
        this.wing_1.setRotationPoint(0.6F, 0.8F, 6.0F);
        this.wing_1.addBox(-10.0F, 0.0F, -0.3F, 10, 12, 0, 0.0F);
        this.setRotateAngle(wing_1, -1.5707963267948966F, 0.0F, -2.792526803190927F);
        this.tentacle = new ModelRenderer(this, 0, 16);
        this.tentacle.setRotationPoint(-5.0F, 1.3F, 5.2F);
        this.tentacle.addBox(0.0F, 0.0F, 0.0F, 10, 6, 0, 0.0F);
        this.setRotateAngle(tentacle, 1.0016444577195458F, 0.0F, 0.0F);
        this.wing = new ModelRenderer(this, 20, 16);
        this.wing.mirror = true;
        this.wing.setRotationPoint(-0.5F, 1.0F, 6.0F);
        this.wing.addBox(-0.5F, 0.0F, 0.0F, 10, 12, 0, 0.0F);
        
        this.setRotateAngle(wing, -1.5707963267948966F, 0, 2.792526803190927F);
        this.body.addChild(this.leg);
        this.body.addChild(this.wing_1);
        this.body.addChild(this.tentacle);
        this.body.addChild(this.wing);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	EntityButterfly but = (EntityButterfly) entity;
    	float swing = but.getSwing();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.body.offsetX, this.body.offsetY, this.body.offsetZ);
        GlStateManager.translate(this.body.rotationPointX * f5, this.body.rotationPointY * f5, this.body.rotationPointZ * f5);
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.translate(-this.body.offsetX, -this.body.offsetY, -this.body.offsetZ);
        GlStateManager.translate(-this.body.rotationPointX * f5, -this.body.rotationPointY * f5, -this.body.rotationPointZ * f5);
        this.body.render(f5);
        GlStateManager.popMatrix();
        this.setRotateAngle(body, 3.141592653589793F + entity.rotationPitch * 0.017453292F, f4, 0);
        this.setRotateAngles(wing, 2.792526803190927F, -swing);
        this.setRotateAngles(wing_1, -2.792526803190927F, swing);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    public void setRotateAngles(ModelRenderer modelRenderer, float z, float swing) {
    		modelRenderer.rotateAngleZ = z - swing * 0.017453292F;
    }
}
