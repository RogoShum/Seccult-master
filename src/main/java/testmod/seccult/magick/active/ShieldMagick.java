package testmod.seccult.magick.active;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import testmod.seccult.entity.EntityShieldFX;
import testmod.seccult.init.ModMagicks;

public class ShieldMagick extends Magick implements DefenceMagic{
	protected DamageSource damage;
	
	public ShieldMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
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
			EntityShieldFX fx = new EntityShieldFX(entity.world);
			fx.setPosition(entity.posX, entity.posY + entity.height / 2, entity.posZ);
			fx.setOwner((EntityLivingBase)entity, (int)this.strengh * 10, (int)this.attribute);
			if(!entity.world.isRemote)
				entity.world.spawnEntity(fx);
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
		return ModMagicks.ShieldMagickColor;
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return true;
	}
	
	@Override
	public boolean doMagickNeedStrength() {
		return true;
	}
}
