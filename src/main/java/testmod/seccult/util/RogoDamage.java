package testmod.seccult.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public interface RogoDamage {

	float getAttackDamage(ItemStack itemstack, EntityLivingBase target, EntityLivingBase attacker);

	public static final DamageSource VOID = (new DamageSource("Void")).setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute();
	public static final DamageSource MagickDamage = (new DamageSource("MagickDamage")).setDamageBypassesArmor().setDamageIsAbsolute();
	public static final DamageSource RangeDamage = (new DamageSource("RangeDamage")).setDamageBypassesArmor().setDamageIsAbsolute();
	public static final DamageSource SummonDamage = (new DamageSource("SummonDamage")).setDamageBypassesArmor().setDamageIsAbsolute();
	public static final DamageSource MeleeDamage = (new DamageSource("MeleeDamage")).setDamageBypassesArmor().setDamageIsAbsolute();
}
