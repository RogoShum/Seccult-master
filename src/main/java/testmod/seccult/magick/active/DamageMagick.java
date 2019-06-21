package testmod.seccult.magick.active;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import testmod.seccult.init.ModDamage;
import testmod.seccult.magick.ActiveHandler;

public class DamageMagick extends Magick{
	protected DamageSource damage;
	
	public DamageMagick(String nbtName, boolean hasDetailedText) 
	{
		super(nbtName, hasDetailedText);
	}
	
	@Override
	public void doMagick() {
		super.doMagick();
	}
	
	public void damage(Entity pl)
	{
		damage = ModDamage.causeNormalEntityDamage(pl);
	}
	
	public void doMagickToEntity(Entity e) 
	{
		if(e != null)
		{
			Vec3d QAQ = e.getLookVec();
			QAQ.scale(0.2);
			e.motionX = QAQ.x * strengh;
			e.motionY = QAQ.y * strengh;
			e.motionZ = QAQ.z * strengh;
		}
	}

	@Override
	void toEntity() {
		if(entity instanceof EntityLivingBase && e != null)
		{
			MagickFX();
			damage(e);
			EntityLivingBase living = (EntityLivingBase) entity;
			living.attackEntityFrom(damage, strengh);
			living.hurtResistantTime = -1;
		}
		else if(e != null)
		{
			doMagickToEntity(entity);
		}
	}

	@Override
	void toBlock() {
		e.world.destroyBlock(block, true);
	}

	@Override
	void MagickFX() 
	{
		int particles = 32;
		for (int i = 0; i < particles; i++) {
                entity.world.spawnParticle(EnumParticleTypes.SPELL_MOB, entity.posX + (this.rand.nextDouble() - 0.5D) * (double)entity.width, entity.posY + this.rand.nextDouble() * (double)entity.height - 0.25D, entity.posZ + (this.rand.nextDouble() - 0.5D) * (double)entity.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
		}
	}

	@Override
	public int getColor() {
		return ActiveHandler.DamageMagickColor;
	}
}
