package testmod.seccult.entity;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfo.Overlay;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.IBossBase;
import testmod.seccult.init.ModSounds;

public class EntitySound extends Entity implements IBossBase{
	private ArrayList<Entity> ownerEntity;
	private BlockPos pos = null;
	private static final DataParameter<BlockPos> blockPos = EntityDataManager.<BlockPos>createKey(EntitySound.class, DataSerializers.BLOCK_POS);
	public EntitySound(World worldIn) {
		super(worldIn);
		setSize(0, 0);
	}

	public EntitySound(World world, ArrayList<Entity> owner) {
		super(world);
		this.ownerEntity = owner;
	}
	
	@Override
    protected void entityInit()
    {
        this.dataManager.register(blockPos, new BlockPos(1, 1, 1));
    }
	
    public BlockPos getPos()
    {
        return this.dataManager.get(blockPos);
    }

    public void setPos(BlockPos skinId)
    {
        this.dataManager.set(blockPos, skinId);
    }
    public void updatePos()
    {
    	setPos(this.getPosition());
    }
    
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(this.ownerEntity == null) return;
		
		boolean stillAlive = false;
		for(int i = 0; i < ownerEntity.size(); ++i)
		{
			Entity boss = ownerEntity.get(i);
			if(boss.isEntityAlive() && !boss.isDead)
			{
				if(pos == null)
					pos = boss.getPosition();
				
				BlockPos newPos = new BlockPos((boss.posX + pos.getX()) / 2,(
						boss.posY + pos.getY()) / 2,
						(boss.posZ + pos.getZ()) / 2);
				
				pos = newPos;
				stillAlive = true;
			}
		}
		
		System.out.println(this.getPositionVector());
		
		if(!this.world.isRemote && this.ticksExisted % 20 == 0)
		{
			this.setPositionAndRotation(pos.getX(), pos.getY(), pos.getZ(), this.prevRotationYaw, this.prevRotationPitch);
			updatePos();
		}
		
		if(this.world.isRemote && this.ticksExisted > 10)
		{
			System.out.println(this.getPos());
		}
		
		if(!stillAlive)
		{
			this.setDead();
		}
	}
	
	@Override
	public Color getBarColor() {
		return Color.PURPLE;
	}

	@Override
	public boolean DarkenSky() {
		return false;
	}

	@Override
	public Overlay getOverlay() {
		return Overlay.NOTCHED_20;
	}

	@Override
	public SoundEvent getBGM() {
		return ModSounds.zero;
	}
}
