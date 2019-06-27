package testmod.seccult.magick.active;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import testmod.seccult.init.ModDamage;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class FrozenMagick extends Magick{
	protected DamageSource damage;
	
	public FrozenMagick(String nbtName, boolean hasDetailedText) 
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
		if(entity != null && player != null)
		{
			MagickFX();
			damage(player);
			StateManager.setState(entity, StateManager.FROZEN, (int)strengh);
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
}
