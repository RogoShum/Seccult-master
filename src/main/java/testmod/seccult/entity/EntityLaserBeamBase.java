package testmod.seccult.entity;

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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.FX.SuperLaserBeamFX;
import testmod.seccult.util.MathHelper.MathHelper;

public class EntityLaserBeamBase extends Entity{

	private static final DataParameter<Float> Length = EntityDataManager.<Float>createKey(EntityLaserBeamBase.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> Width = EntityDataManager.<Float>createKey(EntityLaserBeamBase.class, DataSerializers.FLOAT);
	private static final DataParameter<NBTTagCompound> TAG = EntityDataManager.<NBTTagCompound>createKey(EntityLaserBeamBase.class, DataSerializers.COMPOUND_TAG);
	
	private int Num;
	private Entity owner;
	private EntityLaserBeamBase follower;
	private EntityLaserBeamBase upper;
	private boolean isTail;
	private int Damage;
	private EnumHand hand = EnumHand.MAIN_HAND;
	private double[] position = new double[3];
	private LaserLengthChecker lengthchecker = new LaserLengthChecker();
	private SuperLaserBeamFX laserbeam;
	
	public EntityLaserBeamBase(World worldIn) {
		super(worldIn);
		this.setSize(1F, 1F);
	}

    protected void entityInit()
    {	
        this.dataManager.register(Length, Float.valueOf(0));
        this.dataManager.register(Width, Float.valueOf(0));
        this.dataManager.register(TAG, new NBTTagCompound());
    }
    
    public void setTail() {
    	this.isTail = true;
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
    	/*if(owner == null) {
    		this.setDead();
    	}*/
    	
    	if(follower == null)
    	{
    		double[] p = new double[3];
    		p[0] = this.posX;
    		p[1] = this.posY;
    		p[2] = this.posZ;
    		lengthchecker.setPosition(p);
    	}

    	if(isTail)
    	{
    		position = lengthchecker.getPosition();
    		float dx = (float) (position[0] - this.posX);
    	    float dy = (float) (position[1] - this.posY);
    		float dz = (float) (position[2] - this.posZ);
    		float distance = (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
    		
    	 	if(laserbeam == null || (laserbeam != null && !laserbeam.isAlive()))
    	 	{
    	 		createLaser(this.world, this.posX, this.posY, this.posZ, this, distance);
    	 	}
    	 	else if(laserbeam != null)
    	 	{
    	 		laserbeam.setHeight(distance);
    	 	}
    		/*NBTTagCompound nbt = new NBTTagCompound();
    		nbt.setDouble("X", this.posX - position[0]);
    		nbt.setDouble("Y", this.posY - position[1]);
    		nbt.setDouble("Z", this.posZ - position[2]);
    		setTag(nbt);*/
    	}
    	
    	if(!this.world.isRemote)
    	{
    		if(owner != null) {
        		Vec3d handVec = owner.getLookVec().rotateYaw(hand == EnumHand.MAIN_HAND ? -0.4F : 0.4F);
        		Vec3d lok = owner.getLookVec();
       		 	Vec3d position = owner.getPositionVector().addVector(handVec.x, handVec.y - 0.8F + owner.getEyeHeight(), handVec.z);
       		 	Vec3d QAQ = position.addVector(lok.x * this.Num, lok.y * this.Num, lok.z * this.Num);
       		 	this.setLocationAndAngles(QAQ.x, QAQ.y, QAQ.z, owner.rotationYaw, owner.rotationPitch);
       		 	if(!owner.isEntityAlive())
       		 		this.setDead();
        	}
    		else
    		{
    			setDead();
    		}
    		
    		if(this.world.isAirBlock(getPosition()) && follower == null && this.Num <= 120)
    		{
    			int a = this.Num + 1;
    			spawnLaser(a);
    		}
    		else if(follower != null && !follower.isEntityAlive())
    		{
    			follower = null;
    		}
    		
    		if(!this.isTail) {
    		if(upper == null) 
    		{
    			this.setDead();
    			return;
    		}
    		else if(!upper.isEntityAlive())
    		{
    			this.setDead();
    			return;
    		}
    		}
    	}
    	if(!this.world.isAirBlock(getPosition()) && this.follower != null) {
    		this.follower.setDead();
    	}
    	if(this.getMyWidth() == 1)
    		return;
    	this.collideWithNearbyEntities();
    }

	public void createLaser(World worldIn, double posXIn, double posYIn, double posZIn, Entity player, float height)
	{
			SuperLaserBeamFX laser =  new SuperLaserBeamFX(worldIn, posXIn, posYIn, posZIn, player, height);
			laser.setRBGColorF(1, 0.5F, 0);
			laser.setAlphaF(1F);
			laser.setMaxAge(2);
			Minecraft.getMinecraft().effectRenderer.addEffect(laser);
			this.laserbeam = (SuperLaserBeamFX) laser;
	}
    
    protected void collideWithNearbyEntities()
    {
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());

        if (!list.isEmpty())
        {
            for (int l = 0; l < list.size(); ++l)
            {
                Entity entity = list.get(l);
                this.applyEntityCollision(entity);
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
    
    private void spawnLaser(int n) {
		EntityLaserBeamBase f = new EntityLaserBeamBase(world);
		f.upper = this;
		f.Num = n;
		f.owner = this.owner;
		f.Damage = this.Damage;
		f.lengthchecker = this.lengthchecker;
		this.follower = f;
		Vec3d p = MathHelper.onLookVec(this, 0.9, 0);
		f.setPositionAndRotation(p.x, p.y, p.z, this.rotationYaw, this.rotationPitch);
		if(!this.world.isRemote)
		this.world.spawnEntity(f);
    }
    
    public void setLength(float length)
    {
        this.dataManager.set(Length, Float.valueOf(length));
    }
	
    public void setWidth(float width)
    {
        this.dataManager.set(Width, Float.valueOf(width));
    }
    
    public float getMyLength()
    {
        return this.dataManager.get(Length).floatValue();
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

    /**
     * Gets how bright this entity is.
     */
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
