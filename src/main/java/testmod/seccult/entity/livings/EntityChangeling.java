package testmod.seccult.entity.livings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import testmod.seccult.Seccult;
import testmod.seccult.entity.RogoEntityGetData;

public class EntityChangeling extends EntityCreature implements RogoEntityGetData{
	private static final DataParameter<NBTTagCompound> NBT = EntityDataManager.<NBTTagCompound>createKey(EntityChangeling.class, DataSerializers.COMPOUND_TAG);
	private static final DataParameter<Boolean> Change = EntityDataManager.<Boolean>createKey(EntityChangeling.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> Release = EntityDataManager.<Boolean>createKey(EntityChangeling.class, DataSerializers.BOOLEAN);
	public ArrayList<EntityPlayer> players = new ArrayList<EntityPlayer>();
	public List<Entity> MyEntityList;
	private ArrayList<String> StoragedEntity = new ArrayList<String>();
	private Entity Entity;
	
	public EntityChangeling(World worldIn) {
		super(worldIn);
		setChange(true);
	}
	
	public Entity getEntity()
	{
		return this.Entity;
	}
	
	@Override
	protected void entityInit() 
	{
		super.entityInit();
		this.dataManager.register(NBT, new NBTTagCompound());
		this.dataManager.register(Change, false);
		this.dataManager.register(Release, false);
	}
	
    protected void initEntityAI()
    {
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }
    
	@Override
	public void onUpdate()
	{
		super.onUpdate();
        Entity Sentity = null;
        this.MyEntityList = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(0.5));
        
        if ((this.MyEntityList != null) && (this.MyEntityList.size() > 0))
	    {
	      for (int j1 = 0; j1 < this.MyEntityList.size(); ++j1)
	      {
	        Sentity = (Entity)this.MyEntityList.get(j1);
	        
		    if ((Sentity == null) || (this.world.isRemote) || (Sentity instanceof EntityPlayer) || (Sentity instanceof EntityItemFrame) || (Sentity instanceof EntityPainting) ) {
			    continue;
			}
	        
	        if(Sentity instanceof EntityChangeling) 
	        {
	        	EntityChangeling changeling = (EntityChangeling) Sentity;
	        	if(changeling.Entity != null) 
		        	this.Entity = changeling.Entity;
	        	continue;
	        }
	        
	        if(getChange() && (this.Entity == null || this.Entity != null && this.Entity instanceof EntityItem)) 
	        {
	        	copy(Sentity);
	        }
	        
		    ResourceLocation className = EntityList.getKey(Sentity.getClass());
		    if(className == null) 
		    continue;
	        
	        if(getChange() && (this.Entity == null || this.Entity != null && !EntityList.getKey(Sentity.getClass()).equals(EntityList.getKey(this.Entity.getClass())))) 
	        {
	        	copy(Sentity);
	        }
	      }      
        }
        
        if(this.MyEntityList.isEmpty()) {
        	setChange(true);
        }
        else 
        {
        	setChange(false);
        }
        
        if(this.world.isRemote && this.Entity != null) 
        {
        	
			if(this.Entity != null && this.Entity instanceof EntityLivingBase)
			{
				EntityLivingBase living = (EntityLivingBase) this.Entity;
				living.setHealth(this.getHealth());
			}
        }
        
		if(!this.world.isRemote && this.Entity != null && !(Entity instanceof EntityPlayer))
		{
		    NBTTagCompound entity = new NBTTagCompound();
		    ResourceLocation className = EntityList.getKey(this.Entity.getClass());
		    if(className != null) 
		    {
		    	updateData(className.toString());
		    	entity.setString("id", className.toString());
		    	setTag(this.Entity.writeToNBT(entity));
		    }
		    else if(this.Entity instanceof EntityItem)
		    {
		    	updateData(EntityList.getKey(EntityItem.class).toString());
		    	entity.setString("id", EntityList.getKey(EntityItem.class).toString());
		    	setTag(this.Entity.writeToNBT(entity));
		    }
		    
		}
		else if(this.world.isRemote)
		{
			NBTTagCompound nbt = getTag();
		    if (nbt != null && !(getTag().getString("id").equals("")))
		    {
		    	Entity newEntity = EntityList.createEntityFromNBT(getTag(), world);
		    	
		    	if(newEntity != null && (this.Entity == null || this.Entity != null && (!EntityList.getKey(newEntity).equals(EntityList.getKey(this.Entity))))) 
		    	{
		    		if(this.Entity != null)
		    			this.Entity.setDead();
		    		
		    		this.width = newEntity.width;
		    		this.height = newEntity.height;
		    		newEntity.readFromNBT(nbt);
		    		newEntity.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		    		this.Entity = newEntity;
		    		this.world.spawnEntity(newEntity);
		    	}
		    }
		}
		
		if(!this.world.isRemote)
		{
			setSeccultData();
			getSeccultDataToEntity();
		}
		
		/*if(!this.StoragedEntity.isEmpty() && this.ticksExisted % 20 == 0)
		{
            Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation(this.StoragedEntity.get(this.rand.nextInt(this.StoragedEntity.size()))), this.world);
            this.Entity = entity;
            if(this.Entity instanceof EntityItem)
            {
            	if(this.rand.nextInt(2) == 1) {
                Item it = null;
            	
                Iterator<Item> ilist = Item.REGISTRY.iterator();
                int icount = 0;
                while (ilist.hasNext())
                {
                  it = (Item)ilist.next();
                  if (it != null) {
                    icount++;
                  }
                }
                for (int j = 0; j < 150;)
                {
                  int k = 1 + this.world.rand.nextInt(icount);
                  ilist = Item.REGISTRY.iterator();
                  while ((k > 0) && (ilist.hasNext()))
                  {
                    it = (Item)ilist.next();
                    k--;
                  }
                  if (it != null)
                  {
                    j++;
                    EntityItem item = (EntityItem) this.Entity;
                    item.setItem(new ItemStack(it, this.rand.nextInt(64) + 1));
                  }
                }
            	}
            	else
            	{
                Block bl = null;
            	
                Iterator<Block> blist = Block.REGISTRY.iterator();
                int bcount = 0;
                while (blist.hasNext())
                {
                  bl = (Block)blist.next();
                  if (bl != null) {
                    bcount++;
                  }
                }
                for (int j = 0; j < 150;)
                {
                  int k = 1 + this.world.rand.nextInt(bcount);
                  blist = Block.REGISTRY.iterator();
                  while ((k > 0) && (blist.hasNext()))
                  {
                    bl = (Block)blist.next();
                    k--;
                  }
                  if (bl != null)
                  {
                    j++;
                    EntityItem item = (EntityItem) this.Entity;
                    item.setItem(new ItemStack(bl, this.rand.nextInt(64) + 1));
                  }
                }
            	}
            }
            if(this.world.isRemote) 
            {
            	this.world.spawnEntity(entity);
            }
		}*/
	}
	
	private void copy(Entity Sentity)
	{
		this.Entity = Sentity;
		this.width = Sentity.width;
		this.height = Sentity.height;
    	setChange(false);
	}
	
	public void setTag(NBTTagCompound nbt) 
	{
		this.dataManager.set(NBT, nbt);
	}
	  
	public NBTTagCompound getTag() 
	{
		return this.dataManager.get(NBT);
	}
	
	public void setChange(Boolean chan)
	{
		this.dataManager.set(Change, chan);
	}
	
	public Boolean getChange()
	{
		return this.dataManager.get(Change);
	}
	
	public void setRelease(Boolean re)
	{
		this.dataManager.set(Release, re);
	}
	
	public Boolean getRelease()
	{
		return this.dataManager.get(Release);
	}

    public NBTTagList getSeccultData()
    {
        NBTTagCompound nbttagcompound = this.getEntityData();
        return nbttagcompound != null ? (NBTTagList) nbttagcompound.getTag(Seccult.Data) : null;
    }

    public void getSeccultDataToEntity()
    {
    	NBTTagList nbttaglist = getSeccultData();
    	if(nbttaglist != null) {
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            updateData(nbttaglist.getStringTagAt(i));
        }
    	}
    }

	private void updateData(String s)
	{
		if(!this.StoragedEntity.contains(s)) 
		{
			this.StoragedEntity.add(s);
		}
	}
	
	public void setSeccultData() {
		NBTTagList nbttaglist = getSeccultData();
		ArrayList<String> entity = new ArrayList<>();
		
    	if(nbttaglist != null) {
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            entity.add(nbttaglist.getStringTagAt(i));
        }
    	}
    	
        for (int i = 0; i < this.StoragedEntity.size(); ++i)
        {
            if(!entity.contains(this.StoragedEntity.get(i)))
            	entity.add(this.StoragedEntity.get(i));
        }
        
        NBTTagList newlist = new NBTTagList();
        
        for (int i = 0; i < entity.size(); ++i)
        {
            newlist.appendTag(new NBTTagString(entity.get(i)));
        }
        
        this.getEntityData().setTag(Seccult.Data, newlist);
	}
}
