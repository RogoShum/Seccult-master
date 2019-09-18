package testmod.seccult.items.armor;

import java.util.UUID;

import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import testmod.seccult.init.ModItems;

public class ChlorophyteArmor extends MagickArmor{

	public ChlorophyteArmor(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		
		if(!hasArmorSetItem(player))
			return;
		
		if(world.getLight(player.getPosition()) >= 7 && player.ticksExisted % 50 == 0)
		{
			itemStack.getTagCompound().setBoolean("BrightEnough", true);
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
		else if(world.getLight(player.getPosition()) < 7)
		{
			if(itemStack.getTagCompound().hasKey("BrightEnough"))
			itemStack.getTagCompound().removeTag("BrightEnough");
		}
	}
	
	public static boolean hasArmorSetItem(EntityPlayer player) {
		return hasArmorSetItem(player, 0, ModItems.CHLOROPHYTE_HELMET) && 
				hasArmorSetItem(player, 1, ModItems.CHLOROPHYTE_CHEST) && 
				hasArmorSetItem(player, 2, ModItems.CHLOROPHYTE_LEGGINGS) && 
				hasArmorSetItem(player, 3, ModItems.CHLOROPHYTE_BOOTS);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
		UUID uuid = new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0);
		
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		boolean lightLevel = stack.hasTagCompound() && stack.getTagCompound().hasKey("BrightEnough");
		if (slot == armorType) {
			attrib.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(uuid, "ShadowWarrior modifier", lightLevel ? 0.5F : 0,  1));
			attrib.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(uuid, "ShadowWarrior modifier", lightLevel ? 0.5F : 0, 1));
		}
		return attrib;
	}
}
