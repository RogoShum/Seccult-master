package testmod.seccult.items.armor.Chlorophyte;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import testmod.seccult.items.armor.ChlorophyteArmor;
import testmod.seccult.items.armor.MagickArmor.CoreType;

public class ChlorophyteBoot extends ChlorophyteArmor{

	public ChlorophyteBoot(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(0.2F, 1, 1F);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		// TODO Auto-generated method stub
		super.onArmorTick(world, player, itemStack);
		addMagickCore(itemStack, CoreType.JumpCore);
	}
}
