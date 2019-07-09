package testmod.seccult.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import testmod.seccult.init.ModItems;

public class OceanArmor extends FunctionArmor{

	public OceanArmor(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if(!hasArmorSetItem(player)) return;
		float speed = player.capabilities.getWalkSpeed();
		if(player.isInWater())
		{
			player.setAir(300);
			if(!player.isSneaking())
			{
			player.motionX = reduceMotion((float)(player.motionX * 1.05), speed);
			player.motionZ = reduceMotion((float)(player.motionZ * 1.05), speed);
			
			if(!player.onGround)
				player.motionY = reduceMotion((float)(player.motionY * 1), (float)0.9);
			}
		}
		else
		{
			player.motionX *= 0.9;
			player.motionZ *= 0.9;
			if(!player.isSneaking())
				player.motionY = reduceMotion((float)(player.motionY * 1.1), (float)0.3);
			else
				player.motionY = reduceMotion((float)(player.motionY * 0.9), (float)0.3);
		}
	}
	
	public boolean hasArmorSetItem(EntityPlayer player) {
		return hasArmorSetItem(player, 0, ModItems.OCEAN_HELMET) && 
				hasArmorSetItem(player, 1, ModItems.OCEAN_CHEST) && 
				hasArmorSetItem(player, 2, ModItems.OCEAN_LEGGINGS) && 
				hasArmorSetItem(player, 3, ModItems.OCEAN_BOOTS);
	}
	
	public float reduceMotion(float value, float limit)
	{
		float reduce = value;
		if(reduce > limit)
			reduce = limit;
		if(reduce < -limit)
			reduce = -limit;
		return reduce;
	}
}
