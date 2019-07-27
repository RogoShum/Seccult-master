package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.renderer.entity.RenderSquid;
import net.minecraft.entity.Entity;

/**
 * ModelSquid - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ModelJellyFish extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer[] legs = new ModelRenderer[8];

    public ModelJellyFish() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.legs[0] = new ModelRenderer(this, 0, 0);
        this.legs[0].setRotationPoint(2.0F, 1.8F, 2.0F);
        this.legs[0].addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(legs[0], 0.0F, 0.7853981633974483F, 0.0F);
        this.legs[2] = new ModelRenderer(this, 0, 0);
        this.legs[2].setRotationPoint(-2.0F, 1.8F, 2.0F);
        this.legs[2].addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(legs[2], 0.0F, -0.7853981633974483F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 17.0F, 0.0F);
        this.body.addBox(-3.0F, -2.0F, -3.0F, 6, 4, 6, 0.0F);
        this.legs[4] = new ModelRenderer(this, 20, 0);
        this.legs[4].setRotationPoint(0.0F, 1.8F, 2.0F);
        this.legs[4].addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.legs[5] = new ModelRenderer(this, 20, 0);
        this.legs[5].setRotationPoint(0.0F, 1.8F, -2.0F);
        this.legs[5].addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.legs[3] = new ModelRenderer(this, 0, 0);
        this.legs[3].setRotationPoint(2.0F, 1.8F, -2.0F);
        this.legs[3].addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(legs[3], 0.0F, -0.7853981633974483F, 0.0F);
        this.legs[7] = new ModelRenderer(this, 20, 0);
        this.legs[7].setRotationPoint(-2.0F, 1.8F, 0.0F);
        this.legs[7].addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(legs[7], 0.0F, 1.5707963267948966F, 0.0F);
        this.legs[1] = new ModelRenderer(this, 0, 0);
        this.legs[1].setRotationPoint(-2.0F, 1.8F, -2.0F);
        this.legs[1].addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(legs[1], 0.0F, 0.7853981633974483F, 0.0F);
        this.legs[6] = new ModelRenderer(this, 20, 0);
        this.legs[6].setRotationPoint(2.0F, 1.8F, 0.0F);
        this.legs[6].addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(legs[6], 0.0F, 1.5707963267948966F, 0.0F);
        
        for (int j = 0; j < this.legs.length; ++j)
        {
        	this.body.addChild(this.legs[j]);
        }
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        for (int j = 0; j < this.legs.length; ++j)
        {
        	if(j % 2 == 0)
        		this.legs[j].rotateAngleX = f2;
        	else
        		this.legs[j].rotateAngleX = -f2;
        }
        
        this.body.render(f5);
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
