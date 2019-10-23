package testmod.seccult.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.items.armor.MagickArmor;

public class ItemKnowledgeScroll extends ItemMagickable{

	public ItemKnowledgeScroll(String name) {
		super(name);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(playerIn.getHeldItem(handIn).getItem() == this && ItemMagickable.getMagickString(playerIn.getHeldItem(handIn)) != null)
		{
			PlayerData data = PlayerDataHandler.get(playerIn);
			data.addMagickData(ModMagicks.GetMagickIDByString(ItemMagickable.getMagickString(playerIn.getHeldItem(handIn))));
			playerIn.getHeldItem(handIn).shrink(1);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if(ItemMagickable.getMagickString(stack) != null)
			MagickArmor.addStringToTooltip(I18n.format(ModMagicks.getI18nNameByID(ModMagicks.GetMagickIDByString(ItemMagickCore.getMagickString(stack))) + "_name"), tooltip);
	}
}
