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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.api.ModMagicks;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.client.gui.button.SpellButton;

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
	public List<String> tooltip = new ArrayList();
    
	private NBTTagList NewMagickNBTList = new NBTTagList();
	
    int xSize, ySize, selectX, selectY, offsetX, offsetY;
	private boolean PanelOpen;
    
    public SpellProgrammerGui(EntityPlayer player)
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
    	//添加小面板的按钮
    	PanelButton.add(BUTTON_UP_A);
    	PanelButton.add(BUTTON_UP_B);
    	PanelButton.add(BUTTON_DOWN_A);
    	PanelButton.add(BUTTON_DOWN_B);
    	PanelButton.add(BUTTON_FORK);
    	//设定一下主要面板的尺寸
    	xSize = 172;
    	ySize = 172;
    	//自动计算屏幕正中心的位置
    	offsetX = (this.width - this.xSize) / 2;
    	offsetY = (this.height - this.ySize) / 2;
    	//添加新按钮
    	//同时对按钮的显示功能进行覆写
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
                    	//判断式里是指鼠标在按钮的坐标范围内
                    	//指鼠标移上去
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
		//这里把小面板的按钮做了个整合
		PanelButtonList.add(up_a);
		PanelButtonList.add(up_b);
		PanelButtonList.add(down_a);
		PanelButtonList.add(down_b);
		PanelButtonList.add(fork);
		
		buttonList.addAll(PanelButtonList);
		//这个函数块对左侧面板里的按钮进行了坐标计算
		//然后添加按钮
    	addMagickButton();
    	//暂时还没啥用
    	updateScreen();
    }
    
    
    //鼠标点击后调用
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    	super.mouseClicked(mouseX, mouseY, mouseButton);
    	clickedSpellButton(mouseX, mouseY, 1);
    	realeaseClicked(mouseX, mouseY, 1);
    }
    
    //这个是更新当前选中的按钮
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

	//鼠标松开后调用
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
    	super.mouseReleased(mouseX, mouseY, state);
    	realeaseClicked(mouseX, mouseY, 2);
    	clickedSpellButton(mouseX, mouseY, 2);
    }
    
    //这个是往其他面板增加定制按钮用的
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
    		
    		if(!ismagickButton && magick[i] >= 5)
    		{
    			ismagickButton = true;
    			z = 5;
    		}
    		else
    		{
    			z++;
    		}
    		
    		if((z < 5 && z % 4 == 0) || (z>5) && (z-1)%4 == 0) 
    		{
    			hdis = 0;
    			wdis += 17;
    		}
    		
    		if(z == 5) 
    		{
    			wdis = 49;
    			hdis = 0;
    		}
    		
    			this.buttonList.add(new SpellButton(magick[i], offsetX - 80 + (16 * hdis) + hdis, offsetY + 7 + wdis, false));
    		hdis++;
    	}
    }
    
    //这个是GUI的主要函数
    //用于渲染图形界面
    //所以每一帧都会调用
    //可以当更新器用...?
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
		    String power_name = I18n.format(selected.spellPower_name);
			String attribute_name = I18n.format(selected.spellAttribute_name);
		    this.fontRenderer.drawString(power_name, offsetX + 196, offsetY + 90, 0x404040);
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
        	this.drawTexturedModalRect(mouseX, mouseY, 32, 192, 18, 18);
        	GlStateManager.popMatrix();
        }
        handleCanBeCompile();
    }
    
    //关闭小面板
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

	//打开小面板
	private void openPanel()
	{
		if(PanelOpen)
		{	
			GlStateManager.pushMatrix();
			this.drawTexturedModalRect(selected.x - 8, selected.y -18, 80, 176, 32, 16);
			GlStateManager.popMatrix();
			String power = String.valueOf(selected.spellPower);
			String attribute = String.valueOf(selected.spellAttribute);
		    this.fontRenderer.drawString(power, selected.x - 1, selected.y - 15, 0x404040);
		    this.fontRenderer.drawString(attribute, selected.x + 11, selected.y - 15, 0x404040);
		    

		    
			for(int i = 0; i < PanelButtonList.size(); i++)
			{
				PanelButtonList.get(i).enabled = true;
					switch(PanelButtonList.get(i).id)
					{
						case BUTTON_UP_A:
							PanelButtonList.get(i).x = selected.x - 1;
							PanelButtonList.get(i).y = selected.y - 16;
							break;
						case BUTTON_UP_B:
							PanelButtonList.get(i).x = selected.x + 11;
							PanelButtonList.get(i).y = selected.y - 16;
							break;
						case BUTTON_DOWN_A:
							PanelButtonList.get(i).x = selected.x - 1;
							PanelButtonList.get(i).y = selected.y - 6;
							break;
						case BUTTON_DOWN_B:
							PanelButtonList.get(i).x = selected.x + 11;
							PanelButtonList.get(i).y = selected.y - 6;
							break;
						case BUTTON_FORK:
							PanelButtonList.get(i).x = selected.x + 19;
							PanelButtonList.get(i).y = selected.y - 17;
							break;
					}
			}
		}
	}
    
	//→ →暂时还没用
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
        	 
        	 if(!button.hasLinked) 
        	 {
        		 canProgeammer = false;
        		 return;
        	 }
        }
        
        canProgeammer = true;
    }
    
    //按过按钮之后
    //会调用这个
    //可以给按钮添加动作
    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
    	for(int i = 0; i < magick.length; i++)
    	{
    		if(button.id == magick[i])
    		{
    			SpellButton spellbutton = new SpellButton(button.id, button.x, button.y, true);
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
    	for(int i = 0; i < SelectedButtonList.size(); i++)
    	{
    		if(SelectedButtonList.get(i).isMagickButton)
    		{	
    			SelectedButtonList.get(i).checkLinkedSort();
    			NBTTagCompound nbt = new NBTTagCompound();
    			int amount = SelectedButtonList.get(i).sortID - 1;
    			nbt.setInteger("Magick", SelectedButtonList.get(i).id);
    			nbt.setInteger("MagickPower", SelectedButtonList.get(i).spellPower);
    			nbt.setInteger("MagickAttribute", SelectedButtonList.get(i).spellAttribute);
    			nbt.setInteger("SelectNumber", amount);
    			int[] SelectorList =  SelectedButtonList.get(i).Sort;
    			int[] SelectorPowerList =  SelectedButtonList.get(i).Power;
    			int[] SelectorAttributeList =  SelectedButtonList.get(i).Attribute;

    			for(int z = amount; z > 0; z--)
    			{
    				String selector = "Selector"+String.valueOf(z);
    				nbt.setInteger(selector, SelectorList[z]);
    				nbt.setInteger(selector+"Power", SelectorPowerList[z]);
    				nbt.setInteger(selector+"Attribute", SelectorAttributeList[z]);
    			}
    			NewMagickNBTList.appendTag(nbt);
    		}
    	}
    	
    	SelectedButtonList.clear();
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