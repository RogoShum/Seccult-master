package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import testmod.seccult.entity.livings.landCreature.EntityDreamPop;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * EntityDreamPop - RogoShum
 * Created using Tabula 7.0.1
 */
public class ModelDreamPop extends ModelBase {
	public ModelRenderer body;
    public ModelRenderer body_layer;
    public ModelRenderer hand_r;
    public ModelRenderer hand_l;
    public ModelRenderer head;
    public ModelRenderer hand_r_1;
    public ModelRenderer hand_l_1;
    public ModelRenderer head_1;

    public ModelDreamPop() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body_layer = new ModelRenderer(this, 32, 16);
        this.body_layer.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.body_layer.addBox(-3.0F, 0.0F, -2.0F, 6, 8, 4, 0.0F);
        this.setRotateAngle(body_layer, 0.2617993877991494F, 0.0F, 0.0F);
        this.hand_r = new ModelRenderer(this, 0, 28);
        this.hand_r.setRotationPoint(-5.7F, 3.0F, 0.0F);
        this.hand_r.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(hand_r, -0.3490658503988659F, -0.13962634015954636F, 0.7853981633974483F);
        this.hand_l = new ModelRenderer(this, 8, 28);
        this.hand_l.setRotationPoint(5.7F, 3.0F, 0.0F);
        this.hand_l.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(hand_l, -0.3490658503988659F, -0.13962634015954636F, -0.7853981633974483F);
        this.hand_r_1 = new ModelRenderer(this, 32, 28);
        this.hand_r_1.setRotationPoint(-4.7F, 2.3F, 0.1F);
        this.hand_r_1.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(hand_r_1, -0.3490658503988659F, -0.13962634015954636F, 0.7853981633974483F);
        this.head_1 = new ModelRenderer(this, 32, 0);
        this.head_1.setRotationPoint(0.0F, 1.1F, 0.0F);
        this.head_1.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.setRotateAngle(head_1, -0.2617993877991494F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 1.1F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.setRotateAngle(head, -0.2617993877991494F, 0.0F, 0.0F);
        this.hand_l_1 = new ModelRenderer(this, 40, 28);
        this.hand_l_1.setRotationPoint(4.7F, 2.3F, 0.1F);
        this.hand_l_1.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(hand_l_1, -0.3490658503988659F, -0.13962634015954636F, -0.7853981633974483F);
        this.body = new ModelRenderer(this, 0, 16);
        this.body.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.body.addBox(-3.0F, 0.0F, -2.0F, 6, 8, 4, 0.0F);
        this.setRotateAngle(body, 0.2617993877991494F, 0.0F, 0.0F);
        this.body.addChild(this.hand_r);
        this.body.addChild(this.hand_l);
        this.body_layer.addChild(this.hand_r_1);
        this.body_layer.addChild(this.head_1);
        this.body.addChild(this.head);
        this.body_layer.addChild(this.hand_l_1);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
		body.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount + 0.2617993877991494F;
		
		if(body.rotateAngleX > 0.2617993877991494F)
			body.rotateAngleX = 0.2617993877991494F;
		
		body_layer.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount + 0.2617993877991494F;
		
		if(body_layer.rotateAngleX > 0.2617993877991494F)
			body_layer.rotateAngleX = 0.2617993877991494F;
		
		this.head.rotateAngleX = headPitch * 0.017453292F - body.rotateAngleX;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
    	
		this.head_1.rotateAngleX = headPitch * 0.017453292F - body.rotateAngleX;
		this.head_1.rotateAngleY = netHeadYaw * 0.017453292F;
		
		this.hand_l.rotateAngleX = -0.3490658503988659F - body.rotateAngleX;
		this.hand_l_1.rotateAngleX = -0.3490658503988659F - body.rotateAngleX;
		this.hand_r.rotateAngleX = -0.3490658503988659F - body.rotateAngleX;
		this.hand_r_1.rotateAngleX = -0.3490658503988659F - body.rotateAngleX;
		
		if(entity instanceof EntityDreamPop)
		{
			EntityDreamPop pop = (EntityDreamPop) entity;
			if(pop.getState() == 1)
			{
				this.hand_l.rotateAngleX += -1.0471975511965977;
				this.hand_l_1.rotateAngleX += -1.0471975511965977;
				this.hand_r.rotateAngleX += -1.0471975511965977;
				this.hand_r_1.rotateAngleX += -1.0471975511965977;
			}
		}
		
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.body_layer.offsetX, this.body_layer.offsetY, this.body_layer.offsetZ);
        GlStateManager.translate(this.body_layer.rotationPointX * scale, this.body_layer.rotationPointY * scale, this.body_layer.rotationPointZ * scale);
        GlStateManager.scale(1.2D, 1.2D, 1.2D);
        GlStateManager.translate(-this.body_layer.offsetX, -this.body_layer.offsetY, -this.body_layer.offsetZ);
        GlStateManager.translate(-this.body_layer.rotationPointX * scale, -this.body_layer.rotationPointY * scale, -this.body_layer.rotationPointZ * scale);
        this.body_layer.render(scale);
        GlStateManager.popMatrix();
        this.body.render(scale);
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
