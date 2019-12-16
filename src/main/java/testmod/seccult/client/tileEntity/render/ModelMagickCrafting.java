package testmod.seccult.client.tileEntity.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelChest - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ModelMagickCrafting extends ModelBase {
    public ModelRenderer magick_crafting;

    public ModelMagickCrafting() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.magick_crafting = new ModelRenderer(this, 0, 0);
        this.magick_crafting.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.magick_crafting.addBox(-8.0F, -2.0F, -8.0F, 16, 4, 16, 0.0F);
    }

    public void render() 
    { 
        this.magick_crafting.render(0.0625F);
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
