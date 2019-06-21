package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLaser extends ModelBase {
    public ModelRenderer cube;

    public ModelLaser() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.cube = new ModelRenderer(this, 0, 0);
        this.cube.setRotationPoint(-4.0F, -4.0F, -8.0F);
        this.cube.addBox(0.0F, 0.0F, 0.0F, 8, 8, 16, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.cube.render(f5);
    }
}
