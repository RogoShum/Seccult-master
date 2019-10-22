package testmod.seccult.items.Accessories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import testmod.seccult.events.ModEventHandler;
import testmod.seccult.events.PlayerDataUpdateEvent;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.ImplementationHandler;
import testmod.seccult.magick.MagickCompiler;
import testmod.seccult.magick.active.Magick;

public class AMagicCaster extends ItemAccessories{

	public AMagicCaster(String name) {
		super(name);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		
		if(this.getMagickData(itemStack) == null)
		{
			int[] Imple_1 = {ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI), 0, 0};
			int[] Imple_2 = {ModMagicks.GetMagickIDByString(ImplementationHandler.FocuseI), 30, 0};
			
			this.pushMagick(itemStack, Magick.getMagickTag(ModMagicks.GetMagickIDByString(ModMagicks.LifeAbsorptionMagick), 1, 4, Imple_1, Imple_2, true, false));
		}
		
		if(!hasAccessories(player, itemStack)) return;
		if(getMagickData(itemStack) != null)
			TickMagick(getMagickData(itemStack), player);
	}
	
	private void TickMagick(NBTTagCompound itemStack, EntityPlayer player)
	{
		MagickCompiler compiler = new MagickCompiler();
		compiler.pushMagickData(itemStack, player);
		ModEventHandler.playerData.getCompiler().add(compiler);
	}
	
	public NBTTagCompound getMagickData(ItemStack stack)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("Magick_Data"))
		{
			return stack.getTagCompound().getCompoundTag("Magick_Data");
		}

		return null;
	}
	
	public void pushMagick(ItemStack stack, NBTTagCompound magick)
	{
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		stack.getTagCompound().setTag("Magick_Data", magick);
	}
	
	public void clearMagick(ItemStack stack)
	{
		if(!stack.hasTagCompound())
			return;
		
		if(stack.getTagCompound().hasKey("Magick_Data"))
			stack.getTagCompound().removeTag("Magick_Data");
	}
}
