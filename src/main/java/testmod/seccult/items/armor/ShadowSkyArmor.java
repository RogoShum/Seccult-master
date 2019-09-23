package testmod.seccult.items.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import testmod.seccult.Seccult;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.armor.MagickArmor.CoreType;
import testmod.seccult.items.armor.ShadowSky.ModelShadowSkyArmor;

@Mod.EventBusSubscriber(modid = Seccult.MODID)
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
		
	}
	
	@Override
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
