package testmod.seccult.items.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.armor.ShadowSky.ModelShadowSkyArmor;

public class ShadowSkyArmor extends MagickArmor {

	public ShadowSkyArmor(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}

	public static boolean hasArmorSetItem(EntityPlayer player) {
		return hasArmorSetItem(player, 0, ModItems.SHADOW_SKY_HELMET) && 
				hasArmorSetItem(player, 1, ModItems.SHADOW_SKY_CHEST) && 
				hasArmorSetItem(player, 2, ModItems.SHADOW_SKY_LEGGINGS) && 
				hasArmorSetItem(player, 3, ModItems.SHADOW_SKY_BOOTS);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		double motion = Math.abs(player.motionX) + Math.abs(player.motionY) + Math.abs(player.motionZ);
		if(!world.isRemote && hasArmorSetItem(player) && player.isSneaking())
			player.noClip = true;
		
		if(world.isRemote && hasArmorSetItem(player) && player.isSneaking() && motion > 0.5)
			player.noClip = true;

	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot,
			ModelBiped _default) {
		
		if(itemStack != ItemStack.EMPTY)
		{
			if(itemStack.getItem() instanceof ShadowSkyArmor)
			{
				ModelShadowSkyArmor armor = new ModelShadowSkyArmor(armorSlot);
				return armor;
			}
		}
		
		return null;
	}
}
