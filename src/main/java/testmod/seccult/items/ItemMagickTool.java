package testmod.seccult.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import testmod.seccult.NBTTools;
import testmod.seccult.Seccult;
import testmod.seccult.items.armor.MagickArmor;
import testmod.seccult.items.armor.MagickArmor.I18n_String;

public abstract class ItemMagickTool extends ItemBase{
	public static final String STRENGTH = "Tool_Strength";
	public static final String ATTRIBUTE = "Tool_Attribute";
	public static final String COOLDOWN = "Tool_CoolDown";
	
	protected float strength;
	protected float attribute;
	protected float cooldown;
	protected float stackCoolDwon;
	
	public ItemMagickTool(String name) {
		super(name);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	public void initAttribue(ItemStack stack)
	{
		this.strength = Math.max(1F, getItemStrength(stack));
		this.attribute = Math.max(1F, getItemAttribute(stack));
		this.cooldown = Math.max(1F, getItemCoolDown(stack));
	}
	
	public static ItemStack randomUpgrade(ItemStack stack)
	{
		int r = Seccult.rand.nextInt(3);
		
		switch(r)
		{
			case 0: 
				setItemStrength(stack, getItemStrength(stack) + 1);
				break;
			case 1: 
				setItemAttribute(stack, getItemAttribute(stack) + 1);
				break;
			case 2: 
				setItemCoolDown(stack, getItemCoolDown(stack) - 5);
				break;
		}
		
		return stack;
	}
	
	public static ItemStack randomDowngrade(ItemStack stack)
	{
		int r = Seccult.rand.nextInt(3);
		
		switch(r)
		{
			case 0: 
				setItemStrength(stack, getItemStrength(stack) - 1);
				break;
			case 1: 
				setItemAttribute(stack, getItemAttribute(stack) - 1);
				break;
			case 2: 
				setItemCoolDown(stack, getItemCoolDown(stack) + 5);
				break;
		}
		
		return stack;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 7200;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(this.stackCoolDwon > 0)
			stackCoolDwon--;
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
	public static ItemStack getStack(EntityPlayer playerIn, EnumHand handIn)
	{
		return playerIn.getHeldItem(handIn);
	}
	
	public static void setItemStrength(ItemStack stack, float value)
	{
		NBTTools.getStackTag(stack).setFloat(STRENGTH, Math.max(value, 1));
	}
	
	public static void setItemAttribute(ItemStack stack, float value)
	{
		NBTTools.getStackTag(stack).setFloat(ATTRIBUTE, Math.max(value, 1));
	}
	
	public static void setItemCoolDown(ItemStack stack, float value)
	{
		NBTTools.getStackTag(stack).setFloat(COOLDOWN, Math.max(value, 1));
	}
	
	public static float getItemStrength(ItemStack stack)
	{
		return NBTTools.getStackTag(stack).getFloat(STRENGTH);
	}
	
	public static float getItemAttribute(ItemStack stack)
	{
		return NBTTools.getStackTag(stack).getFloat(ATTRIBUTE);
	}

	public static float getItemCoolDown(ItemStack stack)
	{
		return NBTTools.getStackTag(stack).getFloat(COOLDOWN);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		MagickArmor.addStringToTooltip("&3" + I18n.format(I18n_String.Tool_Strength) + " " + getItemStrength(stack), tooltip);
		MagickArmor.addStringToTooltip("&3" + I18n.format(I18n_String.Tool_Attribute) + " " + getItemAttribute(stack), tooltip);
		MagickArmor.addStringToTooltip("&3" + I18n.format(I18n_String.Tool_CoolDown) + " " + getItemCoolDown(stack), tooltip);
	}
	
	public static class I18n_String
	{
		public static final String Tool_Strength = ("seccult.tool.strength");
		public static final String Tool_Attribute = ("seccult.tool.attribute");
		public static final String Tool_CoolDown = ("seccult.tool.cooldown");
	}
}
