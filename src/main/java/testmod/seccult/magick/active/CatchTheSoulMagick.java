package testmod.seccult.magick.active;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import testmod.seccult.entity.EntityBlackVelvetHell;
import testmod.seccult.entity.SpiritManager;
import testmod.seccult.entity.livings.EntitySpirit;
import testmod.seccult.entity.livings.EntitySpiritContainer;
import testmod.seccult.init.ModMagicks;

public class CatchTheSoulMagick extends Magick implements ControllerMagic{
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
		if(entity != null && entity instanceof EntityLivingBase && !(entity instanceof EntitySpirit) && player != null)
		{
			MagickFX();
			
			if(!(entity instanceof EntitySpiritContainer))
			{
				EntitySpiritContainer container = new EntitySpiritContainer(entity.world);
				container.setPosition(entity.posX, entity.posY, entity.posZ);
				container.setBody((EntityLivingBase)entity);
				if(!entity.world.isRemote)
					entity.world.spawnEntity(container);
			
				SpiritManager.replace((EntityLivingBase) entity);
			}
			else
			{
				EntitySpiritContainer container = (EntitySpiritContainer) entity;
				if(container.getSoul() != null)
				{
					SpiritManager.replace((EntityLivingBase) entity); 
					container.getSoul().isDead = true;
				}
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
		return ModMagicks.CatchTheSoulMagickColor;
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return false;
	}
	
	@Override
	public boolean doMagickNeedStrength() {
		return false;
	}
}
