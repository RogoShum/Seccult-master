package testmod.seccult.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.FX.FrozenBlockFX;
import testmod.seccult.client.FX.SuperLaserBeamFX;
import testmod.seccult.magick.active.TeleportMagick;
import testmod.seccult.magick.implementation.ImplementationFocused;

public class EntityLaserBeamBase extends Entity{

	private static final DataParameter<Float> Length = EntityDataManager.<Float>createKey(EntityLaserBeamBase.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> Width = EntityDataManager.<Float>createKey(EntityLaserBeamBase.class, DataSerializers.FLOAT);
	private static final DataParameter<NBTTagCompound> TAG = EntityDataManager.<NBTTagCompound>createKey(EntityLaserBeamBase.class, DataSerializers.COMPOUND_TAG);
	
	private Entity owner;
	private int Damage;
	private float distance;
	private EnumHand hand = EnumHand.MAIN_HAND;
	private SuperLaserBeamFX laserbeam;
	
	public EntityLaserBeamBase(World worldIn) {
		super(worldIn);
		this.setSize(0.0F, 0.0F);
	}

    protected void entityInit()
    {	
        this.dataManager.register(Length, Float.valueOf(0));
        this.dataManager.register(Width, Float.valueOf(0));
        this.dataManager.register(TAG, new NBTTagCompound());
    }
    
    public void setOwner(Entity o) {
    	this.owner = o;
    }
    
    public Entity getOwner() {
    	return this.owner;
    }
    
    public void setDamage(int d) {
    	this.Damage = d;
    }
    
    @Override
    public void onUpdate() {
    	super.onUpdate();
    		Vec3d lok = this.getLookVec();
    		BlockPos block = ImplementationFocused.getBlockLookedAt(this, 120);
    		if(block == null)
    		{
       		 	Vec3d position = this.getPositionVector().addVector(lok.x * 120, lok.y * 120, lok.z * 120);
       		 	block = new BlockPos(position);
    		}
    		else
    		block.add(lok.x * -1, lok.y * -1, lok.z * -1);
    		float dx = (float) (block.getX() - this.posX);
    	    float dy = (float) (block.getY() - this.posY);
    		float dz = (float) (block.getZ() - this.posZ);
    		distance = (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
    		
    	 	if(this.world.isRemote && laserbeam == null || (laserbeam != null && !laserbeam.isAlive()))
    	 	{
    	 		createLaser(this.world, this.posX, this.posY, this.posZ, this, distance);
    	 	}
    	 	else if(laserbeam != null)
    	 	{
    	 		laserbeam.setHeight(distance);
    	 	}

    	if(!this.world.isRemote)
    	{
    		if(owner != null) {
        		Vec3d handVec = owner.getLookVec().rotateYaw(hand == EnumHand.MAIN_HAND ? -0.4F : 0.4F);
       		 	Vec3d position = owner.getPositionVector().addVector(handVec.x, handVec.y - 0.8 + owner.getEyeHeight(), handVec.z);
       		 	Vec3d QAQ = position.addVector(lok.x * -1.5, lok.y * -1.5, lok.z * -1.5);
       		 	TeleportMagick.setPlayerTP(this, QAQ.x, QAQ.y, QAQ.z, 0);
       		 	this.setLocationAndAngles(QAQ.x, QAQ.y, QAQ.z, owner.rotationYaw, owner.rotationPitch);
       		 	if(!owner.isEntityAlive())
       		 		this.setDead();
        	}
    		else
    		{
    			setDead();
    		}
    	}

    	this.collideWithNearbyEntities();
    }

	public void createLaser(World worldIn, double posXIn, double posYIn, double posZIn, Entity player, float height)
	{
		SuperLaserBeamFX laser =  new SuperLaserBeamFX(worldIn, posXIn, posYIn, posZIn, player, height);
		laser.setRBGColorF(1, 0.5F, 0);
		laser.setAlphaF(1F);
		laser.setMaxAge(3);
		Minecraft.getMinecraft().effectRenderer.addEffect(laser);
		this.laserbeam = (SuperLaserBeamFX) laser;
	}
    
    protected void collideWithNearbyEntities()
    {
    	Vec3d lok = this.getLookVec();
		Vec3d position = this.getPositionVector().addVector(lok.x * distance / 2, lok.y * distance / 2, lok.z * distance / 2);
    	AxisAlignedBB boundingBox = new AxisAlignedBB(position.x, position.y, position.z, position.x, position.y, position.z).grow(distance / 2, distance / 2, distance / 2);
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, boundingBox);
        List<Vec3d> vecs = new ArrayList<>();
        for(float f = 0; f < distance; f+=1.5)
        {
        	Vec3d vec = this.getPositionVector().addVector(lok.x * f, lok.y * f - 0.4, lok.z * f);
        	//Minecraft.getMinecraft().effectRenderer.addEffect(new LightFX(this.world, vec.x, vec.y, vec.z, 0, 0, 0, 5));
        	vecs.add(vec);
        }
        
        if (!list.isEmpty() && !vecs.isEmpty())
        {
            for (int l = 0; l < list.size(); ++l)
            {
                Entity entity = list.get(l);
                for(Vec3d vec: vecs)
                {
                	if(entity.getDistance(vec.x, vec.y, vec.z) <= 0.8 && entity != owner)
                		this.applyEntityCollision(entity);
                }
                
            }
        }
    }
    
    @Override
    public void applyEntityCollision(Entity entity) {
    	if(entity instanceof EntityLivingBase)
		{
			EntityLivingBase e = (EntityLivingBase) entity;
			if(owner != null && e != owner) {
		    	  if(!attackEntityAsMob(e))
		    	  {
		    		  if(e.getHealth() > this.Damage) {
		    			  e.setHealth(e.getHealth() - this.Damage);
		    		  }
		    		  else{
		    			e.setHealth(0);
		    		  	e.onDeath(DamageSource.causeIndirectMagicDamage(this, owner));
		    		  }
		    	  }
			}
		}
    }
    
    public boolean attackEntityAsMob(Entity par1Entity)
    {
         boolean var4 = false;
         if(owner != null && owner instanceof EntityLivingBase)
             var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase) owner), this.Damage);
         else if(owner != null)
        	 var4 = par1Entity.attackEntityFrom(new EntityDamageSource("seccult:Laser", this), this.Damage);
         return var4;
    }
	
    public void setWidth(float width)
    {
        this.dataManager.set(Width, Float.valueOf(width));
    }
    
    public float getMyWidth()
    {
        return this.dataManager.get(Width).floatValue();
    }

    public void setTag(NBTTagCompound tag)
    {
        this.dataManager.set(TAG, tag);
    }
    
    public NBTTagCompound getMyTag()
    {
        return this.dataManager.get(TAG);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    public float getBrightness()
    {
        return 5.0F;
    }
    
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
	}

}
