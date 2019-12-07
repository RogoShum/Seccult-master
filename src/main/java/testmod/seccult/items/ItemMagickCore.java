package testmod.seccult.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.textlib.TextLib;
import testmod.seccult.init.ModItems;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.items.armor.MagickArmor;

public class ItemMagickCore extends ItemMagickable{
	private static final ResourceLocation Core_Prefix = new ResourceLocation(Seccult.MODID, "core_type");
	
	public ItemMagickCore(String name) {
		super(name);
		this.addPropertyOverride(Core_Prefix, new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return hasMagick(stack) ? 1 : 0;
			}
		});
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemNoteBook.setNewNote(TextLib.Category.Item, player, TextLib.Item.MagickCore_1);
		
		return super.onItemRightClick(worldIn, player, handIn);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(entityIn instanceof EntityPlayer)
			ItemNoteBook.setNewNote(TextLib.Category.Item, (EntityPlayer)entityIn, TextLib.Item.MagickCore_base);
		
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if(ItemMagickable.getMagickString(stack) != null)
			MagickArmor.addStringToTooltip(I18n.format(ModMagicks.getI18nNameByID(ModMagicks.GetMagickIDByString(ItemMagickCore.getMagickString(stack))) + "_name"), tooltip);
	}
	
	
	public static boolean hasMagick(ItemStack stack)
	{
		boolean b = false;
		if(getMagick(stack) != null)
			b = true;
		return b;
	}

}
