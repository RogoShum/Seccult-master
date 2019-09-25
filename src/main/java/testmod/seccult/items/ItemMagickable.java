package testmod.seccult.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.active.Magick;

public class ItemMagickable extends ItemBase{

	public ItemMagickable(String name) {
		super(name);
		this.maxStackSize = 1;
	}
	
	public static boolean storeMagick(ItemStack stack, Magick magick)
	{
		return ItemMagickable.storeMagickString(stack, magick.getNbtName());
	}

	public static boolean storeMagickString(ItemStack stack, String s)
	{
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		stack.getTagCompound().setString("MagickStored", s);
		return true;
	}
	
	public static String getMagickString(ItemStack stack)
	{
		String s = null;
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("MagickStored"))
			s = stack.getTagCompound().getString("MagickStored");
		
		return s;
	}
	
	public static Magick getMagick(ItemStack stack)
	{
		Magick magick = null;
		if(ItemMagickable.getMagickString(stack) != null)
		{
			String s = ItemMagickable.getMagickString(stack);
			magick = ModMagicks.getMagickFromName(s);
		}
		
		return magick;
	}
	
	public static int getMagickColorInt(ItemStack stack)
	{
		Magick magick = ItemMagickable.getMagick(stack);
		if(magick != null)
		{
			return magick.getColor();
		}
		
		return 0;
	}
	
	public static float[] getMagickColorRGB(ItemStack stack)
	{
		Magick magick = ItemMagickable.getMagick(stack);
		if(magick != null)
		{
			return magick.getRGB();
		}
		
		float[] f = {0, 0, 0};
		
		return f;
	}
}
