package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import testmod.seccult.entity.livings.water.EntityBoneShark;

/**
 * ModelBoneShark - RogoShum
 * Created using Tabula 7.0.1
 */
public class ModelBoneShark extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer spine;
    public ModelRenderer head;
    public ModelRenderer fin;
    public ModelRenderer fin2;
    public ModelRenderer spine1;
    public ModelRenderer fin_1;
    public ModelRenderer spine2;
    public ModelRenderer tail;
    public ModelRenderer mouth;
    public ModelRenderer mouth2;

    public ModelBoneShark() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.body.addBox(-3.0F, -2.0F, -3.0F, 6, 4, 5, 0.0F);
        this.setRotateAngle(body, 0.091106186954104F, 0.0F, 0.0F);
        this.spine = new ModelRenderer(this, 0, 10);
        this.spine.setRotationPoint(0.0F, -0.8F, 0.0F);
        this.spine.addBox(-0.5F, -0.5F, -6.0F, 1, 1, 10, 0.0F);
        this.setRotateAngle(spine, 0.03490658503988659F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 22, 0);
        this.head.setRotationPoint(0.0F, 0.0F, -3.4F);
        this.head.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 2, 0.0F);
        this.mouth = new ModelRenderer(this, 22, 6);
        this.mouth.setRotationPoint(0.0F, -0.1F, -2.2F);
        this.mouth.addBox(-1.5F, -1.6F, -3.5F, 3, 2, 4, 0.0F);
        this.setRotateAngle(mouth, 0.091106186954104F, 0.0F, 0.0F);
        this.fin = new ModelRenderer(this, 40, 0);
        this.fin.setRotationPoint(-2.4F, 0.7F, -3.7F);
        this.fin.addBox(-6.0F, 0.0F, -6.0F, 6, 1, 6, 0.0F);
        this.setRotateAngle(fin, 0.0F, 1.2976522988577839F, -0.27314402793711257F);
        this.spine2 = new ModelRenderer(this, 0, 22);
        this.spine2.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.spine2.addBox(-0.5F, -0.5F, -1.0F, 1, 1, 8, 0.0F);
        this.setRotateAngle(spine2, 0.045553093477052F, 0.0F, 0.0F);
        this.mouth2 = new ModelRenderer(this, 22, 12);
        this.mouth2.setRotationPoint(0.0F, 0.8F, -2.2F);
        this.mouth2.addBox(-1.5F, -0.2F, -2.5F, 3, 1, 3, 0.0F);
        this.setRotateAngle(mouth2, -0.31869712141416456F, 0.0F, 0.0F);
        this.fin_1 = new ModelRenderer(this, 0, 37);
        this.fin_1.setRotationPoint(0.0F, 0.5F, 3.3F);
        this.fin_1.addBox(0.0F, -4.3F, -1.0F, 0, 4, 5, 0.0F);
        this.setRotateAngle(fin_1, -0.7285004297824331F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 0, 26);
        this.tail.setRotationPoint(0.0F, 0.5F, 3.3F);
        this.tail.addBox(0.0F, 0.0F, 0.0F, 0, 7, 7, 0.0F);
        this.setRotateAngle(tail, 1.0927506446736497F, 0.0F, 0.0F);
        this.spine1 = new ModelRenderer(this, 0, 10);
        this.spine1.setRotationPoint(0.0F, 0.1F, 4.0F);
        this.spine1.addBox(-0.5F, -0.5F, -1.0F, 1, 1, 10, 0.0F);
        this.setRotateAngle(spine1, -0.22759093446006054F, 0.0F, 0.0F);
        this.fin2 = new ModelRenderer(this, 40, 0);
        this.fin2.setRotationPoint(2.4F, 0.7F, -3.7F);
        this.fin2.addBox(0.0F, 0.0F, 0.0F, 6, 1, 6, 0.0F);
        this.setRotateAngle(fin2, 0.0F, 0.27314402793711257F, 0.27314402793711257F);
        this.body.addChild(this.spine);
        this.body.addChild(this.head);
        this.head.addChild(this.mouth);
        this.body.addChild(this.fin);
        this.spine1.addChild(this.spine2);
        this.head.addChild(this.mouth2);
        this.spine.addChild(this.fin_1);
        this.spine2.addChild(this.tail);
        this.spine.addChild(this.spine1);
        this.body.addChild(this.fin2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	EntityBoneShark shark = (EntityBoneShark) entity;
        this.body.render(f5);
        this.head.rotateAngleY=f4;
        this.body.rotateAngleY = -shark.getSwing() * 0.017453292F;
        this.spine1.rotateAngleY = shark.getSwing() * 0.017453292F;
        this.spine2.rotateAngleY = -shark.getSwing() * 0.017453292F;
        
        if(shark.getIsSleeping())
        	this.mouth2.rotateAngleX = 45 * 0.017453292F;
        else
        	this.mouth2.rotateAngleX = -0.31869712141416456F;
        	
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
