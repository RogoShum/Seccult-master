package testmod.seccult.magick.active;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.potions.ModPotions;

public class AllSeeMagick extends Magick{
	
	public AllSeeMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
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
			if(entity != null && entity instanceof EntityLivingBase)
			{
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(ModPotions.allsee, (int)this.strengh * 100, (int)this.attribute));
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
		return ModMagicks.AllSeeMagickColor;
	}
}
