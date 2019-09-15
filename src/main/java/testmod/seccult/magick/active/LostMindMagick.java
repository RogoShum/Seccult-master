package testmod.seccult.magick.active;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import testmod.seccult.init.ModDamage;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class LostMindMagick extends Magick implements ControllerMagic{
	protected DamageSource damage;
	
	public LostMindMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}
	
	@Override
	public void doMagick() {
		super.doMagick();
	}
	
	public void damage(Entity pl)
	{
		damage = ModDamage.causeNormalEntityDamage(pl);
	}

	@Override
	void toEntity() {
		if(entity != null && player != null)
		{
			MagickFX();
			damage(player);
			StateManager.setState(entity, StateManager.LOST_MIND, (int)(strengh), (int)attribute);
			entity.attackEntityFrom(damage, attribute);
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
		return ModMagicks.LoseMindMagickColor;
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
