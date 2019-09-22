package testmod.seccult.items.armor;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
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
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.entity.livings.EntitySpirit;
import testmod.seccult.init.ModItems;
import testmod.seccult.magick.magickState.StateManager;

@Mod.EventBusSubscriber(modid = Seccult.MODID)
public class MagickArmor extends ArmorBase{
	
	public static final String NONE_CORE = I18n.format("seccult.none.core");
	
	public static final String Magick_Enhance = I18n.format("seccult.armor.enhance");
	public static final String Magick_Relife = I18n.format("seccult.armor.relief");
	public static final String Magick_Limit = I18n.format("seccult.armor.limit");
	
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
		
		if(coreType.equals(CoreType.LifeCore) || player.ticksExisted % 30 == 0)
			player.heal(0.5F);
			
	}
	
	public static boolean addMagickCore(ItemStack stack, String coreType)
	{
		if(getCore(stack).equals(NONE_CORE))
		{
			stack.getTagCompound().setString("MagickCore", coreType);
			return true;
		}
		else
			return false;
	}
	
	public static boolean hasFlyingCore(EntityPlayer player)
	{
		String coreType0 = getCore(player.inventory.armorItemInSlot(0));
		String coreType1 = getCore(player.inventory.armorItemInSlot(1));
		String coreType2 = getCore(player.inventory.armorItemInSlot(2));
		String coreType3 = getCore(player.inventory.armorItemInSlot(3));
		return coreType0.equals(CoreType.FlyingCore) || coreType1.equals(CoreType.FlyingCore) || coreType2.equals(CoreType.FlyingCore) || coreType3.equals(CoreType.FlyingCore);
	}
	
	public static boolean hasJumpCore(EntityPlayer player)
	{
		String coreType0 = getCore(player.inventory.armorItemInSlot(0));
		String coreType1 = getCore(player.inventory.armorItemInSlot(1));
		String coreType2 = getCore(player.inventory.armorItemInSlot(2));
		String coreType3 = getCore(player.inventory.armorItemInSlot(3));
		return coreType0.equals(CoreType.JumpCore) || coreType1.equals(CoreType.JumpCore) || coreType2.equals(CoreType.JumpCore) || coreType3.equals(CoreType.JumpCore);
	}
	
	@SubscribeEvent
	public static void jumpCore(LivingJumpEvent event)
	{
		System.out.println("QAQ");
		if(!(event.getEntityLiving() instanceof EntityPlayer))
			return;
		System.out.println("QAQ");
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		player.motionY += 0.2F;
		StateManager.setPlayerMove(player, player.motionX, player.motionY += 0.2F, player.motionZ, 1);
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		this.addStringToTooltip("&b" + getCore(stack), tooltip);
		if(GuiScreen.isShiftKeyDown())
		{
			this.addStringToTooltip("&5" + Magick_Enhance + " " + this.getMagicEnhance(), tooltip);
			this.addStringToTooltip("&5" + Magick_Relife + " " + this.getMagicRelief(), tooltip);
			this.addStringToTooltip("&5" + Magick_Limit + " " + this.getMagicUpperlimit(), tooltip);
		}
	}
	
	public void addStringToTooltip(String s, List<String> tooltip) {
		tooltip.add(s.replaceAll("&", "\u00a7"));
	}
	
	public static String getCore(ItemStack stack)
	{
		boolean hasCore = stack.hasTagCompound() && stack.getTagCompound().hasKey("MagickCore");
		String coreType = NONE_CORE;
		if(hasCore)
			coreType = stack.getTagCompound().getString("MagickCore");
		return I18n.format(coreType);
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
				attrib.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(uuid, "Magick modifier", 0.5F,  1));
				attrib.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(uuid, "Magick modifier", 0.5F,  1));
				attrib.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(uuid, "Magick modifier", 0.5F,  1));
			}
			if(coreType.equals(CoreType.SpeedCore))
			{
				attrib.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(uuid, "Magick modifier", 0.5F,  1));
				attrib.put(SharedMonsterAttributes.FLYING_SPEED.getName(), new AttributeModifier(uuid, "Magick modifier", 0.5F,  1));
				attrib.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(uuid, "Magick modifier", 0.5F,  1));
			}
			if(coreType.equals(CoreType.LifeCore))
			attrib.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuid, "Magick modifier", 1F, 1));
		}
		return attrib;
	}
	
	public static class CoreType
	{
		public static final String FlyingCore = I18n.format("seccult.flying.core");
		public static final String PeaceCore = I18n.format("seccult.peace.core");
		public static final String SpeedCore = I18n.format("seccult.speed.core");
		public static final String JumpCore = I18n.format("seccult.jump.core");
		public static final String DefenceCore = I18n.format("seccult.defence.core");
		public static final String LifeCore = I18n.format("seccult.life.core");
	}
}
