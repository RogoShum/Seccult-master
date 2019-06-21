package testmod.seccult.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class EntitySCPBase extends EntityLiving{

	protected boolean AttackedByGreatDamage;
	protected int DeadTimes;
	protected int DeadTimeCanBeSuffered;
	
	public EntitySCPBase(World worldIn) {
		super(worldIn);
	}
	
    @Override
    public boolean isEntityAlive() {
    	return !this.isDead;
    }

	public void setSufferTimes(int a) {
		DeadTimeCanBeSuffered = a;
	}
	
	@Override
	public void setDead() {
		DeadTimes++;
		if(this.world.isRemote)
			this.isDead = true;
		else {
			if(DeadTimes >= DeadTimeCanBeSuffered || AttackedByGreatDamage)
				this.isDead = true;
		}
	}
	
	protected void Moveto(double x, double y, double z, float speed) {
       this.motionX += (Math.signum(x - this.posX) - this.motionX) * speed;
       this.motionY += (Math.signum(y - this.posY) - this.motionY) * speed;
       this.motionZ += (Math.signum(z - this.posZ) - this.motionZ) * speed;
       
       this.posX += this.motionX;
       this.posY += this.motionY;
       this.posZ += this.motionZ;
	}
}
