package testmod.seccult.items.tools.TRWeapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import testmod.seccult.Seccult;
import testmod.seccult.items.TRprojectile.TRprojectileID;

public class Meowmere extends ItemWeaponBase{

	public Meowmere(String name) {
		super(name);
		ItemAttribute(true, 200, 0, 16, 12, "seccult:trblade", TRprojectileID.Meowmere);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if(super.onEntitySwing(entityLiving, stack))
		{
			entityLiving.world.playSound((EntityPlayer)null, new BlockPos(entityLiving.posX, entityLiving.posY, entityLiving.posZ), SoundEvents.ENTITY_CAT_AMBIENT, SoundCategory.PLAYERS, 2F, 0.7F + Seccult.rand.nextFloat());
			 return true;
		}
		return false;
	}
}
