package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.projectile.TRMagickProjectile;
import testmod.seccult.init.ModItems;

@SideOnly(Side.CLIENT)
public class RenderTRMagick extends Render<TRMagickProjectile>
{   
    public RenderTRMagick(RenderManager manager) {
        super(manager);
        
        this.shadowSize = 0;
    }
    
    public void doRender(TRMagickProjectile par1Entity, double x, double y, double z, float entityYaw, float partialTicks) {
    	 /*GlStateManager.pushMatrix();
         this.bindEntityTexture(par1Entity);
         GlStateManager.translate((float)x, (float)y, (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.scale(1, 1, 1);
         TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(ModItems.StarFury_Beam);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         float f = textureatlassprite.getMinU();
         float f1 = textureatlassprite.getMaxU();
         float f2 = textureatlassprite.getMinV();
         float f3 = textureatlassprite.getMaxV();

         GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate((float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

         if (this.renderOutlines)
         {
             GlStateManager.enableColorMaterial();
             GlStateManager.enableOutlineMode(this.getTeamColor(par1Entity));
         }

         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
         bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex((double)f, (double)f3).normal(0.0F, 1.0F, 0.0F).endVertex();
         bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex((double)f1, (double)f3).normal(0.0F, 1.0F, 0.0F).endVertex();
         bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex((double)f1, (double)f2).normal(0.0F, 1.0F, 0.0F).endVertex();
         bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex((double)f, (double)f2).normal(0.0F, 1.0F, 0.0F).endVertex();
         tessellator.draw();

         if (this.renderOutlines)
         {
             GlStateManager.disableOutlineMode();
             GlStateManager.disableColorMaterial();
         }

         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();*/
         super.doRender(par1Entity, x, y, z, entityYaw, partialTicks);
    }
    
	@Override
    protected ResourceLocation getEntityTexture(TRMagickProjectile par1Entity) {
        return null;
    }
}