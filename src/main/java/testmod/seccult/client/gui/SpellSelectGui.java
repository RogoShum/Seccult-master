package testmod.seccult.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
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
import testmod.seccult.client.gui.button.MagickInfoButton;
import testmod.seccult.client.gui.button.SpellButtonAdvance;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.active.Magick;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkPlayerAddMagick;
import testmod.seccult.network.NetworkTransFloat;
import testmod.seccult.network.NetworkTransString;

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
    private static final int BUTTON_UPSORT = 98;
    
    private static final int BUTTON_UP_A = 97;
    private static final int BUTTON_DOWN_A = 96;
    
    private static final int BUTTON_UP_B = 95;
    private static final int BUTTON_DOWN_B = 94;
    private static final int BUTTON_FORK = 93;
    
    protected List<GuiButton> PanelButtonList = Lists.<GuiButton>newArrayList();
    protected List<SpellButtonAdvance> SpellButtonList = Lists.<SpellButtonAdvance>newArrayList();
    
    private SpellButtonAdvance selectedOne;
    private SpellButtonAdvance selectedTwo;

    private MagickInfoButton prveMagickInfo;
    private MagickInfoButton MagickInfo;
    private GuiTextField nameField;
    
    protected List<MagickInfoButton> MagickInfoButtonList = Lists.<MagickInfoButton>newArrayList();
	public List<String> tooltip = new ArrayList<String>();
	
    int xSize, ySize, selectX, selectY, offsetX, offsetY;
    
    private PlayerData data;
    
    public SpellSelectGui(EntityPlayer player)
    {
    	this.player = player;
    	this.data = PlayerDataHandler.get(player);
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
    	ySize = 158;
    	
    	offsetX = (this.width - this.xSize) / 2;
    	offsetY = (this.height - this.ySize) / 2 - 15;
    	
    	 this.nameField = new GuiTextField(0, this.fontRenderer, (int)(offsetX * 2.25), offsetY + 6, 103, 12);
         this.nameField.setTextColor(-1);
         this.nameField.setDisabledTextColour(-1);
         this.nameField.setEnableBackgroundDrawing(false);
         this.nameField.setMaxStringLength(35);
         
     	this.buttonList.add(new GuiButton(BUTTON_SHIFT, offsetX + 232, offsetY + 18, 16, 16, "")
     	{
     		@Override
     			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
     			if (this.visible)
                 {
     				GlStateManager.pushMatrix();
                     GlStateManager.color(1.0F, 1.0F, 1.0F);
                     mc.getTextureManager().bindTexture(TEXTURE);
                     GlStateManager.scale(2, 2, 0);
                     GlStateManager.translate((double)(offsetX + 232)/2, (offsetY + 18)/2, this.zLevel);
                     this.drawTexturedModalRect(0, 0, 0, -68, 8, 8);
                     GlStateManager.popMatrix();
                     
     			}
     		}
     	});
     	
     	this.buttonList.add(new GuiButton(BUTTON_DELETE, offsetX + 232, offsetY + 40, 16, 16, "")
 		{
     		@Override
 			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
 			if (this.visible && this.enabled)
             {
 				GlStateManager.pushMatrix();
                 GlStateManager.color(1.0F, 1.0F, 1.0F);
                 mc.getTextureManager().bindTexture(TEXTURE);
                 GlStateManager.scale(2, 2, 0);
                 GlStateManager.translate((double)(offsetX + 232)/2, (offsetY + 40)/2, this.zLevel);
                 this.drawTexturedModalRect(0, 0, 8, -68, 8, 8);
                 GlStateManager.popMatrix();
 			}
 		}
 		});
     	
     	this.buttonList.add(new GuiButton(BUTTON_DELETE, offsetX + 232, offsetY + 62, 16, 16, "")
 		{
     		@Override
 			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
 			if (this.visible && this.enabled)
             {
 				GlStateManager.pushMatrix();
                 GlStateManager.color(1.0F, 1.0F, 1.0F);
                 mc.getTextureManager().bindTexture(TEXTURE);
                 GlStateManager.scale(2, 2, 0);
                 GlStateManager.translate((double)(offsetX + 232)/2, (offsetY + 62)/2, this.zLevel);
                 this.drawTexturedModalRect(0, 0, 16, -68, 8, 8);
                 GlStateManager.popMatrix();
 			}
 		}
 		});
         
    	updateScreen();
    }
    
    private void addMagickButton(NBTTagList MagickList)
    {
    	this.buttonList.removeAll(SpellButtonList);
    	SpellButtonList.clear();
		int wdis = 0;
    	for(int i = 0; i < MagickList.tagCount(); i++)
    	{
    		NBTTagCompound SingleMagickList = MagickList.getCompoundTagAt(i);
    		SpellButtonAdvance button = new SpellButtonAdvance(i, SingleMagickList, offsetX + 2, offsetY + 24 + wdis, 56, 16, false);
    		button.sortID = i;
			
			this.SpellButtonList.add(button);
			wdis+=16;
    	}
    	this.buttonList.addAll(SpellButtonList);
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	updateScreen();
    	GlStateManager.pushMatrix();
		drawDefaultBackground();

		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableBlend();
		mc.getTextureManager().bindTexture(TEXTURE);
		this.drawTexturedModalRect(offsetX, offsetY, 0, 0, xSize, ySize);
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		if(selectedOne == selectedTwo)
		{
			selectedTwo = null;
		}
		
    	if(selectedOne != null)
    	{
    		/*GlStateManager.pushMatrix();
			mc.getTextureManager().bindTexture(TEXTURE_TWO);
			this.drawTexturedModalRect(selectedOne.x, selectedOne.y, 128, 172 + 4, 16, 16);
			GlStateManager.popMatrix();*/
    		
			GlStateManager.pushMatrix();
			mc.getTextureManager().bindTexture(TEXTURE);
			GlStateManager.enableBlend();
			this.drawTexturedModalRect(this.offsetX, selectedOne.y, 0, -86, 56, 16);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
    	}
    	else
    	{
    		buttonList.removeAll(MagickInfoButtonList);
    		MagickInfoButtonList.clear();
    		this.nameField.setText("");
    	}
    	
    	if(selectedTwo != null)
    	{
    		GlStateManager.pushMatrix();
			mc.getTextureManager().bindTexture(TEXTURE_TWO);
			this.drawTexturedModalRect(selectedTwo.x, selectedTwo.y, 112, 172 + 4, 16, 16);
			GlStateManager.popMatrix();
			
			GlStateManager.pushMatrix();
			mc.getTextureManager().bindTexture(TEXTURE);
			GlStateManager.enableBlend();
			this.drawTexturedModalRect(this.offsetX, selectedTwo.y, 0, -86, 56, 16);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
    	}
    	this.nameField.setEnabled(this.selectedOne!=null);
    	this.nameField.drawTextBox();
    	
    	if(MagickInfo != null)
    	{
    		GlStateManager.pushMatrix();
    		mc.getTextureManager().bindTexture(TEXTURE_TWO);
    		this.drawTexturedModalRect(MagickInfo.x + 42, MagickInfo.y + 3, 64, 172 + 4, 16, 16);
    		GlStateManager.popMatrix();
    	}
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.nameField.textboxKeyTyped(typedChar, keyCode))
        {
            this.renameSpell();
        }
        else
        {
            super.keyTyped(typedChar, keyCode);
            if(keyCode == Keyboard.KEY_E)
            {
            	
            }
        }
    }
    
    public void renameSpell()
    {
    	if(this.selectedOne!=null)
    	{
    		NetworkHandler.getNetwork().sendToServer(new NetworkTransString(selectedOne.sortID, this.nameField.getText(), player, 1));
    	}
    }
    
    public static int newLine(int y)
    {
    	return y+10;
    }
    
    private void openPanel(MagickInfoButton selected)
	{
    	
		
		buttonList.removeAll(PanelButtonList);
		PanelButtonList.clear();
    	GuiButton up_a = new GuiButton(BUTTON_UP_A, 0, 0, 4, 2, "")
		{
    		@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible && this.enabled)
            {
				this.zLevel = 1.0F;
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE_TWO);
                this.drawTexturedModalRect(this.x, this.y, 56, 172 + 20, this.width, this.height);
			}
		}
		};
		
		GuiButton up_b = new GuiButton(BUTTON_UP_B, 0, 0, 4, 2, "")
		{
    		@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible && this.enabled)
            {
				this.zLevel = 1.0F;
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE_TWO);
                this.drawTexturedModalRect(this.x, this.y, 56, 172 + 20, this.width, this.height);
			}
		}
		};
		
		GuiButton down_a = new GuiButton(BUTTON_DOWN_A, 0, 0, 4, 2, "")
		{
    		@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible && this.enabled)
            {
				this.zLevel = 1.0F;
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE_TWO);
                this.drawTexturedModalRect(this.x, this.y, 56, 172 + 28, this.width, this.height);
			}
		}
		};
		
		GuiButton down_b = new GuiButton(BUTTON_DOWN_B, 0, 0, 4, 2, "")
		{
    		@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible && this.enabled)
            {
				this.zLevel = 1.0F;
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE_TWO);
                this.drawTexturedModalRect(this.x, this.y, 56, 172 + 28, this.width, this.height);
			}
		}
		};
    	
		GuiButton fork = new GuiButton(BUTTON_FORK, 0, 0, 3, 3, "")
		{
    		@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible && this.enabled)
            {
				this.zLevel = 1.0F;
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE_TWO);
                this.drawTexturedModalRect(this.x, this.y, 56, 172 + 36, this.width, this.height);
			}
		}
		};
		
		PanelButtonList.add(up_a);
		PanelButtonList.add(up_b);
		PanelButtonList.add(down_a);
		PanelButtonList.add(down_b);
		PanelButtonList.add(fork);
		
		buttonList.addAll(PanelButtonList);
    	
			boolean doAttribute = true;
			boolean doStrengh = true;
			
			if(selected.MagickId >= 7)
			{
				Magick magick = ModMagicks.getMagickFromName(
			    		ModMagicks.GetMagickStringByID(selected.MagickId));
				doAttribute = magick.doMagickNeedAtrribute();
				doStrengh = magick.doMagickNeedStrength();
			}
			else
				doAttribute = false;
			
			if(!doStrengh && !doAttribute)
				return;

			for(int i = 0; i < PanelButtonList.size(); i++)
			{
					switch(PanelButtonList.get(i).id)
					{
						case BUTTON_UP_A:
							if(doStrengh && !doAttribute)
						    {
							PanelButtonList.get(i).x = selected.x + 48;
							PanelButtonList.get(i).y = selected.y+5;
						    }
							else
							if(doStrengh)
						    {
							PanelButtonList.get(i).x = selected.x + 44;
							PanelButtonList.get(i).y = selected.y+5;
						    }
							break;
						case BUTTON_DOWN_A:
							if(doStrengh && !doAttribute)
						    {
							PanelButtonList.get(i).x = selected.x + 48;
							PanelButtonList.get(i).y = selected.y+14;
						    }
							else
							if(doStrengh)
						    {
							PanelButtonList.get(i).x = selected.x + 44;
							PanelButtonList.get(i).y = selected.y+14;
						    }
							break;
						case BUTTON_UP_B:
							if(doAttribute)
						    {
							PanelButtonList.get(i).x = selected.x + 51;
							PanelButtonList.get(i).y = selected.y+5;
						    }
							else
								PanelButtonList.get(i).enabled = false;
							break;
						case BUTTON_DOWN_B:
							if(doAttribute)
						    {
							PanelButtonList.get(i).x = selected.x + 51;
							PanelButtonList.get(i).y = selected.y+14;
						    }
							else
								PanelButtonList.get(i).enabled = false;
							break;
						case BUTTON_FORK:
							PanelButtonList.get(i).x = selected.x + 55;
							PanelButtonList.get(i).y = selected.y + 3;
							break;
					}
			}
	}
    
	private void closePanel() {
		for(int i = 0; i < PanelButtonList.size(); i++)
		{
			PanelButtonList.get(i).enabled = false;
				switch(PanelButtonList.get(i).id)
				{
					case BUTTON_UP_A:
						PanelButtonList.get(i).x = 0;
						PanelButtonList.get(i).y = 0;
						break;
					case BUTTON_UP_B:
						PanelButtonList.get(i).x = 0;
						PanelButtonList.get(i).y = 0;
						break;
					case BUTTON_DOWN_A:
						PanelButtonList.get(i).x = 0;
						PanelButtonList.get(i).y = 0;
						break;
					case BUTTON_DOWN_B:
						PanelButtonList.get(i).x = 0;
						PanelButtonList.get(i).y = 0;
						break;
					case BUTTON_FORK:
						PanelButtonList.get(i).x = 0;
						PanelButtonList.get(i).y = 0;
						break;
				}
		}
		
		buttonList.removeAll(PanelButtonList);
		PanelButtonList.clear();
	}
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
    	if(button instanceof SpellButtonAdvance)
    	{
    		/*if(selectedTwo == button)
    		{
    			selectedTwo = null;
    			return;
    		}*/
    		
			buttonList.removeAll(MagickInfoButtonList);
			MagickInfoButtonList.clear();
    		
    		closePanel();
    		MagickInfo = null;
    		if(selectedOne == button)
    		{
    			selectedOne = null;
    			return;
    		}

    		selectedOne = (SpellButtonAdvance) button;
    		this.nameField.setText(selectedOne.getSpellName());
    		
		    int[][] magick = selectedOne.getMagickAttribute();

		    for(int i = 0; i < selectedOne.SpellCount; ++i)
		    {
				 int posY = 0;
				 int posX = 60;
				 
				 MagickInfoButton infoButton = new MagickInfoButton(offsetX + 60 + (i * posX), offsetY + 36 + posY, 40, 24, false);
				 infoButton.pushInfo(magick[i][0], magick[i][1], magick[i][2], selectedOne.buttonId, true, i);
				 infoButton.pushed = true;
				 MagickInfoButtonList.add(infoButton);
				 
				 posY = posY+32;
				 
				 int[][] selector = selectedOne.getSelectorAttribute(i);
				 for(int x = 0; x < selectedOne.getSelectorCount(magick[i][3]); ++x)
				 {
					 MagickInfoButton buttonS = new MagickInfoButton(offsetX + 60 + (i * posX), offsetY + 36 + posY, 40, 18, false);
					 buttonS.pushInfo(selector[x][0], selector[x][1], selector[x][2], selectedOne.buttonId, false, x);
					 MagickInfoButtonList.add(buttonS);
					 posY = posY+20;
				 }
		    }

	    	this.buttonList.addAll(MagickInfoButtonList);
		    
    		//else
    			//selectedTwo = (SpellButtonAdvance) button;	
    	}
    	
    	if(button instanceof MagickInfoButton)
    	{
    		MagickInfo=(MagickInfoButton)button;
    		openPanel((MagickInfoButton)button);
    	}

        switch (button.id)
        {
        case BUTTON_SHIFT:
        	if(selectedOne != null && selectedTwo != null)
        	{
        		int[][] buttonShift = new int[1][];
        		buttonShift[0] = new int[2];
        		buttonShift[0][0] = selectedOne.sortID;
        		buttonShift[0][1] = selectedTwo.sortID;
        		NetworkHandler.getNetwork().sendToServer(new NetworkPlayerAddMagick(player.getUniqueID(), buttonShift, null, null, null, -12451, false, false));
        		selectedOne = null;
        		selectedTwo = null;
        		
        		onGuiClosed();
        	}
            break;
        case BUTTON_DELETE:
        	if(selectedOne != null)
        	{
        		NetworkHandler.getNetwork().sendToServer(new NetworkPlayerAddMagick(player.getUniqueID(), null, null, null, null, -(selectedOne.sortID), false, false));
        		this.buttonList.remove(selectedOne);
        		selectedOne = null;
        		onGuiClosed();
        	}
        	if(selectedTwo != null)
        	{
        		NetworkHandler.getNetwork().sendToServer(new NetworkPlayerAddMagick(player.getUniqueID(), null, null, null, null, -(selectedTwo.sortID), false, false));
        		this.buttonList.remove(selectedTwo);
        		selectedTwo = null;
        		onGuiClosed();
        	}
            break;
        case BUTTON_UPSORT:
        	if(selectedOne!= null && selectedOne.sortID > 0)
        	{
        		int[][] buttonShift = new int[1][];
        		buttonShift[0] = new int[2];
        		buttonShift[0][0] = selectedOne.sortID;
        		buttonShift[0][1] = selectedOne.sortID-1;
        		NetworkHandler.getNetwork().sendToServer(new NetworkPlayerAddMagick(player.getUniqueID(), buttonShift, null, null, null, -12451, false, false));
        		selectedOne = null;
        		onGuiClosed();
        	}
        	break;
        case BUTTON_FORK:
        	closePanel();
        	MagickInfo=null;
            break;
        case BUTTON_UP_A:
        	updateSpellVir(MagickInfo.MagickId > 6? true:false, MagickInfo.spellSort, MagickInfo.insideSort, true, MagickInfo.Magick_Power + 1);
            break;
        case BUTTON_UP_B:
        	updateSpellVir(MagickInfo.MagickId > 6? true:false, MagickInfo.spellSort, MagickInfo.insideSort, false, MagickInfo.Magick_Attribute + 1);
            break;
        case BUTTON_DOWN_A:
        	updateSpellVir(MagickInfo.MagickId > 6? true:false, MagickInfo.spellSort, MagickInfo.insideSort, true, MagickInfo.Magick_Power - 1 < 1? 1:MagickInfo.Magick_Power - 1);
            break;
        case BUTTON_DOWN_B:
        	updateSpellVir(MagickInfo.MagickId > 6? true:false, MagickInfo.spellSort, MagickInfo.insideSort, false, MagickInfo.Magick_Attribute - 1 < 1? 1:MagickInfo.Magick_Attribute - 1);
            break;
        default:
            super.actionPerformed(button);
            return;
        }
    }
    
    private void updateSpellVir(boolean isMagick, int spellSort, int magickSort, boolean isPower, int value)
    {
    	if(isPower)
    		MagickInfo.Magick_Power = value;
    	else
    		MagickInfo.Magick_Attribute = value;
    	
    	int[][] buttonShift = new int[1][];
		buttonShift[0] = new int[5];
		buttonShift[0][0] = isMagick? 1 : 0;
		buttonShift[0][1] = spellSort;
		buttonShift[0][2] = magickSort;
		buttonShift[0][3] = isPower? 1 : 0;
		buttonShift[0][4] = value;
    	NetworkHandler.getNetwork().sendToServer(new NetworkPlayerAddMagick(player.getUniqueID(), buttonShift, null, null, null, -12452, false, false));
    }
    
    @Override
    public void onGuiClosed() {
        PlayerData data = PlayerDataHandler.get(player);
        if(data.getAllMagick().tagCount() > 0)
        for(int i = 0; i < data.getAllMagick().tagCount();i++)
        {
        	data.getAllMagick().removeTag(i);
        }
        NetworkHandler.getNetwork().sendToServer(new NetworkTransFloat(0, player, 4));
    	super.onGuiClosed();
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    	super.mouseClicked(mouseX, mouseY, mouseButton);
    	this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }
    
    public void updateScreen()
    {
        super.updateScreen();
        PlayerData data = PlayerDataHandler.get(player);
        if(MagickList != data.getAllMagick())
        {
        	MagickList = data.getAllMagick();
        	addMagickButton(MagickList);
        }
    }
}