package testmod.seccult.magick.active;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import testmod.seccult.Seccult;
import testmod.seccult.entity.EntityBloodBeam;
import testmod.seccult.entity.EntityShieldFX;
import testmod.seccult.init.ModMagicks;

public class LifeAbsorptionMagick extends Magick{
	protected DamageSource damage;
	
	public LifeAbsorptionMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}
	
	@Override
	public void doMagick() {
		super.doMagick();
	}

	@Override
	void toEntity() {
		if(entity != null && entity instanceof EntityLivingBase && player != null && player instanceof EntityLivingBase)
		{
			MagickFX();
			EntityLivingBase living = (EntityLivingBase) entity;
			
			for(int i = 0; i < this.attribute; ++i)
			{
				living.setHealth(living.getHealth() - this.strengh);
				EntityBloodBeam fx = new EntityBloodBeam(entity.world, (EntityLivingBase)player, this.strengh);
				fx.setPosition(entity.posX + Seccult.rand.nextFloat(), entity.posY + Seccult.rand.nextFloat(), entity.posZ + Seccult.rand.nextFloat());
				if(!entity.world.isRemote)
					entity.world.spawnEntity(fx);
			}
		}
	}

	@Override
	void toBlock() {
	}

	@Override
	void MagickFX() 
	{
	}

	@Override
	public int getColor() {
		return ModMagicks.LifeAbsorptionMagickColor;
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return true;
	}
}
