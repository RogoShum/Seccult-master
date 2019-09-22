package testmod.seccult.items.armor;

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.armor.MagickArmor.CoreType;
import testmod.seccult.items.armor.Ocean.ModelOceanArmor;
import testmod.seccult.items.armor.ShadowSky.ModelShadowSkyArmor;
import testmod.seccult.magick.implementation.ImplementationFocused;
import testmod.seccult.magick.magickState.StateManager;

public class OceanArmor extends MagickArmor{
	public static int right_height = 39;
	public OceanArmor(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}
	
	public static boolean hasArmorSetItem(EntityPlayer player) {
		return hasArmorSetItem(player, 0, ModItems.OCEAN_HELMET) && 
				hasArmorSetItem(player, 1, ModItems.OCEAN_CHEST) && 
				hasArmorSetItem(player, 2, ModItems.OCEAN_LEGGINGS) && 
				hasArmorSetItem(player, 3, ModItems.OCEAN_BOOTS);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		addMagickCore(itemStack, CoreType.LifeCore);
		if(hasArmorSetItem(player))
		{
			List<Entity> en = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(32));
			
			for(int i = 0; i < en.size(); ++i)
			{
				Entity entity = en.get(i);
				if(player.getDistance(entity) <= 2 || ImplementationFocused.getEntityLookedAt(entity, 32) == player)
				{
					StateManager.setState(entity, StateManager.LOST_MIND, 3, 1);
				}
			}
		}
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
	
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot,
			ModelBiped _default) {
		
		if(itemStack != ItemStack.EMPTY)
		{
			if(itemStack.getItem() instanceof OceanArmor)
			{
				ModelOceanArmor armor = new ModelOceanArmor(armorSlot, false);
				return armor;
			}
		}
		
		return null;
	}
}
