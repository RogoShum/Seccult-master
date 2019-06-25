package testmod.seccult.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import testmod.seccult.init.ModItems;

public class RecoveryArmor extends FunctionArmor{

	public RecoveryArmor() {
		super("recovery_helmet", ModItems.ARMOR_MATERIAL_SPA, 1, EntityEquipmentSlot.HEAD);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if(!hasArmorSetItem(player, 0, ModItems.RECOVERY_HELMET)) return;
		if(!(player.ticksExisted % 20 == 0)) return;
		for(int i = 0; i < player.inventory.mainInventory.size(); i++)
		{
			ItemStack item = player.inventory.mainInventory.get(i);
			if(item.isItemDamaged())
				item.setItemDamage(item.getItemDamage() - 1);
		}
		
		for(int i = 0; i < player.inventory.armorInventory.size(); i++)
		{
			ItemStack item = player.inventory.armorInventory.get(i);
			if(item.isItemDamaged())
				item.setItemDamage(item.getItemDamage() - 1);
		}
	}
}
