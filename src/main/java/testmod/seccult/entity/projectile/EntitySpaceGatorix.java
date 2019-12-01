package testmod.seccult.entity.projectile;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.ISpaceEntity;
import testmod.seccult.entity.livings.landCreature.EntityDreamPop;
import testmod.seccult.entity.livings.landCreature.EntityNightmarePop;
import testmod.seccult.entity.livings.landCreature.EntitySpaceManager;
import testmod.seccult.init.ModDamage;
import testmod.seccult.init.ModSounds;

public class EntitySpaceGatorix extends EntityThrowable implements ISpaceEntity {
	private static final DataParameter<Float> GATORIX_STATE = EntityDataManager.<Float>createKey(EntitySpaceGatorix.class, DataSerializers.FLOAT);
	private float damage = 25;
	private EntityLivingBase victim;
	private EntityLivingBase prevVictim;
	private UUID victimUUID;
	private int ExistedLimit;
	private boolean explosion;
	
	public EntitySpaceGatorix(World worldIn) {
		super(worldIn);
		this.setSize(0.4F, 0.4F);
		this.ExistedLimit = 300;
		this.damage = 25;
	}
	
	public EntitySpaceGatorix(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
		this.setSize(0.4F, 0.4F);
		this.setNoGravity(true);
		this.ExistedLimit = 300;
		this.damage = 25;
	}

	public EntitySpaceGatorix(World worldIn, EntityLivingBase throwerIn, EntityLivingBase victim) {
		this(worldIn, throwerIn);
		this.victim = victim;
		this.setNoGravity(false);
		this.ExistedLimit = 300;
		this.damage = 25;
	}
	
	public void setVictim(EntityLivingBase victim)
	{
		this.victim = victim;
	}
	
	public EntityLivingBase getVictim()
	{
		return this.victim;
	}
	
	public void setPrevVictim(EntityLivingBase victim)
	{
		this.prevVictim = victim;
	}
	
	public EntityLivingBase getPrevVictim()
	{
		return this.prevVictim;
	}
	
	public void setCharged(float size)
	{
		this.setState(this.height += size);
	}
	
	public void setExplosion(boolean explosion)
	{
		this.explosion = explosion;
	}
	
	@Override
	protected void entityInit() 
	{
		super.entityInit();
		this.dataManager.register(GATORIX_STATE, 0F);
	}
	
	protected void setState(float id) 
	{
		this.dataManager.set(GATORIX_STATE, id);
	}
	  
	protected float getState() 
	{
		return this.dataManager.get(GATORIX_STATE).floatValue();
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(this.getState() != 0)
		{
			this.setSize(this.getState(), this.getState());
			this.ExistedLimit = 1000;
		}
		
		if(this.ticksExisted == 1)
		{
			this.world.playSound((EntityPlayer)null, this.getPosition(), ModSounds.gatorix_spawn, SoundCategory.NEUTRAL, 2F, 1F);
		}
		
		if(this.ticksExisted == 15)
			this.world.playSound((EntityPlayer)null, this.getPosition(), ModSounds.gatorix_flying, SoundCategory.NEUTRAL, 0.2F, 0.5F);
		
		if(this.ticksExisted % 25 == 0)
			this.world.playSound((EntityPlayer)null, this.getPosition(), ModSounds.gatorix_flying, SoundCategory.NEUTRAL, 0.2F, 0.5F);
		
		if(this.prevVictim!= null && this.prevVictim.isDead)
			this.prevVictim = null;
		
		if(victim != null)
		{
			Moveto(victim.posX, victim.posY, victim.posZ, 0.02);
			if(this.victim.isDead)
				this.victim = null;
		}
		else
		{
			List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(16));
			
			for(int i = 0; i < list.size(); ++i)
			{
				if(list.get(i) instanceof EntityCreeper)
					this.victim = (EntityLivingBase) list.get(i);
			}
		}
		
		if(this.ticksExisted > ExistedLimit)
			this.setDead();
		if(!this.world.isRemote)
		destroyBlocksInAABB(getEntityBoundingBox());
		collideWithNearbyEntities();
		
		if(this.victim == null && this.victimUUID != null)
		{
			this.victim = this.getEntityByUUID(victimUUID);
		}
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
                else if(!(entity instanceof ISpaceEntity) && ( entity instanceof EntityThrowable || (entity.height+entity.width) / 2 <= this.height))
                {
                	if(!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isCreative())
                		entity.setDead();
                	if(entity == this.victim)
                		this.setDead();
                }
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
	
	protected boolean destroyBlocksInAABB(AxisAlignedBB p_70972_1_)
    {
        int i = MathHelper.floor(p_70972_1_.minX);
        int j = MathHelper.floor(p_70972_1_.minY);
        int k = MathHelper.floor(p_70972_1_.minZ);
        int l = MathHelper.floor(p_70972_1_.maxX);
        int i1 = MathHelper.floor(p_70972_1_.maxY);
        int j1 = MathHelper.floor(p_70972_1_.maxZ);
        boolean flag = false;
        boolean flag1 = false;

        for (int k1 = i; k1 <= l; ++k1)
        {
            for (int l1 = j; l1 <= i1; ++l1)
            {
                for (int i2 = k; i2 <= j1; ++i2)
                {
                    BlockPos blockpos = new BlockPos(k1, l1, i2);
                    IBlockState iblockstate = this.world.getBlockState(blockpos);
                    Block block = iblockstate.getBlock();

                    if (!block.isAir(iblockstate, this.world, blockpos) && iblockstate.getMaterial() != Material.FIRE)
                    {
                        if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this))
                        {
                            flag = true;
                        }
                        else if (block.canEntityDestroy(iblockstate, this.world, blockpos, this))
                        {
                            if (block != Blocks.COMMAND_BLOCK && block != Blocks.REPEATING_COMMAND_BLOCK && block != Blocks.CHAIN_COMMAND_BLOCK && block != Blocks.IRON_BARS && !(block instanceof BlockLiquid))
                            {
                                flag1 = this.world.setBlockToAir(blockpos) || flag1;
                            }
                            else
                            {
                                flag = true;
                            }
                        }
                        else
                        {
                            flag = true;
                        }
                    }
                }
            }
        }

        if (flag1)
        {
            double d0 = p_70972_1_.minX + (p_70972_1_.maxX - p_70972_1_.minX) * (double)this.rand.nextFloat();
            double d1 = p_70972_1_.minY + (p_70972_1_.maxY - p_70972_1_.minY) * (double)this.rand.nextFloat();
            double d2 = p_70972_1_.minZ + (p_70972_1_.maxZ - p_70972_1_.minZ) * (double)this.rand.nextFloat();
            this.world.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }

        return flag;
    }
	
	@Override
	protected void onImpact(RayTraceResult result) 
	{
		if (!this.world.isRemote && result.entityHit != null)
        {
			if(result.entityHit instanceof ISpaceEntity)
				return;
			
			if(this.ExistedLimit < 500 && this.ticksExisted > 200)
				damage -= (ticksExisted - 200) / 10;
			
			if(this.ExistedLimit > 500)
			{
				 if(this.ticksExisted < 900)
					 damage += (0.6F + this.getState()) * damage;
				 else
					 damage -= (ticksExisted - 900) / 5;
			}
			
			result.entityHit.hurtResistantTime = -1;
			if(this.thrower != null)
				result.entityHit.attackEntityFrom(ModDamage.causeForbiddenEntityDamage(this, this.thrower), damage);
			else
				result.entityHit.attackEntityFrom(ModDamage.causeForbiddenEntityDamage(this), damage);
			if(result.entityHit instanceof Entity && (result.entityHit.height+result.entityHit.width) / 2 <= this.height && (result.entityHit instanceof EntityPlayer && !((EntityPlayer)result.entityHit).isCreative()))
				result.entityHit.setDead();
			if(result.entityHit == this.victim)
        		this.setDead();
			
			if(this.explosion && this.ExistedLimit > 500)
				this.newExplosion(this.world, this, this.posX, this.posY, this.posZ, Math.min((this.damage - 20), 10), true);
        }
		
		if (!this.world.isRemote && result.getBlockPos() != null)
        {
			if(world.getBlockState(result.getBlockPos()).getBlock().canEntityDestroy(world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos(), this))
				this.world.destroyBlock(result.getBlockPos(), true);
			if(this.explosion && this.ExistedLimit > 500)
				this.newExplosion(this.world, this, this.posX, this.posY, this.posZ, Math.min((this.damage - 20), 10), true);
			this.setDead();
        }
	}

    protected float getGravityVelocity()
    {
        return -0.015F;
    }
	
	@Override
	public float getEyeHeight() {
		return this.height / 2;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setFloat("GatorixDamage", damage);
        compound.setFloat("GatorixState", this.getState());
        
        if(this.victim != null)
        	compound.setUniqueId("VictimUUID", this.victim.getUniqueID());
        else if(this.victimUUID != null)
        	compound.setUniqueId("VictimUUID", this.victimUUID);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.damage = compound.getFloat("GatorixDamage");
		this.setCharged(compound.getFloat("GatorixState"));
		
		if(compound.hasKey("VictimUUID"))
			this.victimUUID = compound.getUniqueId("VictimUUID");
	}
	
    public Explosion newExplosion(World world, @Nullable Entity entityIn, double x, double y, double z, float strength, boolean isSmoking)
    {
        Explosion explosion = new SpaceExplosion(world, entityIn, x, y, z, strength, isSmoking);
        if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(world, explosion)) return explosion;
        explosion.doExplosionA();
        explosion.doExplosionB(true);
        return explosion;
    }
	
	public class SpaceExplosion extends Explosion
	{
	    /** whether or not this explosion spawns smoke particles */
	    private final boolean damagesTerrain;
	    private final Random random;
	    private final World world;
	    private final double x;
	    private final double y;
	    private final double z;
	    private final Entity exploder;
	    private final float size;
	    /** A list of ChunkPositions of blocks affected by this explosion */
	    private final List<BlockPos> affectedBlockPositions;
	    /** Maps players to the knockback vector applied by the explosion, to send to the client */
	    private final Map<EntityPlayer, Vec3d> playerKnockbackMap;
	    private final Vec3d position;

	    public SpaceExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size,
				boolean damagesTerrain) {
	    	super(worldIn, entityIn, x, y, z, 0, false, false);
	        this.random = new Random();
	        this.affectedBlockPositions = Lists.<BlockPos>newArrayList();
	        this.playerKnockbackMap = Maps.<EntityPlayer, Vec3d>newHashMap();
	        this.world = worldIn;
	        this.exploder = entityIn;
	        this.size = size;
	        this.x = x;
	        this.y = y;
	        this.z = z;
	        this.damagesTerrain = damagesTerrain;
	        this.position = new Vec3d(this.x, this.y, this.z);
		}
	    
	    /**
	     * Does the first part of the explosion (destroy blocks)
	     */
	    public void doExplosionA()
	    {
	        Set<BlockPos> set = Sets.<BlockPos>newHashSet();
	        int i = 16;

	        for (int j = 0; j < 16; ++j)
	        {
	            for (int k = 0; k < 16; ++k)
	            {
	                for (int l = 0; l < 16; ++l)
	                {
	                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15)
	                    {
	                        double d0 = (double)((float)j / 15.0F * 2.0F - 1.0F);
	                        double d1 = (double)((float)k / 15.0F * 2.0F - 1.0F);
	                        double d2 = (double)((float)l / 15.0F * 2.0F - 1.0F);
	                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
	                        d0 = d0 / d3;
	                        d1 = d1 / d3;
	                        d2 = d2 / d3;
	                        float f = this.size * (0.7F + this.world.rand.nextFloat() * 0.6F);
	                        double d4 = this.x;
	                        double d6 = this.y;
	                        double d8 = this.z;

	                        for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F)
	                        {
	                            BlockPos blockpos = new BlockPos(d4, d6, d8);
	                            IBlockState iblockstate = this.world.getBlockState(blockpos);

	                            if (iblockstate.getMaterial() != Material.AIR)
	                            {
	                                float f2 = this.exploder != null ? this.exploder.getExplosionResistance(this, this.world, blockpos, iblockstate) : iblockstate.getBlock().getExplosionResistance(world, blockpos, (Entity)null, this);
	                                f -= (f2 + 0.3F) * 0.3F;
	                            }

	                            if (f > 0.0F && (this.exploder == null || this.exploder.canExplosionDestroyBlock(this, this.world, blockpos, iblockstate, f)))
	                            {
	                                set.add(blockpos);
	                            }

	                            d4 += d0 * 0.30000001192092896D;
	                            d6 += d1 * 0.30000001192092896D;
	                            d8 += d2 * 0.30000001192092896D;
	                        }
	                    }
	                }
	            }
	        }

	        this.affectedBlockPositions.addAll(set);
	        float f3 = this.size * 2.0F;
	        int k1 = MathHelper.floor(this.x - (double)f3 - 1.0D);
	        int l1 = MathHelper.floor(this.x + (double)f3 + 1.0D);
	        int i2 = MathHelper.floor(this.y - (double)f3 - 1.0D);
	        int i1 = MathHelper.floor(this.y + (double)f3 + 1.0D);
	        int j2 = MathHelper.floor(this.z - (double)f3 - 1.0D);
	        int j1 = MathHelper.floor(this.z + (double)f3 + 1.0D);
	        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB((double)k1, (double)i2, (double)j2, (double)l1, (double)i1, (double)j1));
	        net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.world, this, list, f3);
	        Vec3d vec3d = new Vec3d(this.x, this.y, this.z);

	        for (int k2 = 0; k2 < list.size(); ++k2)
	        {
	            Entity entity = list.get(k2);

	            if (!entity.isImmuneToExplosions())
	            {
	                double d12 = entity.getDistance(this.x, this.y, this.z) / (double)f3;

	                if (d12 <= 1.0D)
	                {
	                    double d5 = entity.posX - this.x;
	                    double d7 = entity.posY + (double)entity.getEyeHeight() - this.y;
	                    double d9 = entity.posZ - this.z;
	                    double d13 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);

	                    if (d13 != 0.0D)
	                    {
	                        d5 = d5 / d13;
	                        d7 = d7 / d13;
	                        d9 = d9 / d13;
	                        double d14 = (double)this.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
	                        double d10 = (1.0D - d12) * d14;
	                        entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f3 + 1.0D)));
	                        double d11 = d10;

	                        if (entity instanceof EntityLivingBase)
	                        {
	                            d11 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase)entity, d10);
	                        }

	                        entity.motionX += d5 * d11;
	                        entity.motionY += d7 * d11;
	                        entity.motionZ += d9 * d11;

	                        if (entity instanceof EntityPlayer)
	                        {
	                            EntityPlayer entityplayer = (EntityPlayer)entity;

	                            if (!entityplayer.isSpectator() && (!entityplayer.isCreative() || !entityplayer.capabilities.isFlying))
	                            {
	                                this.playerKnockbackMap.put(entityplayer, new Vec3d(d5 * d10, d7 * d10, d9 * d10));
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    }

	    /**
	     * Does the second part of the explosion (sound, particles, drop spawn)
	     */
	    public void doExplosionB(boolean spawnParticles)
	    {
	        this.world.playSound((EntityPlayer)null, this.x, this.y, this.z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);

	        if (this.size >= 2.0F && this.damagesTerrain)
	        {
	            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
	        }
	        else
	        {
	            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
	        }

	        if (this.damagesTerrain)
	        {
	            for (BlockPos blockpos : this.affectedBlockPositions)
	            {
	                IBlockState iblockstate = this.world.getBlockState(blockpos);
	                Block block = iblockstate.getBlock();

	                if (spawnParticles)
	                {
	                    double d0 = (double)((float)blockpos.getX() + this.world.rand.nextFloat());
	                    double d1 = (double)((float)blockpos.getY() + this.world.rand.nextFloat());
	                    double d2 = (double)((float)blockpos.getZ() + this.world.rand.nextFloat());
	                    double d3 = d0 - this.x;
	                    double d4 = d1 - this.y;
	                    double d5 = d2 - this.z;
	                    double d6 = (double)MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
	                    d3 = d3 / d6;
	                    d4 = d4 / d6;
	                    d5 = d5 / d6;
	                    double d7 = 0.5D / (d6 / (double)this.size + 0.1D);
	                    d7 = d7 * (double)(this.world.rand.nextFloat() * this.world.rand.nextFloat() + 0.3F);
	                    d3 = d3 * d7;
	                    d4 = d4 * d7;
	                    d5 = d5 * d7;
	                    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + this.x) / 2.0D, (d1 + this.y) / 2.0D, (d2 + this.z) / 2.0D, d3, d4, d5);
	                    this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5);
	                }

	                if (iblockstate.getMaterial() != Material.AIR)
	                {
	                    block.onBlockExploded(this.world, blockpos, this);
	                }
	            }
	        }
	    }

	    public Map<EntityPlayer, Vec3d> getPlayerKnockbackMap()
	    {
	        return this.playerKnockbackMap;
	    }

	    /**
	     * Returns either the entity that placed the explosive block, the entity that caused the explosion or null.
	     */
	    @Nullable
	    public EntityLivingBase getExplosivePlacedBy()
	    {
	        if (this.exploder == null)
	        {
	            return null;
	        }
	        else if (this.exploder instanceof EntityTNTPrimed)
	        {
	            return ((EntityTNTPrimed)this.exploder).getTntPlacedBy();
	        }
	        else
	        {
	            return this.exploder instanceof EntityLivingBase ? (EntityLivingBase)this.exploder : null;
	        }
	    }

	    public void clearAffectedBlockPositions()
	    {
	        this.affectedBlockPositions.clear();
	    }

	    public List<BlockPos> getAffectedBlockPositions()
	    {
	        return this.affectedBlockPositions;
	    }

	    public Vec3d getPosition(){ return this.position; }
	}
}
