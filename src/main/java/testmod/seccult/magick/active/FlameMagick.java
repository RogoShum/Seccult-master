package testmod.seccult.magick.active;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import testmod.seccult.client.FX.LightFX;
import testmod.seccult.magick.ActiveHandler;

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
		
		if(strengh < 25)
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
			living.world.newExplosion(null, living.posX, living.posY, living.posZ, strengh, true, true);
			living.hurtResistantTime = -1;
		}
		}
	}

    public Explosion newExplosion(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking)
    {
        Explosion explosion = new Explosion(entityIn.world, entityIn, x, y, z, strength, isFlaming, isSmoking);
        if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(entityIn.world, explosion)) return explosion;
        explosion.doExplosionA();
        explosion.doExplosionB(true);
        return explosion;
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
		System.out.println("World isRemote " + entity.world.isRemote);
		if(strengh >= 10)
		{
            double d0 = (double)((float)entity.posX + entity.world.rand.nextFloat());
            double d1 = (double)((float)entity.posY + entity.world.rand.nextFloat());
            double d2 = (double)((float)entity.posZ + entity.world.rand.nextFloat());
            double d3 = d0 - entity.posX;
            double d4 = d1 - entity.posY;
            double d5 = d2 - entity.posZ;
            double d6 = (double)MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
            d3 = d3 / d6;
            d4 = d4 / d6;
            d5 = d5 / d6;
            double d7 = 0.5D / (d6 / (double)Math.sqrt(this.strengh) + 0.1D);
            d7 = d7 * (double)(entity.world.rand.nextFloat() * entity.world.rand.nextFloat() + 0.3F);
            d3 = d3 * d7;
            d4 = d4 * d7;
            d5 = d5 * d7;
        	Particle me = new LightFX(entity.world, (d0 + entity.posX) / 2.0D, (d1 + entity.posY) / 2.0D, (d2 + entity.posZ) / 2.0D, d3, d4, d5);
        	me.setRBGColorF(255, 20, 20);
        	Particle smoke = new LightFX(entity.world, d0, d1, d2, d3, d4, d5);
        	smoke.setRBGColorF(255, 20, 20);
        	Minecraft.getMinecraft().effectRenderer.addEffect(me);
        	Minecraft.getMinecraft().effectRenderer.addEffect(smoke);
		}
	}

	@Override
	public int getColor() {
		return ActiveHandler.FlameMagickColor;
	}
}
