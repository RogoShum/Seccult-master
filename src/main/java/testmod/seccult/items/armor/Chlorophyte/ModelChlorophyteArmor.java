package testmod.seccult.items.armor.Chlorophyte;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;

/**
 * ModelPlayer - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ModelChlorophyteArmor extends ModelBiped {
    public ModelRenderer Left_arm;
    public ModelRenderer Right_leg;
    public ModelRenderer Right_arm;
    public ModelRenderer Helmet;
    public ModelRenderer Left_boot;
    public ModelRenderer Body;
    public ModelRenderer Right_boot;
    public ModelRenderer Left_leg;
    public ModelRenderer claw_left;
    public ModelRenderer claw_left_1;
    public ModelRenderer claw_left_2;
    public ModelRenderer claw_right;
    public ModelRenderer claw_right_1;
    public ModelRenderer claw_right_2;
    public ModelRenderer horn;
    public ModelRenderer Helmet_1;
    public ModelRenderer horn_1;
    public ModelRenderer horn_2;
    public ModelRenderer horn_3;
    public ModelRenderer horn_4;
    public ModelRenderer Helmet_2;
    public ModelRenderer horn_5;
    public ModelRenderer horn_6;
    public ModelRenderer horn_7;
    public ModelRenderer horn_8;
    public ModelRenderer horn_9;
    public ModelRenderer claw_left_boot;
    public ModelRenderer claw_left_boot_1;
    public ModelRenderer claw_left_boot_2;
    public ModelRenderer claw_right_boot;
    public ModelRenderer claw_right_boot_1;
    public ModelRenderer claw_right_boot_2;

    private EntityEquipmentSlot armorSlot;
    
    public ModelChlorophyteArmor(EntityEquipmentSlot armorSlot) {

    	this.armorSlot = armorSlot;
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.horn_1 = new ModelRenderer(this, 0, 10);
        this.horn_1.setRotationPoint(3.7F, -7.0F, -3.0F);
        this.horn_1.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(horn_1, 0.5009094953223726F, -1.1838568316277536F, 0.0F);
        this.horn_2 = new ModelRenderer(this, 0, 10);
        this.horn_2.setRotationPoint(0.2F, -2.3F, 0.0F);
        this.horn_2.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(horn_2, 0.5009094953223726F, 1.1383037381507017F, 0.0F);
        this.Helmet_2 = new ModelRenderer(this, 32, 0);
        this.Helmet_2.setRotationPoint(0.0F, -0.4F, 0.0F);
        this.Helmet_2.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.2F);
        this.setRotateAngle(Helmet_2, 0.22759093446006054F, 0.0F, 0.0F);
        this.claw_right = new ModelRenderer(this, 0, 10);
        this.claw_right.setRotationPoint(-2.0F, -3.0F, 0.0F);
        this.claw_right.addBox(-0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(claw_right, 0.5462880558742251F, -3.141592653589793F, -0.8651597102135892F);
        this.claw_left_boot = new ModelRenderer(this, 0, 10);
        this.claw_left_boot.setRotationPoint(1.7F, 10.4F, 2.5F);
        this.claw_left_boot.addBox(0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(claw_left_boot, -0.9105382707654417F, 0.9105382707654417F, 0.0F);
        this.claw_right_boot_2 = new ModelRenderer(this, 0, 10);
        this.claw_right_boot_2.setRotationPoint(-2.3F, 9.9F, -0.4F);
        this.claw_right_boot_2.addBox(-0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(claw_right_boot_2, 0.9105382707654417F, 1.8212510744560826F, 0.0F);
        this.Right_arm = new ModelRenderer(this, 40, 32);
        this.Right_arm.mirror = true;
        this.Right_arm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.Right_arm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.25F);
        this.Left_leg = new ModelRenderer(this, 0, 48);
        this.Left_leg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.Left_leg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.25F);
        this.horn_4 = new ModelRenderer(this, 0, 10);
        this.horn_4.setRotationPoint(0.2F, -2.3F, 0.0F);
        this.horn_4.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(horn_4, 0.5009094953223726F, 1.1383037381507017F, 0.0F);
        this.horn_5 = new ModelRenderer(this, 0, 10);
        this.horn_5.setRotationPoint(2.0F, -6.7F, -1.0F);
        this.horn_5.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(horn_5, 1.0927506446736497F, -0.36425021489121656F, 0.0F);
        this.horn_7 = new ModelRenderer(this, 0, 10);
        this.horn_7.setRotationPoint(-0.2F, -2.0F, 0.0F);
        this.horn_7.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(horn_7, 0.5009094953223726F, -1.1383037381507017F, 0.0F);
        this.horn_3 = new ModelRenderer(this, 0, 10);
        this.horn_3.setRotationPoint(-1.0F, 1.5F, -1.0F);
        this.horn_3.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(horn_3, 0.9105382707654417F, 1.1383037381507017F, 0.0F);
        this.claw_right_boot_1 = new ModelRenderer(this, 0, 10);
        this.claw_right_boot_1.setRotationPoint(-1.7F, 10.4F, 2.3F);
        this.claw_right_boot_1.addBox(-0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(claw_right_boot_1, 0.9105382707654417F, 2.321986036853256F, 0.0F);
        this.claw_left = new ModelRenderer(this, 0, 10);
        this.claw_left.setRotationPoint(2.0F, -3.0F, 0.0F);
        this.claw_left.addBox(0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(claw_left, -0.5462880558742251F, 0.0F, 0.8651597102135892F);
        this.Left_boot = new ModelRenderer(this, 0, 48);
        this.Left_boot.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.Left_boot.addBox(-2.0F, 8.0F, -2.0F, 4, 4, 4, 0.45F);
        this.Left_arm = new ModelRenderer(this, 48, 48);
        this.Left_arm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.Left_arm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.25F);
        this.Right_boot = new ModelRenderer(this, 0, 32);
        this.Right_boot.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.Right_boot.addBox(-2.0F, 8.0F, -2.0F, 4, 4, 4, 0.45F);
        this.claw_right_boot = new ModelRenderer(this, 0, 10);
        this.claw_right_boot.setRotationPoint(1.3F, 2.9F, 2.5F);
        this.claw_right_boot.addBox(-0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(claw_right_boot, -2.3666664657043106F, 0.31869712141416456F, 0.0F);
        this.Helmet_1 = new ModelRenderer(this, 32, 0);
        this.Helmet_1.mirror = true;
        this.Helmet_1.setRotationPoint(0.0F, -0.2F, 0.0F);
        this.Helmet_1.addBox(-4.0F, -8.5F, -4.4F, 8, 8, 8, 0.4F);
        this.setRotateAngle(Helmet_1, 0.22759093446006054F, 0.0F, 0.0F);
        this.claw_left_boot_1 = new ModelRenderer(this, 0, 10);
        this.claw_left_boot_1.setRotationPoint(2.3F, 9.9F, 0.0F);
        this.claw_left_boot_1.addBox(0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(claw_left_boot_1, -0.9105382707654417F, 1.3658946726107624F, 0.0F);
        this.horn_9 = new ModelRenderer(this, 0, 10);
        this.horn_9.setRotationPoint(-0.2F, -2.0F, 0.0F);
        this.horn_9.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(horn_9, 0.5009094953223726F, -1.1383037381507017F, 0.0F);
        this.claw_left_2 = new ModelRenderer(this, 0, 10);
        this.claw_left_2.setRotationPoint(2.0F, -1.4F, 0.0F);
        this.claw_left_2.addBox(0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(claw_left_2, -0.46949356878647464F, 0.0F, 1.8212510744560826F);
        this.claw_left_1 = new ModelRenderer(this, 0, 10);
        this.claw_left_1.setRotationPoint(3.1F, -3.0F, 0.0F);
        this.claw_left_1.addBox(0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(claw_left_1, 0.18203784098300857F, 0.0F, 1.2292353921796064F);
        this.claw_left_boot_2 = new ModelRenderer(this, 0, 10);
        this.claw_left_boot_2.setRotationPoint(-2.3F, 3.0F, 2.5F);
        this.claw_left_boot_2.addBox(0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(claw_left_boot_2, -2.367539130330308F, -0.31869712141416456F, 0.0F);
        this.claw_right_2 = new ModelRenderer(this, 0, 10);
        this.claw_right_2.setRotationPoint(-2.0F, -1.4F, 0.0F);
        this.claw_right_2.addBox(-0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(claw_right_2, 0.4553564018453205F, -3.141592653589793F, -1.8668041679331349F);
        this.horn_8 = new ModelRenderer(this, 0, 10);
        this.horn_8.setRotationPoint(1.0F, 1.5F, -0.7F);
        this.horn_8.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(horn_8, 0.9105382707654417F, -1.1383037381507017F, 0.0F);
        this.Body = new ModelRenderer(this, 16, 32);
        this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.25F);
        this.claw_right_1 = new ModelRenderer(this, 0, 10);
        this.claw_right_1.setRotationPoint(-3.1F, -3.0F, 0.0F);
        this.claw_right_1.addBox(-0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(claw_right_1, -0.18203784098300857F, -3.141592653589793F, -1.2222540751716289F);
        this.horn = new ModelRenderer(this, 0, 10);
        this.horn.setRotationPoint(-3.7F, -7.0F, -3.0F);
        this.horn.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(horn, 0.5009094953223726F, 1.1383037381507017F, 0.0F);
        this.horn_6 = new ModelRenderer(this, 0, 10);
        this.horn_6.setRotationPoint(-2.0F, -6.7F, -1.0F);
        this.horn_6.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(horn_6, 1.0927506446736497F, 0.36425021489121656F, 0.0F);
        this.Right_leg = new ModelRenderer(this, 0, 32);
        this.Right_leg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.Right_leg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.25F);
        this.Helmet = new ModelRenderer(this, 32, 0);
        this.Helmet.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Helmet.addBox(-4.0F, -9.0F, -4.5F, 8, 8, 8, 0.6F);
        this.setRotateAngle(Helmet, -0.4553564018453205F, 0.0F, 0.0F);
        this.Helmet.addChild(this.horn_1);
        this.horn.addChild(this.horn_2);
        this.Helmet_1.addChild(this.Helmet_2);
        this.Right_arm.addChild(this.claw_right);
        this.Left_boot.addChild(this.claw_left_boot);
        this.Right_boot.addChild(this.claw_right_boot_2);
        this.horn_3.addChild(this.horn_4);
        this.Helmet_2.addChild(this.horn_5);
        this.horn_1.addChild(this.horn_7);
        this.horn_2.addChild(this.horn_3);
        this.Right_boot.addChild(this.claw_right_boot_1);
        this.Left_arm.addChild(this.claw_left);
        this.Body.addChild(this.claw_right_boot);
        this.Helmet.addChild(this.Helmet_1);
        this.Left_boot.addChild(this.claw_left_boot_1);
        this.horn_8.addChild(this.horn_9);
        this.Left_arm.addChild(this.claw_left_2);
        this.Left_arm.addChild(this.claw_left_1);
        this.Body.addChild(this.claw_left_boot_2);
        this.Right_arm.addChild(this.claw_right_2);
        this.horn_7.addChild(this.horn_8);
        this.Right_arm.addChild(this.claw_right_1);
        this.Helmet.addChild(this.horn);
        this.Helmet_2.addChild(this.horn_6);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	
    	this.Helmet.showModel = this.armorSlot == EntityEquipmentSlot.HEAD;
        this.Body.showModel = this.armorSlot == EntityEquipmentSlot.CHEST;
        this.Right_arm.showModel = this.armorSlot == EntityEquipmentSlot.CHEST;
        this.Left_arm.showModel = this.armorSlot == EntityEquipmentSlot.CHEST;
        this.Left_leg.showModel = this.armorSlot == EntityEquipmentSlot.LEGS;
        this.Right_leg.showModel = this.armorSlot == EntityEquipmentSlot.LEGS;
        
        this.Right_boot.showModel = this.armorSlot == EntityEquipmentSlot.FEET;
        this.Left_boot.showModel = this.armorSlot == EntityEquipmentSlot.FEET;
        
        bipedHead = Helmet;
		bipedBody = Body;
		bipedRightArm = Right_arm;
		bipedLeftArm = Left_arm;
		bipedRightLeg = Right_leg;
		bipedLeftLeg = Left_leg;
				
		if(armorSlot == EntityEquipmentSlot.LEGS) {
		  	this.Body.showModel = true;
			bipedBody = Body;
			bipedRightLeg = Right_leg;
			bipedLeftLeg = Left_leg;
		} else {
			bipedRightLeg = Right_boot;
			bipedLeftLeg = Left_boot;
		}
    	
		super.render(entity, f, f1, f2, f3, f4, f5);
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
