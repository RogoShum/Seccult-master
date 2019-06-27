package testmod.seccult.entity.livings;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityStand extends EntityLiving{
	
	private static final DataParameter<Float> StandEnergy = EntityDataManager.<Float>createKey(EntityStand.class, DataSerializers.FLOAT);
	
	protected EntityLiving Owner;
	
	protected double MyMaxSpeed;
	protected double MyMaxStrength;
	protected double MyMaxRange;
	protected double MyMaxDurability;
	
	protected double MySpeed;
	protected double MyStrength;
	protected double MyRange;
	protected double MyDurability;
	
	@Override
	public boolean canBePushed() {
		return false;
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(StandEnergy, Float.valueOf(5));
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}
	
	public EntityStand(World worldIn) {
		super(worldIn);
	}

	public void setOwner(EntityLiving a) {
		this.Owner = a;
	}

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)getStandEnergy());
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
    }
    
	@Override
	public void onUpdate() {
		setHealth(getStandEnergy());
		if(isEntityAlive())
			deathTime = -1;
		super.onUpdate();
		EnergyCalculate();
	}
	
	protected void EnergyCalculate() {
		EntityLiving owner = this.Owner;
		setStandEnergy(owner.getHealth() / owner.getMaxHealth());
		
		if(getStandEnergy() < 50 && getStandEnergy() > 25) {
			MySpeed = MyMaxSpeed * 0.75;
			MyStrength = MyMaxStrength * 0.75;
			MyDurability = MyMaxDurability * 0.65;
			MyRange = MyMaxRange * 0.65;
		}else if(getStandEnergy() < 25) {
			MySpeed = MyMaxSpeed * 0.35;
			MyStrength = MyMaxStrength * 0.35;
			MyDurability = MyMaxDurability * 0.25;
			MyRange = MyMaxRange * 0.25;
		}
		
		if(getStandEnergy() <= -10)
			this.isDead = true;
	}
	
	@Override
	public boolean isEntityAlive() {
		return !isDead && getStandEnergy() > -10.0F;
	}
    
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("MySpeed"))
			this.MyMaxSpeed = nbt.getDouble("MySpeed");
		if(nbt.hasKey("MyStrength"))
			this.MyMaxStrength = nbt.getDouble("MyStrength");
		if(nbt.hasKey("MyDurability"))
			this.MyMaxDurability = nbt.getDouble("MyDurability");
		if(nbt.hasKey("MyRange"))
			this.MyMaxRange = nbt.getDouble("MyRange");
		super.readEntityFromNBT(nbt);
	}
    
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setDouble("MySpeed", MyMaxSpeed);
		nbt.setDouble("MyStrength", MyStrength);
		nbt.setDouble("MyDurability", MyDurability);
		nbt.setDouble("MyRange", MyMaxRange);
		super.writeEntityToNBT(nbt);
	}
	
	protected void setMaxFourState(double a, double b, double c, double d) {
		MyMaxStrength = a;
		MyMaxSpeed = b;
		MyMaxRange = c;
		MyMaxDurability = d;
	}
	
    public float getStandEnergy()
    {
        return this.dataManager.get(StandEnergy).floatValue();
    }
    
    public void setStandEnergy(float size)
    {
        this.dataManager.set(StandEnergy, Float.valueOf(size));
    }
}
