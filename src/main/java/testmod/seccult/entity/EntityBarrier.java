package testmod.seccult.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.magick.active.MoveMagick;
import testmod.seccult.magick.magickState.StateManager;

public class EntityBarrier extends Entity implements ISpaceEntity{
	private static final DataParameter<Float> HEIGHT = EntityDataManager.<Float>createKey(EntityBarrier.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> WIDTH = EntityDataManager.<Float>createKey(EntityBarrier.class, DataSerializers.FLOAT);
	private static final DataParameter<BlockPos> BLOCKPOS = EntityDataManager.<BlockPos>createKey(EntityBarrier.class, DataSerializers.BLOCK_POS);
	
	private Set<Entity> entityList = new HashSet<Entity>();
	
	public EntityBarrier(World worldIn) {
		super(worldIn);
		this.setSize(1F, 1F);
		this.setNoGravity(true);
		this.noClip = true;
	}

	public EntityBarrier(World worldIn, Vec3d vec, float height, float width) {
		this(worldIn);
		if(!worldIn.isRemote)
		{
			this.setHeight(height);
			this.setWidth(width);
			this.setPos(new BlockPos(vec));
		}
		this.setPosition(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
		if(this.width != this.getWidth() || this.height != this.getHeight())
			this.setSize(getWidth(), getHeight());
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(this.width != this.getWidth() || this.height != this.getHeight())
			this.setSize(getWidth(), getHeight());
		if(this.getPos() != BlockPos.ORIGIN)
			this.setPositionAndUpdate(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
		collideWithNearbyEntities();

		for(Iterator<Entity> it = this.entityList.iterator(); it.hasNext();)
		{
			Entity entity = it.next();
			
			if(this.getEntityBoundingBox().minX > entity.getEntityBoundingBox().minX && entity.motionX < 0)
				entity.motionX = 0.1;
			if(this.getEntityBoundingBox().maxX < entity.getEntityBoundingBox().maxX && entity.motionX > 0)
				entity.motionX = -0.1;
			if(this.getEntityBoundingBox().minY > entity.getEntityBoundingBox().minY && entity.motionY < 0)
			{
				entity.motionY = 0.0;
				entity.onGround = true;
			}
			if(this.getEntityBoundingBox().maxY < entity.getEntityBoundingBox().maxY && entity.motionY > 0)
				entity.motionY = -0.1;
			if(this.getEntityBoundingBox().minZ > entity.getEntityBoundingBox().minZ && entity.motionZ < 0)
				entity.motionZ = 0.1;
			if(this.getEntityBoundingBox().maxZ < entity.getEntityBoundingBox().maxZ && entity.motionZ > 0)
				entity.motionZ = -0.1;
			
			if(!(this.getEntityBoundingBox().maxX > entity.posX && entity.posX > this.getEntityBoundingBox().minX &&
	    	   this.getEntityBoundingBox().maxZ > entity.posZ && entity.posZ > this.getEntityBoundingBox().minZ &&
	    	   this.getEntityBoundingBox().maxY > entity.posY + entity.height && entity.posY + entity.height > this.getEntityBoundingBox().minY))
			{
				entity.setPositionAndUpdate(this.posX, this.posY, this.posZ);
			}
			
			if(entity.dimension != this.dimension)
			{
				entity.changeDimension(dimension);
				entity.setPositionAndUpdate(this.posX, this.posY, this.posZ);
			}
			
			if(entity.isDead || !this.world.loadedEntityList.contains(entity))
				it.remove();
		}
		
		if(this.ticksExisted > 6000)
			this.setDead();
		
        if (this.world.isRemote && this.ticksExisted % 3 == 0)
        {
            this.world.spawnParticle(EnumParticleTypes.TOTEM, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0, this.rand.nextDouble(), 0);
        }
	}
	
	  @SideOnly(Side.CLIENT)
	    public int getBrightnessForRender()
	    {
	        return 15728880;
	    }

	    /**
	     * Gets how bright this entity is.
	     */
	    public float getBrightness()
	    {
	        return 5.0F;
	    }
	
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox()
    {
        return this.isEntityAlive() ? this.getEntityBoundingBox() : null;
    }
	
    protected void collideWithNearbyEntities()
    {
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
        if (!list.isEmpty())
        {
            for (int l = 0; l < list.size(); ++l)
            {
                Entity entity = list.get(l);
                if(!(entity instanceof ISpaceEntity))
                	this.applyEntityCollision(entity);
            }
        }
    }
	
    public void applyEntityCollision(Entity entityIn)
    {
    	//System.out.println(this.getEntityBoundingBox().maxX > entityIn.posX && entityIn.posX > this.getEntityBoundingBox().minX);
    	if(this.getEntityBoundingBox().maxX > entityIn.posX && entityIn.posX > this.getEntityBoundingBox().minX &&
    	   this.getEntityBoundingBox().maxZ > entityIn.posZ && entityIn.posZ > this.getEntityBoundingBox().minZ &&
    	   this.getEntityBoundingBox().maxY > entityIn.posY + entityIn.height/2 && entityIn.posY + entityIn.height/2 > this.getEntityBoundingBox().minY)
    	{
    		if(!this.entityList.contains(entityIn))
    			entityList.add(entityIn);
    	}
    }

	@Override
	protected void entityInit() 
	{
		this.dataManager.register(HEIGHT, 0F);
		this.dataManager.register(WIDTH, 0F);
		this.dataManager.register(BLOCKPOS, BlockPos.ORIGIN);
	}
	
	protected void setHeight(float id) 
	{
		this.dataManager.set(HEIGHT, id);
	}

	protected float getHeight() 
	{
		return this.dataManager.get(HEIGHT).floatValue();
	}
	
	protected void setWidth(float id) 
	{
		this.dataManager.set(WIDTH, id);
	}
	
	protected float getWidth() 
	{
		return this.dataManager.get(WIDTH).floatValue();
	}
	
	protected void setPos(BlockPos id) 
	{
		this.dataManager.set(BLOCKPOS, id);
	}
	
	protected BlockPos getPos() 
	{
		return this.dataManager.get(BLOCKPOS);
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.setHeight(compound.getFloat("VoidHeight"));
		this.setWidth(compound.getFloat("VoidWidth"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setFloat("VoidHeight", this.getHeight());
		compound.setFloat("VoidWidth", this.getWidth());
	}

}
