package testmod.seccult.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class EntityStandBase extends EntityLiving{
	protected EntityLiving Owner;
	protected float StandEnergy;
	
	protected double MyMaxSpeed;
	protected double MyMaxStrength;
	protected double MyMaxRange;
	protected double MyMaxDurability;
	
	protected double MySpeed;
	protected double MyStrength;
	protected double MyRange;
	protected double MyDurability;

	public EntityStandBase(World worldIn) {
		super(worldIn);
	}

	public void setOwner(EntityLiving a) {
		this.Owner = a;
	}
	
	protected void EnergyCalculate() {
		EntityLiving owner = this.Owner;
		this.StandEnergy = owner.getHealth() / owner.getMaxHealth();
		
		if(StandEnergy < 50 && StandEnergy > 25) {
			MySpeed = MyMaxSpeed * 0.75;
			MyStrength = MyMaxStrength * 0.75;
			MyDurability = MyMaxDurability * 0.65;
			MyRange = MyMaxRange * 0.65;
		}else if(StandEnergy < 25) {
			MySpeed = MyMaxSpeed * 0.35;
			MyStrength = MyMaxStrength * 0.35;
			MyDurability = MyMaxDurability * 0.25;
			MyRange = MyMaxRange * 0.25;
		}
		
		if(StandEnergy <= 0)
			this.isDead = true;
	}
}
