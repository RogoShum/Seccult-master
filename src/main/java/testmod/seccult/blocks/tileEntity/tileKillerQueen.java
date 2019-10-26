package testmod.seccult.blocks.tileEntity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import testmod.seccult.Seccult;
import testmod.seccult.entity.EntityBlackVelvetHell;
import testmod.seccult.entity.livings.EntityKingCrimson;
import testmod.seccult.init.ModSounds;

public class tileKillerQueen extends TileEntity implements ITickable {
	private ArrayList<String> trueNameList = new ArrayList<String>();
	private ArrayList<KillerTime> killerList = new ArrayList<KillerTime>();
	private int killerRange = 64;
	
	@SuppressWarnings("deprecation")
	public tileKillerQueen() {
		
	}
	
	@Override
	public void update() 
	{
		List<Entity> entityList = this.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(this.pos).grow(killerRange));
		for(int i = 0; i < entityList.size(); ++i)
		{
            String trueName = EntityList.getEntityString(entityList.get(i));

            if (trueName == null)
            {
            	trueName = "generic";
            }
            
            if(trueNameList.contains(trueName) && !this.hasEntity(entityList.get(i)))
            {
            	killerList.add(new KillerTime(entityList.get(i)));
            	doRingSound();
            }
		}
		
		for(int i = 0; i < killerList.size(); ++i)
		{
			KillerTime timeToDie = killerList.get(i);
			timeToDie.tick();
			
			if(timeToDie.done)
				this.killerList.remove(timeToDie);
		}
	}

	public void doRingSound()
	{
		this.world.playSound(null, pos, ModSounds.ringRingRing, SoundCategory.VOICE, 0.01F, 0.8F + this.world.rand.nextFloat());
	}
	
	public boolean hasEntity(Entity victim)
	{
		for(int i = 0; i < killerList.size(); ++i)
		{
			KillerTime timeToDie = killerList.get(i);
			if(timeToDie.getEntity() == victim)
				return true;
		}
		return false;
	}
	
	public boolean putEntity(Entity entity)
	{
		
		 String trueName = EntityList.getEntityString(entity);
		 if(!this.trueNameList.contains(trueName))
		 {
			 this.trueNameList.add(trueName);
			 return true;
		 }
		 return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if(compound.hasKey("KillerList"))
		{
			NBTTagList list = compound.getTagList("KillerList", 8);
			for(int i = 0; i < list.tagCount(); ++i)
			{
				this.trueNameList.add(list.getStringTagAt(i));
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if(!this.trueNameList.isEmpty())
		{
			NBTTagList list = new NBTTagList();
			for(int i = 0; i < trueNameList.size(); ++i)
			{
				list.appendTag(new NBTTagString(trueNameList.get(i)));
			}
			compound.setTag("KillerList", list);
		}
		return compound;
	}
	
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }
    
    public class KillerTime
    {
    	private Entity victim;
    	private int tickTime;
    	private boolean done;
    	
    	public KillerTime(Entity victim) 
    	{
    		this.victim = victim;
		}
    	
    	public void tick()
    	{
    		if(tickTime == 40 && victim instanceof EntityLivingBase)
    			StandAttack(victim);
    		else if(tickTime == 40)
    			BlackVelvetHell(victim);
    		
    		tickTime++;
    		
    		if(tickTime > 100)
    		{
    			victim.setDead();
    			victim.isDead = true;
    			victim.world.removeEntityDangerously(victim);
    		}
    		
    		if(tickTime > 102 && victim.world.loadedEntityList.contains(victim))
    		{
    			victim.world.loadedEntityList.remove(victim);
    		}
    		
    		if((!victim.isEntityAlive() && victim.isDead) || !victim.world.loadedEntityList.contains(victim))
    			this.done = true;
    		
    		if(this.tickTime > 100)
    			this.done = true;
    	}
    	
    	public void StandAttack(Entity entity)
    	{
    		EntityKingCrimson killerKing = new EntityKingCrimson(victim.world);
    		killerKing.setOwner((EntityLivingBase)victim);
    		killerKing.setKillerOwner();
    		killerKing.setPosition(victim.posX, victim.posY, victim.posZ);
    		if(!victim.world.isRemote)
    		victim.world.spawnEntity(killerKing);
    	}
    	
    	public void BlackVelvetHell(Entity entity)
    	{
    		EntityBlackVelvetHell blackvelvethell = new EntityBlackVelvetHell(victim.world);
    		blackvelvethell.setOwner((EntityLivingBase)victim);
    		blackvelvethell.setPrisoner(victim);
    		blackvelvethell.setPosition(victim.posX, victim.posY, victim.posZ);
    		if(!victim.world.isRemote)
    		victim.world.spawnEntity(blackvelvethell);
    	}
    	
    	public Entity getEntity()
    	{
    		return this.victim;
    	}
    }
}
