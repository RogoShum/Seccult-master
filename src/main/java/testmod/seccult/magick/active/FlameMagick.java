package testmod.seccult.magick.active;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;

public class FlameMagick extends Magick{

	public FlameMagick(String name, boolean hasDetailedText) {
		super(name, hasDetailedText);
		// TODO Auto-generated constructor stub
	}

	@Override
	void toEntity() {
			setFire(strengh);
			MagickFX();
		if(entity instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase) entity;
		
		if(strengh < 15)
		{
			living.setFire((int) strengh);
			living.attackEntityFrom(DamageSource.IN_FIRE, strengh / 2);
			living.hurtResistantTime = -1;
		}
		else
		{
			living.setFire((int) strengh);
			living.attackEntityFrom(DamageSource.IN_FIRE, strengh);
			living.hurtResistantTime = -1;
			living.world.newExplosion(null, living.posX, living.posY, living.posZ, strengh / 10, false, false);
			living.hurtResistantTime = -1;
		}
		}
	}
	
	private void setFire(float i)
	{
		entity.setFire((int) strengh);
	}
	
	@Override
	void toBlock() 
	{
		
	}

	@Override
	void MagickFX() {
		double[] pos = new double[3], vec = new double[3];
		pos[0] = entity.posX;
		pos[1] = entity.posY;
		pos[2] = entity.posZ;
		float[] color = {RGB[0], RGB[1], RGB[2]};
		NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, strengh, 102));
	}

	@Override
	public int getColor() {
		return ModMagicks.FlameMagickColor;
	}
}
