package testmod.seccult.items.Accessories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import testmod.seccult.events.PlayerDataUpdateEvent;
import testmod.seccult.magick.MagickCompiler;

public class AMagicCaster extends ItemAccessories{
	private NBTTagCompound MAGICK;
	public AMagicCaster(String name) {
		super(name);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if(!hasAccessories(player, itemStack)) return;
		if(MAGICK != null)
			TickMagick(player);
	}
	
	private void TickMagick(EntityPlayer player)
	{
		MagickCompiler compiler = new MagickCompiler();
		compiler.pushMagickData(MAGICK, player);
		PlayerDataUpdateEvent.compiler.add(compiler);
	}
	
	public void pushMagick(NBTTagCompound magick)
	{
		this.MAGICK = magick;
	}
	
	public void clearMagick()
	{
		this.MAGICK = null;
	}
}
