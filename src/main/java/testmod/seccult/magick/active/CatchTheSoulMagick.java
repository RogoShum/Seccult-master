package testmod.seccult.magick.active;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import testmod.seccult.entity.EntityBlackVelvetHell;
import testmod.seccult.init.ModMagicks;

public class CatchTheSoulMagick extends Magick{
	protected DamageSource damage;
	
	public CatchTheSoulMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}
	
	@Override
	public void doMagick() {
		super.doMagick();
	}

	@Override
	void toEntity() {
		if(entity != null && entity instanceof EntityLivingBase && player != null)
		{
			MagickFX();
			EntityBlackVelvetHell blackvelethell = new EntityBlackVelvetHell(entity.world);
			blackvelethell.setPosition(entity.posX, entity.posY, entity.posZ);
			blackvelethell.setPrisoner(entity);
			if(!entity.world.isRemote)
				entity.world.spawnEntity(blackvelethell);
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
		return ModMagicks.CatchTheSoulMagickColor;
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return false;
	}
}
