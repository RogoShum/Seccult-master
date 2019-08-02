package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import testmod.seccult.entity.livings.EntityBase;
import testmod.seccult.entity.livings.water.EntityRockShellLeviathan;

/**
 * ModelRockShellLeviathan - RogoShum
 * Created using Tabula 7.0.1
 */
public class ModelRockShellLeviathan extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer shell_bottom;
    public ModelRenderer shell;
    public ModelRenderer shell_1;
    public ModelRenderer shell_2;
    public ModelRenderer shell_3;
    public ModelRenderer shell_4;
    public ModelRenderer shell_5;
    public ModelRenderer eye2;
    public ModelRenderer plant_;
    public ModelRenderer plant__1;
    public ModelRenderer plant__2;
    public ModelRenderer plant__3;
    public ModelRenderer plant__4;
    public ModelRenderer plant__5;
    public ModelRenderer eye;
    public ModelRenderer leg;
    public ModelRenderer leg_1;
    public ModelRenderer shape2;
    public ModelRenderer shape2_1;

    public ModelRockShellLeviathan() {
        this.textureWidth = 64;
        this.textureHeight = 128;
        this.plant__4 = new ModelRenderer(this, 0, 90);
        this.plant__4.setRotationPoint(0.0F, -6.0F, -1.5F);
        this.plant__4.addBox(0.0F, 0.0F, 0.0F, 3, 3, 0, 0.0F);
        this.shape2_1 = new ModelRenderer(this, 0, 0);
        this.shape2_1.setRotationPoint(0.0F, -4.3F, 0.6F);
        this.shape2_1.addBox(-1.5F, 0.0F, -3.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(shape2_1, -0.5009094953223726F, 0.0F, 0.0F);
        this.shell_2 = new ModelRenderer(this, 0, 24);
        this.shell_2.setRotationPoint(0.1F, -6.6F, -4.1F);
        this.shell_2.addBox(-6.0F, -3.0F, -6.0F, 12, 6, 12, 0.0F);
        this.plant__5 = new ModelRenderer(this, 0, 90);
        this.plant__5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.plant__5.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 0, 0.0F);
        this.setRotateAngle(plant__5, 0.0F, -1.5707963267948966F, 0.0F);
        this.plant__3 = new ModelRenderer(this, 0, 90);
        this.plant__3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.plant__3.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 0, 0.0F);
        this.setRotateAngle(plant__3, 0.0F, -1.5707963267948966F, 0.0F);
        this.shell_4 = new ModelRenderer(this, 0, 44);
        this.shell_4.setRotationPoint(-5.0F, -5.2F, -4.6F);
        this.shell_4.addBox(-4.0F, -2.0F, -4.0F, 8, 4, 8, 0.0F);
        this.shell_5 = new ModelRenderer(this, 0, 44);
        this.shell_5.setRotationPoint(-5.0F, -5.2F, 4.6F);
        this.shell_5.addBox(-4.0F, -2.0F, -4.0F, 8, 4, 8, 0.0F);
        this.eye = new ModelRenderer(this, 22, 90);
        this.eye.setRotationPoint(0.0F, 0.0F, 0.4F);
        this.eye.addBox(-1.5F, -1.5F, -0.4F, 3, 3, 0, 0.0F);
        this.shell_1 = new ModelRenderer(this, 0, 24);
        this.shell_1.setRotationPoint(-3.4F, -5.6F, 0.2F);
        this.shell_1.addBox(-6.0F, -3.0F, -6.0F, 12, 6, 12, 0.0F);
        this.shape2 = new ModelRenderer(this, 0, 0);
        this.shape2.setRotationPoint(0.0F, -4.3F, 0.6F);
        this.shape2.addBox(-1.5F, 0.0F, -3.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(shape2, -0.5009094953223726F, 0.0F, 0.0F);
        this.plant__1 = new ModelRenderer(this, 0, 90);
        this.plant__1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.plant__1.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 0, 0.0F);
        this.setRotateAngle(plant__1, 0.0F, -1.5707963267948966F, 0.0F);
        this.eye2 = new ModelRenderer(this, 10, 90);
        this.eye2.setRotationPoint(0.0F, -1.0F, -8.2F);
        this.eye2.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 1, 0.0F);
        this.shell = new ModelRenderer(this, 0, 24);
        this.shell.setRotationPoint(3.4F, -7.3F, 3.0F);
        this.shell.addBox(-6.0F, -3.0F, -6.0F, 12, 6, 12, 0.0F);
        this.shell_3 = new ModelRenderer(this, 0, 44);
        this.shell_3.setRotationPoint(5.0F, -6.4F, -5.1F);
        this.shell_3.addBox(-4.0F, -2.0F, -4.0F, 8, 4, 8, 0.0F);
        this.shell_bottom = new ModelRenderer(this, 0, 68);
        this.shell_bottom.setRotationPoint(0.0F, 19.8F, 0.0F);
        this.shell_bottom.addBox(-8.0F, -2.0F, -8.0F, 16, 4, 16, 0.8F);
        this.plant_ = new ModelRenderer(this, 0, 90);
        this.plant_.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.plant_.addBox(0.0F, 0.0F, 0.0F, 3, 3, 0, 0.0F);
        this.plant__2 = new ModelRenderer(this, 0, 90);
        this.plant__2.setRotationPoint(-3.3F, -6.0F, 2.1F);
        this.plant__2.addBox(0.0F, 0.0F, 0.0F, 3, 3, 0, 0.0F);
        this.leg = new ModelRenderer(this, 0, 58);
        this.leg.setRotationPoint(-7.0F, 3.5F, -7.0F);
        this.leg.addBox(-2.0F, -4.0F, -4.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(leg, 0.0F, 0.7853981633974483F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 18.0F, 0.0F);
        this.body.addBox(-8.0F, -6.0F, -8.0F, 16, 8, 16, -0.5F);
        this.setRotateAngle(body, -0.27314402793711257F, 0.0F, 0.0F);
        this.leg_1 = new ModelRenderer(this, 0, 58);
        this.leg_1.setRotationPoint(7.0F, 3.5F, -7.0F);
        this.leg_1.addBox(-2.0F, -4.0F, -4.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(leg_1, 0.0F, -0.7853981633974483F, 0.0F);
        this.shell_2.addChild(this.plant__4);
        this.leg_1.addChild(this.shape2_1);
        this.body.addChild(this.shell_2);
        this.plant__4.addChild(this.plant__5);
        this.plant__2.addChild(this.plant__3);
        this.body.addChild(this.shell_4);
        this.body.addChild(this.shell_5);
        this.eye2.addChild(this.eye);
        this.body.addChild(this.shell_1);
        this.leg.addChild(this.shape2);
        this.plant_.addChild(this.plant__1);
        this.body.addChild(this.eye2);
        this.body.addChild(this.shell);
        this.body.addChild(this.shell_3);
        this.shell.addChild(this.plant_);
        this.shell_1.addChild(this.plant__2);
        this.shell_bottom.addChild(this.leg);
        this.shell_bottom.addChild(this.leg_1);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
        this.body.render(scale);
        this.shell_bottom.render(scale);
        EntityRockShellLeviathan shell = (EntityRockShellLeviathan) entity;
        
        if(shell.getDefenseMode())
        {
        	this.body.rotateAngleX = 0;
        	this.shell_bottom.rotateAngleX = 0;
        	this.body.setRotationPoint(0.0F, 21.0F, 0.0F);
        }
        else
        {
        	this.body.rotateAngleX = -0.27314402793711257F;
        	this.shell_bottom.rotateAngleX = 0;
        	this.body.setRotationPoint(0.0F, 18.0F, 0.0F);
        }
        
        this.leg.rotateAngleX = shell.getSwing() * 0.174F;
        this.leg_1.rotateAngleX = -shell.getSwing() * 0.174F;
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
