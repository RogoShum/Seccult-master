package testmod.seccult.entity.projectile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.entity.RenderWitherSkull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.EntityBarrier;
import testmod.seccult.entity.EntityVoid;
import testmod.seccult.entity.ISpaceEntity;
import testmod.seccult.init.ModSounds;

public class EntityAlterSpace extends EntityThrowable implements ISpaceEntity{
	private static final DataParameter<Integer> ALTER_STATE = EntityDataManager.<Integer>createKey(EntityAlterSpace.class, DataSerializers.VARINT);
	private EntityLivingBase victim;
	private UUID victimUUID;
	private int ExistedLimit;
	private AlterType type;
	private float alterHeight;
	private float alterWidth;
	
	public EntityAlterSpace(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 0.6F);
	}

	public EntityAlterSpace(World worldIn, EntityLivingBase throwerIn, EntityLivingBase victim, float height, float width, AlterType type) {
		super(worldIn, throwerIn);
		this.victim = victim;
		this.setNoGravity(true);
		this.alterHeight = height;
		this.alterWidth = width;
		this.type = type;
		if(!this.world.isRemote)
			this.setState(this.type.getVaule());
		this.ExistedLimit = 300;
	}
	
	public void setVictim(EntityLivingBase victim)
	{
		this.victim = victim;
	}
	
	public EntityLivingBase getVictim()
	{
		return this.victim;
	}
	
	public void changeType(AlterType type)
	{
		this.type = type;
	}
	
	@Override
	protected void entityInit() 
	{
		super.entityInit();
		this.dataManager.register(ALTER_STATE, 0);
	}
	
	protected void setState(int id) 
	{
		this.dataManager.set(ALTER_STATE, id);
	}
	  
	public int getState() 
	{
		return this.dataManager.get(ALTER_STATE).intValue();
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!this.world.isRemote && this.type != null)
		{
			this.setState(this.type.getVaule());
		}
		
		if(this.ticksExisted == 1)
		{
			this.world.playSound((EntityPlayer)null, this.getPosition(), ModSounds.gatorix_spawn, SoundCategory.NEUTRAL, 2F, 1F);
		}
		
		if(this.ticksExisted == 15)
			this.world.playSound((EntityPlayer)null, this.getPosition(), ModSounds.gatorix_flying, SoundCategory.NEUTRAL, 0.2F, 0.5F);
		
		if(this.ticksExisted % 25 == 0)
			this.world.playSound((EntityPlayer)null, this.getPosition(), ModSounds.gatorix_flying, SoundCategory.NEUTRAL, 0.2F, 0.5F);

		if(victim != null)
		{
			Moveto(victim.posX, victim.posY, victim.posZ, 0.04);
			if(this.victim.isDead)
				this.victim = null;
		}
		else
		{
			this.releaseSpaceEnergy(this);
		}
		
		if(this.ticksExisted > ExistedLimit)
			this.releaseSpaceEnergy(this);
		
		if(!this.world.isRemote)
		collideWithNearbyEntities();
		
		if(this.victim == null && this.victimUUID != null)
		{
			this.victim = this.getEntityByUUID(victimUUID);
		}
	}
	
	private void releaseSpaceEnergy(BlockPos pos)
	{
		this.releaseSpaceEnergy(pos.getX(), pos.getY() + 1, pos.getZ());
	}
	
	private void releaseSpaceEnergy(Entity entity)
	{
		this.releaseSpaceEnergy(entity.posX, entity.posY, entity.posZ);
	}
	
	private void releaseSpaceEnergy(double x, double y, double z)
	{
		if(this.world.isRemote)
			return ;
		Entity entity = null;

		if(this.type.getVaule() == AlterType.Barrier.getVaule())
		{
			EntityBarrier barrier = new EntityBarrier(getEntityWorld(), new Vec3d(x, y, z), alterHeight, alterWidth);
			entity = barrier;
		}
		else if(this.type.getVaule() == AlterType.Void.getVaule())
		{
			EntityVoid entityVoid = new EntityVoid(getEntityWorld(), new Vec3d(x, y, z), alterHeight, alterWidth);
			entity = entityVoid;
		}
		else if(this.type.getVaule() == AlterType.TerrainTrans.getVaule())
		{
			Iterable<BlockPos> pos = BlockPos.getAllInBox((int)x - this.rand.nextInt(5) - (int)alterWidth, (int)y - this.rand.nextInt(5) - (int)alterHeight, (int)z - this.rand.nextInt(5) - (int)alterWidth
					, (int)x + this.rand.nextInt(5) + (int)alterWidth, (int)y + this.rand.nextInt(5) + (int)alterHeight, (int)z + this.rand.nextInt(5) + (int)alterWidth);
			
			List<Entity> entityList = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow((int)alterHeight + this.rand.nextInt(5)));

			int posX = 64 - this.rand.nextInt(128);
			int posY = 64 - this.rand.nextInt(128);
			int posZ = 64 - this.rand.nextInt(128);

			for(BlockPos blockPos: pos)
			{
				IBlockState state = this.world.getBlockState(blockPos.add(posX, posY, posZ));
				this.world.setBlockState(blockPos.add(posX, posY, posZ), this.world.getBlockState(blockPos));
				this.world.setBlockState(blockPos, state);
			}

			for(Entity transEntity : entityList)
			{
				if(!(transEntity instanceof ISpaceEntity))
					transEntity.setPositionAndUpdate(transEntity.posX + posX, transEntity.posY + posY, transEntity.posZ + posZ);
			}

			this.world.playSound((EntityPlayer)null, this.getPosition(), ModSounds.gatorix_spawn, SoundCategory.NEUTRAL, 3F, 1F);
			this.world.playSound((EntityPlayer)null, this.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 2F, 1F);
			
			this.world.playSound((EntityPlayer)null, this.getPosition().add(posX, posY, posZ), ModSounds.gatorix_spawn, SoundCategory.NEUTRAL, 3F, 1F);
			this.world.playSound((EntityPlayer)null, this.getPosition().add(posX, posY, posZ), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 2F, 1F);

	        if (this.world.isRemote)
	        {
	            for (int i = 0; i < 4; ++i)
	            {
	                this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + posX + (this.rand.nextDouble() - 0.5D) * x + this.rand.nextInt(5) + (int)alterWidth, this.posY + posY + this.rand.nextDouble() * y + this.rand.nextInt(5) + (int)alterHeight - 0.25D, this.posZ + posZ + (this.rand.nextDouble() - 0.5D) * z + this.rand.nextInt(5) + (int)alterWidth, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
	            }
	        }
	        
	        if (this.world.isRemote)
	        {
	            for (int i = 0; i < 4; ++i)
	            {
	                this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * x + this.rand.nextInt(5) + (int)alterWidth, this.posY + this.rand.nextDouble() * y + this.rand.nextInt(5) + (int)alterHeight - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * z + this.rand.nextInt(5) + (int)alterWidth, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
	            }
	        }
		}
		
		if(entity != null)
			this.world.spawnEntity(entity);
	}
	
    @Nullable
    public EntityLivingBase getEntityByUUID(UUID uuid)
    {
        for (int j2 = 0; j2 < this.world.getLoadedEntityList().size(); ++j2)
        {
            Entity entityplayer = this.world.getLoadedEntityList().get(j2);

            if (uuid.equals(entityplayer.getUniqueID()) && entityplayer instanceof EntityLivingBase)
            {
                return (EntityLivingBase) entityplayer;
            }
        }

        return null;
    }
	
    protected void collideWithNearbyEntities()
    {
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(0.5D));

        if (!list.isEmpty())
        {
            for (int l = 0; l < list.size(); ++l)
            {
                Entity entity = list.get(l);
                if(entity instanceof EntityAlterSpace || entity instanceof EntitySpaceGatorix)
                	this.applyEntityCollision(entity);
            }
        }
    }
	
    public void applyEntityCollision(Entity entityIn)
    {
          double d0 = entityIn.posX - this.posX;
          double d1 = entityIn.posZ - this.posZ;
          double d2 = MathHelper.absMax(d0, d1);

          if (d2 >= 0.009999999776482582D)
          {
               d2 = (double)MathHelper.sqrt(d2);
               d0 = d0 / d2;
               d1 = d1 / d2;
               double d3 = 1.0D / d2;

               if (d3 > 1.0D)
               {
                   d3 = 1.0D;
               }

               d0 = d0 * d3;
               d1 = d1 * d3;
               d0 = d0 * 0.05000000074505806D;
               d1 = d1 * 0.05000000074505806D;
               d0 = d0 * (double)(1.0F - this.entityCollisionReduction);
               d1 = d1 * (double)(1.0F - this.entityCollisionReduction);

               this.addVelocity(-d0, 0.0D, -d1);
               entityIn.addVelocity(d0, 0.0D, d1);
          }
    }
    
	protected void Moveto(double x, double y, double z, double speed) {
	       this.motionX += (Math.signum(x - this.posX) - this.motionX) * speed;
	       this.motionY += (Math.signum(y - this.posY) - this.motionY) * speed;
	       this.motionZ += (Math.signum(z - this.posZ) - this.motionZ) * speed;
	       
	       this.posX += this.motionX;
	       this.posY += this.motionY;
	       this.posZ += this.motionZ;

	       this.setPosition(this.posX, this.posY, this.posZ);
	}

	@Override
	protected void onImpact(RayTraceResult result) 
	{
		if (!this.world.isRemote && result.entityHit != null)
        {
			if(!(result.entityHit instanceof ISpaceEntity))
				this.releaseSpaceEnergy(result.entityHit);
			this.setDead();
        }
		
		if (!this.world.isRemote && result.getBlockPos() != null)
        {
				this.releaseSpaceEnergy(result.getBlockPos());
			this.setDead();
        }
	}
	
    protected float getGravityVelocity()
    {
        return 0.0F;
    }
	
	@Override
	public float getEyeHeight() {
		return this.height / 2;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setFloat("AlterHeight", this.alterHeight);
		compound.setFloat("AlterWidth", this.alterWidth);
		if(this.type != null)
			compound.setString("AlterType", this.type.getName());
        if(this.victim != null)
        	compound.setUniqueId("VictimUUID", this.victim.getUniqueID());
        else if(this.victimUUID != null)
        	compound.setUniqueId("VictimUUID", this.victimUUID);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.type = AlterType.getType(compound.getString("AlterType"));
		this.alterHeight = compound.getFloat("AlterHeight");
		this.alterWidth = compound.getFloat("AlterWidth");
		
		if(compound.hasKey("VictimUUID"))
			this.victimUUID = compound.getUniqueId("VictimUUID");
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
	
	public static enum AlterType
	{
		Barrier(0, "Barrier"), Void(1, "Void"), TerrainTrans(2, "TerrainTrans");
		
		private String typeName;
		private int vaule;
		
		AlterType(int vaule, String name)
		{
			typeName = name;
			this.vaule = vaule;
		}
		
		public String getName() {return typeName;}
		
		public int getVaule() {return vaule;}
		
		public static AlterType getType(String name) 
		{
			switch (name)
			{
				case "Void":
					return Void;
				case "TerrainTrans":
					return TerrainTrans;
				default :
					return Barrier;
			}
		}
		
		public static AlterType getType(int vaule) 
		{
			switch (vaule)
			{
				case 1:
					return Void;
				case 2:
					return TerrainTrans;
				default :
					return Barrier;
			}
		}
	}
}
