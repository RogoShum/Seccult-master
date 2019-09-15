package testmod.seccult.magick.active;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import testmod.seccult.init.ModDamage;
import testmod.seccult.init.ModMagicks;

public class PosionMagick extends Magick implements ControllerMagic{
	public PosionMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
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
			if(entity instanceof EntityLivingBase)
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, (int)strengh * 10, (int)attribute));

			entity.attackEntityFrom(ModDamage.causeFrozenDamage(player), strengh);
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
		return ModMagicks.PosionMagickColor;
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
