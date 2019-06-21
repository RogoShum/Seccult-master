package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLaserLayer extends ModelBase {
    public ModelRenderer cubeLayer;

    public ModelLaserLayer() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.cubeLayer = new ModelRenderer(this, 0, 24);
        this.cubeLayer.setRotationPoint(-8.0F, -8.0F, -8.0F);
        this.cubeLayer.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.cubeLayer.render(f5);
    }
}
