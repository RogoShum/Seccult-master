package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * DummySystem - RogoShum
 * Created using Tabula 7.0.1
 */
public class ModelDummySystem extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer right_head;
    public ModelRenderer left_head;
    public ModelRenderer bodypart_up;
    public ModelRenderer bodypart_down;
    public ModelRenderer bodypart_left;
    public ModelRenderer bodypart_right;
    public ModelRenderer soul_stone;

    public ModelDummySystem() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.right_head = new ModelRenderer(this, 0, 20);
        this.right_head.setRotationPoint(-3.3F, 1.3F, 0.2F);
        this.right_head.addBox(-2.0F, 0.0F, -1.0F, 4, 6, 2, 0.0F);
        this.setRotateAngle(right_head, 0.0F, 0.22759093446006054F, 1.730144887501979F);
        this.soul_stone = new ModelRenderer(this, 62, 0);
        this.soul_stone.setRotationPoint(0.0F, 3.0F, -3.0F);
        this.soul_stone.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.bodypart_down = new ModelRenderer(this, 0, 40);
        this.bodypart_down.setRotationPoint(0.0F, 4.0F, -3.0F);
        this.bodypart_down.addBox(-4.0F, 0.0F, 0.0F, 8, 10, 1, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 14, 5, 0.0F);
        this.bodypart_up = new ModelRenderer(this, 0, 30);
        this.bodypart_up.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.bodypart_up.addBox(-4.0F, 0.0F, 0.0F, 8, 2, 1, 0.0F);
        this.bodypart_right = new ModelRenderer(this, 52, 0);
        this.bodypart_right.setRotationPoint(0.0F, 2.0F, -3.0F);
        this.bodypart_right.addBox(-4.0F, 0.0F, 0.0F, 3, 2, 1, 0.0F);
        this.bodypart_left = new ModelRenderer(this, 52, 0);
        this.bodypart_left.mirror = true;
        this.bodypart_left.setRotationPoint(5.0F, 2.0F, -3.0F);
        this.bodypart_left.addBox(-4.0F, 0.0F, 0.0F, 3, 2, 1, 0.0F);
        this.left_head = new ModelRenderer(this, 0, 20);
        this.left_head.mirror = true;
        this.left_head.setRotationPoint(3.3F, 1.3F, 0.2F);
        this.left_head.addBox(-2.0F, 0.0F, -1.0F, 4, 6, 2, 0.0F);
        this.setRotateAngle(left_head, 0.0F, -0.22759093446006054F, -1.730144887501979F);
        this.head = new ModelRenderer(this, 26, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.body.addChild(this.right_head);
        this.body.addChild(this.soul_stone);
        this.body.addChild(this.bodypart_down);
        this.body.addChild(this.bodypart_up);
        this.body.addChild(this.bodypart_right);
        this.body.addChild(this.bodypart_left);
        this.body.addChild(this.left_head);
        this.body.addChild(this.head);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
        this.body.render(scale * 1.3F);
        this.head.rotateAngleX = headPitch * 0.017453292F;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
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
