package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * SpaceManager - RogoShum
 * Created using Tabula 7.0.1
 */
public class ModelSpaceManager extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer up_1;
    public ModelRenderer right_1;
    public ModelRenderer left_1;
    public ModelRenderer down_1;
    public ModelRenderer head;
    public ModelRenderer up_2;
    public ModelRenderer up_3;
    public ModelRenderer right_2;
    public ModelRenderer right_3;
    public ModelRenderer left_2;
    public ModelRenderer left_3;
    public ModelRenderer down_2;
    public ModelRenderer down_3;

    public ModelSpaceManager() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.left_1 = new ModelRenderer(this, 16, 32);
        this.left_1.setRotationPoint(6.0F, 0.0F, 0.5F);
        this.left_1.addBox(-2.0F, -8.0F, 0.0F, 4, 8, 1, 0.0F);
        this.setRotateAngle(left_1, -0.8651597102135892F, 0.0F, 1.5707963267948966F);
        this.up_3 = new ModelRenderer(this, 0, 22);
        this.up_3.setRotationPoint(0.0F, -6.7F, 0.0F);
        this.up_3.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 2, 0.0F);
        this.setRotateAngle(up_3, 1.8212510744560826F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.body.addBox(-6.0F, -6.0F, 0.0F, 12, 12, 1, 0.0F);
        this.setRotateAngle(body, 0.2617993877991494F, 0.0F, 0.0F);
        this.left_3 = new ModelRenderer(this, 16, 41);
        this.left_3.setRotationPoint(0.0F, -6.7F, 0.0F);
        this.left_3.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 2, 0.0F);
        this.setRotateAngle(left_3, 1.0927506446736497F, 0.0F, 0.0F);
        this.down_2 = new ModelRenderer(this, 16, 22);
        this.down_2.setRotationPoint(0.0F, -3.0F, -0.5F);
        this.down_2.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 2, 0.0F);
        this.left_2 = new ModelRenderer(this, 16, 41);
        this.left_2.setRotationPoint(0.0F, -3.0F, -0.5F);
        this.left_2.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 2, 0.0F);
        this.down_3 = new ModelRenderer(this, 16, 22);
        this.down_3.setRotationPoint(0.0F, -6.7F, 0.0F);
        this.down_3.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 2, 0.0F);
        this.setRotateAngle(down_3, 0.8196066167365371F, 0.0F, 0.0F);
        this.down_1 = new ModelRenderer(this, 16, 13);
        this.down_1.setRotationPoint(0.0F, 6.0F, 0.5F);
        this.down_1.addBox(-2.0F, -8.0F, 0.0F, 4, 8, 1, 0.0F);
        this.setRotateAngle(down_1, -0.22759093446006054F, 0.0F, -3.141592653589793F);
        this.right_3 = new ModelRenderer(this, 0, 41);
        this.right_3.setRotationPoint(0.0F, -6.7F, 0.0F);
        this.right_3.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 2, 0.0F);
        this.setRotateAngle(right_3, 1.0927506446736497F, 0.0F, 0.0F);
        this.up_1 = new ModelRenderer(this, 0, 13);
        this.up_1.setRotationPoint(0.0F, -6.0F, 0.5F);
        this.up_1.addBox(-2.0F, -8.0F, 0.0F, 4, 8, 1, 0.0F);
        this.setRotateAngle(up_1, -0.9105382707654417F, 0.0F, 0.0F);
        this.up_2 = new ModelRenderer(this, 0, 22);
        this.up_2.setRotationPoint(0.0F, -3.0F, -0.5F);
        this.up_2.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 2, 0.0F);
        this.right_2 = new ModelRenderer(this, 0, 41);
        this.right_2.setRotationPoint(0.0F, -3.0F, -0.5F);
        this.right_2.addBox(-3.0F, -8.0F, 0.0F, 6, 8, 2, 0.0F);
        this.head = new ModelRenderer(this, 28, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.head.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0.0F);
        this.setRotateAngle(head, -0.2617993877991494F, 0.0F, 0.0F);
        this.right_1 = new ModelRenderer(this, 0, 32);
        this.right_1.setRotationPoint(-6.0F, 0.0F, 0.5F);
        this.right_1.addBox(-2.0F, -8.0F, 0.0F, 4, 8, 1, 0.0F);
        this.setRotateAngle(right_1, -0.8651597102135892F, 0.0F, -1.5707963267948966F);
        this.body.addChild(this.left_1);
        this.up_2.addChild(this.up_3);
        this.left_2.addChild(this.left_3);
        this.down_1.addChild(this.down_2);
        this.left_1.addChild(this.left_2);
        this.down_2.addChild(this.down_3);
        this.body.addChild(this.down_1);
        this.right_2.addChild(this.right_3);
        this.body.addChild(this.up_1);
        this.up_1.addChild(this.up_2);
        this.right_1.addChild(this.right_2);
        this.body.addChild(this.head);
        this.body.addChild(this.right_1);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
    	this.head.rotateAngleX = headPitch * 0.017453292F;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		
		left_1.rotateAngleX = MathHelper.cos(limbSwing * 0.1662F) * limbSwingAmount -0.8651597102135892F + this.head.rotateAngleX / 3;
		right_1.rotateAngleX = MathHelper.cos(limbSwing * 0.1662F) * limbSwingAmount -0.8651597102135892F + this.head.rotateAngleX / 3;
		up_1.rotateAngleX = MathHelper.cos(limbSwing * 0.1662F) * -limbSwingAmount -0.9105382707654417F + this.head.rotateAngleX / 3;
		down_1.rotateAngleX = MathHelper.cos(limbSwing * 0.1662F) * -limbSwingAmount -0.22759093446006054F + this.head.rotateAngleX / 3;
		
		left_3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount + 1.0927506446736497F + this.head.rotateAngleX / 2;
		right_3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount + 1.0927506446736497F + this.head.rotateAngleX / 2;
		up_3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * -limbSwingAmount + 1.8212510744560826F + this.head.rotateAngleX / 2;
		down_3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * -limbSwingAmount + 1.0927506446736497F + this.head.rotateAngleX / 2;
		
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.body.offsetX, this.body.offsetY, this.body.offsetZ);
        GlStateManager.translate(this.body.rotationPointX * scale, this.body.rotationPointY * scale, this.body.rotationPointZ * scale);
        GlStateManager.scale(1.5D, 1.5D, 1.5D);
        GlStateManager.translate(-this.body.offsetX, -this.body.offsetY, -this.body.offsetZ);
        GlStateManager.translate(-this.body.rotationPointX * scale, -this.body.rotationPointY * scale, -this.body.rotationPointZ * scale);
        this.body.render(scale);
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
