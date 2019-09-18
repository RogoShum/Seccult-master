
package testmod.seccult.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class Modeltest extends ModelBase
{
  //fields
    ModelRenderer body;
    ModelRenderer rightarm;
    ModelRenderer leftarm;
    ModelRenderer rightleg;
    ModelRenderer leftleg;
    ModelRenderer hand11;
    ModelRenderer hand22;
    ModelRenderer head1;
    ModelRenderer head2;
    ModelRenderer headside;
    ModelRenderer headside2;
    ModelRenderer headback;
    ModelRenderer lindai;
    ModelRenderer skyrim;
    ModelRenderer core;
    ModelRenderer skyrim2;
    ModelRenderer skyrim1;
    ModelRenderer skyrim3;
    ModelRenderer skyrimww;
    ModelRenderer jio1;
    ModelRenderer jio2;
    ModelRenderer skyrim4;
  
  public Modeltest()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      body = new ModelRenderer(this, 36, 0);
      body.addBox(-4F, 0F, -2F, 8, 2, 6);
      body.setRotationPoint(0F, 10F, -1F);
      body.setTextureSize(64, 32);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      rightarm = new ModelRenderer(this, 0, 0);
      rightarm.addBox(-3F, -2F, -2F, 4, 1, 4);
      rightarm.setRotationPoint(-6F, 10F, 0F);
      rightarm.setTextureSize(64, 32);
      rightarm.mirror = true;
      setRotation(rightarm, 0F, 0F, 0F);
      leftarm = new ModelRenderer(this, 0, 0);
      leftarm.addBox(-1F, -2F, -2F, 4, 1, 4);
      leftarm.setRotationPoint(6F, 10F, 0F);
      leftarm.setTextureSize(64, 32);
      leftarm.mirror = true;
      setRotation(leftarm, 0F, 0F, 0F);
      rightleg = new ModelRenderer(this, 0, 5);
      rightleg.addBox(-2F, 0F, -2F, 1, 6, 4);
      rightleg.setRotationPoint(-3F, 10F, 0F);
      rightleg.setTextureSize(64, 32);
      rightleg.mirror = true;
      setRotation(rightleg, 0F, 0F, 0F);
      leftleg = new ModelRenderer(this, 0, 5);
      leftleg.addBox(-2F, 0F, -2F, 1, 6, 4);
      leftleg.setRotationPoint(6F, 10F, 0F);
      leftleg.setTextureSize(64, 32);
      leftleg.mirror = true;
      setRotation(leftleg, 0F, 0F, 0F);
      hand11 = new ModelRenderer(this, 13, 5);
      hand11.addBox(0F, 0F, 0F, 1, 8, 2);
      hand11.setRotationPoint(-9F, 3F, -1F);
      hand11.setTextureSize(64, 32);
      hand11.mirror = true;
      setRotation(hand11, 0F, 0F, 0F);
      hand22 = new ModelRenderer(this, 13, 5);
      hand22.addBox(0F, 0F, 0F, 1, 8, 2);
      hand22.setRotationPoint(8F, 3F, -1F);
      hand22.setTextureSize(64, 32);
      hand22.mirror = true;
      setRotation(hand22, 0F, 0F, 0F);
      head1 = new ModelRenderer(this, 36, 0);
      head1.addBox(0F, 0F, 0F, 8, 2, 6);
      head1.setRotationPoint(-4F, 0F, -3F);
      head1.setTextureSize(64, 32);
      head1.mirror = true;
      setRotation(head1, 0F, 0F, 0F);
      head2 = new ModelRenderer(this, 40, 27);
      head2.addBox(0F, 0F, 0F, 8, 1, 4);
      head2.setRotationPoint(-4F, 1F, 2F);
      head2.setTextureSize(64, 32);
      head2.mirror = true;
      setRotation(head2, 0.6108652F, 0F, 0F);
      headside = new ModelRenderer(this, 58, 17);
      headside.addBox(0F, 0F, 0F, 0, 7, 3);
      headside.setRotationPoint(4F, -3F, 4F);
      headside.setTextureSize(64, 32);
      headside.mirror = true;
      setRotation(headside, -0.9773844F, 0F, 0F);
      headside2 = new ModelRenderer(this, 58, 17);
      headside2.addBox(0F, 0F, 0F, 0, 7, 3);
      headside2.setRotationPoint(-4F, -3F, 4F);
      headside2.setTextureSize(64, 32);
      headside2.mirror = true;
      setRotation(headside2, -0.9773844F, 0F, 0F);
      headback = new ModelRenderer(this, 42, 29);
      headback.addBox(0F, 0F, 0F, 8, 0, 3);
      headback.setRotationPoint(-4F, -3F, 4F);
      headback.setTextureSize(64, 32);
      headback.mirror = true;
      setRotation(headback, -1.012291F, 0F, 0F);
      lindai = new ModelRenderer(this, 26, 0);
      lindai.addBox(0F, 0F, 0F, 4, 12, 0);
      lindai.setRotationPoint(-2F, 1F, -3F);
      lindai.setTextureSize(64, 32);
      lindai.mirror = true;
      setRotation(lindai, -0.0872665F, 0F, 0F);
      skyrim = new ModelRenderer(this, 20, 0);
      skyrim.addBox(0F, 0F, 0F, 8, 16, 0);
      skyrim.setRotationPoint(-4F, 3F, 2F);
      skyrim.setTextureSize(64, 32);
      skyrim.mirror = true;
      setRotation(skyrim, 0.1745329F, 0F, 0.1919862F);
      core = new ModelRenderer(this, 36, 9);
      core.addBox(0F, 0F, 0F, 4, 2, 1);
      core.setRotationPoint(-2F, 1F, -4F);
      core.setTextureSize(64, 32);
      core.mirror = true;
      setRotation(core, 0F, 0F, 0F);
      skyrim2 = new ModelRenderer(this, 31, 16);
      skyrim2.addBox(0F, 0F, 0F, 4, 16, 0);
      skyrim2.setRotationPoint(-4F, 3F, 2F);
      skyrim2.setTextureSize(64, 32);
      skyrim2.mirror = true;
      setRotation(skyrim2, -0.1745329F, 1.570796F, 0.1745329F);
      skyrim1 = new ModelRenderer(this, 31, 16);
      skyrim1.addBox(0F, 0F, 0F, 4, 16, 0);
      skyrim1.setRotationPoint(4F, 3F, 2F);
      skyrim1.setTextureSize(64, 32);
      skyrim1.mirror = true;
      setRotation(skyrim1, 0.1745329F, 1.570796F, 0.1745329F);
      skyrim3 = new ModelRenderer(this, 20, 0);
      skyrim3.addBox(0F, 0F, 0F, 8, 16, 0);
      skyrim3.setRotationPoint(4F, 3F, 2F);
      skyrim3.setTextureSize(64, 32);
      skyrim3.mirror = true;
      setRotation(skyrim3, -0.1745329F, 3.141593F, 0.1919862F);
      skyrimww = new ModelRenderer(this, 36, 0);
      skyrimww.addBox(0F, 0F, 0F, 8, 2, 6);
      skyrimww.setRotationPoint(-4F, 2F, -3F);
      skyrimww.setTextureSize(64, 32);
      skyrimww.mirror = true;
      setRotation(skyrimww, 0F, 0F, 0F);
      jio1 = new ModelRenderer(this, 12, 16);
      jio1.addBox(0F, 0F, 0F, 4, 2, 5);
      jio1.setRotationPoint(0F, 22F, -3F);
      jio1.setTextureSize(64, 32);
      jio1.mirror = true;
      setRotation(jio1, 0F, 0F, 0F);
      jio2 = new ModelRenderer(this, 12, 16);
      jio2.addBox(0F, 0F, 0F, 4, 2, 5);
      jio2.setRotationPoint(-4F, 22F, -3F);
      jio2.setTextureSize(64, 32);
      jio2.mirror = true;
      setRotation(jio2, 0F, 0F, 0F);
      skyrim4 = new ModelRenderer(this, 20, 0);
      skyrim4.addBox(0F, 0F, 0F, 8, 16, 0);
      skyrim4.setRotationPoint(4F, 3F, 3F);
      skyrim4.setTextureSize(64, 32);
      skyrim4.mirror = true;
      setRotation(skyrim4, -0.1745329F, 3.141593F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    body.render(f5);
    rightarm.render(f5);
    leftarm.render(f5);
    rightleg.render(f5);
    leftleg.render(f5);
    hand11.render(f5);
    hand22.render(f5);
    head1.render(f5);
    head2.render(f5);
    headside.render(f5);
    headside2.render(f5);
    headback.render(f5);
    lindai.render(f5);
    skyrim.render(f5);
    core.render(f5);
    skyrim2.render(f5);
    skyrim1.render(f5);
    skyrim3.render(f5);
    skyrimww.render(f5);
    jio1.render(f5);
    jio2.render(f5);
    skyrim3.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
