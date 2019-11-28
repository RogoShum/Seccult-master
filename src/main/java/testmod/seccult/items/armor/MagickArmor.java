package testmod.seccult.items.armor;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.entity.livings.EntitySpirit;
import testmod.seccult.init.ModItems;
import testmod.seccult.magick.magickState.StateManager;

public class MagickArmor extends ArmorBase{

	public MagickArmor(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}
	
	public static boolean hasArmorSetItem(EntityPlayer player) {
		return hasArmorSetItem(player, 0, ModItems.SPA_HELMET) && 
				hasArmorSetItem(player, 1, ModItems.SPA_CHEST) && 
				hasArmorSetItem(player, 2, ModItems.SPA_LEGGINGS) && 
				hasArmorSetItem(player, 3, ModItems.SPA_BOOTS);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		
		String coreType = getCore(itemStack);
		
		if(hasFlyingCore(player))
		{
			player.capabilities.allowFlying = true;
		}
		else if(!player.isSpectator() && !player.capabilities.isCreativeMode) 
		{
			player.capabilities.allowFlying = false;
			player.capabilities.isFlying = false;
		}
		
		if(coreType.equals(CoreType.PeaceCore))
		{
			EntitySpirit spirit = null;
		
			List<Entity> spirits = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(32));
			for(int i = 0; i < spirits.size(); ++i)
			{
				if(spirits.get(i) instanceof EntitySpirit)
				{
					spirit = (EntitySpirit) spirits.get(i);
					break;
				}
			}
			List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(16));
			for(int i = 0; i < entities.size(); ++i)
			{
				Entity entity = entities.get(i);
				
				if(entity instanceof EntityLivingBase)
				{
					EntityLivingBase living = (EntityLivingBase) entity;
					if(living.getAttackingEntity() == player)
					{
						living.setRevengeTarget(null);
					}
					else
						living.setRevengeTarget(spirit);
				}
				
				if(entity instanceof EntityLiving)
				{
					EntityLiving living = (EntityLiving) entity;
					
					if(living.getAttackingEntity() == player)
					{
						living.setAttackTarget(null);
					}
					else
						living.setAttackTarget(spirit);
				}
			}
		}
		
		if(coreType.equals(CoreType.LifeCore) || player.ticksExisted % 20 == 0)
			player.heal(0.75F);
	}
	
	public static boolean addMagickCore(ItemStack stack, String coreType)
	{
		if(getCore(stack).equals(I18n_String.NONE_CORE))
		{
			stack.getTagCompound().setString("MagickCore", coreType);
			return true;
		}
		else
			return false;
	}
	
	public static boolean hasFlyingCore(EntityPlayer player)
	{
		return hasCore(player, CoreType.FlyingCore);
	}
	
	public static boolean hasJumpCore(EntityPlayer player)
	{
		return hasCore(player, CoreType.JumpCore);
	}
	
	public static boolean hasAttackCore(EntityPlayer player)
	{
		return hasCore(player, CoreType.AttackCore);
	}
	
	public static boolean hasCore(EntityPlayer player, String type)
	{
		String coreType0 = getCore(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD));
		String coreType1 = getCore(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST));
		String coreType2 = getCore(player.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
		String coreType3 = getCore(player.getItemStackFromSlot(EntityEquipmentSlot.FEET));
		return coreType0.equals(type) || coreType1.equals(type) || coreType2.equals(type) || coreType3.equals(type);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		addStringToTooltip("&b" + I18n.format(getCore(stack)), tooltip);
		if(GuiScreen.isShiftKeyDown())
		{
			addStringToTooltip("&5" + I18n.format(I18n_String.Magick_Enhance) + " " + this.getMagicEnhance(), tooltip);
			addStringToTooltip("&5" + I18n.format(I18n_String.Magick_Relife) + " " + this.getMagicRelief(), tooltip);
			addStringToTooltip("&5" + I18n.format(I18n_String.Magick_Limit) + " " + this.getMagicUpperlimit(), tooltip);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void addStringToTooltip(String s, List<String> tooltip) {
		tooltip.add(s.replaceAll("&", "\u00a7"));
	}

	public static String getCore(ItemStack stack)
	{
		boolean hasCore = stack.hasTagCompound() && stack.getTagCompound().hasKey("MagickCore");
		String coreType = I18n_String.NONE_CORE;
		if(hasCore)
			coreType = stack.getTagCompound().getString("MagickCore");
		return coreType;
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
		UUID uuid = new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0);
		
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		String coreType = getCore(stack);

		if (slot == armorType) {
			if(coreType.equals(CoreType.DefenceCore))
			{
				attrib.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(uuid, "Magick modifier", 1F,  1));
				attrib.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(uuid, "Magick modifier", 1F,  1));
				attrib.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(uuid, "Magick modifier", 1F,  1));
			}
			if(coreType.equals(CoreType.SpeedCore))
			{
				attrib.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(uuid, "Magick modifier", 0.5F,  1));
				attrib.put(SharedMonsterAttributes.FLYING_SPEED.getName(), new AttributeModifier(uuid, "Magick modifier", 0.5F,  1));
				attrib.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(uuid, "Magick modifier", 0.5F,  1));
			}
			if(coreType.equals(CoreType.LifeCore))
				attrib.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuid, "Magick modifier", 1F, 1));
			
			if(coreType.equals(CoreType.AttackCore))
				attrib.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(uuid, "Magick modifier", 3F, 1));
		}
		return attrib;
	}

	public static class CoreType
	{
		public static final String FlyingCore = ("seccult.flying.core");
		public static final String PeaceCore = ("seccult.peace.core");
		public static final String SpeedCore = ("seccult.speed.core");
		public static final String JumpCore = ("seccult.jump.core");
		public static final String DefenceCore = ("seccult.defence.core");
		public static final String LifeCore = ("seccult.life.core");
		public static final String AttackCore = ("seccult.attack.core");
	}

	public static class I18n_String
	{
		public static final String NONE_CORE = ("seccult.none.core");
		
		public static final String Magick_Enhance = ("seccult.armor.enhance");
		public static final String Magick_Relife = ("seccult.armor.relief");
		public static final String Magick_Limit = ("seccult.armor.limit");
	}
}
