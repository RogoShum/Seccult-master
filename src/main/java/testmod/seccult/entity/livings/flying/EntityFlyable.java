package testmod.seccult.entity.livings.flying;

import java.util.Calendar;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.EntityBase;
import testmod.seccult.init.ModItems;

public abstract class EntityFlyable extends EntityBase implements IAnimals
{
    private static final DataParameter<Byte> HANGING = EntityDataManager.<Byte>createKey(EntityFlyable.class, DataSerializers.BYTE);
    /** Coordinates of where the bat spawned. */
    protected BlockPos spawnPosition;
    public int dropLine = 180;
    protected boolean hang;
    
    protected Block hangBlock;
    
    public EntityFlyable(World worldIn)
    {
        super(worldIn);
        this.setIsBatHanging(true);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(HANGING, Byte.valueOf((byte)0));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
    }

    public boolean getIsBatHanging()
    {
        return (((Byte)this.dataManager.get(HANGING)).byteValue() & 1) != 0;
    }

    public void setIsBatHanging(boolean isHanging)
    {
        byte b0 = ((Byte)this.dataManager.get(HANGING)).byteValue();

        if (isHanging)
        {
            this.dataManager.set(HANGING, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.dataManager.set(HANGING, Byte.valueOf((byte)(b0 & -2)));
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        
        if (this.getIsBatHanging())
        {
        	if(this.hang)
        	{
        		this.posY = (double)MathHelper.floor(this.posY) + 1.0D - (double)this.height;
        		this.motionX = 0.0D;
        		this.motionY = 0.0D;
        		this.motionZ = 0.0D;
        	}
        }
        else
        {
            this.motionY *= 0.6000000238418579D;
        }
    }
    
    protected void updateAITasks()
    {
        super.updateAITasks();
        
        if (this.getIsBatHanging())
        {
        	hangingMode();
        }
        else
        {
        	movingMode();
        }
    }

    public abstract void hangingMode();
    
    public abstract void movingMode();
    
    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public void fall(float distance, float damageMultiplier)
    {
    }

    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {
    }

    /**
     * Return whether this entity should NOT trigger a pressure plate or a tripwire.
     */
    public boolean doesEntityNotTriggerPressurePlate()
    {
        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	if(!this.world.isRemote && source == DamageSource.STARVE && this.posY > dropLine && this.getHealth() < 1)
    		this.dropItem(ModItems.SPA, 1);
    	return super.attackEntityFrom(source, amount);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.dataManager.set(HANGING, Byte.valueOf(compound.getByte("BatFlags")));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setByte("BatFlags", ((Byte)this.dataManager.get(HANGING)).byteValue());
    }

    public float getEyeHeight()
    {
        return this.height / 2.0F;
    }
}
