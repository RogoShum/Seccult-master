package testmod.seccult.items.armor.Magick;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.armor.ArmorBase;
import testmod.seccult.items.armor.MagickArmor;

public class MagickHelmet extends MagickArmor{

	public MagickHelmet(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(0.1F, 0.5F, 0.5F);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if(!player.capabilities.isCreativeMode && !hasArmorSetItem(player))
			player.capabilities.allowFlying = false;
		if(hasArmorSetItem(player))
			player.capabilities.allowFlying = true;
	}
	
}