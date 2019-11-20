package testmod.seccult.items.tools.TRWeapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import testmod.seccult.items.TRprojectile.TRprojectileID;

public class VampireKnives extends ItemWeaponBase{

	public VampireKnives(String name) {
		super(name);
		ItemAttribute(true, 31, 0, 15, 15, "seccult:trblade", TRprojectileID.VampireKnives);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if(super.onEntitySwing(entityLiving, stack))
		{
			entityLiving.world.playSound((EntityPlayer)null, new BlockPos(entityLiving.posX, entityLiving.posY, entityLiving.posZ), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 2F, 1.8F);
			 return true;
		}
		return super.onEntitySwing(entityLiving, stack);
	}
}
