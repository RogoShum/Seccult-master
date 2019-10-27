package testmod.seccult.events;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.ClientProxy;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.client.FX.FogFX;
import testmod.seccult.client.FX.LightFX;
import testmod.seccult.client.FX.StarFX;
import testmod.seccult.client.gui.GuiElementLoader;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.ItemWand;
import testmod.seccult.items.armor.OceanArmor;
import testmod.seccult.items.armor.ShadowSkyArmor;
import testmod.seccult.items.armor.Ocean.OceanHelmet;
import testmod.seccult.magick.MagickCompiler;
import testmod.seccult.magick.magickState.StateManager;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkMutekiGamer;
import testmod.seccult.network.NetworkPlayerAddMagick;
import testmod.seccult.network.NetworkTransFloat;

public class HUDHandler {
    int xSize, ySize, offsetX, offsetY;
    protected Minecraft mc = Minecraft.getMinecraft();
    protected HUDScreen gui = new HUDScreen();
    protected PlayerData data;
    //private OceanHelmetFX laser;
    
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onDraw(RenderGameOverlayEvent.Post event) {
		ScaledResolution resolution = event.getResolution();
		float partialTicks = event.getPartialTicks();
		if (event.getType() == ElementType.AIR) {
			//renderAir(resolution, partialTicks);
		}else 
			if(event.getType() == ElementType.ALL)
		{
			renderMagickBar(resolution, partialTicks);
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onKeyInput(ClientTickEvent event) 
	{
		
		if(Minecraft.getMinecraft().world == null)  return;
			EntityPlayer player = Minecraft.getMinecraft().player;
			KeyBinding[] keyBindings = ClientProxy.keyBindings;

			if(keyBindings[2].isKeyDown())
			{
				NetworkHandler.getNetwork().sendToServer(new NetworkTransFloat(0, player, 2));
			}
			
			if(keyBindings[1].isKeyDown())
			{
				NetworkHandler.getNetwork().sendToServer(new NetworkTransFloat(0, player, 3));
			}
			
			KeyBinding[] keyNumber = {keyBindings[3], keyBindings[4], keyBindings[5], keyBindings[6], keyBindings[7], keyBindings[8]
					,keyBindings[9], keyBindings[10], keyBindings[11]};
			
			for(int k = 0; k < keyNumber.length; k++)
        	{
        		if(keyNumber[k].isPressed() && player.getHeldItemMainhand().getItem() == ModItems.Wand)
        		{
        			ItemWand wand = (ItemWand) player.getHeldItemMainhand().getItem();
        			NetworkHandler.getNetwork().sendToAll(new NetworkTransFloat(0, player, 4));
        			if (wand.MagickList != null)
        			{
        		        int limit = wand.MagickList.tagCount();
        		        if(limit > 9)
        		        	limit = 9;
        		        
        		        if(limit >= k)
        		        {
        		        	NetworkHandler.getNetwork().sendToServer(new NetworkTransFloat(k, player, 1));

                			wand.doCircle = 0;
                			
                			{
                    			for(int c = 0; c < 20 ; c++) {
                    	            double d0 = (double)((float)player.posX + mc.world.rand.nextFloat());
                    	            double d1 = (double)((float)player.posY + player.height / 2 + mc.world.rand.nextFloat());
                    	            double d2 = (double)((float)player.posZ + mc.world.rand.nextFloat());
                    	            double d3 = (1 - 2*StateManager.rand.nextFloat()) / 2;
                    	            double d4 = (1 - 2*StateManager.rand.nextFloat()) / 2;
                    	            double d5 = (1 - 2*StateManager.rand.nextFloat()) / 2;
                    	        	Particle me = new LightFX(mc.world, (d0 + player.posX) / 2.0D, (d1 + player.posY + player.height / 2) / 2.0D, (d2 + player.posZ) / 2.0D, d3/6, d4/6, d5/6, 0.3F);
                    	        	me.setRBGColorF(wand.staffColor[0], wand.staffColor[1], wand.staffColor[2]);
                    	        	Particle smoke = new StarFX(mc.world, d0, d1, d2, d3 / 5, d4 / 5, d5 / 5, 0.1F);
                    	        	Minecraft.getMinecraft().effectRenderer.addEffect(me);
                    	        	Minecraft.getMinecraft().effectRenderer.addEffect(smoke);
                    			}
                			}
        		        }
        		    }
        		}
        	}
			
			if(ShadowSkyArmor.hasArmorSetItem(player) && player.isSneaking())
			{
				for (int c = 0; c < 10; c++) {
					double tx = player.posX + player.world.rand.nextFloat() - player.width / 2;
					double ty = player.posY + player.world.rand.nextFloat() + player.height / 2;
					double tz = player.posZ + player.world.rand.nextFloat() - player.width / 2;
					double motionX = 0.25F - 0.5 * player.world.rand.nextFloat();
					double motionY = 0.25F - 0.5 * player.world.rand.nextFloat();
					double motionZ = 0.25F - 0.5 * player.world.rand.nextFloat();

					Particle blackFog = new FogFX(player.world, tx, ty, tz, motionX / 50, motionY / 50, motionZ / 50, player.world.rand.nextFloat() * 5);
					blackFog.setRBGColorF(0, 0, 0);
		    		Minecraft.getMinecraft().effectRenderer.addEffect(blackFog);
				}
			
				if(Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown())
				{
					Vec3d look = player.getLookVec();
					player.motionX = look.x * player.getAIMoveSpeed() * 4;
					player.motionY = look.y * player.getAIMoveSpeed() * 4;
					player.motionZ = look.z * player.getAIMoveSpeed() * 4;
				
					player.noClip = true;
				}
			
				if(Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown())
				{
					Vec3d look = player.getLookVec();
					player.motionX = -look.x * player.getAIMoveSpeed() * 4;
					player.motionY = -look.y * player.getAIMoveSpeed() * 4;
					player.motionZ = -look.z * player.getAIMoveSpeed() * 4;
				
					player.noClip = true;
				}
			}
			
			if(OceanArmor.hasArmorSetItem(player) && player.isOverWater())
			{
				if(Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown())
				{
					Vec3d look = player.getLookVec();
					player.motionX = look.x * player.getAIMoveSpeed() * 4;
					player.motionY = look.y * player.getAIMoveSpeed() * 4;
					player.motionZ = look.z * player.getAIMoveSpeed() * 4;
				
					player.noClip = true;
				}
			
				if(Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown())
				{
					Vec3d look = player.getLookVec();
					player.motionX = -look.x * player.getAIMoveSpeed() * 4;
					player.motionY = -look.y * player.getAIMoveSpeed() * 4;
					player.motionZ = -look.z * player.getAIMoveSpeed() * 4;
				
					player.noClip = true;
				}
			}
	}
	
	@SideOnly(Side.CLIENT)
	public void renderMagickBar(ScaledResolution scaledRes, float pticks)
    { 	
		data = PlayerDataHandler.get(this.mc.player);
        float i = (int) data.getMaxMana();
        float k = (int)(data.getMana());
        if((mc.player.getHeldItemMainhand().getItem() == ModItems.Wand || k < i) && i > 0)
        {
        	GlStateManager.pushMatrix();
        	GlStateManager.color(0, 1, 1);
        	this.mc.getTextureManager().bindTexture(Gui.ICONS);
        	xSize = 182;
        	ySize = 5;
        	offsetX = (scaledRes.getScaledWidth() - this.xSize) / 2;
        	offsetY = (scaledRes.getScaledHeight() - this.ySize * 10);
        	gui.drawTexturedModalRect(offsetX, offsetY, 0, 64, xSize, ySize);
        	gui.drawTexturedModalRect(offsetX, offsetY, 0, 69, (int)(k / i * xSize), ySize);
            
            String s = "Mana Vaule" + data.getMana();
            int i1 = (scaledRes.getScaledWidth() - this.getFontRenderer().getStringWidth(s)) / 2;
            int j1 = scaledRes.getScaledHeight() - 31 - 4;
            this.getFontRenderer().drawString(s, i1 + 1, j1, 0);
            this.getFontRenderer().drawString(s, i1 - 1, j1, 0);
            this.getFontRenderer().drawString(s, i1, j1 + 1, 0);
            this.getFontRenderer().drawString(s, i1, j1 - 1, 0);
            this.getFontRenderer().drawString(s, i1, j1, 8453920);
            GlStateManager.popMatrix();
        }
    }
	
	@SideOnly(Side.CLIENT)
    protected void renderAir(ScaledResolution scaledRes, float pticks)
    {
    	EntityPlayer player = this.mc.player;
    	int air = 0;
    	if(OceanArmor.hasArmorSetItem(player))
    	{
    		//OceanHelmet(player.world, player.posX,  player.posY,  player.posZ, player);
    		ItemStack item = player.inventory.armorInventory.get(3);
    		OceanHelmet armor = (OceanHelmet) item.getItem();
    		air = armor.getAir();
    		if(air < 300)
    		{
        	int i1 = scaledRes.getScaledWidth() / 2 + 91;
        	int j1 = scaledRes.getScaledHeight() - 39;
        	int k2 = j1 - 10;
        	int k6 = MathHelper.ceil((double)(air - 2) * 10.0D / 300.0D);
        	int i7 = MathHelper.ceil((double)air * 10.0D / 300.0D) - k6;
        	GlStateManager.pushMatrix();
        	GlStateManager.enableBlend();
        	this.mc.getTextureManager().bindTexture(Gui.ICONS);
        	for (int k7 = 0; k7 < k6 + i7; ++k7)
        	{
            if (k7 < k6)
            {
            	gui.drawTexturedModalRect(i1 - k7 * 8 - 9, k2, 16, 18, 9, 9);
            }
            else
            {
            	gui.drawTexturedModalRect(i1 - k7 * 8 - 9, k2, 25, 18, 9, 9);
            }
        	}
        	GlStateManager.disableBlend();
        	GlStateManager.popMatrix();
    		}
    	}
    	/*else if(laser != null)
    	{
    		laser.setExpired();
    		laser = null;
    	}*/
    }
	
	/*private void OceanHelmet(World world, double xCoordIn, double yCoordIn, double zCoordIn, EntityPlayer player)
	{
		if(laser == null || player.ticksExisted % 1200 == 0)
		{
			if(laser != null)
				laser.setPlayer();
			OceanHelmetFX ocean = new OceanHelmetFX(world, xCoordIn, yCoordIn, zCoordIn, player);
			ocean.setRBGColorF(1, 1, 1);
			Minecraft.getMinecraft().effectRenderer.addEffect(ocean);
			this.laser = ocean;
			
		}
	}*/
	
	@SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer()
    {
        return this.mc.fontRenderer;
    }
    
	@SideOnly(Side.CLIENT)
    public class HUDScreen extends GuiScreen{
    	
    }
}