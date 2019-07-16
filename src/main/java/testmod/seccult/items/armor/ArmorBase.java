package testmod.seccult.items.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import testmod.seccult.Seccult;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.registerModel;

public class ArmorBase extends ItemArmor implements registerModel
{
	private float MagicRelief;
	private float MagicEnhance;
	private float MagicUpperlimit;
	public ArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabsLoader.tab);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Seccult.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	protected void setMagickAttribute(float MagicRelief, float MagicEnhance, float MagicUpperlimit)
	{
		this.MagicRelief = MagicRelief;
		this.MagicEnhance = MagicEnhance;
		this.MagicUpperlimit = MagicUpperlimit;
	}
	
	public static boolean hasArmorSetItem(EntityPlayer player, int i, Item item) {
		if(player == null || player.inventory == null || player.inventory.armorInventory == null)
			return false;
		
		ItemStack stack = player.inventory.armorInventory.get(3 - i);
		if(stack.isEmpty())
			return false;

		switch(i) {
		case 0: return stack.getItem() == item;
		case 1: return stack.getItem() == item;
		case 2: return stack.getItem() == item;
		case 3: return stack.getItem() == item;
		}

		return false;
	}
	
	public float getMagicRelief()
	{
		return MagicRelief;
	}
	
	public float getMagicEnhance()
	{
		return MagicEnhance;
	}
	
	public float getMagicUpperlimit()
	{
		return MagicUpperlimit;
	}
	
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot,
			ModelBiped _default) {
		// TODO Auto-generated method stub
		return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
	}
}
