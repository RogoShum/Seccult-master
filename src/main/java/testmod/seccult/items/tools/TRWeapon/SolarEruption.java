package testmod.seccult.items.tools.TRWeapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import testmod.seccult.items.TRprojectile.TRprojectileID;

public class SolarEruption extends ItemWeaponBase{

	public SolarEruption(String name) {
		super(name);
		ItemAttribute(true, 14, 0, 19, 24, "seccult:solar", TRprojectileID.SolarEruption_Handle);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		 if(super.onLeftClickEntity(stack, player, entity))
		 {
			 player.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, 2F, 2F);
			 return true;
		 }
		 return false;
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if(super.onEntitySwing(entityLiving, stack))
		{
			entityLiving.world.playSound((EntityPlayer)null, new BlockPos(entityLiving.posX, entityLiving.posY, entityLiving.posZ), SoundEvents.ENTITY_FIREWORK_LARGE_BLAST, SoundCategory.PLAYERS, 1F, 0F);
			entityLiving.world.playSound((EntityPlayer)null, new BlockPos(entityLiving.posX, entityLiving.posY, entityLiving.posZ), SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 2F, 1F);
			entityLiving.world.playSound((EntityPlayer)null, new BlockPos(entityLiving.posX, entityLiving.posY, entityLiving.posZ), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.PLAYERS, 2F, 2F);
			 return true;
		}
		return false;
	}
}
