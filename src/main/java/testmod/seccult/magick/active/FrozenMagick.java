package testmod.seccult.magick.active;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import testmod.seccult.entity.EntityFrozenFX;
import testmod.seccult.init.ModDamage;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class FrozenMagick extends Magick{
	protected DamageSource damage;
	
	public FrozenMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
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
			StateManager.setState(entity, StateManager.FROZEN, (int)(strengh), (int)attribute);
			EntityFrozenFX fx = new EntityFrozenFX(entity.world);
			fx.setPosition(entity.posX, entity.posY, entity.posZ);
			fx.setOwner(entity);
			if(!entity.world.isRemote)
				entity.world.spawnEntity(fx);
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
		return ModMagicks.FrozenMagickColor;
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return true;
	}
}
