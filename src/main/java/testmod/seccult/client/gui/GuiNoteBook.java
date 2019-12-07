package testmod.seccult.client.gui;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.textlib.TextLib;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.items.ItemNoteBook;

public class GuiNoteBook extends GuiScreen {
	private static final Logger LOGGER = LogManager.getLogger();
    /** The player editing the book */
    private EntityPlayer readingPlayer;
    private final ItemStack book;
    
    private int playerTick;
    private boolean updateButton;
    
    private int bookTotalPages = 0;
    private int bookTotalPagesCache = 0;
    private int currPage = 0;
    private int currPageCache;
    
    private NBTTagCompound categoryComponents;
    
    private NBTTagCompound itemsComponents[];
    private String[][] itemTagList;
    
    private NBTTagCompound itemsComponentsCache;
    private String[][] itemTagListCache;
    
    protected List<GuiButton> CateButtonList = Lists.<GuiButton>newArrayList();
    protected List<GuiButton> ItemButtonList = Lists.<GuiButton>newArrayList();
    
    private GuiNoteBook.NextPageButton buttonNextPage;
    private GuiNoteBook.NextPageButton buttonPreviousPage;

    private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation(Seccult.MODID + ":" + "textures/gui/notebook.png");
    
    public GuiNoteBook(EntityPlayer player, ItemStack book) {
    	this.readingPlayer = player;
        this.book = book;
        this.categoryComponents = ItemNoteBook.getAllCategories(book);
        book.getTagCompound().removeTag("Note_New_Knowladge");
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        if(updateButton)
        {
        	if(this.readingPlayer.ticksExisted > this.playerTick + 5 && this.itemTagList == null && this.itemsComponents != null && this.currPage >= 0)
        	{
        		this.buttonList.removeAll(this.ItemButtonList);
        		this.ItemButtonList.clear();
        		int offsetY = 0;
        		
        		Set<String> keys = this.itemsComponents[this.currPage].getKeySet();
        		Iterator<String> it = keys.iterator();
        		
        		while(it.hasNext())
        		{
        			GuiItemButton item = new GuiItemButton(it.next(), 155, 40 + offsetY*18);
        			this.ItemButtonList.add(item);
        			offsetY++;
        		}
        		
        		this.buttonList.addAll(this.ItemButtonList);
        		
        		updateButton = false;
        		updateItemIcon();
        	}
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);

        int i = (this.width - 192) / 2;
        int j = 2;
        this.buttonNextPage = (GuiNoteBook.NextPageButton)this.addButton(new GuiNoteBook.NextPageButton(1, i + 120, 165, true));
        this.buttonPreviousPage = (GuiNoteBook.NextPageButton)this.addButton(new GuiNoteBook.NextPageButton(2, i + 38, 165, false));
        int baseX = 165;
        int baseY = 70;
        int offY = 0;
        int count = 0;
        if(this.categoryComponents != null)
        {
        	Set<String> keys = this.categoryComponents.getKeySet();
        		
        	for(String cate : keys)
        	{
        		this.addCateButton(new CategoryButton(cate, baseX + count*38, baseY + offY*35));
        		
        		count+=1;
        		
        		if(count % 3 == 0)
        		{
        			count=0;
        			offY+=1;
        		}
        	}
        }
        this.buttonList.addAll(CateButtonList);
        this.updateButtons();
    }

    protected <T extends CategoryButton> T addCateButton(T buttonIn)
    {
        this.CateButtonList.add(buttonIn);
        return buttonIn;
    }
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }
    
    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    private void updateButtons()
    {
        if(this.currPage > this.bookTotalPages)
        	this.currPage = this.bookTotalPages;

        this.updateButton = true;
    	
        this.buttonNextPage.visible = this.currPage < this.bookTotalPages;
        this.buttonPreviousPage.visible = this.currPage > -1;

        updateItemIcon();
    }

    private void updateItemIcon()
    {
    	if(this.itemTagList != null)
        {
        	for(GuiButton cate : this.CateButtonList)
        	{
        		cate.visible = false;
        	}
        	
        	for(GuiButton cate : this.ItemButtonList)
        	{
        		cate.visible = false;
        	}
        }
        else if(!this.ItemButtonList.isEmpty())
        {
        	for(GuiButton cate : this.CateButtonList)
        	{
        		cate.visible = false;
        	}
        	
        	for(GuiButton cate : this.ItemButtonList)
        	{
        		cate.visible = true;
        	}
        }
        else
        	for(GuiButton cate : this.CateButtonList)
        	{
        		cate.visible = true;
        	}
    }
    
    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 1)
            {
            	if(this.currPage <= this.bookTotalPages)
                {
                    ++this.currPage;
                }
            }
            else if (button.id == 2)
            {
            	if(this.currPage > 0)
                {
                    --this.currPage;
                }
            	else if(this.itemTagList != null)
            	{
            		this.bookTotalPages = this.itemsComponents.length - 1;
            		this.currPage = this.currPageCache;
                	this.itemTagList=null;
            	}
                else if(!this.ItemButtonList.isEmpty())
                {
                	this.buttonList.removeAll(this.ItemButtonList);
                	this.ItemButtonList.clear();
                	--this.currPage;
                }
            }

            if(button instanceof GuiItemButton)
            {
            	GuiItemButton item = (GuiItemButton) button;
            	NBTTagList tagList = ItemNoteBook.getNoteItem(this.itemsComponents[this.currPage], item.buttonString);
            	int page = 1;
            	int line = 0;
            	String[][] newTagList = new String[page][8];
            	for(Iterator<NBTBase> it = tagList.iterator(); it.hasNext();)
            	{
            		NBTTagString tag = (NBTTagString) it.next();
            		String string = "    " + I18n.format(tag.getString());
            		String[] splited= this.splitString(string);
            		for(int i = 0; i < splited.length; ++i)
            		{
            			newTagList[page-1][line] = splited[i];
            			line++;
            			if(line > 7)
            			{
            				++page;
            				String[][] newNewTagList = new String[page][8];
            				for (int x = 0; x < newTagList.length; x++) {
            					for (int z = 0; z < newTagList[x].length; z++) {
            						newNewTagList[x][z] = newTagList[x][z];
            					}
            				}
            				
            				newTagList = newNewTagList;
            				
            				line=0;
            			}
            		}
            	}

            	this.bookTotalPages = page - 1;
            	this.currPageCache = this.currPage;
            	this.bookTotalPagesCache = this.bookTotalPages;
            	this.currPage = 0;
            	 this.itemTagList = newTagList;
            }
            else if(button instanceof CategoryButton)
            {
            	CategoryButton cate = (CategoryButton) button;
            	NBTTagCompound allTag = ItemNoteBook.getCategory(categoryComponents, cate.buttonString);
            	int cache = allTag.getSize() % 7 > 0 ? 1 : 0;
            	int size = allTag.getSize() / 7 + cache;
            	
            	this.itemsComponents = new NBTTagCompound[size];
            	this.bookTotalPages = size - 1;
            	this.currPage = 0;
            	
            	int page= 0;
            	
            	NBTTagCompound cacheTag = new NBTTagCompound();
            	
            	Set<String> keys = allTag.getKeySet();
        		Iterator<String> it = keys.iterator();
            	
            	for(int i = 0; i < allTag.getSize(); ++i)
            	{
            		String name = it.next();
            		cacheTag.setTag(name, allTag.getTag(name));
            		if(i >= 6)
            		{
            			this.itemsComponents[page] = cacheTag;
            			page++;
            			cacheTag = new NBTTagCompound();
            		}
            		else if(i == allTag.getSize() - 1)
            		{
            			this.itemsComponents[page] = cacheTag;
            		}
            	}

            	this.playerTick = this.readingPlayer.ticksExisted;
            }

            this.updateButtons();
        }
    }

    @Nullable
    public String[] splitString(String text)
    {
        if (text == null)
        {
            return null;
        }
        else
        {
            int i = 0;
            boolean flag = false;
            boolean flag_2 = false;
            String[] string = new String[0];
            String s = "";
            for (int j = 0; j < text.length(); ++j)
            {
                char c0 = text.charAt(j);
                int k = this.fontRenderer.getCharWidth(c0);

                if (k < 0 && j < text.length() - 1)
                {
                    ++j;
                    c0 = text.charAt(j);

                    if (c0 != 'l' && c0 != 'L')
                    {
                        if (c0 == 'r' || c0 == 'R')
                        {
                            flag = false;
                        }
                    }
                    else
                    {
                        flag = true;
                    }

                    k = 0;
                }

                i += k;

                if (flag && k > 0)
                {
                    ++i;
                }
                
                s = s + c0;
                
                if(i >= 110)
                {
                	String[] backup = string.clone();
                	string = new String[string.length+1];
                	for(int c = 0; c < backup.length; ++c)
                	{
                		string[c] = backup[c];
                	}
                	string[string.length - 1] = s;
                	i = 0;
                	s = "";
                	flag_2 = true;
                }
            }
            
            if(!flag_2)
            {
            	string = new String[1];
            	string[0] = text;
            }
            else if(i < 110)
            {
            	String[] backup = string.clone();
            	string = new String[string.length+1];
            	for(int c = 0; c < backup.length; ++c)
            	{
            		string[c] = backup[c];
            	}
            	string[string.length - 1] = s;
            	i = 0;
            	s = "";
            }
            
            return string;
        }
    }
    
    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Minecraft.getMinecraft().gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.displayGuiScreen((GuiScreen)null);

            if (this.mc.currentScreen == null)
            {
                this.mc.setIngameFocus();
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
        int i = (this.width - 192) / 2;
        int c = (this.height - 230) / 2;
        int j = 2;
        this.drawTexturedModalRect(i, c, 0, 0, 168, 192);

        String s4 = I18n.format(TextLib.NOTE_BASE);
        this.fontRenderer.drawString(s4, 192 - 40, 18, 0);

        if(this.itemTagList != null)
        {
        	int baseX = 192 - 40;
        	int baseY = 40;
        	for(int f = 0; f < this.itemTagList[this.currPage].length; ++f)
        	{
        		String tag = this.itemTagList[this.currPage][f];
        		this.fontRenderer.drawString(tag, baseX, baseY, 0);
        		baseY += 16;
        	}
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void drawItemIcon(String string, int x, int y)
    {
    		TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(Item.getByNameOrId(string));
        	GlStateManager.pushMatrix();
        	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        	this.mc.getTextureManager().bindTexture(net.minecraft.client.renderer.texture.TextureMap.LOCATION_BLOCKS_TEXTURE);
        	this.drawTexturedModalRect(x, y, textureatlassprite, 16, 16);
        	GlStateManager.popMatrix();
    }
    
    /**
     * Executes the click event specified by the given chat component
     */
    public boolean handleComponentClick(ITextComponent component)
    {
        ClickEvent clickevent = component.getStyle().getClickEvent();

        if (clickevent == null)
        {
            return false;
        }
        else if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE)
        {
            String s = clickevent.getValue();

            try
            {
                int i = Integer.parseInt(s) - 1;

                if (i >= 0 && i < this.bookTotalPages && i != this.currPage)
                {
                    this.currPage = i;
                    this.updateButtons();
                    return true;
                }
            }
            catch (Throwable var5)
            {
                ;
            }

            return false;
        }
        else
        {
            boolean flag = super.handleComponentClick(component);

            if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND)
            {
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            return flag;
        }
    }
    
    @SideOnly(Side.CLIENT)
    static class NextPageButton extends GuiButton
    {
        private final boolean isForward;

        public NextPageButton(int buttonId, int x, int y, boolean isForwardIn)
        {
            super(buttonId, x, y, 23, 13, "");
            this.isForward = isForwardIn;
        }

        /**
         * Draws this button to the screen.
         */
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if (this.visible)
            {
                boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
                int i = 0;
                int j = 192;

                if (flag)
                {
                    i += 23;
                }

                if (!this.isForward)
                {
                    j += 13;
                }

                this.drawTexturedModalRect(this.x, this.y, i, j, 23, 13);
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public class CategoryButton extends GuiButton
    {
    	private String buttonString;
        /** The x position of this control. */
        public int x;
        /** The y position of this control. */
        public int y;
        public CategoryButton(String buttonString, int x, int y)
        {
            super(0, x, y, 16, 16, "");
            this.buttonString = buttonString;
            this.x = x;
            this.y = y;
        }

        /**
         * Draws this button to the screen.
         */
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if (this.visible)
            {
                boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                if (flag)
                	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                else
                	GlStateManager.color(0.7F, 0.7F, 0.7F, 0.7F);
                
                mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
                int i = 0;
                int j = 0;

                switch(buttonString)
                {
                	case TextLib.CATEGORY_INTRODUCE:
                		i = 168;
                		break;
                	case TextLib.CATEGORY_MATERIAL:
                		i = 184;
                		break;
                	case TextLib.CATEGORY_CREATURE:
                		i = 200;
                		break;
                	case TextLib.CATEGORY_DAEDRA:
                		i = 216;
                		break;
                	case TextLib.CATEGORY_TOOL:
                		i = 168;
                		j = 16;
                		break;
                	case TextLib.CATEGORY_BIOME:
                		i = 184;
                		j = 16;
                		break;
                	case TextLib.CATEGORY_ITEM:
                		i = 200;
                		j = 16;
                		break;
                	case TextLib.CATEGORY_HOLYEQUIPMENT:
                		i = 216;
                		j = 16;
                		break;
                }

                this.drawTexturedModalRect(this.x, this.y, i, j, 16, 16);
                FontRenderer fontrenderer = mc.fontRenderer;
                int s = ModMagicks.BlackVelvetHellMagickColor;

                this.drawCenteredString(fontrenderer, I18n.format(buttonString), this.x + this.width / 2, this.y + this.height + 2, s);
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public class GuiItemButton extends GuiButton
    {
    	private RenderItem itemRender;
    	private String buttonString;
        /** The x position of this control. */
        public int x;
        /** The y position of this control. */
        public int y;
        public GuiItemButton(String buttonString, int x, int y)
        {
            super(0, x, y, 100, 16, buttonString);
            this.buttonString = buttonString;
            this.itemRender = mc.getRenderItem();
            this.x = x;
            this.y = y;
        }

        /**
         * Draws this button to the screen.
         */
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if (this.visible)
            {
                boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                
                FontRenderer fontrenderer = mc.fontRenderer;
                ItemStack stack = new ItemStack(Item.getByNameOrId(buttonString));
                this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
                int j = ModMagicks.BlackVelvetHellMagickColor;
                fontrenderer.drawString(stack.getDisplayName(), this.x + 20, this.y + (this.height - 10) / 2, j);
            }
        }
    }
}