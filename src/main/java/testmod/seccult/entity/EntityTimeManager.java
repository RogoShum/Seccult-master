package testmod.seccult.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.particleHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.projectile.TRprojectileBase;
import testmod.seccult.magick.magickState.StateManager;

public class EntityTimeManager extends Entity{
	private static final DataParameter<Integer> EFFECT = EntityDataManager.<Integer>createKey(EntityTimeManager.class, DataSerializers.VARINT);

	protected UUID upperUUID = null;
	protected long uppermost = 0;
	protected long upperleast = 0;
	protected EntityLivingBase MyLord;
	protected int MyRange;
	private boolean timestop;
	private int limit;
	private long timeset;
	private ArrayList<EntityDummy> hookedEntitys = new ArrayList<EntityDummy>();
	private ArrayList<EntityLiving> hookedLivings = new ArrayList<EntityLiving>();
	private ArrayList<Entity> hookedprojectile = new ArrayList<Entity>();
	
	public EntityTimeManager(World worldIn) {
		super(worldIn);
		this.setSize(1.0F, 1.0F);
	}
	
	@Override
	public void setDead() {
		if(this.world.isRemote)
			this.isDead = true;
		else 
		{
			if(this.MyLord == null) 
			{
				this.isDead = true;
				unLoadHooks();
			}
		}
	}
	
	public EntityTimeManager(World worldIn, EntityLivingBase lord, int range) {
		this(worldIn);
		this.setSize(1.0F, 1.0F);
		this.MyLord = lord;
		this.upperUUID = lord.getUniqueID();
		this.upperleast = lord.getUniqueID().getLeastSignificantBits();
		this.uppermost = lord.getUniqueID().getMostSignificantBits();
		this.MyRange = range;
		this.limit = 300;
	}
	
	public EntityTimeManager(World worldIn, EntityLivingBase lord, int range, int time) {
		this(worldIn, lord, range);
		this.limit = time;
	}
	
	@Override
	public void onUpdate() {
		if(!this.world.isRemote) {
		super.onUpdate();
		
		if(this.MyRange > -10 && (this.MyLord == null || !this.MyLord.getEntityData().hasKey("TimeStop") || this.MyLord.getEntityData().getInteger("TimeStop") == 0 || (limit > 10 && limit !=300))) {
			setRender(1);
			this.MyRange--;
			loadUUID();
			if(this.MyRange <= 40) {
				if(MyLord != null)
					MyLord.getEntityData().setInteger("TimeStop", 0);
				unLoadHooks();
			this.isDead = true;
			this.MyLord = null;
			this.setDead();
			}
			return;
		}
		if(limit != 300)
		limit++;
		if(MyLord.getEntityData().hasKey("TimeStop") && MyLord.getEntityData().getInteger("TimeStop") == 2) {
			List<EntityLiving> livings = this.hookedLivings;
			if(livings != null && (livings.size() > 0)) {
			for (int a = 0; a < livings.size(); ++a) {
				EntityLiving d = livings.get(a);
				StateManager.setState(d, StateManager.NO_AI, 10);
			}
			}
			
			List<Entity> projectile = this.hookedprojectile;
			if(projectile != null && (projectile.size() > 0)) {
			for (int a = 0; a < projectile.size(); ++a) {
				Entity p = projectile.get(a);
				frozeEntity(p);
			}
			}
			/*ParticleManager render = Minecraft.getMinecraft().effectRenderer;
			Field partic;
			Field particleAge;
			try {
				
				partic = ParticleManager.class.getDeclaredField("queue");
				partic.setAccessible(true);
				Queue<Particle> queue = (Queue<Particle>) partic.get(render);
				if (!queue.isEmpty())
				{
					for (Particle particle = queue.poll(); particle != null; particle = queue.poll())
					{
						particleAge = Particle.class.getDeclaredField("particleAge");
						particleAge.setAccessible(true);
						int age = (int)particleAge.get(particle);
						particleAge.set(particle, --age);
						particle.setPosition(particleHelper.getPrevPosX(particle), particleHelper.getPrevPosY(particle), particleHelper.getPrevPosZ(particle));
					}
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			 */
		    List<Entity> list = MyLord.world.getEntitiesWithinAABBExcludingEntity(MyLord, MyLord.getEntityBoundingBox().grow(MyRange));
		    Entity entity = null;    

		      for (int j1 = 0; j1 < list.size(); ++j1)
		      {
		        entity = (Entity)list.get(j1);
		        if (entity != null && entity != this.MyLord)
		        {
		        	TimeStop(entity);
		        }
		        
		      }
		}
		
		this.MyLord.getEntityData().setInteger("TimeStop", 2);
		setRender(7);
		itemEffectFollowUser();
		if(!timestop) {
		this.timeset = this.world.getWorldTime();
		this.timestop = true;
		}
		else
		this.world.setWorldTime(this.timeset);
		}
	}
	
	private void unLoadHooks() 
	{
		List<EntityDummy> hooks = this.hookedEntitys;
		this.hookedprojectile.clear();
			if(hooks != null && (hooks.size() > 0)) {
			for (int a = 0; a < hooks.size(); ++a) {
				EntityDummy d = hooks.get(a);
				DummyManager.restore(d);
    }
	}
	}
	
	private void frozeEntity(Entity hitEntity) 
	{
		  hitEntity.setPosition(hitEntity.prevPosX, hitEntity.prevPosY, hitEntity.prevPosZ);

	      hitEntity.rotationYaw = hitEntity.prevRotationYaw;
	      hitEntity.rotationPitch = hitEntity.prevRotationPitch;
	      hitEntity.motionX = 0.0D;
	      hitEntity.motionY = -0.0D;
	      hitEntity.motionZ = 0.0D;

	      hitEntity.setAir(0);

	      hitEntity.ticksExisted -= 1;

	      hitEntity.fallDistance -= 0.076865F;
	}
	
	private void TimeStop(Entity hitEntity) {
	    if ((hitEntity != null) && (hitEntity.ticksExisted >= 2))
	    {
		    ResourceLocation className = EntityList.getKey(hitEntity.getClass());
		    if(className == null && !(hitEntity instanceof EntityItem)) 
		    {
		    	frozeEntity(hitEntity);
		    	return; 
		    }
	    	
	      if (!(hitEntity instanceof EntityLivingBase)) {
	    	  if(hitEntity instanceof TRprojectileBase) 
	    	  {
	    		  this.hookedprojectile.add(hitEntity);
	    		  return;
	    	  }
	    	  if(!(hitEntity instanceof EntityDummy) && !(hitEntity instanceof EntityTimeManager))
	    	  this.hookedEntitys.add(DummyManager.replace(hitEntity));
	          return;
	      }
	      
	      frozeEntity(hitEntity);
	      
	      EntityLivingBase living = (EntityLivingBase)hitEntity;
	      if(living instanceof EntityLiving) {
	    	  this.hookedLivings.add((EntityLiving) living);
	      }
	      living.rotationYawHead = living.prevRotationYawHead;

	      if (living instanceof EntityTameable)
	      {
	        living.motionY -= 1.0E-006D;
	      }
	      
	      if (hitEntity instanceof EntityPlayerMP)
	      {
	        EntityPlayerMP player = (EntityPlayerMP)hitEntity;
	        player.connection.setPlayerLocation(player.prevPosX, player.prevPosY, player.prevPosZ, player.prevRotationYaw, player.prevRotationPitch, Collections.emptySet());
	      }
	    }
	  }
	
	  public void itemEffectFollowUser()
	  {
		  Vec3d qaq = this.MyLord.getLookVec();
		  Vec3d qwq = this.MyLord.getPositionVector();
		  Vec3d QAQ = qwq.addVector(qaq.x * 2, (qaq.y + this.MyLord.height / 2) * 2, qaq.z * 2);
		  this.posX = QAQ.x;
		  this.posY = QAQ.y;
		  this.posZ = QAQ.z;
	  }
	
		private void loadUUID() {
	        if(!this.getEntityWorld().isRemote && this.MyLord == null && this.upperUUID != null) {
		        double range = 128D;
		        List<EntityLivingBase> connections = this.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(range, range, range));
		        Iterator<EntityLivingBase> possibleConnections = connections.iterator();
		        while(possibleConnections.hasNext()) {
		        	EntityLivingBase possibleConnection = (EntityLivingBase)possibleConnections.next();
		            if(possibleConnection.getUniqueID().equals(this.upperUUID)) {
		            	this.MyLord = possibleConnection;
		            	break;
		            }
		        }
	        }
	        else
	        	this.setDead();
		}
	  
	@Override
    protected void entityInit()
    {
        this.dataManager.register(EFFECT, Integer.valueOf(5));
    }
	
    public int getRenderSkin()
    {
        return ((Integer)this.dataManager.get(EFFECT)).intValue();
    }

    public void setRender(int skinId)
    {
        this.dataManager.set(EFFECT, Integer.valueOf(skinId));
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("ParentUUIDMost") && nbt.hasKey("ParentUUIDLeast")) {
			this.uppermost = nbt.getLong("ParentUUIDMost");
			this.upperleast = nbt.getLong("ParentUUIDLeast");
            this.upperUUID = new UUID(nbt.getLong("ParentUUIDMost"), nbt.getLong("ParentUUIDLeast"));
        }
		
		if(nbt.hasKey("MyRange"))
			this.MyRange = nbt.getInteger("MyRange");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		if(!nbt.hasKey("ParentUUIDMost") && !nbt.hasKey("ParentUUIDLeast")) {
			nbt.setLong("ParentUUIDMost", this.uppermost);
			nbt.setLong("ParentUUIDLeast", this.upperleast);
        }
		
		if(!nbt.hasKey("MyRange"))
			nbt.setInteger("MyRange", this.MyRange);
		
		
		List<EntityDummy> hooks = this.hookedEntitys;
			if(hooks != null && (hooks.size() > 0)) {
			for (int a = 0; a < hooks.size(); ++a) {
				EntityDummy d = hooks.get(a);
				DummyManager.restore(d);
    }
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
}
