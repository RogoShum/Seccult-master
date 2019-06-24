package testmod.seccult.client.gui.button;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.Seccult;
import testmod.seccult.api.ModMagicks;
import testmod.seccult.magick.active.Magick;

public class SpellButton extends GuiButton{

    private static final String TEXTURE_PATH = Seccult.MODID + ":" + "textures/gui/spell_programmer.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);
	public int spellPower;
	public int spellAttribute;
	public int sortID;
	public boolean sortLock;
	
	public String spellPower_name;
	public String spellAttribute_name;
    float r,g,b;
    int colorR,colorG,colorB;
    
	public SpellButton getLinked;
	public SpellButton prveLinked;
	public boolean hasLinked;
	private boolean clicked;
	private boolean displayLayer;
	public boolean isMagickButton;
	public float rotationAngle;
	
	public int[] Sort;
	public int[] Power;
	public int[] Attribute;
	
	public SpellButton(int buttonId, int x, int y, boolean display) {
		super(buttonId, x, y, 16, 16, "");
		this.displayLayer = display;
		defineAttribute();
		if(isMagickButton)
			handleColor(buttonId);
		
	}
	
	public void defineAttribute()
	{
		
		if(this.id >= 5)
			this.isMagickButton = true;
		
		spellPower_name = "spell_power_name";
		switch(id)
		{
			case 6:
				spellAttribute_name = "spell_power_affiliated_name";
				break;
			case 7:
				spellAttribute_name = "spell_time_name";
				break;
			default:
				spellAttribute_name = "spell_none";
				break;
		}
	}
	
	public void handleColor(int id)
	{
		Random rand = new Random();
	    r = 256 - id * rand.nextInt(id * id);
	    b = 256 - id * rand.nextInt(id * id);
	    g = 256 - id * rand.nextInt(id * id);
	    while(r < 0)
	    	r += 256;
	    while(g < 0)
	    	g += 256;
	    while(b < 0)
	    	b += 256;
	    
	    colorR = (int) r;
	    colorG = (int) g;
	    colorB = (int) b;
	    
	    r = r/256;
	    g = g/256;
	    b = b/256;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		rotationAngle++;
		if(rotationAngle > 360)
			rotationAngle = 0;
		checkLinkedSort();
		checkLinked();
		if(spellPower < 0)
			spellPower = 0;
		
		if(spellAttribute < 0)
			spellAttribute = 0;
		
		if(clicked)
		{
			this.x = mouseX - 8;
			this.y = mouseY - 8;
		}
		
		if (this.visible)
		{
	        if(this.getLinked != null)
	        {
	        	int xDis = this.getLinked.x - this.x;
	        	int yDis = this.getLinked.y - this.y;
	        	int[] center = new int[2];
	        	center[0] = this.x + 8;
	        	center[1] = this.y + 8;
	        	double radian = Math.atan2(yDis, xDis);
	        	
	            float angle = (float) (radian*(180/Math.PI));
	        	double distance = Math.sqrt(xDis * xDis + yDis * yDis);
	        	GlStateManager.pushMatrix();
	        	this.SdrawHorizontalLine(0, (int)distance, 1, angle, center);
	        	GlStateManager.popMatrix();
	        }
			drawLayer(mc);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        String MagickName = ModMagicks.GetMagickStringByID(this.id);
        String BUTTON_PATH = Seccult.MODID + ":" + "textures/gui/button/" + MagickName + ".png";
        ResourceLocation BUTTON_TEXTURE = new ResourceLocation(BUTTON_PATH);
        mc.getTextureManager().bindTexture(BUTTON_TEXTURE);
        //int x = mouseX - this.height, y = mouseY - this.width;
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.0625F, 0.0625F, 0.0625F);
        this.drawTexturedModalRect(this.x * 16, this.y * 16, 0, 0, this.width * 16, this.height * 16);
        //this.drawGradientRect(mouseX, mouseY, mouseX+20, mouseY+20, 0xFF000000, 0xFF000000);
        GlStateManager.popMatrix();
		}
		//System.out.println("QAQ");
	}
	
	public void checkLinkedSort() {
		
		if(!isMagickButton && prveLinked == null)
		{
			sortID = 1;
		}
		else if(prveLinked != null)
		{
			sortID = prveLinked.sortID + 1;
		}
		
		if(sortID == 1)
		{
			Sort = new int[1];
			Power = new int[1];
			Attribute = new int[1];
			Sort[0] = id;
			Power[0] = spellPower;
			Attribute[0] = spellAttribute;
			if(getLinked != null)
			{
				getLinked.Sort = Sort;
				getLinked.Power = Power;
				getLinked.Attribute = Attribute;
				getLinked.sortLock = true;
			}
		}
		else
		{
			if(sortLock)
			{
				if(!isMagickButton) 
				{
					int[] Sort0 = updateArray(0);
					int[] Sort1 = updateArray(1);
					int[] Sort2 = updateArray(2);
					
				if(getLinked != null)
				{
					if(getLinked.Sort != Sort0)
					{
						getLinked.Sort = Sort0;
						getLinked.sortLock = true;
					}
					
					if(getLinked.Power != Sort1)
					{
						getLinked.Power = Sort1;
						getLinked.sortLock = true;
					}
					
					if(getLinked.Attribute != Sort2)
					{
						getLinked.Attribute = Sort2;
						getLinked.sortLock = true;
					}
				}
				}
				else
				{
					Sort = updateArray(0);
					Power = updateArray(1);
					Attribute = updateArray(2);
				}
				
				sortLock = false;
			}
		}
	}

	private int[] updateArray(int state)
	{
		int[] OldSort = new int[1];
		int value = 0;
		
		switch(state)
		{
			case 0:
				OldSort = Sort;
				value = id;
				break;
			case 1:
				OldSort = Power;
				value = spellPower;
				break;
			case 2:
				OldSort = Attribute;
				value = spellAttribute;
				break;
		}
		
		int[] NewSort = new int[OldSort.length + 1];
		for(int i = 0; i < OldSort.length; i++)
			NewSort[i] = OldSort[i];
		NewSort[OldSort.length] = value;
		return NewSort;
	}
	
	private void checkLinked() {
		if(this.isMagickButton)
		{
			this.getLinked = null;
			if(prveLinked != null)
				hasLinked = true;
			else
				hasLinked = false;
			return;
		}
		
		if(this.getLinked == null)
		{
			this.hasLinked = false;
			return;
		}
		else
		{
			if(this.getLinked.isMagickButton)
				this.hasLinked = true;
			else
			{
				this.hasLinked = this.getLinked.hasLinked;
			}
		}
	}

	private void drawLayer(Minecraft mc)
	{
		if(this.hasLinked && displayLayer)
		{
			GlStateManager.pushMatrix();
			GlStateManager.color(r,b,g, 1F);
	        mc.getTextureManager().bindTexture(TEXTURE);
			if(!this.isMagickButton)
			{
				this.colorR = this.getLinked.colorR;
				this.colorG = this.getLinked.colorG;
				this.colorB = this.getLinked.colorB;
				
				this.r = this.getLinked.r;
				this.g = this.getLinked.g;
				this.b = this.getLinked.b;
			}
			else
			{
				GlStateManager.pushMatrix();
				GlStateManager.translate(this.x+7.5, this.y+7.5, this.zLevel);
				GlStateManager.rotate(rotationAngle, 0, 0, 1);
				this.drawTexturedModalRect(-9, -9, 32, 192, 18, 18);
				GlStateManager.popMatrix();
			}
			
	        this.drawTexturedModalRect(this.x-1, this.y-1, 32, 192, 18, 18);
	        GlStateManager.popMatrix();
		}
	}
	
	public boolean checkOnPaper(int mouseX, int mouseY, int width, int height)
	{
		int offsetX = (width - 172) / 2;
    	int offsetY = (height - 172) / 2;
		if(this.clicked)
			return true;
		int x = this.x - offsetX - 15, y = this.y - offsetY;
    	if (x >= 0 && y >= 0 && x < 172 + 1 && y < 172)
    	{
    		return true;
    	}
    	else
    		return false;
	}
	
	public SpellButton clicked()
	{
			clicked = true;
			return this;
	}
	
	public SpellButton release()
	{
			clicked = false;
			return null;
	}
	
    protected void SdrawHorizontalLine(int startX, int endX, int y, float angle, int[] center)
    {
        if (endX < startX)
        {
            int i = startX;
            startX = endX;
            endX = i;
        }

        drawGradientRect(startX, y, endX + 1, y + 1, angle, center);
    }
	
    protected void drawGradientRect(int left, int top, int right, int bottom, float angle, int[] center)
    {
        GlStateManager.translate(center[0], center[1], this.zLevel);
        GlStateManager.rotate(angle, 0, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, (double)this.zLevel).color(colorR, colorG, colorB, 1).endVertex();
        bufferbuilder.pos((double)left, (double)top, (double)this.zLevel).color(256 - colorR, 256 - colorG, 256 - colorB, 1F).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, (double)this.zLevel).color(256 - colorR, 256 - colorG, 256 - colorB, 1F).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, (double)this.zLevel).color(colorR, colorG, colorB, 1).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
