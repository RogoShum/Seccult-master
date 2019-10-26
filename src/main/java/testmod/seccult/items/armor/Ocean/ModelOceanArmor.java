package testmod.seccult.items.armor.Ocean;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ModelPlayer - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
@SideOnly(Side.CLIENT)
public class ModelOceanArmor extends ModelBiped {
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
    public ModelRenderer claw_left_3;
    public ModelRenderer claw_right;
    public ModelRenderer claw_right_1;
    public ModelRenderer claw_right_2;
    public ModelRenderer claw_right_3;
    public ModelRenderer claw_left_boot;
    public ModelRenderer claw_left_boot_1;
    public ModelRenderer claw_left_boot_2;
    public ModelRenderer claw_left_boot_3;
    public ModelRenderer Tail;
    public ModelRenderer Tail_1;
    public ModelRenderer Tail_2;
    public ModelRenderer Tail_3;
    public ModelRenderer Tail_4;
    public ModelRenderer claw_right_boot;
    public ModelRenderer claw_right_boot_1;
    public ModelRenderer claw_right_boot_2;
    public ModelRenderer claw_right_boot_3;
    public ModelRenderer horn;


    private EntityEquipmentSlot armorSlot;
    
    private float swing;
    private boolean isLayer;
    private ModelOceanArmor layer;
    
    public ModelOceanArmor(EntityEquipmentSlot armorSlot, boolean isLayer) {
    	
    	this.isLayer = isLayer;
    	this.armorSlot = armorSlot;
    	
    	 this.textureWidth = 64;
         this.textureHeight = 64;
         this.claw_right_1 = new ModelRenderer(this, 0, 10);
         this.claw_right_1.setRotationPoint(-3.1F, 6.6F, 0.0F);
         this.claw_right_1.addBox(-0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
         this.setRotateAngle(claw_right_1, 0.0F, -3.141592653589793F, -1.2222540751716289F);
         this.Left_arm = new ModelRenderer(this, 48, 48);
         this.Left_arm.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.Left_arm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.25F);
         this.claw_right_boot = new ModelRenderer(this, 0, 10);
         this.claw_right_boot.setRotationPoint(0.7F, 10.3F, 2.5F);
         this.claw_right_boot.addBox(-0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
         this.setRotateAngle(claw_right_boot, 0.9105382707654417F, 3.096039560112741F, 0.0F);
         this.Helmet = new ModelRenderer(this, 32, 0);
         this.Helmet.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.Helmet.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
         this.claw_right = new ModelRenderer(this, 0, 10);
         this.claw_right.setRotationPoint(-2.0F, 5.5F, 0.0F);
         this.claw_right.addBox(-0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
         this.setRotateAngle(claw_right, 0.0F, -3.141592653589793F, -0.8651597102135892F);
         this.claw_left = new ModelRenderer(this, 0, 10);
         this.claw_left.setRotationPoint(1.7F, 5.2F, 0.0F);
         this.claw_left.addBox(0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
         this.setRotateAngle(claw_left, 0.0F, 0.0F, 0.8651597102135892F);
         this.claw_right_2 = new ModelRenderer(this, 0, 10);
         this.claw_right_2.setRotationPoint(-2.0F, 7.6F, 0.0F);
         this.claw_right_2.addBox(-0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
         this.setRotateAngle(claw_right_2, 0.0F, -3.141592653589793F, -1.8212510744560826F);
         this.Tail = new ModelRenderer(this, 0, 0);
         this.Tail.setRotationPoint(0.0F, 12.3F, 2.0F);
         this.Tail.addBox(-1.5F, -2.0F, -2.5F, 3, 2, 5, 0.0F);
         this.setRotateAngle(Tail, -0.9560913642424937F, -0.015834851299178387F, 0.0F);
         this.Left_boot = new ModelRenderer(this, 0, 48);
         this.Left_boot.setRotationPoint(1.9F, 12.0F, 0.0F);
         this.Left_boot.addBox(-2.0F, 7.6F, -2.0F, 4, 4, 4, 0.45F);
         this.claw_left_boot_3 = new ModelRenderer(this, 0, 18);
         this.claw_left_boot_3.setRotationPoint(0.9F, 10.6F, 1.6F);
         this.claw_left_boot_3.addBox(-2.0F, -3.4F, 0.0F, 4, 5, 0, 0.0F);
         this.setRotateAngle(claw_left_boot_3, -0.9560913642424937F, 0.4553564018453205F, -0.045553093477052F);
         this.Tail_1 = new ModelRenderer(this, 8, 13);
         this.Tail_1.setRotationPoint(0.0F, -1.4F, 2.8F);
         this.Tail_1.addBox(-1.0F, -1.0F, -1.3F, 2, 2, 5, 0.0F);
         this.setRotateAngle(Tail_1, 0.27314402793711257F, 0.0F, 0.0F);
         this.claw_left_boot_1 = new ModelRenderer(this, 0, 10);
         this.claw_left_boot_1.setRotationPoint(0.7F, 10.3F, 2.5F);
         this.claw_left_boot_1.addBox(0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
         this.setRotateAngle(claw_left_boot_1, -0.9105382707654417F, 0.36425021489121656F, 0.0F);
         this.claw_right_3 = new ModelRenderer(this, 0, 18);
         this.claw_right_3.setRotationPoint(-2.0F, 6.7F, 0.0F);
         this.claw_right_3.addBox(-2.0F, -3.4F, 0.0F, 4, 5, 0, 0.0F);
         this.setRotateAngle(claw_right_3, 0.0F, -3.141592653589793F, -1.4114477660878142F);
         this.claw_left_boot = new ModelRenderer(this, 0, 10);
         this.claw_left_boot.setRotationPoint(-1.0F, 10.3F, 2.5F);
         this.claw_left_boot.addBox(0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
         this.setRotateAngle(claw_left_boot, -0.9105382707654417F, -0.045553093477052F, 0.0F);
         this.claw_right_boot_3 = new ModelRenderer(this, 0, 18);
         this.claw_right_boot_3.setRotationPoint(-0.8F, 10.6F, 1.6F);
         this.claw_right_boot_3.addBox(-2.0F, -3.4F, 0.0F, 4, 5, 0, 0.0F);
         this.setRotateAngle(claw_right_boot_3, -0.9560913642424937F, -0.4553564018453205F, -0.045553093477052F);
         this.Left_leg = new ModelRenderer(this, 0, 48);
         this.Left_leg.setRotationPoint(1.9F, 12.0F, 0.0F);
         this.Left_leg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.25F);
         this.claw_left_3 = new ModelRenderer(this, 0, 18);
         this.claw_left_3.setRotationPoint(1.7F, 6.7F, 0.0F);
         this.claw_left_3.addBox(-2.0F, -3.4F, 0.0F, 4, 5, 0, 0.0F);
         this.setRotateAngle(claw_left_3, 0.0F, 0.0F, 1.4114477660878142F);
         this.claw_left_boot_2 = new ModelRenderer(this, 0, 10);
         this.claw_left_boot_2.setRotationPoint(2.2F, 9.9F, 1.6F);
         this.claw_left_boot_2.addBox(0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
         this.setRotateAngle(claw_left_boot_2, -0.9105382707654417F, 0.8196066167365371F, 0.0F);
         this.Right_boot = new ModelRenderer(this, 0, 32);
         this.Right_boot.setRotationPoint(-1.9F, 12.0F, 0.0F);
         this.Right_boot.addBox(-2.0F, 7.6F, -2.0F, 4, 4, 4, 0.45F);
         this.Right_arm = new ModelRenderer(this, 40, 32);
         this.Right_arm.mirror = true;
         this.Right_arm.setRotationPoint(-5.0F, 2.0F, 0.0F);
         this.Right_arm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.25F);
         this.claw_left_2 = new ModelRenderer(this, 0, 10);
         this.claw_left_2.setRotationPoint(1.7F, 7.3F, 0.0F);
         this.claw_left_2.addBox(0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
         this.setRotateAngle(claw_left_2, 0.0F, 0.0F, 1.8212510744560826F);
         this.claw_left_1 = new ModelRenderer(this, 0, 10);
         this.claw_left_1.setRotationPoint(2.8F, 6.3F, 0.0F);
         this.claw_left_1.addBox(0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
         this.setRotateAngle(claw_left_1, 0.0F, 0.0F, 1.2292353921796064F);
         this.claw_right_boot_1 = new ModelRenderer(this, 0, 10);
         this.claw_right_boot_1.setRotationPoint(-1.1F, 10.3F, 2.5F);
         this.claw_right_boot_1.addBox(-0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
         this.setRotateAngle(claw_right_boot_1, 0.9105382707654417F, 2.759889146178633F, 0.0F);
         this.Tail_2 = new ModelRenderer(this, 8, 13);
         this.Tail_2.setRotationPoint(0.0F, 0.5F, 3.5F);
         this.Tail_2.addBox(-1.0F, -1.0F, -1.3F, 2, 1, 5, 0.0F);
         this.setRotateAngle(Tail_2, 0.27314402793711257F, 0.0F, 0.0F);
         this.Tail_3 = new ModelRenderer(this, 18, 0);
         this.Tail_3.setRotationPoint(0.0F, -0.4F, 4.6F);
         this.Tail_3.addBox(-3.0F, -1.0F, -0.5F, 6, 1, 3, 0.0F);
         this.setRotateAngle(Tail_3, 0.27314402793711257F, 0.0F, 0.0F);
         this.Body = new ModelRenderer(this, 16, 32);
         this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.Body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.25F);
         this.claw_right_boot_2 = new ModelRenderer(this, 0, 10);
         this.claw_right_boot_2.mirror = true;
         this.claw_right_boot_2.setRotationPoint(-2.3F, 9.9F, 1.6F);
         this.claw_right_boot_2.addBox(-0.2F, -3.4F, -0.5F, 1, 5, 1, 0.0F);
         this.setRotateAngle(claw_right_boot_2, 0.9105382707654417F, 2.321986036853256F, 0.0F);
         this.Tail_4 = new ModelRenderer(this, 17, 6);
         this.Tail_4.setRotationPoint(0.0F, 0.0F, 2.8F);
         this.Tail_4.addBox(-2.0F, -1.0F, -0.5F, 4, 1, 3, 0.0F);
         this.setRotateAngle(Tail_4, 0.27314402793711257F, 0.0F, 0.0F);
         this.Right_leg = new ModelRenderer(this, 0, 32);
         this.Right_leg.setRotationPoint(-1.9F, 12.0F, 0.0F);
         this.Right_leg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.25F);
         this.horn = new ModelRenderer(this, 0, 10);
         this.horn.setRotationPoint(0.0F, -7.0F, -4.0F);
         this.horn.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.2F);
         this.setRotateAngle(horn, 0.5009094953223726F, 0.0F, 0.0F);
         this.Right_arm.addChild(this.claw_right_1);
         this.Right_boot.addChild(this.claw_right_boot);
         this.Right_arm.addChild(this.claw_right);
         this.Left_arm.addChild(this.claw_left);
         this.Right_arm.addChild(this.claw_right_2);
         this.Body.addChild(this.Tail);
         this.Left_boot.addChild(this.claw_left_boot_3);
         this.Tail.addChild(this.Tail_1);
         this.Left_boot.addChild(this.claw_left_boot_1);
         this.Right_arm.addChild(this.claw_right_3);
         this.Left_boot.addChild(this.claw_left_boot);
         this.Right_boot.addChild(this.claw_right_boot_3);
         this.Left_arm.addChild(this.claw_left_3);
         this.Left_boot.addChild(this.claw_left_boot_2);
         this.Left_arm.addChild(this.claw_left_2);
         this.Left_arm.addChild(this.claw_left_1);
         this.Right_boot.addChild(this.claw_right_boot_1);
         this.Helmet.addChild(this.horn);
         this.Tail_1.addChild(this.Tail_2);
         this.Tail_2.addChild(this.Tail_3);
         this.Right_boot.addChild(this.claw_right_boot_2);
         this.Tail_2.addChild(this.Tail_4);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	this.isSneak = entity.isSneaking();
    	
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
		
		swing = (float) (Math.abs(entity.motionX) + Math.abs(entity.motionY) + Math.abs(entity.motionZ)) * 90F - 20;
		
		if(swing < -20F)
			swing = -20F;
		
		if(swing > 20F)
			swing = 20F;
    	
		this.Tail.rotateAngleX = swing * 0.017453292F;
		this.Tail_1.rotateAngleX = swing * 0.017453292F;
		this.Tail_2.rotateAngleX = swing * 0.017453292F;
		this.Tail_3.rotateAngleX = swing * 0.017453292F;
		this.Tail_4.rotateAngleX = swing * 0.017453292F;
		
		int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
		
		super.render(entity, f, f1, f2, f3, f4, f5);
		
    	/*if(this.isLayer)
    	{
    		GlStateManager.pushMatrix();
    		int i = 15728880;
            int j = i % 65536;
            int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
    		ResourceLocation resource = new ResourceLocation(Seccult.MODID + ":textures/models/armor/ocean_glow_layer.png");
    		Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
    		GlStateManager.popMatrix();
    	}
    	else if(this.layer == null)
    	{
    		this.layer = new ModelOceanArmor(this.armorSlot, true);
    		setModelAttributes(layer);
    		this.layer.render(entity, f, f1, f2, f3, f4, f5);
    	}*/
		
		/*GlStateManager.pushMatrix();
		int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
		ResourceLocation resource = new ResourceLocation(Seccult.MODID + ":textures/models/armor/ocean_glow_layer.png");
		Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
		super.render(entity, f, f1, f2, f3, f4, f5);
		GlStateManager.popMatrix();
		*/
		
        /*this.Left_arm.render(f5);
        this.Right_arm.render(f5);
        this.Left_leg.render(f5);
        this.Body.render(f5);
        this.Helmet.render(f5);
        this.Right_leg.render(f5);
        */
    }
    
    public void setQAQModelAttributes(ModelBase model)
    {
    	model.swingProgress = this.swingProgress;
    	model.isRiding = this.isRiding;
    	model.isChild = this.isChild;
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
