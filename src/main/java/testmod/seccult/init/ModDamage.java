package testmod.seccult.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class ModDamage {
	public static final DamageSource normalMagic = new DamageSource("seccult-normal-magick").setDamageBypassesArmor().setMagicDamage();
	public static final DamageSource pureMagic = new DamageSource("seccult-pure-magick").setDamageBypassesArmor().setMagicDamage().setDamageIsAbsolute();
	public static final DamageSource darkMagic = new DamageSource("seccult-dark-magick").setDamageBypassesArmor().setMagicDamage().setDamageIsAbsolute().setDamageAllowedInCreativeMode();
	public static final DamageSource forbiddenMagic = new DamageSource("seccult-forbidden-magick").setDamageBypassesArmor().setMagicDamage().setDamageIsAbsolute().setDamageAllowedInCreativeMode().setExplosion().setFireDamage();
	
	public static final DamageSource MagickOverLoad = new DamageSource("seccult-overload").setDamageBypassesArmor().setMagicDamage();
	
    public static DamageSource causeNormalPlayerDamage(EntityPlayer player)
    {
        return new EntityDamageSource(normalMagic.damageType, player);
    }
    
    public static DamageSource causeNormalEntityDamage(Entity player)
    {
        return new EntityDamageSource(normalMagic.damageType, player);
    }
}
