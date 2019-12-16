package testmod.seccult.client.tileEntity.render;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.blocks.tileEntity.tileMagickCrafting;
import testmod.seccult.client.FX.ModFX;
import testmod.seccult.client.entity.render.ParticleRender;
import testmod.seccult.events.EntityRenderHandler;

@SideOnly(Side.CLIENT)
public class RenderMagickCrafting extends TileEntitySpecialRenderer<tileMagickCrafting> 
{
	private static final ResourceLocation res = new ResourceLocation(Seccult.MODID + ":textures/tile/magick_crafing.png");
	private static final ResourceLocation light = new ResourceLocation(Seccult.MODID + ":textures/tile/magick_crafing_lighting.png");
	private static final ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
	
	private final ModelMagickCrafting model = new ModelMagickCrafting();
	private final Random random = new Random();
	
	@Override
	public void render(tileMagickCrafting tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		float craftingHeight = ((float)tile.getPower() / (float)tileMagickCrafting.MAX_MAGICKPOWER / 0.6F);
		double time = EntityRenderHandler.ticksInGame + partialTicks;

		GlStateManager.pushMatrix();
		GlStateManager.translate(x+0.5, y + 1.5 + craftingHeight * 0.45 + (0.075 * Math.sin((time + 10) / tile.randDouble) * craftingHeight), z+0.5);
		GlStateManager.rotate(180, 0, 0, 1);
		
		GlStateManager.pushMatrix();
		this.bindTexture(res);
		this.model.render();
		GlStateManager.popMatrix();
		
		
		
		GlStateManager.pushMatrix();
		int cc = 15728880;
        int j = cc % 65536;
        int k = cc / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
        GlStateManager.enableBlend();
        GlStateManager.color(0.9F, 0.2F, 0.4F, craftingHeight / 2 + 0.1F);
		this.bindTexture(light);
		this.model.render();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.5, -0.4, -0.5);
		int items = 0;
		
		for(int i = 0; i < tile.getStackList().size(); i++)
			if(tile.getStackList().get(i).isEmpty())
				break;
			else items++;
		
		float[] angles = new float[items];

		float anglePer = 360F / items;
		float totalAngle = 0F;
		for(int i = 0; i < angles.length; i++)
			angles[i] = totalAngle += anglePer;

		

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		if(tile.getStackList().size() > 2)
			time *= (double)tile.getStackList().size() / (double)10;
		
		float distance = (float)tile.getStackList().size() * 0.02F;
		if(distance > 0.15F)
			distance = 0.15F;
		
		float crafting = (float)tile.getCraftingTime() / (float)tile.getMaxCraftingTime();
		
		
		
		if(tile.getMaxCraftingTime() > 0)
		{
			distance *= crafting;
			time *= 1.0F * (1F-crafting);
		}
		
		float green = tile.getMaxCraftingTime() > 0 ? crafting : 1F;
		float blue = tile.getMaxCraftingTime() > 0 ? 1F - 0.5F * crafting : 1F;

		//distance = 0;
		GlStateManager.enableBlend();
		GlStateManager.color(1.0F, green, blue, blue);
		//setMagentaLayer(1.0F, green, blue, blue);
		for(int i = 0; i < tile.getStackList().size(); i++) {
			GlStateManager.pushMatrix();
	        ItemStack stack = tile.getStackList().get(i);
	        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
			GlStateManager.translate(0.5F, 1.4F - craftingHeight*0.15F, 0.5F);
			if(Block.getBlockFromItem(stack.getItem()) != Blocks.AIR)
				GlStateManager.translate(0F, 0.15F, 0.0F);
			GlStateManager.rotate(angles[i] + (float) time, 0F, 1F, 0F);
			GlStateManager.translate(distance * 2, 0F, distance);
			GlStateManager.rotate(-(float) time * tile.getStackList().size(), 0F, 1F, 0F);
			GlStateManager.rotate(180F, 0F, 0F, 1F);
			GlStateManager.translate(0D, 0.075 * Math.sin((time + i * 90) / 10D), 0F);
			
			Minecraft mc = Minecraft.getMinecraft();
			if(!stack.isEmpty()) {
				//setMagentaLayer(1.0F, green, blue, blue);
				//System.out.println(blue);
				//IBakedModel ibakedmodel = mc.getRenderItem().getItemModelWithOverrides(stack, (World)null, (EntityLivingBase)null);
	           // ibakedmodel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND, false);
	            mc.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
			}
			
			GlStateManager.popMatrix();
			
		}
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
		
		GlStateManager.popMatrix();
		
		if(tile.getMaxCraftingTime() > 0)
		{
			ParticleRender.renderFX(ModFX.Light, tile.getPos().getX() + 0.5, tile.getPos().getY() + 0.35F + craftingHeight * 0.45 + (0.075 * Math.sin((time + 10) / tile.randDouble) * craftingHeight)
				, tile.getPos().getZ() + 0.5, (1F-crafting) * 1.25F, 0.35F * (1F - crafting), new float[] {1F, 0.0F, 0.5F});
		}
	}
	
    protected void setMagentaLayer(float colorRed, float colorGreen, float colorBlue, float colorAlpha)
    {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
        GlStateManager.color(colorRed, colorGreen,colorBlue, colorAlpha);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.disableTexture2D();
        GlStateManager.bindTexture(0);
        GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
	
    protected ResourceLocation getEntityTexture()
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
