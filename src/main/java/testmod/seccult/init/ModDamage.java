package testmod.seccult.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class ModDamage {
	public static final DamageSource normalMagic = new DamageSource("seccult-normal-magick").setMagicDamage();
	public static final DamageSource pureMagic = new DamageSource("seccult-pure-magick").setDamageBypassesArmor().setMagicDamage().setDamageIsAbsolute();
	public static final DamageSource darkMagic = new DamageSource("seccult-dark-magick").setDamageBypassesArmor().setMagicDamage().setDamageIsAbsolute().setDamageAllowedInCreativeMode();
	public static final DamageSource BlackVelvetHell = new DamageSource("seccult-blackVelvetHell").setDamageBypassesArmor().setMagicDamage().setDamageIsAbsolute().setDamageAllowedInCreativeMode();
	public static final DamageSource forbiddenMagic = new DamageSource("seccult-forbidden-magick").setDamageBypassesArmor().setMagicDamage().setDamageIsAbsolute().setDamageAllowedInCreativeMode().setExplosion().setFireDamage();
	
	public static final DamageSource MagickOverLoad = new DamageSource("seccult-overload").setDamageBypassesArmor().setMagicDamage().setDifficultyScaled();
	
	public static final DamageSource Electro = DamageSource.LIGHTNING_BOLT.setMagicDamage().setDamageBypassesArmor().setFireDamage();
	
	public static final DamageSource Thunder = new DamageSource("seccult-frozen").setMagicDamage().setDamageBypassesArmor().setFireDamage().setDamageIsAbsolute();
	
	public static final DamageSource Frozen = new DamageSource("seccult-frozen").setMagicDamage().setDamageBypassesArmor();
	
	public static final DamageSource Fire = DamageSource.IN_FIRE.setMagicDamage().setExplosion();

	public static final DamageSource Flame = new DamageSource("seccult-flame").setMagicDamage().setDamageBypassesArmor().setDamageIsAbsolute().setExplosion();

	public static final DamageSource Posion = new DamageSource("seccult-posion").setMagicDamage().setDamageBypassesArmor().setDifficultyScaled();
	
    public static DamageSource causeNormalPlayerDamage(EntityPlayer player)
    {
        return new EntityDamageSource(normalMagic.damageType, player);
    }
    
    public static DamageSource causeNormalEntityDamage(Entity player)
    {
        return new EntityDamageSource(normalMagic.damageType, player);
    }
    
    public static DamageSource causePureEntityDamage(Entity player)
    {
        return new EntityDamageSource(pureMagic.damageType, player);
    }
    
    public static DamageSource causeDarkEntityDamage(Entity player)
    {
        return new EntityDamageSource(darkMagic.damageType, player);
    }
    
    public static DamageSource causeBlackVelvetHellDamage(Entity player)
    {
        return new EntityDamageSource(BlackVelvetHell.damageType, player);
    }
    
    public static DamageSource causeForbiddenEntityDamage(Entity player)
    {
        return new EntityDamageSource(forbiddenMagic.damageType, player);
    }
    
    public static DamageSource causeForbiddenEntityDamage(Entity source, Entity indirectEntityIn)
    {
        return new EntityDamageSourceIndirect(forbiddenMagic.damageType, source, indirectEntityIn);
    }
    
    public static DamageSource causeMagickFireDamage(Entity player)
    {
        return new EntityDamageSource(Fire.damageType, player);
    }
    
    public static DamageSource causeMagickFlameDamage(Entity player)
    {
        return new EntityDamageSource(Flame.damageType, player);
    }
    
    public static DamageSource causeMagickElectroDamage(Entity player)
    {
        return new EntityDamageSource(Electro.damageType, player);
    }
    
    public static DamageSource causeMagickThunderDamage(Entity player)
    {
        return new EntityDamageSource(Thunder.damageType, player);
    }
    
    public static DamageSource causeFrozenDamage(Entity player)
    {
        return new EntityDamageSource(Frozen.damageType, player);
    }
    
    public static DamageSource causePosionEntityDamage(Entity player)
    {
        return new EntityDamageSource(Posion.damageType, player);
    }
}
