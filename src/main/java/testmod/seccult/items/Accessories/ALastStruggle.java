package testmod.seccult.items.Accessories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import testmod.seccult.init.ModItems;

public class ALastStruggle extends ItemAccessories{
	public ALastStruggle(String name) {
		super(name);
		this.setMaxDamage(2000);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if(!hasAccessories(player, itemStack)) return;
		for(int i = 0; i < player.inventory.mainInventory.size(); i++)
		{
			ItemStack item = player.inventory.mainInventory.get(i);
			if(item.isItemStackDamageable() && item.getMaxDamage() - item.getItemDamage() == 0)
				protectItem(item, itemStack);
		}
		
		for(int i = 0; i < player.inventory.armorInventory.size(); i++)
		{
			ItemStack item = player.inventory.armorInventory.get(i);
			if(item.isItemStackDamageable() && item.getMaxDamage() - item.getItemDamage() == 0)
				protectItem(item, itemStack);
		}
	}
	
	private void protectItem(ItemStack item, ItemStack me)
	{
		item.setItemDamage(item.getItemDamage() - 2);
		me.setItemDamage(me.getItemDamage() + 2);
	}
}
