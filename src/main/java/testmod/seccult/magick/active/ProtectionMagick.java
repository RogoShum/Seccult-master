package testmod.seccult.magick.active;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import testmod.seccult.entity.EntityProtectionShieldFX;
import testmod.seccult.init.ModMagicks;

public class ProtectionMagick extends Magick{
	protected DamageSource damage;
	
	public ProtectionMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
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
			EntityProtectionShieldFX fx = new EntityProtectionShieldFX(entity.world);
			double lookX = entity.getLookVec().x;
			double lookZ = entity.getLookVec().z;
			fx.setPositionAndRotation(entity.posX + lookX * this.strengh / 8, entity.posY + entity.height / 2, entity.posZ + lookZ * this.strengh / 8, entity.rotationYaw, entity.rotationPitch);
			fx.setOwner((EntityLivingBase)entity, (int)this.strengh);
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
		return ModMagicks.ProtectMagickColor;
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return false;
	}
}
