package testmod.seccult.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityVoid extends Entity implements ISpaceEntity{
	private static final DataParameter<Float> HEIGHT = EntityDataManager.<Float>createKey(EntityVoid.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> WIDTH = EntityDataManager.<Float>createKey(EntityVoid.class, DataSerializers.FLOAT);
	private static final DataParameter<BlockPos> BLOCKPOS = EntityDataManager.<BlockPos>createKey(EntityVoid.class, DataSerializers.BLOCK_POS);
	
	public EntityVoid(World worldIn) {
		super(worldIn);
		this.setSize(1F, 1F);
		this.setNoGravity(true);
		this.noClip = true;
	}

	public EntityVoid(World worldIn, Vec3d vec, float height, float width) {
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
		
		if(!this.world.isRemote)
			this.destroyBlocksInAABB(getEntityBoundingBox());
		collideWithNearbyEntities();
		
		if(this.ticksExisted > 4000)
			this.setDead();
		
        if (this.world.isRemote)
        {
            for (int i = 0; i < 2; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.TOWN_AURA, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }
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
                double d0 = entityIn.posX - this.posX;
                double d1 = entityIn.posZ - this.posZ;
                double d2 = MathHelper.absMax(d0, d1);


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
                    if(this.ticksExisted % 10 == 0)
                    {
                    	entityIn.hurtResistantTime = -1;
                    	entityIn.attackEntityFrom(DamageSource.OUT_OF_WORLD, 2);
        				if(entityIn instanceof EntityLivingBase)
        				{
        					((EntityLivingBase)entityIn).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(((EntityLivingBase)entityIn).getHealth());
        				}
                    }
                    entityIn.addVelocity(-d0, 0.0D, -d1);

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

        for (int k1 = i; k1 < l; ++k1)
        {
            for (int l1 = j; l1 < i1; ++l1)
            {
                for (int i2 = k; i2 < j1; ++i2)
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
