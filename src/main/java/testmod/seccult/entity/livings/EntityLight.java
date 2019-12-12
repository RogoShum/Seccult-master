package testmod.seccult.entity.livings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.FX.ParticleFX;
import testmod.seccult.client.FX.SuperLaserBeamFX;
import testmod.seccult.entity.EntityLmr;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.ItemMagickable;
import testmod.seccult.magick.ImplementationHandler;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.TransPoint;
import testmod.seccult.util.ChunkCoordinates;
import testmod.seccult.util.MathHelper.MathHelper;

public class EntityLight extends EntityMob
{
	private static final DataParameter<Integer> Light_VARIANT = EntityDataManager.<Integer>createKey(EntityLight.class, DataSerializers.VARINT);
	private ChunkCoordinates currentFlightTarget = null;
    private EntityLivingBase findtoattack = null;
	public boolean isATField;
    protected int keeptime;
    private boolean TestMode;
    protected boolean isok;
    private int ShieldEnergy;
    
	public EntityLight(World world) {
		super(world);
        this.setSize(0.5F, 0.5F);
        this.isImmuneToFire = true;
        this.TestMode = false;
        this.ShieldEnergy = 10;
	}
	
	  protected boolean isAIEnabled()
	  {
	    return true;
	  }
	  
	  public int getTotalArmorValue()
	  {
	    return 25;
	  }

	  protected void entityInit()
	  {
	      super.entityInit();
	      this.dataManager.register(Light_VARIANT, Integer.valueOf(5));
	  }
		
	  public int getRenderSpeed()
	  {
	      return ((Integer)this.dataManager.get(Light_VARIANT)).intValue();
	  }

	  public void setRenderSpeed(int speed)
	  {
	      this.dataManager.set(Light_VARIANT, Integer.valueOf(speed));
	  }
	  
	protected void dropFewItems(boolean arg1, int arg2)
	    {
		ItemStack s = new ItemStack(ModItems.KnowledgeScroll);
		ItemMagickable.storeMagickString(s,ImplementationHandler.FocuseI);
		if(!this.world.isRemote)
		this.entityDropItem(s, 0);
	        if (this.rand.nextInt(1) == 0)
	        {
	            this.dropItem(ModItems.SPA, 1);
	        }
	        super.dropFewItems(arg1, arg2);
	    }
	  
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(9.0D);
    }
    
    public boolean canBePushed()
    {
      return false;
    }
    
    @Override
    public void onUpdate()
    {
	   super.onUpdate();
	   this.onGround = false;
        Entity entity = null;	    
        double effectiveBoundary = 32.0D;
        double effectiveBoundary2 = 1D;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(effectiveBoundary, effectiveBoundary, effectiveBoundary));
        List<Entity> list2 = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(effectiveBoundary2, effectiveBoundary2, effectiveBoundary2));
        if ((list != null) && (list.size() > 0))
	    {
        	double d4 = -1.0D;
	      for (int j1 = 0; j1 < list.size(); ++j1)
	      {
	        entity = (Entity)list.get(j1);
	        
	        if ((this.world.isRemote) || (entity == null) || (entity instanceof EntityItemFrame) || (entity instanceof EntityPainting)) {
		          continue;
		    }
	        

	              double d5 = entity.getDistanceSq(this.posX, this.posY, this.posZ);

	              if ((16.0D < 0.0D || d5 < 16.0D * 16.0D) && (d4 == -1.0D || d5 < d4))
	              {
	            	  if(entity instanceof EntityLivingBase)
	                  {
	                  d4 = d5;
	                  EntityLivingBase entity1 = (EntityLivingBase)entity;
	                  findtoattack = entity1;
	                  }
	                  }            	
	            }      
          }
       
        if ((list2 != null) && (list2.size() > 0))
	    {
	      for (int j1 = 0; j1 < list2.size(); ++j1)
	      {
	        entity = (Entity)list2.get(j1);
	        
	        if ((this.world.isRemote) || (entity == null) || (entity instanceof EntityItemFrame) || (entity instanceof EntityPainting)) {
		          continue;
		    }
	        	Ref(entity);
	         }      
          }
        
        int xdir = 1;
        int zdir = 1;
        
        int keep_trying = 50;

        EntityLivingBase e = null;
        
         if (this.currentFlightTarget == null) {
        	this.currentFlightTarget = new ChunkCoordinates((int)this.posX, (int)this.posY, (int)this.posZ);
        	}
         
         if ((this.rand.nextInt(300) == 0) || (this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 2.1F))
    	 {
    	     Block bid = Blocks.STONE;
    	     while (true) { if ((bid == Blocks.AIR) || (keep_trying == 0)) break;
    	     zdir = this.rand.nextInt(10) + 8;
    	     xdir = this.rand.nextInt(10) + 8;
    	     if (this.rand.nextInt(2) == 0) zdir = -zdir;
    	     if (this.rand.nextInt(2) == 0) xdir = -xdir;
    	     this.currentFlightTarget.set((int)this.posX + xdir, (int)this.posY + this.rand.nextInt(20) - 10, (int)this.posZ + zdir);
    	     bid = this.world.getBlockState(getPosition()).getBlock();
    	     if ((bid == Blocks.AIR) && 
    	    	(!(canSeeTarget(this.currentFlightTarget.posX, this.currentFlightTarget.posY, this.currentFlightTarget.posZ)))) {
    	      bid = Blocks.STONE;
    	         }
    	 
    	         --keep_trying;
    	       }
    	 }
        if ((this.rand.nextInt(7) == 2) && (this.world.getDifficulty() != EnumDifficulty.PEACEFUL))
             {
               e = findSomethingToAttack(e);
               if (e != null)
               {
                 this.currentFlightTarget.set((int)e.posX, (int)(e.posY + e.height / 2.0F), (int)e.posZ);
                	 EntityLmr entitylmr = new EntityLmr(world, this, 2, 2, 2);
                     world.spawnEntity(entitylmr);     
               }
             }else {
                 float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) - 90.0F;
                 float var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
                 this.moveForward = 0.75F;
                 this.rotationYaw += var8 / 4.0F;
             }
        double var1 = currentFlightTarget.posX + 0.5D - this.posX;
        double var3 = currentFlightTarget.posY + 0.1D - this.posY;
        double var5 = currentFlightTarget.posZ + 0.5D - this.posZ;
        int move = shouleMove(var1, var3, var5, isok);
        if(!(move == 40)){
        }  
        onPrcticle();  
        ShieldThing();
        
}
    
    public void ShieldDecrease() {
    	ShieldEnergy = ShieldEnergy - 1;
    }
    
    public int getShield() {
    	return ShieldEnergy;
    }
    
    protected void ShieldThing() {
        if(this.ticksExisted % 200 == 0)
        	ShieldEnergy++;
        
        if(ShieldEnergy > 10) {
        	ShieldEnergy = 10;
        }
        if(ShieldEnergy < 0) {
        	ShieldEnergy = 0;
        }
    }
    
    public void ChangeToTest(EntityPlayer player) {
    	if(!player.world.isRemote) {
    	if(TestMode) {
    		TestMode = false;
    		player.sendMessage(new TextComponentString("Test Mode Disabled"));
    	}else
    	{
    		TestMode = true;
    		player.sendMessage(new TextComponentString("Test Mode Enabled"));
    	}
    	}
    }
    
    protected int shouleMove(double var1, double var3, double var5, boolean isok) {
        if(!isok && !TestMode) {
        this.motionX += (Math.signum(var1) * 0.4D - this.motionX) * 0.2D;
        this.motionY += (Math.signum(var3) * 0.699999988079071D - this.motionY) * 0.2000000014901161D;
        this.motionZ += (Math.signum(var5) * 0.4D - this.motionZ) * 0.2D;
        setRenderSpeed(0);
        return 40;
        }
        
        if(isok || TestMode) {
        	this.motionX = 0;
        	this.motionY = 0;
        	this.motionZ = 0;
        	this.posX = this.lastTickPosX;
        	this.posY = this.lastTickPosY;
        	this.posZ = this.lastTickPosZ;
        	setRenderSpeed(1);
        	return 50;
        }
        return 40;
    }

    private void onPrcticle() {
    	double x,y,z;
		x = this.posX - 1;
		y = this.posY;
		z = this.posZ - 1;
	    for (int i = 0; i < 5; ++i)
	    {
		double d0 = x + this.world.rand.nextInt(3);
		double d1 = y + this.world.rand.nextInt(3);
		double d2 = z + this.world.rand.nextInt(3);
		this.world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, d0, d1, d2, 0.2, 0, 0.2);
	    }
	    this.world.spawnParticle(EnumParticleTypes.PORTAL, this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ, 0.2, 0.2, 0.2);
	    //createLaser(world, posX, posY, posZ, this, 20);
    }
    
	/*public void createLaser(World worldIn, double posXIn, double posYIn, double posZIn, Entity player, float height)
	{
			SuperLaserBeamFX laser =  new SuperLaserBeamFX(worldIn, posXIn, posYIn, posZIn, player, height);
			laser.setRBGColorF(1, 0.5F, 0);
			laser.setAlphaF(1F);
			laser.setMaxAge(2);
			Minecraft.getMinecraft().effectRenderer.addEffect(laser);
	}*/
    
    @Override
    public void fall(float distance, float damageMultiplier) {
    	
    }
    
    private EntityLivingBase findSomethingToAttack(EntityLivingBase entity){
        entity = findtoattack;
        if (entity != null && this.canEntityBeSeen(entity) && isSuitableTarget(entity)) {
        	isok = true;
            if (entity.getDistanceSq(entity) < 4096.0D)
            {
            	this.faceEntity(entity, 180, 90);;
            }
        	keeptime--;
        	return entity;
        }
        else {
        	isok = false;
        	return null;}
       }
    
    public boolean canSeeTarget(double pX, double pY, double pZ)
    {
    	return (this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.55D, this.posZ), new Vec3d(pX, pY, pZ), false) == null);
    }
    
    protected void Ref(Entity hitEntity)
    {
      if(!(hitEntity instanceof EntityLivingBase || hitEntity instanceof EntityItem || hitEntity instanceof EntityLmr))
      {
    	  final double x,y,z;
    	  x = hitEntity.motionX;
	      y = hitEntity.motionY;
	      z = hitEntity.motionZ;
	      hitEntity.rotationYaw = -hitEntity.prevRotationYaw;
	      hitEntity.rotationPitch = -hitEntity.prevRotationPitch;
	      ref_fx(hitEntity);
	      hitEntity.motionX = -x * 10;
	      hitEntity.motionY = -y * 10;
	      hitEntity.motionZ = -z * 10;
     }
    }
    protected void ref_fx(Entity hitEntity)
    {
    	double[] vec = {0, 0, 0};
		double[] pos = {hitEntity.posX, hitEntity.posY + hitEntity.height / 2, hitEntity.posZ};
		float[] color = {1F, 0.9F, 0.5F};
		
    	NetworkHandler.sendToAllAround(new NetworkEffectData(pos, vec, color, 1, 7)
				, new TransPoint(hitEntity.dimension, hitEntity.posX, hitEntity.posY, hitEntity.posZ, 32), hitEntity.world);
    }
       private boolean isSuitableTarget(EntityLivingBase par1EntityLiving)
       {
         if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) return false;
     
         if (par1EntityLiving == null)
         {
           return false;
         }
         
         if (par1EntityLiving instanceof EntityLight)
         {
           return false;
         }
         
         if (par1EntityLiving instanceof EntityPlayer)
         {
        	 EntityPlayer pl = (EntityPlayer) par1EntityLiving;
        	 if(pl.isCreative())
           return false;
         }
         
         if (!(par1EntityLiving.isEntityAlive()))
         {
           return false;
         }
     
         if (!(getEntitySenses().canSee(par1EntityLiving)))
         {
           return false;
         }
     
         if (par1EntityLiving instanceof EntityTameable)
         {
           EntityTameable e = (EntityTameable)par1EntityLiving;
           if (e.isTamed()) {
             return false;
           }
         }
         return true;
       }
}
