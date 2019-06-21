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

	public SpellButton getLinked;
	public SpellButton prveLinked;
	public boolean hasLinked;
	private boolean clicked;
	private boolean displayLayer;
	public boolean isMagickButton;
	
	public int[] Sort;
	public int[] Power;
	public int[] Attribute;
	
	public SpellButton(int buttonId, int x, int y, boolean display) {
		super(buttonId, x, y, 16, 16, "");
		this.displayLayer = display;
		defineAttribute();
		if(isMagickButton)
		{
		Random rand = new Random();
	    r = 256 - buttonId * rand.nextInt(buttonId * buttonId);
	    b = 256 - buttonId * rand.nextInt(buttonId * buttonId);
	    g = 256 - buttonId * rand.nextInt(buttonId * buttonId);
	    while(r < 0)
	    	r += 256;
	    while(g < 0)
	    	g += 256;
	    while(b < 0)
	    	b += 256;
	    r = r/256;
	    g = g/256;
	    b = b/256;
		}
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
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
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
	        	int xDis = this.x - this.getLinked.x;
	        	int yDis = this.y - this.getLinked.y;
	        	int[] center = new int[2];
	        	center[0] = this.x + 8;
	        	center[1] = this.y + 8;
	        	if(xDis < 0)
	        		xDis = -xDis;
	        	if(yDis < 0)
	        		yDis = -yDis;

	            int x=Math.abs(xDis);
	            int y=Math.abs(yDis);
	            double z=Math.sqrt(x*x+y*y);
	            int angle = Math.round((float)(Math.asin(y/z)/Math.PI*180));
	            //System.out.println(angle);
	        	//System.out.println(angle);
	        	double distance = Math.sqrt(xDis * xDis + yDis * yDis);
	        	GlStateManager.pushMatrix();
	        	//this.drawHorizontalLine(20, 30, 1, 0xFFFF1493);
	        	this.SdrawHorizontalLine(0, (int)distance, 1, 0xFFFF1493, 0xFF00FFFF, angle, center);
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
			if(!this.isMagickButton)
			{
				this.r = this.getLinked.r;
				this.g = this.getLinked.g;
				this.b = this.getLinked.b;
			}

				GlStateManager.color(r,b,g);
	        mc.getTextureManager().bindTexture(TEXTURE);
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
	
    protected void SdrawHorizontalLine(int startX, int endX, int y, int startColor, int endColor, int angle, int[] center)
    {
        if (endX < startX)
        {
            int i = startX;
            startX = endX;
            endX = i;
        }

        drawGradientRect(startX, y, endX + 1, y + 1, startColor, endColor, angle, center);
    }
	
    protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor, int angle, int[] center)
    {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
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
        bufferbuilder.pos((double)right, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    protected void SdrawGradientRect(int x1, int y1, int x2, int y2, int startColor, int endColor, int angle, int[] center)
    {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        //GlStateManager.translate(center[0], center[1], this.zLevel);
        //GlStateManager.rotate(angle, 1, 0, 0);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x1, (double)y2, 0.0D).endVertex();
        bufferbuilder.pos((double)x2, (double)y2, 0.0D).endVertex();
        bufferbuilder.pos((double)x2, (double)y1, 0.0D).endVertex();
        bufferbuilder.pos((double)x1, (double)y1, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
