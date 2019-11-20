package testmod.seccult.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.init.ModDamage;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.TransPoint;

public class EntityBlackVelvetHell extends Entity{
	private static final DataParameter<Float> Size = EntityDataManager.<Float>createKey(EntityBlackVelvetHell.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> Prisoner = EntityDataManager.<Float>createKey(EntityBlackVelvetHell.class, DataSerializers.FLOAT);
	
	private Entity Owner;
	private Entity prisoner;
	private boolean overQuest;
	private int tiktok;
	private int nb人nb指数;
	
	
	public EntityBlackVelvetHell(World worldIn) {
		super(worldIn);
		this.setSize(1.0F, 1.0F);
		this.setNoGravity(true);
		this.noClip = true;
		this.collided = false;
		this.isImmuneToFire = true;
		this.setEntityInvulnerable(true);
	}

	public void nb人nb时() {
		setPrisoner(nb人nb指数);
		if(getPrisoner() > 3) {
			this.setDead();
		}
	}
	
	@Override
	public boolean isEntityAlive() {
		return !this.isDead;
	}
	
	@Override
	public void setDead() {
		if(!getEntityWorld().isRemote && getPrisoner() > 2) {
			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.VOICE, 2F, 0F + this.rand.nextFloat() / 2);
	    		double[] Bpos = {this.posX, this.posY + (this.getMySize() / 8), this.posZ};
	    		double[] vec = {0, 0, 0};
				float[] color = {0.2F, 0.0F, 0.5F};
	    		NetworkHandler.sendToAllAround(new NetworkEffectData(Bpos, vec, color, this.getMySize() / 6, 106), 
	    				new TransPoint(-12450, Bpos[0], Bpos[1], Bpos[2], 32), this.world);

			this.isDead = true;
		}else if(getEntityWorld().isRemote){
			this.isDead = true;
		}
	}
	
	@Override
	protected void entityInit() {
        this.dataManager.register(Size, Float.valueOf(1));
        this.dataManager.register(Prisoner, Float.valueOf(0));
	}
	
    public float getMySize()
    {
        return this.dataManager.get(Size).floatValue();
    }
    
    private void setRender(float size)
    {
        this.dataManager.set(Size, Float.valueOf(size));
    }
	
    public float getPrisoner()
    {
        return this.dataManager.get(Prisoner).floatValue();
    }
    
    private void setPrisoner(float p)
    {
        this.dataManager.set(Prisoner, Float.valueOf(p));
    }
    
	public void setPrisoner(Entity entity) {
		this.prisoner = entity;
	}
	
	public void setOwner(Entity entity) {
		this.Owner = entity;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(this.world.isRemote && this.getPrisoner() < 5)
			this.isDead = false;
		nb人nb时();
		tiktok++;
		
		if(this.overQuest)
			this.nb人nb指数++;
		if(this.prisoner != null && !(this.prisoner instanceof EntityBlackVelvetHell)) {
			if(!prisoner.isEntityAlive() && prisoner.isDead) {
				this.overQuest = true;
				return;
			}
			if(this.ticksExisted % 2 ==0)
				this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), SoundEvents.ENTITY_ENDEREYE_DEATH, SoundCategory.VOICE, (float)tiktok / 20, (float)tiktok / 30);
			prisoner.ticksExisted -= 1;
			prisoner.posX = prisoner.prevPosX;
			prisoner.posY = prisoner.prevPosY;
			prisoner.posZ = prisoner.prevPosZ;
			
			posX = prisoner.posX;
			posY = prisoner.posY + (prisoner.height / 2);
			posZ = prisoner.posZ;
			
			float size = Math.max(prisoner.width, prisoner.height) * 2;
			setRender(size);
			
			if((prisoner instanceof EntityLivingBase))
			{
				EntityLivingBase living = (EntityLivingBase) prisoner;
				if(living.getHealth() < tiktok)
				{
					living.onDeath(ModDamage.causeBlackVelvetHellDamage(this.Owner));
					living.setHealth(living.getHealth() - tiktok);
					if(!living.isEntityAlive())
						living.isDead = true;
				}
				
				if(tiktok > 100 && (prisoner.isEntityAlive() || !prisoner.isDead)) 
				{
					living.onDeath(ModDamage.causeBlackVelvetHellDamage(this.Owner));
					living.setHealth(-1000);
					living.isDead = true;
					living.setDead();
				}
			}
			else 
			{
				prisoner.isDead = true;
				prisoner.setDead();
			}
			
			if(tiktok > 200 && (prisoner.isEntityAlive() || !prisoner.isDead))
			{
				NBTTagCompound emptyTag = new NBTTagCompound();
				this.prisoner.readFromNBT(emptyTag);
			}
			
			if(tiktok > 300 && (prisoner.isEntityAlive() || !prisoner.isDead))
			{
				List<Entity> list = this.world.getLoadedEntityList();
				if(list.contains(this.prisoner))
					list.remove(this.prisoner);
			}
		}
		else if(!this.world.isRemote && this.prisoner == null || (this.prisoner instanceof EntityBlackVelvetHell)){
			this.setPrisoner(100);
			this.setDead();
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
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
	}
}
