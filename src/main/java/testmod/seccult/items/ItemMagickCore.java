package testmod.seccult.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
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
