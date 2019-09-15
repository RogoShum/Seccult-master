package testmod.seccult.magick.active;

import testmod.seccult.entity.EntityFrozenFX;
import testmod.seccult.init.ModDamage;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class FrozenMagick extends Magick implements ControllerMagic{
	public FrozenMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}
	
	@Override
	public void doMagick() {
		super.doMagick();
	}

	@Override
	void toEntity() {
		if(entity != null && player != null)
		{
			MagickFX();
			StateManager.setState(entity, StateManager.FROZEN, (int)(strengh), (int)attribute);
			EntityFrozenFX fx = new EntityFrozenFX(entity.world);
			fx.setPosition(entity.posX, entity.posY, entity.posZ);
			fx.setOwner(entity);
			if(!entity.world.isRemote)
				entity.world.spawnEntity(fx);
			entity.attackEntityFrom(ModDamage.causeFrozenDamage(player), attribute);
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
		return ModMagicks.FrozenMagickColor;
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
