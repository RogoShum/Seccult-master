package testmod.seccult.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.Seccult;
import testmod.seccult.init.ModMagicks;

public class SpellButtonAdvance extends SpellButton{
	
	public NBTTagCompound fullMagickList;
	public NBTTagList LoadMagick;
	public NBTTagList LoadSelect;
	public NBTTagList selector;
	public int SpellCount;
	
	public int buttonId;
	
	protected FontRenderer fontRenderer;
	private GuiTextField nameField;
	
	public SpellButtonAdvance(int buttonId, NBTTagCompound Tag, int x, int y, int wid, int hei, boolean display) {
		super(0, x, y, wid, hei, display);
		this.buttonId = buttonId;
		this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
		unZipTag(Tag);
		this.fullMagickList = Tag;
		
		this.nameField = new GuiTextField(0, this.fontRenderer, this.x + 18, this.y + 2, 103, 12);
        this.nameField.setTextColor(-1);
        this.nameField.setDisabledTextColour(-1);
        this.nameField.setEnableBackgroundDrawing(false);
        this.nameField.setMaxStringLength(6);
	}
	
	public void unZipTag(NBTTagCompound SingleMagickList)
	{
		for(int z = 0; z < SingleMagickList.getTagList("Magick", 10).tagCount(); z++)
		{
			LoadMagick = SingleMagickList.getTagList("Magick", 10);
			LoadSelect = SingleMagickList.getTagList("Selector", 10);
			NBTTagCompound MagickNBT = LoadMagick.getCompoundTagAt(z);
			int magickID = MagickNBT.getInteger("Magick");
			this.id = magickID;
		}
		this.SpellCount = SingleMagickList.getTagList("Magick", 10).tagCount();
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible)
		{
			GlStateManager.color(1.0F, 1.0F, 1.0F);
        
			for(int i = 0; i < this.SpellCount; ++i)
			{
				int[][] magick = getMagickAttribute();
				String MagickName = ModMagicks.GetMagickStringByID(magick[i][0]);
				String BUTTON_PATH = Seccult.MODID + ":" + "textures/gui/button/" + MagickName + ".png";
				ResourceLocation BUTTON_TEXTURE = new ResourceLocation(BUTTON_PATH);
				mc.getTextureManager().bindTexture(BUTTON_TEXTURE);
				GlStateManager.pushMatrix();
				GlStateManager.scale(0.0625, 0.0625, 0.0625);
				this.drawTexturedModalRect((this.x + 16*i) * 16, this.y * 16, 0, 0, 16 * 16, this.height * 16);
				GlStateManager.popMatrix();
			}
			
			//this.nameField.setText(this.getSpellName());
			//this.nameField.drawTextBox();
			 //this.fontRenderer.drawStringWithShadow(, this.x + 18, this.y + 2, 0xFFF5EE);
		}
	}
	
	public int[][] getMagickAttribute()
	{
		if(LoadMagick != null && !LoadMagick.hasNoTags()) {
			int[][] at = new int[LoadMagick.tagCount()][4];

			for(int i = 0; i < LoadMagick.tagCount(); ++i)
			{
				NBTTagCompound MagickNBT = LoadMagick.getCompoundTagAt(i);
				int magickId = MagickNBT.getInteger("Magick");
				
				int magickpower = MagickNBT.getInteger("MagickPower");
				int magickattribute = MagickNBT.getInteger("MagickAttribute");
				at[i][0] = magickId;
				at[i][1] = magickpower;
				at[i][2] = magickattribute;
				
				int slot = MagickNBT.getInteger("Slot");
				
				at[i][3] = slot;
			}
			
			return at;
		}
		return null;
	}
	
	public int[][] getSelectorAttribute(int i)
	{
		if(LoadSelect != null && !LoadSelect.hasNoTags()) {
			
			NBTTagList selector = new NBTTagList();
			for(int x = 0; x < LoadSelect.tagCount(); x++)
			{
				if(LoadSelect.getCompoundTagAt(x).getInteger("Slot") == i) {
					NBTTagCompound SelectNBT = LoadSelect.getCompoundTagAt(x);
					selector.appendTag(SelectNBT);
				}
			}
			
			int[][] at = new int[selector.tagCount()][3];

			for(int x = 0; x < selector.tagCount(); x++)
			{
				NBTTagCompound SelectNBT = selector.getCompoundTagAt(x);

				int impleAttribute =  SelectNBT.getInteger("SelectorAttribute");

				int implePower = SelectNBT.getInteger("SelectorPower");
				int impleID = SelectNBT.getInteger("Selector");
				at[x][0] = impleID;
				at[x][1] = implePower;
				at[x][2] = impleAttribute;
			}

			return at;
		}
		return null;
	}
	
	public int getSelectorCount(int i)
	{
		if(LoadSelect != null && !LoadSelect.hasNoTags()) {
			
			NBTTagList selector = new NBTTagList();
			for(int x = 0; x < LoadSelect.tagCount(); x++)
			{
				if(LoadSelect.getCompoundTagAt(x).getInteger("Slot") == i) {
					NBTTagCompound SelectNBT = LoadSelect.getCompoundTagAt(x);
					selector.appendTag(SelectNBT);
				}
			}

			return selector.tagCount();
		}
		return 0;
	}
	
	public String getSpellName()
	{
		if(this.fullMagickList.hasKey("Magick_Name"))
		{
			return this.fullMagickList.getString("Magick_Name");
		}
		return "seccult:none_name";
	}
	
	@Override
	public void checkLinkedSort() {}
}
