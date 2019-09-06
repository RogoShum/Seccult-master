package testmod.seccult.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.client.gui.button.SpellButton;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.active.Magick;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkPlayerAddMagick;

@SideOnly(Side.CLIENT)
public class SpellProgrammerGui extends GuiScreen
{
    private static final String TEXTURE_PATH = Seccult.MODID + ":" + "textures/gui/spell_programmer.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);
    
    private EntityPlayer player;
    private boolean canProgeammer;
	private int[] magick;
    private static final int BUTTON_CONFIRM = 100;
    private static final int BUTTON_DELETE = 99;
    
    private static final int BUTTON_UP_A = 98;
    private static final int BUTTON_DOWN_A = 97;
    
    private static final int BUTTON_UP_B = 96;
    private static final int BUTTON_DOWN_B = 95;
    private static final int BUTTON_FORK = 94;
    
    private ArrayList<Integer> PanelButton = new ArrayList<Integer>();
    
    private SpellButton clicked;
    private SpellButton selected;
    private SpellButton chooseToLink;
    private boolean ismagickButton;
    protected List<GuiButton> PanelButtonList = Lists.<GuiButton>newArrayList();
    protected List<SpellButton> SelectedButtonList = Lists.<SpellButton>newArrayList();
	public List<String> tooltip = new ArrayList<String>();
	
    int xSize, ySize, selectX, selectY, offsetX, offsetY;
	private boolean PanelOpen;
    
    public SpellProgrammerGui(SpellProgrammerDemo demo, EntityPlayer player)
    {
    	this.player = player;
        PlayerData data = PlayerDataHandler.get(player);
        magick = data.getAllMagickData();
        canProgeammer = true;
    } 
    
    @Override
    public void initGui() {
    	super.initGui();
    	//this.buttonList.clear();

    	PanelButton.add(BUTTON_UP_A);
    	PanelButton.add(BUTTON_UP_B);
    	PanelButton.add(BUTTON_DOWN_A);
    	PanelButton.add(BUTTON_DOWN_B);
    	PanelButton.add(BUTTON_FORK);

    	xSize = 172;
    	ySize = 172;

    	offsetX = (this.width - this.xSize) / 2;
    	offsetY = (this.height - this.ySize) / 2;

    	this.buttonList.add(new GuiButton(BUTTON_CONFIRM, offsetX + 195, offsetY + 6, 16, 16, "")
    	{
    		@Override
    			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
    			if (this.visible)
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(TEXTURE);
                    int x = mouseX - this.x, y = mouseY - this.y;
                    this.drawTexturedModalRect(offsetX + 192, offsetY + 4, 0, ySize + 20, 21, 21);
                    if(canProgeammer)
                    	if (x >= 0 && y >= 0 && x < this.width && y < this.height)
                    	{
                    		this.drawTexturedModalRect(this.x, this.y, 48, ySize + 4, this.width, this.height);
                    	}
                    	else
                    	{
                    		this.drawTexturedModalRect(this.x, this.y, 0, ySize + 4, this.width, this.height);
                    	}
                    	else
                        if (x >= 0 && y >= 0 && x < this.width && y < this.height)
                        {
                        	this.drawTexturedModalRect(this.x, this.y, 32, ySize + 4, this.width, this.height);
                        }
                        else
                        {
                        	this.drawTexturedModalRect(this.x, this.y, 16, ySize + 4, this.width, this.height);
                        }
                    
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
                mc.getTextureManager().bindTexture(TEXTURE);
                this.drawTexturedModalRect(offsetX + 192, offsetY + 30, 0, ySize + 20, 21, 21);
                this.drawTexturedModalRect(this.x, this.y, 144, ySize + 4, this.width, this.height);
			}
		}
		});
    	
		GuiButton up_a = new GuiButton(BUTTON_UP_A, 0, 0, 4, 2, "")
		{
    		@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible && this.enabled)
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE);
                this.drawTexturedModalRect(this.x, this.y, 80, 192, this.width, this.height);
			}
		}
		};
		
		GuiButton up_b = new GuiButton(BUTTON_UP_B, 0, 0, 4, 2, "")
		{
    		@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible && this.enabled)
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE);
                this.drawTexturedModalRect(this.x, this.y, 80, 192, this.width, this.height);
			}
		}
		};
		
		GuiButton down_a = new GuiButton(BUTTON_DOWN_A, 0, 0, 4, 2, "")
		{
    		@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible && this.enabled)
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE);
                this.drawTexturedModalRect(this.x, this.y, 80, 194, this.width, this.height);
			}
		}
		};
		
		GuiButton down_b = new GuiButton(BUTTON_DOWN_B, 0, 0, 4, 2, "")
		{
    		@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible && this.enabled)
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE);
                this.drawTexturedModalRect(this.x, this.y, 80, 194, this.width, this.height);
			}
		}
		};
    	
		GuiButton fork = new GuiButton(BUTTON_FORK, 0, 0, 3, 3, "")
		{
    		@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible && this.enabled)
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE);
                this.drawTexturedModalRect(this.x, this.y, 88, 192, this.width, this.height);
			}
		}
		};
		up_a.enabled = false;
		up_b.enabled = false;
		down_a.enabled = false;
		down_b.enabled = false;
		fork.enabled = false;

		PanelButtonList.add(up_a);
		PanelButtonList.add(up_b);
		PanelButtonList.add(down_a);
		PanelButtonList.add(down_b);
		PanelButtonList.add(fork);
		
		buttonList.addAll(PanelButtonList);

    	addMagickButton();
    	updateScreen();
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    	super.mouseClicked(mouseX, mouseY, mouseButton);
    	clickedSpellButton(mouseX, mouseY, 1);
    	realeaseClicked(mouseX, mouseY, 1);
    }

    private void clickedSpellButton(int mouseX, int mouseY, int state)
    {
        for (int i = 0; i < this.SelectedButtonList.size(); ++i)
        {
            SpellButton button = this.SelectedButtonList.get(i);
            int x = mouseX - button.x, y = mouseY - button.y;
        	if (x >= 0 && y >= 0 && x < button.height && y < button.width)
        	{
        		if(state == 1)
        		{
        			if(this.selected != button)
        			{
        				if(chooseToLink != null && !chooseToLink.isMagickButton && legaled(button))
        				{
        					chooseToLink.getLinked = button;
        					button.prveLinked = chooseToLink;
        					chooseToLink = null;
        					return;
        				}
        					this.selected = button.clicked();
        					if(!button.isMagickButton)
        					this.chooseToLink = button;
        					PanelOpen = true;
        			}
        			else 
        			{
        				this.chooseToLink = null;
        				this.selected = null;
        				PanelOpen = false;
        			}
        		}
        		else if(state == 2)
        		{
        			button.release();
        			/*if(this.selected != button)
        				this.selected = button.release();
        			else
        				this.selected = null;*/
        		}
        	}
        }
    }
    
    private boolean legaled(SpellButton button) {
		
		return true;
	}

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
    	super.mouseReleased(mouseX, mouseY, state);
    	realeaseClicked(mouseX, mouseY, 2);
    	clickedSpellButton(mouseX, mouseY, 2);
    }
    
    private void realeaseClicked(int mouseX, int mouseY, int state)
    {
    	if(clicked != null) 
    	{
    	int x = mouseX - offsetX - 15, y = mouseY - offsetY;
    	if(state == 2) {
    	if (x >= 0 && y >= 0 && x < this.xSize + 1 && y < this.ySize)
    	{
    		SelectedButtonList.add(clicked);
    		clicked = null;
    	}
    	else
    		clicked = null;
    	}
    	}
    }
    
    private void addMagickButton()
    {
		int hdis = 0;
		int wdis = 0;
		int z = 0;
    	for(int i = 0; i < magick.length; i++)
    	{
    		
    		if(!ismagickButton && magick[i] >= 3)
    		{
    			ismagickButton = true;
    			z = 3;
    		}
    		else
    		{
    			z++;
    		}
    		
    		if((z < 7 && z % 4 == 0) || (z>7) && (z-1)%4 == 0) 
    		{
    			hdis = 0;
    			wdis += 17;
    		}
    		
    		if(z == 7) 
    		{
    			wdis = 49;
    			hdis = 0;
    		}
    		
    			this.buttonList.add(new SpellButton(magick[i], offsetX - 80 + (16 * hdis) + hdis, offsetY + 7 + wdis,16, 16, false));
    		hdis++;
    	}
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

    	if(selected != null)
    	{
    		boolean onPaper = selected.checkOnPaper(mouseX, mouseY, this.width, this.height);
            if(!onPaper)
            	unloadSelectedButton();
    	}
    	GlStateManager.pushMatrix();
		drawDefaultBackground();

		GlStateManager.color(1F, 1F, 1F);
		mc.getTextureManager().bindTexture(TEXTURE);
		this.drawTexturedModalRect(offsetX + 15, offsetY, 83, 0, this.xSize + 1, this.ySize);
		this.drawTexturedModalRect(offsetX + 192, offsetY + 60, 200, this.ySize + 4, 48, 72);
		this.drawTexturedModalRect(offsetX - 87, offsetY, 0, 0, 83, this.ySize);
		
		GlStateManager.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);
        for (int i = 0; i < this.SelectedButtonList.size(); ++i)
        {
            this.SelectedButtonList.get(i).drawButton(this.mc, mouseX, mouseY, partialTicks);
            boolean onPaper = this.SelectedButtonList.get(i).checkOnPaper(mouseX, mouseY, this.width, this.height);
            if(!onPaper)
            	SelectedButtonList.remove(i);
        }
        
    	if(clicked != null) 
    	{
    		clicked.x = mouseX;
    		clicked.y = mouseY;
    		clicked.drawButton(mc, mouseX, mouseY, mc.getRenderPartialTicks());
    	}
    	
    	if(selected != null)
    	{
			GlStateManager.pushMatrix();
			mc.getTextureManager().bindTexture(TEXTURE);
			this.drawTexturedModalRect(selected.x, selected.y, 128, this.ySize + 4, 16, 16);
			GlStateManager.popMatrix();
			
				openPanel();
			if(selected.id == 0)
				closePanel();
		    String power_name = I18n.format(selected.spellPower_name);
		    this.fontRenderer.drawString(power_name, offsetX + 196, offsetY + 90, 0x404040);
		    
		    String attribute_name = I18n.format(selected.spellAttribute_name);
		    this.fontRenderer.drawString(attribute_name, offsetX + 196, offsetY + 100, 0x404040);
		    
		    String name = ModMagicks.getI18nNameByID(selected.id);
		    String spell_name = I18n.format(name);
		    this.fontRenderer.drawString(spell_name, offsetX + 196, offsetY + 62, 0x404040);
		    
		    String introduction = ModMagicks.getI18nNameByID(selected.id);
		    String spell_introduction = I18n.format(introduction);
		    this.fontRenderer.drawString(spell_introduction, offsetX + 196, offsetY + 72, 0x404040);
    	}
    	else {
    		closePanel();
    	}
    	
        for (int i = 0; i < this.PanelButtonList.size(); ++i)
        {
            this.PanelButtonList.get(i).drawButton(this.mc, mouseX, mouseY, partialTicks);
        }
    	
        if(chooseToLink != null)
        {
        	GlStateManager.pushMatrix();
			mc.getTextureManager().bindTexture(TEXTURE);
        	this.drawTexturedModalRect(mouseX - 8, mouseY - 8, 32, 192, 18, 18);
        	GlStateManager.popMatrix();
        }
        handleCanBeCompile();
    }

	private void closePanel() {
		
		PanelOpen = false;
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
	}

	private void openPanel()
	{
		if(PanelOpen)
		{
			Magick magick = ModMagicks.getMagickFromName(
		    		ModMagicks.GetMagickStringByID(this.selected.id));
			
			
			boolean doAttribute = true;
			if(selected.id >= 7)
				doAttribute = magick.doMagickNeedAtrribute();
			else
				doAttribute = false;
			
			GlStateManager.pushMatrix();
			this.drawTexturedModalRect(selected.x - 8, selected.y -18, 80, 176, 32, 16);
			GlStateManager.popMatrix();
			String power = String.valueOf(selected.spellPower);
		    this.fontRenderer.drawString(power, selected.x - 1, selected.y - 15, 0x404040);
		    
		    
		    if(doAttribute)
		    {
		    	String attribute = String.valueOf(selected.spellAttribute);
		    	this.fontRenderer.drawString(attribute, selected.x + 11, selected.y - 15, 0x404040);
		    }

			for(int i = 0; i < PanelButtonList.size(); i++)
			{
					PanelButtonList.get(i).enabled = true;
					switch(PanelButtonList.get(i).id)
					{
						case BUTTON_UP_A:
							PanelButtonList.get(i).x = selected.x - 1;
							PanelButtonList.get(i).y = selected.y - 16;
							break;
						case BUTTON_DOWN_A:
							PanelButtonList.get(i).x = selected.x - 1;
							PanelButtonList.get(i).y = selected.y - 6;
							break;
						case BUTTON_UP_B:
							if(doAttribute)
						    {
							PanelButtonList.get(i).x = selected.x + 11;
							PanelButtonList.get(i).y = selected.y - 16;
						    }
							else
								PanelButtonList.get(i).enabled = false;
							break;
						case BUTTON_DOWN_B:
							if(doAttribute)
						    {
							PanelButtonList.get(i).x = selected.x + 11;
							PanelButtonList.get(i).y = selected.y - 6;
						    }
							else
								PanelButtonList.get(i).enabled = false;
							break;
						case BUTTON_FORK:
							PanelButtonList.get(i).x = selected.x + 19;
							PanelButtonList.get(i).y = selected.y - 17;
							break;
					}
			}
		}
	}

    private void handleCanBeCompile()
    {
    	if(this.SelectedButtonList.size() < 1)
    	{
    		canProgeammer = false;
    		return;
    	}
    	
        for (int i = 0; i < this.SelectedButtonList.size(); ++i)
        {
        	 SpellButton button = this.SelectedButtonList.get(i);
        	 
        	 if(button.prveLinked != null && button.prveLinked.getLinked != button)
        		 button.prveLinked = null;
        		
        	 if(button.getLinked != null && button.getLinked.prveLinked != button)
        		 button.getLinked = null;
        	 
        	 if((button.id != 5 && button.id != 6) && !button.hasLinked)
        	{
        		canProgeammer = false;
        		return;
        	}
        	 
        	else if((button.id == 5 || button.id == 6) && button.hasLinked)
        	{
        		canProgeammer = false;
         		return;
        	}
        }
        
        canProgeammer = true;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
    	
    	for(int i = 0; i < magick.length; i++)
    	{
    		if(button.id == magick[i])
    		{
    			SpellButton spellbutton = new SpellButton(button.id, button.x, button.y, 16, 16, true);
    			clicked = spellbutton;
    			return;
    		}
    	}
        switch (button.id)
        {
        case BUTTON_CONFIRM:
        	if(canProgeammer)
        	magickCompiler();
            break;
        case BUTTON_DELETE:
        	if(this.selected == null)
        		break;
        	unloadSelectedButton();
            break;
        case BUTTON_FORK:
        	PanelOpen = false;
        	closePanel();
            break;
        case BUTTON_UP_A:
        	selected.spellPower += 1;
            break;
        case BUTTON_UP_B:
        	selected.spellAttribute += 1;
            break;
        case BUTTON_DOWN_A:
        	selected.spellPower -= 1;
            break;
        case BUTTON_DOWN_B:
        	selected.spellAttribute -= 1;
            break;
        default:
            super.actionPerformed(button);
            return;
        }
    }
    
    private boolean magickCompiler()
    {
    	boolean doEntity = true;
    	boolean doBlock = true;
    	
    	int[][] MagickThing = new int[4][];

		int amount = 0;
		List<int[]> ListMagick = new ArrayList<>();
		List<int[]> ListSelector = new ArrayList<>();
		List<int[]> ListSelectorPower = new ArrayList<>();
		List<int[]> ListSelectorAttribute = new ArrayList<>();
		int MaxLength = 0;
    	for(int i = 0; i < SelectedButtonList.size(); i++)
    	{
    		if(SelectedButtonList.get(i).isMagickButton)
    		{	
    			SelectedButtonList.get(i).checkLinkedSort();
    			int[] Selector = SelectedButtonList.get(i).Sort;
    			int[] SelectorPower = SelectedButtonList.get(i).Power;
    			int[] SelectorAttribute = SelectedButtonList.get(i).Attribute;
    			int[] Magick = {SelectedButtonList.get(i).id,
    							SelectedButtonList.get(i).spellPower,
    							SelectedButtonList.get(i).spellAttribute,
    							Selector.length};
    			
    			if(Selector.length > MaxLength)
    				MaxLength = Selector.length;
    			
    			ListSelector.add(Selector);
    			ListSelectorPower.add(SelectorPower);
    			ListSelectorAttribute.add(SelectorAttribute);
    			ListMagick.add(Magick);
    			
    			amount++;
    		}
    		else if(SelectedButtonList.get(i).id == 5)
    			doEntity = false;
    		else if(SelectedButtonList.get(i).id == 6)
    			doBlock = false;
    	}
    	
		MagickThing[0] = new int[amount];
		MagickThing[1] = new int[amount];
		MagickThing[2] = new int[amount];
		MagickThing[3] = new int[amount];
    	
		int[][] SelectorList = new int[amount][];
		int[][] SelectorPowerList = new int[amount][];
		int[][] SelectorAttributeList = new int[amount][];
		
		for(int i = 0; i < amount; i++)
		{
			int[] m = ListMagick.get(i);
			for(int z = 0; z < 4; z ++)
        	{
				MagickThing[z][i] = m[z];
        	}
			int L = MagickThing[3][i];
			int[] im = ListSelector.get(i);
			SelectorList[i] = new int[L];
			SelectorPowerList[i] = new int[L];
			SelectorAttributeList[i] = new int[L];
			for(int z = 0; z < im.length; z++)
			{
				SelectorList[i][z] = ListSelector.get(i)[z];
				SelectorPowerList[i][z] = ListSelectorPower.get(i)[z];
				SelectorAttributeList[i][z] = ListSelectorAttribute.get(i)[z];
			}
		}
    	NetworkHandler.getNetwork().sendToServer(new NetworkPlayerAddMagick(player.getUniqueID(), MagickThing, SelectorList, SelectorPowerList, SelectorAttributeList, amount, doEntity, doBlock));
    	//SelectedButtonList.clear();
    	selected = null;
    	chooseToLink = null;
		return true;
    }
    
    private void unloadSelectedButton()
    {
		this.selected.getLinked = null;
		this.selected.prveLinked = null;
		if(chooseToLink == selected)
			chooseToLink = null;
    	if(SelectedButtonList.contains(this.selected))
    		SelectedButtonList.remove(this.selected);
    	for(int i = 0; i < SelectedButtonList.size(); i++)
    	{
    		if(selected == SelectedButtonList.get(i).getLinked)
    			SelectedButtonList.get(i).getLinked = null;
    		if(selected == SelectedButtonList.get(i).prveLinked)
    			SelectedButtonList.get(i).prveLinked = null;
    	}
		this.selected = null;
    }
    
    @Override
    public void onGuiClosed() {
    	super.onGuiClosed();
    }
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }
    
    public void updateScreen()
    {
        super.updateScreen();
        PlayerData data = PlayerDataHandler.get(player);
        magick = data.getAllMagickData();
    }
}