package testmod.seccult.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.client.gui.button.SpellButton;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkPlayerAddMagick;

@SideOnly(Side.CLIENT)
public class SpellSelectGui extends GuiScreen
{
    private static final String TEXTURE_PATH = Seccult.MODID + ":" + "textures/gui/spellselector.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);
    private static final String TEXTURE_PATH_TWO = Seccult.MODID + ":" + "textures/gui/spell_programmer.png";
    private static final ResourceLocation TEXTURE_TWO = new ResourceLocation(TEXTURE_PATH_TWO);
    
    private EntityPlayer player;
	private NBTTagList MagickList;
    private static final int BUTTON_SHIFT = 100;
    private static final int BUTTON_DELETE = 99;
    
    private SpellButton selectedOne;
    private SpellButton selectedTwo;

    protected List<SpellButton> SelectedButtonList = Lists.<SpellButton>newArrayList();
	public List<String> tooltip = new ArrayList<String>();
	
    int xSize, ySize, selectX, selectY, offsetX, offsetY;
    
    public SpellSelectGui(EntityPlayer player)
    {
    	this.player = player;
        PlayerData data = PlayerDataHandler.get(player);
        MagickList = data.getAllMagick();
        /////////////////
        /////////////////
        //////////////
        
        /*增加一个确认面版
         * *?增加一个确认面版
         	增加一个确认面版*/
    } 
    
    @Override
    public void initGui() {
    	super.initGui();
    	xSize = 256;
    	ySize = 188;
    	//自动计算屏幕正中心的位置
    	offsetX = (this.width - this.xSize) / 2;
    	offsetY = (this.height - this.ySize) / 2;
    	//添加新按钮
    	//同时对按钮的显示功能进行覆写
    	this.buttonList.add(new GuiButton(BUTTON_SHIFT, offsetX + 195, offsetY + 6, 16, 16, "")
    	{
    		@Override
    			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
    			if (this.visible)
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(TEXTURE);
                    this.drawTexturedModalRect(offsetX + 192, offsetY + 4, xSize, ySize, 21, 21);
                    mc.getTextureManager().bindTexture(TEXTURE_TWO);
                    this.drawTexturedModalRect(this.x, this.y, 16, ySize + 4, this.width, this.height);
                    
    			}
    		}
    	});
    	
    	this.buttonList.add(new GuiButton(BUTTON_DELETE, offsetX + 195, offsetY + 32, 16, 16, "")
		{
    		@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible && this.enabled)
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE_TWO);
                this.drawTexturedModalRect(offsetX + 192, offsetY + 30, 0, ySize + 20, 21, 21);
                this.drawTexturedModalRect(this.x, this.y, 144, ySize + 4, this.width, this.height);
			}
		}
		});
 
    	addMagickButton();
    	updateScreen();
    }
    
    private void addMagickButton()
    {
		int hdis = 0;
		int wdis = 0;
    	for(int i = 0; i < MagickList.tagCount(); i++)
    	{
    		NBTTagCompound SingleMagickList = MagickList.getCompoundTagAt(i);
    		
    		for(int z = 0; z < SingleMagickList.getTagList("Magick", 10).tagCount(); z++)
    		{
    			NBTTagList LoadMagick = SingleMagickList.getTagList("Magick", 10);
    			NBTTagList LoadSelect = SingleMagickList.getTagList("Selector", 10);
    			NBTTagCompound MagickNBT = LoadMagick.getCompoundTagAt(z);
    			int magickID = MagickNBT.getInteger("Magick");
    			System.out.println(magickID);
    			int slot = MagickNBT.getInteger("Slot");
    			
    			NBTTagList selector = new NBTTagList();
    			for(int x = 0; x < LoadSelect.tagCount(); x++)
    			{
    				if(LoadSelect.getCompoundTagAt(x).getInteger("Slot") == slot) {
    					NBTTagCompound SelectNBT = LoadSelect.getCompoundTagAt(x);
    					selector.appendTag(SelectNBT);
    				}
    			}
    			
    			if( i != 0&&i % 4 == 0) 
    			{
    				hdis += 20;
    				wdis = 0;
    			}
    		
    			SpellButton button = new SpellButton(magickID, offsetX + 13 + wdis, offsetY + 35 + hdis,32, 32, false);
    			button.sortID = i;
    			this.buttonList.add(button);
    			wdis+=48;
    		}
    	}
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	GlStateManager.pushMatrix();
		drawDefaultBackground();

		GlStateManager.color(1F, 1F, 1F);
		mc.getTextureManager().bindTexture(TEXTURE);
		this.drawTexturedModalRect(offsetX, offsetY, 0, 0, xSize, ySize);
		GlStateManager.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);
		
    	if(selectedOne != null)
    	{
			GlStateManager.pushMatrix();
			mc.getTextureManager().bindTexture(TEXTURE_TWO);
			GlStateManager.translate(selectedOne.x, selectedOne.y, this.zLevel);
			GlStateManager.scale(2.3, 2.3, 2.3);
			this.drawTexturedModalRect(selectedOne.x / 1000, selectedOne.y / 1000, 128, 172 + 4, 16, 16);
			GlStateManager.popMatrix();
		    /*String power_name = I18n.format(selected.spellPower_name);
			String attribute_name = I18n.format(selected.spellAttribute_name);
		    this.fontRenderer.drawString(power_name, offsetX + 196, offsetY + 90, 0x404040);
		    this.fontRenderer.drawString(attribute_name, offsetX + 196, offsetY + 100, 0x404040);
		    
		    String name = ModMagicks.getI18nNameByID(selected.id);
		    String spell_name = I18n.format(name);
		    this.fontRenderer.drawString(spell_name, offsetX + 196, offsetY + 62, 0x404040);
		    
		    String introduction = ModMagicks.getI18nNameByID(selected.id);
		    String spell_introduction = I18n.format(introduction);
		    this.fontRenderer.drawString(spell_introduction, offsetX + 196, offsetY + 72, 0x404040);*/
    	}
    	
    	if(selectedTwo != null)
    	{
    		GlStateManager.pushMatrix();
			mc.getTextureManager().bindTexture(TEXTURE_TWO);
			GlStateManager.translate(selectedOne.x, selectedOne.y, this.zLevel);
			GlStateManager.scale(2.3, 2.3, 2.3);
			this.drawTexturedModalRect(selectedOne.x / 1000, selectedOne.y / 1000, 112, 172 + 4, 16, 16);
			GlStateManager.popMatrix();
			
		    /*String power_name = I18n.format(selected.spellPower_name);
			String attribute_name = I18n.format(selected.spellAttribute_name);
		    this.fontRenderer.drawString(power_name, offsetX + 196, offsetY + 90, 0x404040);
		    this.fontRenderer.drawString(attribute_name, offsetX + 196, offsetY + 100, 0x404040);
		    
		    String name = ModMagicks.getI18nNameByID(selected.id);
		    String spell_name = I18n.format(name);
		    this.fontRenderer.drawString(spell_name, offsetX + 196, offsetY + 62, 0x404040);
		    
		    String introduction = ModMagicks.getI18nNameByID(selected.id);
		    String spell_introduction = I18n.format(introduction);
		    this.fontRenderer.drawString(spell_introduction, offsetX + 196, offsetY + 72, 0x404040);*/
    	}
    	
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
    	if(button instanceof SpellButton)
    	{
    		if(selectedTwo == button)
    		{
    			selectedTwo = null;
    			return;
    		}
    		if(selectedOne == button)
    		{
    			selectedOne = null;
    			return;
    		}
    		if(selectedTwo == null)
    		{
    			System.out.println(button);
    			selectedOne = (SpellButton) button;
    			System.out.println(selectedOne);
    		}
    		if(selectedOne == null)
    			selectedTwo = (SpellButton) button;
    	}
        switch (button.id)
        {
        case BUTTON_SHIFT:
        	
            break;
        case BUTTON_DELETE:
        	if(selectedOne != null)
        	{
        		NetworkHandler.getNetwork().sendToServer(new NetworkPlayerAddMagick(player.getUniqueID(), null, null, null, null, -selectedOne.sortID));
        		this.buttonList.remove(selectedOne);
        		selectedOne = null;
        	}
        	if(selectedTwo != null)
        	{
        		NetworkHandler.getNetwork().sendToServer(new NetworkPlayerAddMagick(player.getUniqueID(), null, null, null, null, -selectedTwo.sortID));
        		this.buttonList.remove(selectedTwo);
        		selectedTwo = null;
        	}
            break;
        default:
            super.actionPerformed(button);
            return;
        }
    } 
    
    
    @Override
    public void onGuiClosed() {
        PlayerData data = PlayerDataHandler.get(player);
        if(data.getAllMagick().tagCount() > 0)
        for(int i = 0; i < data.getAllMagick().tagCount();i++)
        {
        	data.getAllMagick().removeTag(i);
        }
    	super.onGuiClosed();
    }
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }
    
    public void updateScreen()
    {
        super.updateScreen();
    }
}