package testmod.seccult.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.world.gen.TestTeleporter;

public class EntityBorderCrosser extends Entity{
	private static final DataParameter<Integer> TICK = EntityDataManager.<Integer>createKey(EntityBorderCrosser.class, DataSerializers.VARINT);
	private static final DataParameter<Float> Prisoner = EntityDataManager.<Float>createKey(EntityBorderCrosser.class, DataSerializers.FLOAT);

	public int tick;
	private int dimensionId;
	
	public EntityBorderCrosser(World worldIn) {
		super(worldIn);
		this.setSize(5.0F, 5.0F);
		this.setNoGravity(true);
		this.noClip = true;
		this.collided = false;
		this.isImmuneToFire = true;
		this.setEntityInvulnerable(true);
	}

	public EntityBorderCrosser(World worldIn, int dimensionId)
	{
		super(worldIn);
		this.dimensionId = dimensionId;
		this.setSize(5.0F, 5.0F);
		this.setNoGravity(true);
		this.noClip = true;
		this.collided = false;
		this.isImmuneToFire = true;
		this.setEntityInvulnerable(true);
	}
	
	@Override
	public boolean isEntityAlive() {if(this.tick < 120) this.isDead = false; return true;}
	
	@Override
	public void setDead() {if(this.tick >= 120) this.isDead = true;}
	
	@Override
	protected void entityInit() {
        this.dataManager.register(TICK, Integer.valueOf(0));
        this.dataManager.register(Prisoner, Float.valueOf(0));
	}
	
    public float getTICK()
    {
        return this.dataManager.get(TICK).intValue();
    }
    
    private void setTICK(int size)
    {
        this.dataManager.set(TICK, Integer.valueOf(size));
    }
	
    public float getPrisoner()
    {
        return this.dataManager.get(Prisoner).floatValue();
    }
    
    private void setPrisoner(float p)
    {
        this.dataManager.set(Prisoner, Float.valueOf(p));
    }

	@Override
	public void onUpdate() {
		super.onUpdate();
 
		if(this.ticksExisted == 1)
			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), SoundEvents.ENTITY_ENDEREYE_DEATH, SoundCategory.NEUTRAL, 2.0F, 2.0F);
		
		if(this.ticksExisted % 90 == 0)
			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.NEUTRAL, 1.0F, 1.0F);

		List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
		
		for(int i = 0; i < entities.size(); ++i)
		{
			Entity entity = entities.get(i);
			if(entity instanceof EntityBorderCrosser)
				entities.remove(i);
		}
		
		if((entities != null && entities.size() > 0) || tick >= 100) this.tick++;
		
		if(!this.world.isRemote) setTICK(tick);
		
        if (this.world.isRemote)
        {
            for (int i = 0; i < 2; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }
		
		if(tick == 100 && !this.world.isRemote)
		{
			for(int i = 0; i < entities.size(); ++i)
			{
				Entity entity = entities.get(i);
				entity.changeDimension(dimensionId, new TestTeleporter(this.getServer().getWorld(this.dimension)));
			}
			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.NEUTRAL, 2.0F, 1.0F);
			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.NEUTRAL, 2.0F, 2.0F);
		}
		
		if(tick > 120)
			this.setDead();
		
		collideWithNearbyEntities();
	}

	public List<Entity> getCrowedEntity()
	{
		return this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
	}
	
    protected void collideWithNearbyEntities()
    {
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().grow(3), EntitySelectors.getTeamCollisionPredicate(this));

        if (!list.isEmpty())
        {
            for (int l = 0; l < list.size(); ++l)
            {
                Entity entity = list.get(l);
                this.applyEntityCollision(entity);
            }
        }
    }
	
	protected void Moveto(Entity entity, double x, double y, double z, float speed) {
		entity.motionX += (Math.signum(x - entity.posX) - entity.motionX) * speed;
		entity.motionY += (Math.signum(y - entity.posY) - entity.motionY) * speed;
		entity.motionZ += (Math.signum(z - entity.posZ) - entity.motionZ) * speed;
	}
    
	@Override
	public void applyEntityCollision(Entity entityIn) {
        /*if (!this.isRidingSameEntity(entityIn))
        {
            if (!entityIn.noClip && !this.noClip)
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

                    entityIn.addVelocity(-d0, 0.0D, -d1);
                }
            }
        }*/
		
		Moveto(entityIn, this.posX, this.posY + this.height / 3, this.posZ, 0.02F);
		
		if(entityIn.motionX > 0.3 || entityIn.motionX < -0.3)
			entityIn.motionX = 0;
		
		if(entityIn.motionY > 0.3 || entityIn.motionY < -0.3)
			entityIn.motionY = 0;
		
		if(entityIn.motionZ > 0.3 || entityIn.motionZ < -0.3)
			entityIn.motionZ = 0;
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
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) 
	{
		if(compound.hasKey("ChangedDimensionId"))
			this.dimensionId = compound.getInteger("ChangedDimensionId");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) 
	{
		compound.setInteger("ChangedDimensionId", dimensionId);
	}
}
