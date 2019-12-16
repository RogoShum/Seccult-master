package testmod.seccult.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import testmod.seccult.items.ItemMagickCore;
import testmod.seccult.items.ItemMagickTool;
import testmod.seccult.magick.active.AvadaKedavraMagick;
import testmod.seccult.magick.active.Magick;

public class CraftingHelper {
	public static ItemStack MagickCore_Avada;
	public static ItemStack ToolGrower;
	
	public static void init()
	{
		MagickCore_Avada = addNewMagickCore(ModMagicks.AvadaKedavraMagick);
		ToolGrower = setMagickTool(ModItems.GROWER, 2, 3, 20);
	}
	
	public static ItemStack addNewMagickCore(String magick)
	{
		ItemStack stack = new ItemStack(ModItems.MagickCore, 1, 0);
		ItemMagickCore.storeMagickString(stack, magick);
		return stack;
	}
	
	public static ItemStack setMagickTool(Item item, float s, float a, float c)
	{
		ItemStack stack = new ItemStack(item);
		
		ItemMagickTool.setItemStrength(stack, s);
		ItemMagickTool.setItemAttribute(stack, a);
		ItemMagickTool.setItemCoolDown(stack, c);
		
		return stack;
	}
}
