package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.projectile.TRBladeBeam;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.TRprojectile.ItemTerraBeam;
import testmod.seccult.items.TRprojectile.TRprojectileID;

@SideOnly(Side.CLIENT)
public class RenderTRBlade extends Render<TRBladeBeam>
{   
    public RenderTRBlade(RenderManager manager) {
        super(manager);
        
        this.shadowSize = 0;
    }
    
    public void doRenderProjectile(TRBladeBeam projectile, double x, double y, double z, float entityYaw, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y + projectile.getEyeHeight(), (float)z);
        GlStateManager.enableRescaleNormal();
        if(projectile.getRenderSkin() == 12)
        	GlStateManager.scale(0.7, 0.7, 0.7);
        else if(projectile.getRenderSkin() == 10)
        	GlStateManager.scale(1.5, 1.5, 1.5);
        else
        	GlStateManager.scale(1.8, 2, 1.8);
	    GlStateManager.rotate((-entityYaw + 90) * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate((projectile.rotationPitch + 90) * 0.017453292F * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
	    
	    ItemTerraBeam terra = (ItemTerraBeam) ModItems.Beam;
        ItemStack itemstack = new ItemStack(terra);
	    terra.setprojectileID(itemstack, projectile.getRenderSkin());
	    if(itemstack != null) {
	        mc.getRenderItem().renderItem(itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
	    }
	    
        GlStateManager.disableLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
    }
    
    public void doRender(TRBladeBeam par1Entity,double par2,double par4, double par6, float par8, float par9) {
        this.doRenderProjectile((TRBladeBeam)par1Entity, par2, par4, par6, par8, par9);
    }
    
	@Override
    protected ResourceLocation getEntityTexture(TRBladeBeam par1Entity) {
        return null;
    }
}