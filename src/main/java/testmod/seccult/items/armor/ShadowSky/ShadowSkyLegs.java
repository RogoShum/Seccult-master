package testmod.seccult.items.armor.ShadowSky;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.World;
import testmod.seccult.items.armor.ShadowSkyArmor;
import testmod.seccult.items.armor.MagickArmor.CoreType;

public class ShadowSkyLegs extends  ShadowSkyArmor{

	public ShadowSkyLegs(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(0.2F, 3, 2F);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		addMagickCore(itemStack, CoreType.SpeedCore);
	}
}
