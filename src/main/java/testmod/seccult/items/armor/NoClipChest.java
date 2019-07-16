package testmod.seccult.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
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
			{
				player.noClip = true;
			}
			else
				player.noClip = false;
			player.height = 0;
			player.width = 0;
			player.eyeHeight = 1.65F;
		}
		else
			player.noClip = true;
	}
}
