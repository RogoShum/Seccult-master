package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.projectile.EntitySolarEruption;
import testmod.seccult.entity.projectile.TRBladeBeam;
import testmod.seccult.init.ModItems;

@SideOnly(Side.CLIENT)
public class RenderSolarPart extends Render<Entity>
{   
    public RenderSolarPart(RenderManager manager) {
        super(manager);
        
        this.shadowSize = 0;
    }
    
    public void doRenderProjectile(Entity projectile, double x, double y, double z, float entityYaw, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
    }
    
    public void doRender(Entity par1Entity,double par2,double par4, double par6, float par8, float par9) {
        this.doRenderProjectile((Entity)par1Entity, par2, par4, par6, par8, par9);
    }
    
	@Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return null;
    }
}