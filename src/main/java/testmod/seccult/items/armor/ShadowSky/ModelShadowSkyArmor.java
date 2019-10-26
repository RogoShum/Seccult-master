package testmod.seccult.items.armor.ShadowSky;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;

/**
 * Modeltest - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
@SideOnly(Side.CLIENT)
public class ModelShadowSkyArmor extends ModelBiped {
    public ModelRenderer bodyMain;
    public ModelRenderer rightarm;
    public ModelRenderer leftleg;
    public ModelRenderer rightleg;
    public ModelRenderer head1;
    public ModelRenderer leftarm;
    public ModelRenderer body;
    public ModelRenderer core;
    public ModelRenderer lindai;
    public ModelRenderer skyrim;
    public ModelRenderer skyrim3;
    public ModelRenderer skyrim2;
    public ModelRenderer skyrim1;
    public ModelRenderer hand11;
    public ModelRenderer jio1;
    public ModelRenderer jio2;
    public ModelRenderer head2;
    public ModelRenderer skyrim4;
    public ModelRenderer head2_1;
    public ModelRenderer head2_2;
    public ModelRenderer hand22;
    public ModelRenderer head3;

    private EntityEquipmentSlot armorSlot;
    
    private float swing;
    
    public ModelShadowSkyArmor(EntityEquipmentSlot armorSlot) {
		
		this.armorSlot = armorSlot;
		
	    this.textureWidth = 64;
	    this.textureHeight = 32;
	    this.head2_2 = new ModelRenderer(this, 36, 22);
	    this.head2_2.setRotationPoint(0.0F, -1.4F, 3.6F);
	    this.head2_2.addBox(-4.0F, 0.5F, 3.2F, 8, 4, 6, 0.6F);
	    this.setRotateAngle(head2_2, 2.6862362517444724F, 0.0F, 0.0F);
	    this.leftarm = new ModelRenderer(this, 0, 0);
	    this.leftarm.setRotationPoint(5.1F, 2.0F, 0.0F);
	    this.leftarm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
	    this.hand11 = new ModelRenderer(this, 13, 22);
	    this.hand11.setRotationPoint(-3.0F, 3.0F, -1.0F);
	    this.hand11.addBox(-1.0F, 0.0F, 0.0F, 1, 6, 2, 0.0F);
	    this.hand22 = new ModelRenderer(this, 13, 22);
	    this.hand22.mirror = true;
	    this.hand22.setRotationPoint(2.0F, 3.0F, -1.0F);
	    this.hand22.addBox(1.0F, 0.0F, 0.0F, 1, 6, 2, 0.0F);
	    this.leftleg = new ModelRenderer(this, 0, 22);
	    this.leftleg.mirror = true;
	    this.leftleg.setRotationPoint(6.0F, 10.0F, 0.0F);
	    this.leftleg.addBox(-2.0F, 0.0F, -2.0F, 1, 6, 4, 0.0F);
	    this.lindai = new ModelRenderer(this, 21, 0);
	    this.lindai.setRotationPoint(-2.0F, 1.0F, -4.0F);
	    this.lindai.addBox(0.0F, 0.0F, 0.0F, 4, 12, 1, 0.0F);
	    this.setRotateAngle(lindai, -0.08726646259971647F, 0.0F, 0.0F);
	    this.jio1 = new ModelRenderer(this, 12, 16);
	    this.jio1.mirror = true;
	    this.jio1.setRotationPoint(1.9F, 12.0F, 0.0F);
	    this.jio1.addBox(-1.9F, 8.0F, -3.0F, 4, 4, 5, 0.2F);
	    this.core = new ModelRenderer(this, 36, 9);
	    this.core.setRotationPoint(0.0F, -0.1F, -3.5F);
	    this.core.addBox(-2.0F, 0.0F, -0.5F, 4, 2, 1, -0.2F);
	    this.jio2 = new ModelRenderer(this, 12, 16);
	    this.jio2.setRotationPoint(-1.9F, 12.0F, 0.0F);
	    this.jio2.addBox(-1.9F, 8F, -3.0F, 4, 4, 5, 0.2F);
	    this.body = new ModelRenderer(this, 36, 0);
	    this.body.mirror = true;
	    this.body.setRotationPoint(0.0F, 10.0F, -2.0F);
	    this.body.addBox(-4.0F, -0.2F, -1.5F, 8, 2, 6, 0.0F);
	    this.skyrim1 = new ModelRenderer(this, 31, 12);
	    this.skyrim1.setRotationPoint(0.0F, 0.0F, -2.0F);
	    this.skyrim1.addBox(4.0F, 0.5F, 0.0F, 0, 16, 4, 0.0F);
	    this.setRotateAngle(skyrim1, 0.17453292519943295F, 0.0F, -0.19198621771937624F);
	    this.head2 = new ModelRenderer(this, 36, 22);
	    this.head2.setRotationPoint(0.0F, -0.4F, 0.9F);
	    this.head2.addBox(-4.0F, -2.0F, 1.4F, 8, 4, 6, 0.8F);
	    this.setRotateAngle(head2, 1.0927506446736497F, 0.0F, 0.0F);
	    this.skyrim4 = new ModelRenderer(this, 20, 0);
	    this.skyrim4.setRotationPoint(0.0F, -0.0F, 3.5F);
	    this.skyrim4.addBox(-4.0F, 0.0F, -0.5F, 8, 16, 1, 0.0F);
	    this.setRotateAngle(skyrim4, -0.27314402793711257F, 3.141592653589793F, 0.0F);
	    this.head1 = new ModelRenderer(this, 36, 0);
	    this.head1.setRotationPoint(-0.0F, -1.5F, -0.0F);
	    this.head1.addBox(-4.0F, -1.4F, -2.0F, 8, 3, 6, 0.2F);
	    this.skyrim3 = new ModelRenderer(this, 20, 0);
	    this.skyrim3.setRotationPoint(0.0F, 0.0F, 2.0F);
	    this.skyrim3.addBox(-4.0F, 0.0F, 0.0F, 8, 16, 0, 0.0F);
	    this.setRotateAngle(skyrim3, 0.17453292519943295F, 0.0F, -0.19198621771937624F);
	    this.skyrim = new ModelRenderer(this, 20, 0);
	    this.skyrim.mirror = true;
	    this.skyrim.setRotationPoint(0.0F, 0.0F, 2.0F);
	    this.skyrim.addBox(-4.0F, 0.0F, 0.0F, 8, 16, 0, 0.0F);
	    this.setRotateAngle(skyrim, 0.17453292519943295F, 0.0F, 0.19198621771937624F);
	    this.bodyMain = new ModelRenderer(this, 36, 0);
	    this.bodyMain.mirror = true;
	    this.bodyMain.setRotationPoint(0.0F, -0.1F, 0.0F);
	    this.bodyMain.addBox(-4.0F, 0.0F, -3.0F, 8, 2, 6, 0.0F);
	    this.rightarm = new ModelRenderer(this, 0, 0);
	    this.rightarm.setRotationPoint(-5.1F, 2.0F, 0.0F);
	    this.rightarm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
	    this.rightleg = new ModelRenderer(this, 0, 22);
	    this.rightleg.setRotationPoint(-3.0F, 10.0F, 0.0F);
	    this.rightleg.addBox(-2.0F, 0.0F, -2.0F, 1, 6, 4, 0.0F);
	    this.skyrim2 = new ModelRenderer(this, 31, 12);
	    this.skyrim2.setRotationPoint(0.3F, 0.0F, 2.0F);
	    this.skyrim2.addBox(4.0F, 0.0F, 0.0F, 0, 16, 4, 0.0F);
	    this.setRotateAngle(skyrim2, -0.17453292519943295F, 3.141592653589793F, 0.19198621771937624F);
	    this.head2_1 = new ModelRenderer(this, 36, 22);
	    this.head2_1.setRotationPoint(0.0F, -0.6F, 0.3F);
	    this.head2_1.addBox(-4.0F, -2.0F, 1.0F, 8, 4, 6, 0.8F);
	    this.setRotateAngle(head2_1, 1.5481070465189704F, 0.0F, -0.016231562043547264F);
	    this.head3 = new ModelRenderer(this, 35, 0);
	    this.head3.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.head3.addBox(-4.0F, -1.5F, -3.7F, 8, 3, 6, 0.6F);
	    
	    this.head1.addChild(this.head2_2);
	    this.head1.addChild(this.head3);
	    this.rightarm.addChild(this.hand11);
	    this.leftarm.addChild(this.hand22);
	    this.leftleg.addChild(this.jio1);
	    this.bodyMain.addChild(this.core);
	    this.rightleg.addChild(this.jio2);
	    this.bodyMain.addChild(this.skyrim1);
	    this.head1.addChild(this.head2);
	    this.bodyMain.addChild(this.skyrim3);
	    this.bodyMain.addChild(this.skyrim);
	    this.bodyMain.addChild(this.skyrim2);
	    this.head1.addChild(this.head2_1);
	    
	    this.bipedHeadwear.showModel = false;
	}

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	
        this.head1.showModel = this.armorSlot == EntityEquipmentSlot.HEAD;
        this.bodyMain.showModel = this.armorSlot == EntityEquipmentSlot.CHEST;
        this.rightarm.showModel = this.armorSlot == EntityEquipmentSlot.CHEST;
        this.leftarm.showModel = this.armorSlot == EntityEquipmentSlot.CHEST;
        this.leftleg.showModel = this.armorSlot == EntityEquipmentSlot.LEGS;
        this.rightleg.showModel = this.armorSlot == EntityEquipmentSlot.LEGS;
        
        this.lindai.showModel = this.armorSlot == EntityEquipmentSlot.CHEST;
        this.skyrim4.showModel = this.armorSlot == EntityEquipmentSlot.HEAD;
        this.body.showModel = this.armorSlot == EntityEquipmentSlot.LEGS;
        this.bipedHeadwear.showModel = false;
        
        bipedHead = head1;
		bipedBody = bodyMain;
		bipedRightArm = rightarm;
		bipedLeftArm = leftarm;
				
		if(armorSlot == EntityEquipmentSlot.LEGS) {
			bipedBody = body;
			bipedRightLeg = rightleg;
			bipedLeftLeg = leftleg;
		} else {
			bipedRightLeg = jio2;
			bipedLeftLeg = jio1;
		}
		
		swing = (float) (Math.abs(entity.motionX) + Math.abs(entity.motionY) + Math.abs(entity.motionZ)) * 90F;
		
		if(swing < 0)
			swing = 15F;
		
		int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        this.lindai.rotateAngleX = -swing * 0.017453292F;
        this.skyrim4.rotateAngleX = -swing * 0.017453292F;
        this.skyrim4.rotateAngleY = 180 * 0.017453292F;
        
        //super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        GlStateManager.pushMatrix();
        if (entity.isSneaking())
        {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }
    	
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.leftarm.offsetX, this.leftarm.offsetY, this.leftarm.offsetZ);
        GlStateManager.translate(this.leftarm.rotationPointX * f5, this.leftarm.rotationPointY * f5, this.leftarm.rotationPointZ * f5);
        GlStateManager.scale(1.1D, 1.1D, 1.1D);
        GlStateManager.translate(-this.leftarm.offsetX, -this.leftarm.offsetY, -this.leftarm.offsetZ);
        GlStateManager.translate(-this.leftarm.rotationPointX * f5, -this.leftarm.rotationPointY * f5, -this.leftarm.rotationPointZ * f5);
        this.bipedLeftArm.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.leftleg.offsetX, this.leftleg.offsetY, this.leftleg.offsetZ);
        GlStateManager.translate(this.leftleg.rotationPointX * f5, this.leftleg.rotationPointY * f5, this.leftleg.rotationPointZ * f5);
        GlStateManager.scale(1.1D, 1.0D, 1.1D);
        GlStateManager.translate(-this.leftleg.offsetX, -this.leftleg.offsetY, -this.leftleg.offsetZ);
        GlStateManager.translate(-this.leftleg.rotationPointX * f5, -this.leftleg.rotationPointY * f5, -this.leftleg.rotationPointZ * f5);
        this.bipedLeftLeg.render(f5);
        GlStateManager.popMatrix();
       
        this.lindai.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.body.offsetX, this.body.offsetY, this.body.offsetZ);
        GlStateManager.translate(this.body.rotationPointX * f5, this.body.rotationPointY * f5, this.body.rotationPointZ * f5);
        GlStateManager.scale(1.2D, 1.2D, 1.2D);
        GlStateManager.translate(-this.body.offsetX, -this.body.offsetY, -this.body.offsetZ);
        GlStateManager.translate(-this.body.rotationPointX * f5, -this.body.rotationPointY * f5, -this.body.rotationPointZ * f5);
        this.bipedBody.render(f5);
        GlStateManager.popMatrix();
        
        this.skyrim4.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.head1.offsetX, this.head1.offsetY, this.head1.offsetZ);
        GlStateManager.translate(this.head1.rotationPointX * f5, this.head1.rotationPointY * f5, this.head1.rotationPointZ * f5);
        GlStateManager.scale(1.1D, 1.0D, 1.1D);
        GlStateManager.translate(-this.head1.offsetX, -this.head1.offsetY, -this.head1.offsetZ);
        GlStateManager.translate(-this.head1.rotationPointX * f5, -this.head1.rotationPointY * f5, -this.head1.rotationPointZ * f5);
        this.bipedHead.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.bodyMain.offsetX, this.bodyMain.offsetY, this.bodyMain.offsetZ);
        GlStateManager.translate(this.bodyMain.rotationPointX * f5, this.bodyMain.rotationPointY * f5, this.bodyMain.rotationPointZ * f5);
        GlStateManager.scale(1.3D, 1.3D, 1.3D);
        GlStateManager.translate(-this.bodyMain.offsetX, -this.bodyMain.offsetY, -this.bodyMain.offsetZ);
        GlStateManager.translate(-this.bodyMain.rotationPointX * f5, -this.bodyMain.rotationPointY * f5, -this.bodyMain.rotationPointZ * f5);
        this.bodyMain.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.rightarm.offsetX, this.rightarm.offsetY, this.rightarm.offsetZ);
        GlStateManager.translate(this.rightarm.rotationPointX * f5, this.rightarm.rotationPointY * f5, this.rightarm.rotationPointZ * f5);
        GlStateManager.scale(1.1D, 1.1D, 1.1D);
        GlStateManager.translate(-this.rightarm.offsetX, -this.rightarm.offsetY, -this.rightarm.offsetZ);
        GlStateManager.translate(-this.rightarm.rotationPointX * f5, -this.rightarm.rotationPointY * f5, -this.rightarm.rotationPointZ * f5);
        this.bipedRightArm.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.rightleg.offsetX, this.rightleg.offsetY, this.rightleg.offsetZ);
        GlStateManager.translate(this.rightleg.rotationPointX * f5, this.rightleg.rotationPointY * f5, this.rightleg.rotationPointZ * f5);
        GlStateManager.scale(1.1D, 1.0D, 1.1D);
        GlStateManager.translate(-this.rightleg.offsetX, -this.rightleg.offsetY, -this.rightleg.offsetZ);
        GlStateManager.translate(-this.rightleg.rotationPointX * f5, -this.rightleg.rotationPointY * f5, -this.rightleg.rotationPointZ * f5);
        this.bipedRightLeg.render(f5);
        GlStateManager.popMatrix();
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
