package testmod.seccult.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import testmod.seccult.init.ModItems;

public class NoClipChest extends FunctionArmor{

	public NoClipChest() {
		super("noclip_chest", ModItems.ARMOR_MATERIAL_SPA, 1, EntityEquipmentSlot.CHEST);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if(player.capabilities.allowFlying)
		{
			if(player.capabilities.isFlying)
				player.noClip = true;
			else
				player.noClip = false;
		}
		else
			player.noClip = true;
	}
}
