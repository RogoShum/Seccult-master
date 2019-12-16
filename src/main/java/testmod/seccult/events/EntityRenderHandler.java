package testmod.seccult.events;

import org.lwjgl.util.glu.Disk;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.api.PlayerSpellReleaseTool;
import testmod.seccult.api.PlayerSpellReleaseTool.PlayerSpellTool;
import testmod.seccult.events.PlayerDataUpdateEvent.QAQ;
import testmod.seccult.items.armor.ShadowSkyArmor;

public class EntityRenderHandler {
	private static ResourceLocation sphere = new ResourceLocation("seccult:textures/spell/sphere.png");
	private static ResourceLocation circle = new ResourceLocation("seccult:textures/spell/circle.png");
	private static ResourceLocation moon = new ResourceLocation("seccult:textures/spell/moon.png");
	private static ResourceLocation ball = new ResourceLocation("seccult:textures/spell/ball.png");
	private static ResourceLocation star = new ResourceLocation("seccult:textures/spell/star.png");
	private static ResourceLocation square = new ResourceLocation("seccult:textures/spell/square.png");
	
	public EntityRenderHandler() {}
	public static int ticksInGame = 0;
	public static float partialTicks = 0;
	public static float delta = 0;
	public static float total = 0;

	private static void calcDelta() {
		float oldTotal = total;
		total = ticksInGame + partialTicks;
		delta = total - oldTotal;
	}

	@SubscribeEvent
	public void renderTick(RenderTickEvent event) {
		if(event.phase == Phase.START)
			partialTicks = event.renderTickTime;
		else {
			calcDelta();
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onShadowMode(RenderPlayerEvent.Pre event)
	{
		EntityPlayer player = (EntityPlayer)event.getEntityPlayer();
		if(ShadowSkyArmor.hasArmorSetItem(player) && player.isSneaking())
		{
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void clientTickEnd(ClientTickEvent event) {
		if(event.phase == Phase.END) {
			GuiScreen gui = Minecraft.getMinecraft().currentScreen;
			if(gui == null || !gui.doesGuiPauseGame()) {
				ticksInGame++;
				partialTicks = 0;
			}
			calcDelta();
		}
	}
	
	/*@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onStaffParticle(RenderPlayerEvent.Post event)
	{
		EntityPlayer player = (EntityPlayer)event.getEntityPlayer();
		if(player.getHeldItemMainhand().getItem() instanceof ItemStaff)
		{
			float[] color = ItemStaff.getMagickColor(player.getHeldItemMainhand());

			if(color != null)
			for (int i = 0; i < 5; i++) {
				double tx = player.posX + (player.world.rand.nextFloat() * 0.1F);
				double ty = player.posY + (player.world.rand.nextFloat() * 0.1F) + player.height;
				double tz = player.posZ + (player.world.rand.nextFloat() * 0.1F);
				double motionX = 0.25F - 0.5 * player.world.rand.nextFloat();
				double motionY = 0.25F - 0.5 * player.world.rand.nextFloat();
				double motionZ = 0.25F - 0.5 * player.world.rand.nextFloat();

				Vec3d handVec = player.getLookVec().rotateYaw(-0.45F);
				Vec3d right = new Vec3d(tx, ty, tz).addVector(handVec.x, handVec.y, handVec.z);
				Particle ChlorophyteCrystal = new PentagonFX(player.world, right.x, right.y, right.z, motionX / 50, motionY / 50, motionZ / 50, player.world.rand.nextFloat() * 0.5F);
				ChlorophyteCrystal.setRBGColorF(color[0], color[1], color[2]);
		    	Minecraft.getMinecraft().effectRenderer.addEffect(ChlorophyteCrystal);
			}
		}
		
		if(player.getHeldItemMainhand().getItem() instanceof ItemStaff)
		{
			float[] color = ItemStaff.getMagickColor(player.getHeldItemOffhand());

			if(color != null)
			for (int i = 0; i < 5; i++) {
				double tx = player.posX + (player.world.rand.nextFloat() * 0.1F);
				double ty = player.posY + (player.world.rand.nextFloat() * 0.1F) + player.height;
				double tz = player.posZ + (player.world.rand.nextFloat() * 0.1F);
				double motionX = 0.25F - 0.5 * player.world.rand.nextFloat();
				double motionY = 0.25F - 0.5 * player.world.rand.nextFloat();
				double motionZ = 0.25F - 0.5 * player.world.rand.nextFloat();

				Vec3d handVec = player.getLookVec().rotateYaw(0.45F);
				Vec3d right = new Vec3d(tx, ty, tz).addVector(handVec.x, handVec.y, handVec.z);
				Particle ChlorophyteCrystal = new PentagonFX(player.world, right.x, right.y, right.z, motionX / 50, motionY / 50, motionZ / 50, player.world.rand.nextFloat() * 0.5F);
				ChlorophyteCrystal.setRBGColorF(color[0], color[1], color[2]);
		    	Minecraft.getMinecraft().effectRenderer.addEffect(ChlorophyteCrystal);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onStaffParticle(RenderLivingEvent<EntityLivingBase> event)
	{
		EntityLivingBase entity = (EntityLivingBase)event.getEntity();
	}*/
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		Minecraft mc = Minecraft.getMinecraft();

		float partialTicks = event.getPartialTicks();

		for(EntityPlayer player : mc.world.playerEntities)
		{
			PlayerData data = PlayerDataHandler.get(player);
			if(data.isMutekiGamer())
			{
				QAQ.render(player, partialTicks);
			}
			render(player, partialTicks);
		}
	}
	
	public void render(EntityPlayer player, float partTicks) {
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partTicks - renderManager.viewerPosX;
		double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partTicks - renderManager.viewerPosY;
		double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partTicks - renderManager.viewerPosZ;

		PlayerSpellTool tool = PlayerSpellReleaseTool.get(player);
		
		float scale = (float)tool.getCycle() / 50F;
		
		if(scale > 1.5F)
			scale = 1.5F;

		if(tool.getCycle()>0)
			renderSpellCircle(tool.getCycle() + partTicks, scale, x, y, z, tool.getSpellColor());
	}


	public static void renderSpellCircle(float time, float s1, double x, double y, double z, float[] colorVal) {
		TextureManager tex = Minecraft.getMinecraft().getTextureManager();
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y + 0.01, z);
		GlStateManager.rotate(90F, 1F, 0F, 0F);
	    GlStateManager.rotate(time, 0, 0, -1);
		GlStateManager.disableCull();
		GlStateManager.disableLighting();

		float red = colorVal[0];
        float green = colorVal[1];
        float blue = colorVal[2];
        
		GlStateManager.pushMatrix();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
	    GlStateManager.color(red, green, blue);
	    tex.bindTexture(circle);
	    Disk disk = new Disk();
	    disk.setTextureFlag(true);
	    disk.draw(0, s1 * 1.5F, 16, 16);    
	    GlStateManager.popMatrix();
        
		
	    
		/*GlStateManager.pushMatrix();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
	    GlStateManager.color(red, green, blue);
	    GlStateManager.translate(x, y + 0.01, z);
	    tex.bindTexture(sphere);
	    Disk disk = new Disk();
	    disk.setTextureFlag(true);
	    disk.draw(0, s1 * 1.5F, 16, 16);    
	    GlStateManager.popMatrix();
	    
		GlStateManager.pushMatrix();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
	    GlStateManager.color(red, green, blue);
	    GlStateManager.translate(x - s1 * 1.2F, y + 0.01, z);
	    tex.bindTexture(moon);
	    disk.draw(0, s1, 16, 16);
	    GlStateManager.popMatrix();
	    
		GlStateManager.pushMatrix();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
	    GlStateManager.color(red, green, blue);
	    GlStateManager.translate(x + s1 * 1.2F, y + 0.01, z);
	    tex.bindTexture(ball);
	    disk.draw(0, s1, 16, 16);
	    GlStateManager.popMatrix();
	    
		GlStateManager.pushMatrix();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
	    GlStateManager.color(red, green, blue);
	    GlStateManager.translate(x, y + 0.01, z);
	    tex.bindTexture(star);
	    disk.draw(0, s1, 16, 16);
	    GlStateManager.popMatrix();
	    
	    for(int i = 0; i < 3; ++i)
	    {
	    	GlStateManager.pushMatrix();
	    	GlStateManager.rotate(60 * i, 0, 0, 1);
	    	GlStateManager.enableBlend();
	    	GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
	    	GlStateManager.color(red, green, blue);
	    	GlStateManager.translate(x, y + 0.01, z);
	    	tex.bindTexture(square);
	    	disk.draw(0, s1 * 1.1F, 16, 16);
	    	GlStateManager.popMatrix();
	    }
		*/
		GlStateManager.enableCull();
		GlStateManager.popMatrix();
	}
}

