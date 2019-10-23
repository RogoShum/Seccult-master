package testmod.seccult.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.ClientProxy;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.client.FX.OceanHelmetFX;
import testmod.seccult.client.gui.GuiElementLoader;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.armor.OceanArmor;
import testmod.seccult.items.armor.Ocean.OceanHelmet;

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
			renderAir(resolution, partialTicks);
		}else 
			if(event.getType() == ElementType.ALL)
		{
			renderMagickBar(resolution, partialTicks);
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
	
    public FontRenderer getFontRenderer()
    {
        return this.mc.fontRenderer;
    }
    
    public class HUDScreen extends GuiScreen{
    	
    }
}