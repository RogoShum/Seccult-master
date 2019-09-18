package testmod.seccult.items.armor.ShadowSky;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * Modeltest - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ModelShadowSkyArmor_ extends ModelBiped {
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
    public ModelRenderer headback;
    public ModelRenderer headside2;
    public ModelRenderer headside;
    public ModelRenderer skyrim4;
    public ModelRenderer hand22;

    public int slot;
    
    public ModelShadowSkyArmor_(int i) {
    	
    	this.slot = i;
    	
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.leftleg = new ModelRenderer(this, 0, 22);
        this.leftleg.setRotationPoint(6.0F, 10.0F, 0.0F);
        this.leftleg.addBox(-2.0F, 0.0F, -2.0F, 1, 6, 4, 0.0F);
        this.hand11 = new ModelRenderer(this, 13, 22);
        this.hand11.setRotationPoint(-3.0F, -7.0F, -1.0F);
        this.hand11.addBox(-1.0F, 0.0F, 0.0F, 1, 8, 2, 0.0F);
        this.skyrim1 = new ModelRenderer(this, 31, 12);
        this.skyrim1.setRotationPoint(7.7F, 0.0F, 5.0F);
        this.skyrim1.addBox(0.0F, 0.0F, 0.0F, 0, 16, 4, 0.0F);
        this.setRotateAngle(skyrim1, -0.17453292519943295F, -3.141592653589793F, -0.19198621771937624F);
        this.headside2 = new ModelRenderer(this, 58, 17);
        this.headside2.setRotationPoint(8.0F, -3.0F, 7.0F);
        this.headside2.addBox(0.0F, 0.0F, 0.0F, 0, 7, 3, 0.0F);
        this.setRotateAngle(headside2, -0.9773843811168246F, 0.0F, 0.0F);
        this.leftarm = new ModelRenderer(this, 0, 0);
        this.leftarm.setRotationPoint(5.7F, 10.0F, 0.0F);
        this.leftarm.addBox(-1.0F, -10.2F, -2.0F, 4, 12, 4, 0.0F);
        this.core = new ModelRenderer(this, 36, 9);
        this.core.setRotationPoint(2.0F, -1.0F, -1.0F);
        this.core.addBox(0.0F, 0.0F, 0.7F, 4, 2, 1, 0.0F);
        this.headback = new ModelRenderer(this, 41, 27);
        this.headback.setRotationPoint(0.0F, -3.0F, 7.0F);
        this.headback.addBox(0.0F, 0.0F, 0.0F, 8, 0, 3, 0.0F);
        this.setRotateAngle(headback, -1.0122909661567112F, 0.0F, 0.0F);
        this.skyrim2 = new ModelRenderer(this, 31, 12);
        this.skyrim2.setRotationPoint(0.3F, 0.0F, 5.0F);
        this.skyrim2.addBox(0.0F, 0.0F, 0.0F, 0, 16, 4, 0.0F);
        this.setRotateAngle(skyrim2, -0.17453292519943295F, 3.141592653589793F, 0.19198621771937624F);
        this.head2 = new ModelRenderer(this, 40, 27);
        this.head2.setRotationPoint(0.0F, 1.7F, 5.2F);
        this.head2.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        this.setRotateAngle(head2, 0.6108652381980153F, 0.0F, 0.0F);
        this.skyrim4 = new ModelRenderer(this, 20, 0);
        this.skyrim4.setRotationPoint(8.0F, 3.0F, 5.5F);
        this.skyrim4.addBox(0.0F, 0.0F, 0.0F, 8, 16, 0, 0.0F);
        this.setRotateAngle(skyrim4, -0.17453292519943295F, 3.141592653589793F, 0.0F);
        this.skyrim3 = new ModelRenderer(this, 20, 0);
        this.skyrim3.setRotationPoint(8.0F, 0.0F, 5.0F);
        this.skyrim3.addBox(0.0F, 0.0F, 0.0F, 8, 16, 0, 0.0F);
        this.setRotateAngle(skyrim3, -0.17453292519943295F, 3.141592653589793F, -0.19198621771937624F);
        this.rightleg = new ModelRenderer(this, 0, 22);
        this.rightleg.setRotationPoint(-3.0F, 10.0F, 0.0F);
        this.rightleg.addBox(-2.0F, 0.0F, -2.0F, 1, 6, 4, 0.0F);
        this.skyrim = new ModelRenderer(this, 20, 0);
        this.skyrim.setRotationPoint(0.0F, 0.0F, 5.0F);
        this.skyrim.addBox(0.0F, 0.0F, 0.0F, 8, 16, 0, 0.0F);
        this.setRotateAngle(skyrim, 0.17453292519943295F, 0.0F, 0.19198621771937624F);
        this.bodyMain = new ModelRenderer(this, 36, 0);
        this.bodyMain.mirror = true;
        this.bodyMain.setRotationPoint(-4.75F, 0.5F, -3.0F);
        this.bodyMain.addBox(0.0F, 0.0F, 0.0F, 8, 2, 6, 0.0F);
        this.headside = new ModelRenderer(this, 58, 17);
        this.headside.setRotationPoint(0.0F, -3.0F, 7.0F);
        this.headside.addBox(0.0F, 0.0F, 0.0F, 0, 7, 3, 0.0F);
        this.setRotateAngle(headside, -0.9773843811168246F, 0.0F, 0.0F);
        this.head1 = new ModelRenderer(this, 36, 0);
        this.head1.setRotationPoint(-4.75F, -1.5F, -3.0F);
        this.head1.addBox(0.0F, 0.0F, 0.0F, 8, 2, 6, 0.0F);
        this.rightarm = new ModelRenderer(this, 0, 0);
        this.rightarm.setRotationPoint(-5.6F, 10.0F, 0.0F);
        this.rightarm.addBox(-3.0F, -10.2F, -2.0F, 4, 12, 4, 0.0F);
        this.lindai = new ModelRenderer(this, 26, 0);
        this.lindai.setRotationPoint(2.0F, 1.0F, 0.0F);
        this.lindai.addBox(0.0F, 0.0F, 0.0F, 4, 12, 0, 0.0F);
        this.setRotateAngle(lindai, -0.08726646259971647F, 0.0F, 0.0F);
        this.jio2 = new ModelRenderer(this, 12, 16);
        this.jio2.setRotationPoint(-1.0F, 12.0F, -3.0F);
        this.jio2.addBox(-0.2F, -2.0F, 0.0F, 4, 4, 5, 0.0F);
        this.hand22 = new ModelRenderer(this, 13, 22);
        this.hand22.mirror = true;
        this.hand22.setRotationPoint(2.0F, -7.0F, -1.0F);
        this.hand22.addBox(1.0F, 0.0F, 0.0F, 1, 8, 2, 0.0F);
        this.body = new ModelRenderer(this, 36, 0);
        this.body.mirror = true;
        this.body.setRotationPoint(4.0F, 8.0F, 2.0F);
        this.body.addBox(-4.0F, -0.2F, -2.0F, 8, 2, 6, 0.0F);
        this.jio1 = new ModelRenderer(this, 12, 16);
        this.jio1.mirror = true;
        this.jio1.setRotationPoint(-6.0F, 12.0F, -3.0F);
        this.jio1.addBox(0.5F, -2.0F, 0.0F, 4, 4, 5, 0.0F);
        this.rightarm.addChild(this.hand11);
        this.bodyMain.addChild(this.skyrim1);
        this.head1.addChild(this.headside2);
        this.bodyMain.addChild(this.core);
        this.head1.addChild(this.headback);
        this.bodyMain.addChild(this.skyrim2);
        this.head1.addChild(this.head2);
        this.head1.addChild(this.skyrim4);
        this.bodyMain.addChild(this.skyrim3);
        this.bodyMain.addChild(this.skyrim);
        this.head1.addChild(this.headside);
        this.bodyMain.addChild(this.lindai);
        this.rightleg.addChild(this.jio2);
        this.leftarm.addChild(this.hand22);
        this.bodyMain.addChild(this.body);
        this.leftleg.addChild(this.jio1);
        
        bipedHead.showModel = false;
        bipedBody.showModel = false;
        bipedRightArm.showModel = false;
        bipedLeftArm.showModel = false;
        bipedLeftLeg.showModel = false;
        bipedRightLeg.showModel = false;
    }
    
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.leftleg.offsetX, this.leftleg.offsetY, this.leftleg.offsetZ);
        GlStateManager.translate(this.leftleg.rotationPointX * f5, this.leftleg.rotationPointY * f5, this.leftleg.rotationPointZ * f5);
        GlStateManager.scale(1.1D, 1.0D, 1.1D);
        GlStateManager.translate(-this.leftleg.offsetX, -this.leftleg.offsetY, -this.leftleg.offsetZ);
        GlStateManager.translate(-this.leftleg.rotationPointX * f5, -this.leftleg.rotationPointY * f5, -this.leftleg.rotationPointZ * f5);
        this.leftleg.render(f5);
        GlStateManager.popMatrix();
        this.leftarm.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.rightleg.offsetX, this.rightleg.offsetY, this.rightleg.offsetZ);
        GlStateManager.translate(this.rightleg.rotationPointX * f5, this.rightleg.rotationPointY * f5, this.rightleg.rotationPointZ * f5);
        GlStateManager.scale(1.1D, 1.0D, 1.1D);
        GlStateManager.translate(-this.rightleg.offsetX, -this.rightleg.offsetY, -this.rightleg.offsetZ);
        GlStateManager.translate(-this.rightleg.rotationPointX * f5, -this.rightleg.rotationPointY * f5, -this.rightleg.rotationPointZ * f5);
        this.rightleg.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.bodyMain.offsetX, this.bodyMain.offsetY, this.bodyMain.offsetZ);
        GlStateManager.translate(this.bodyMain.rotationPointX * f5, this.bodyMain.rotationPointY * f5, this.bodyMain.rotationPointZ * f5);
        GlStateManager.scale(1.2D, 1.2D, 1.2D);
        GlStateManager.translate(-this.bodyMain.offsetX, -this.bodyMain.offsetY, -this.bodyMain.offsetZ);
        GlStateManager.translate(-this.bodyMain.rotationPointX * f5, -this.bodyMain.rotationPointY * f5, -this.bodyMain.rotationPointZ * f5);
        this.bodyMain.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.head1.offsetX, this.head1.offsetY, this.head1.offsetZ);
        GlStateManager.translate(this.head1.rotationPointX * f5, this.head1.rotationPointY * f5, this.head1.rotationPointZ * f5);
        GlStateManager.scale(1.2D, 1.2D, 1.2D);
        GlStateManager.translate(-this.head1.offsetX, -this.head1.offsetY, -this.head1.offsetZ);
        GlStateManager.translate(-this.head1.rotationPointX * f5, -this.head1.rotationPointY * f5, -this.head1.rotationPointZ * f5);
        this.head1.render(f5);
        GlStateManager.popMatrix();
        this.rightarm.render(f5);
        
        head1.showModel = slot == 3;
        bodyMain.showModel = slot == 2;
        rightarm.showModel = slot == 2;
        leftarm.showModel = slot == 2;
        leftleg.showModel = slot == 1;
        rightleg.showModel = slot == 1;
		jio1.showModel = slot == 0;
		jio2.showModel = slot == 0;
		bipedHeadwear.showModel = false;
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
