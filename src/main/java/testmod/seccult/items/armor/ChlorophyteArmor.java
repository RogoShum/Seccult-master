package testmod.seccult.items.armor;

import java.util.UUID;

import com.google.common.collect.Multimap;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.Seccult;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.armor.Chlorophyte.ModelChlorophyteArmor;
import testmod.seccult.items.armor.Ocean.ModelOceanArmor;

@Mod.EventBusSubscriber(modid = Seccult.MODID)
public class ChlorophyteArmor extends MagickArmor{
	
	public ChlorophyteArmor(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if(world.getLight(player.getPosition()) >= 7 && player.ticksExisted % 50 == 0)
		{
			itemStack.getTagCompound().setBoolean("BrightEnough", true);
		}
		else if(world.getLight(player.getPosition()) < 7)
		{
			if(itemStack.getTagCompound().hasKey("BrightEnough"))
			itemStack.getTagCompound().removeTag("BrightEnough");
		}
		
		if(!hasArmorSetItem(player))
			return;
		
		for(int i = 0; i < player.inventory.armorInventory.size(); i++)
		{
			ItemStack item = player.inventory.armorInventory.get(i);
			if(item.isItemDamaged())
			{
				item.setItemDamage(item.getItemDamage() - 1);
				return;
			}
		}
		
		for(int i = 0; i < player.inventory.mainInventory.size(); i++)
		{
			ItemStack item = player.inventory.mainInventory.get(i);
			if(item.isItemDamaged())
			{
				item.setItemDamage(item.getItemDamage() - 1);
				return;
			}
		}
	}
	
	public static boolean hasArmorSetItem(EntityPlayer player) {
		return hasArmorSetItem(player, 0, ModItems.CHLOROPHYTE_HELMET) && 
				hasArmorSetItem(player, 1, ModItems.CHLOROPHYTE_CHEST) && 
				hasArmorSetItem(player, 2, ModItems.CHLOROPHYTE_LEGGINGS) && 
				hasArmorSetItem(player, 3, ModItems.CHLOROPHYTE_BOOTS);
	}
	
	@SubscribeEvent
	public static void shadowDodge(LivingHurtEvent event)
	{
		if(!(event.getEntityLiving() instanceof EntityPlayer) || !event.getSource().isFireDamage())
			return;
		
		EntityPlayer player = (EntityPlayer)event.getEntityLiving();
		
		if(hasArmorSetItem(player, 0, ModItems.CHLOROPHYTE_HELMET))
			ChlorophyteArmor.damageArmorSetItem(player, 0, 2);
		
		if(hasArmorSetItem(player, 1, ModItems.CHLOROPHYTE_CHEST))
			ChlorophyteArmor.damageArmorSetItem(player, 1, 2);
		
		if(hasArmorSetItem(player, 2, ModItems.CHLOROPHYTE_LEGGINGS))
			ChlorophyteArmor.damageArmorSetItem(player, 2, 2);
		
		if(hasArmorSetItem(player, 3, ModItems.CHLOROPHYTE_BOOTS))
			ChlorophyteArmor.damageArmorSetItem(player, 3, 2);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
		UUID uuid = new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0);
		
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		boolean lightLevel = stack.hasTagCompound() && stack.getTagCompound().hasKey("BrightEnough");
		if (slot == armorType) {
			attrib.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(uuid, "Chlorophyte modifier", lightLevel ? 0.5F : 0,  1));
			attrib.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(uuid, "Chlorophyte modifier", lightLevel ? 0.5F : 0, 1));
			attrib.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(uuid, "Chlorophyte modifier", lightLevel ? 0.5F : 0, 1));
			attrib.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuid, "Chlorophyte modifier", lightLevel ? 0.1F : 0, 1));
		}
		return attrib;
	}
	
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot,
			ModelBiped _default) {
		
		if(itemStack != ItemStack.EMPTY)
		{
			if(itemStack.getItem() instanceof ChlorophyteArmor)
			{
				ModelChlorophyteArmor armor = new ModelChlorophyteArmor(armorSlot);
				return armor;
			}
		}
		
		return null;
	}
}
