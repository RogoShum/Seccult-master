package testmod.seccult.items.Accessories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import testmod.seccult.events.PlayerDataUpdateEvent;
import testmod.seccult.magick.MagickCompiler;

public class ARecoveryCore extends ItemAccessories{
	private NBTTagCompound MAGICK;
	public ARecoveryCore(String name) {
		super(name);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if(!hasAccessories(player, itemStack)) return;
	}
}
