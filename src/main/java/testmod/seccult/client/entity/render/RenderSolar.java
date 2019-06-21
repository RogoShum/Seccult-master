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
import testmod.seccult.entity.projectile.EntitySolarEruption;
import testmod.seccult.entity.projectile.TRBladeBeam;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.TRprojectile.ItemTerraBeam;

@SideOnly(Side.CLIENT)
public class RenderSolar extends Render<EntitySolarEruption>
{   
	private ItemStack itemstack;
    public RenderSolar(RenderManager manager) {
        super(manager);
        
        this.shadowSize = 0;
    }
    
    public void doRenderProjectile(EntitySolarEruption projectile, double x, double y, double z, float entityYaw, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        if(projectile.getRenderSkin() != 3000) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y + projectile.getEyeHeight(), (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(2.7, 3, 2.7);
	    GlStateManager.rotate((-entityYaw + 90) * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate((projectile.rotationPitch + 90) * 0.017453292F * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
	    
	    ItemTerraBeam terra = (ItemTerraBeam) ModItems.Beam;
        itemstack = new ItemStack(terra);
	    terra.setprojectileID(itemstack, projectile.getRenderSkin());
	    if(itemstack != null) {
	        mc.getRenderItem().renderItem(itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
	    }
	    
        GlStateManager.disableLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
        }
    }
    
    public void doRender(EntitySolarEruption par1Entity,double par2,double par4, double par6, float par8, float par9) {
        this.doRenderProjectile((EntitySolarEruption)par1Entity, par2, par4, par6, par8, par9);
    }
    
	@Override
    protected ResourceLocation getEntityTexture(EntitySolarEruption par1Entity) {
        return null;
    }
}