package testmod.seccult.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import testmod.seccult.magick.MagickCompiler;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;

public class EntityIMCircle extends Entity{
	private static final DataParameter<Float> Size = EntityDataManager.<Float>createKey(EntityIMCircle.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> red = EntityDataManager.<Float>createKey(EntityIMCircle.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> green = EntityDataManager.<Float>createKey(EntityIMCircle.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> blue = EntityDataManager.<Float>createKey(EntityIMCircle.class, DataSerializers.FLOAT);
	
	private  NBTTagCompound LoadMagick = new NBTTagCompound();
	private  NBTTagList LoadSelect = new NBTTagList();
	private List<Entity> worked = new ArrayList<Entity>();
	private float particleRedIn;
	private float particleGreenIn;
	private float particleBlueIn;
	private Entity owner;
	
	private boolean doEntity;
	private boolean doBlock;
	
	private float scale;
	private boolean done;
	
	public EntityIMCircle(World worldIn) 
	{
		super(worldIn);
	}
	
	public EntityIMCircle(Entity owner, World worldIn, double x, double y, double z, float scale) 
	{
		super(worldIn);
		this.owner = owner;
		this.setPosition(x, y, z);
		this.scale = scale;
		this.setSize(0, 0);
	}

	public void setData(NBTTagCompound magick, NBTTagList select, boolean doEntity, boolean doBlock)
	{
		this.LoadMagick = magick;
		this.LoadSelect = select;
		this.doEntity = doEntity;
		this.doBlock = doBlock;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(this.world.isRemote)
			return;
		if(owner == null)
		{
			this.setDead();
			return;
		}
		
		if(done)
		scale--;
		
		if(scale < 0)
			this.setDead();
		
		double[] pos = {this.posX, this.posY, this.posZ};
		double[] vec = {0, scale / 100 * this.rand.nextDouble(), 0};
		float[] color = {particleRedIn, particleGreenIn, particleBlueIn};
		if(this.ticksExisted % 10 == 0)
        NetworkHandler.getNetwork().sendToAllAround(new NetworkEffectData(pos, vec, color, scale, 104),
        		new TargetPoint(dimension, pos[0], pos[1], pos[2], 32));
		setRender(scale);
		setRenderColor(particleRedIn, particleGreenIn, particleBlueIn);
		if(this.ticksExisted > 10)
		{
		AxisAlignedBB boundingBox = new AxisAlignedBB(posX, posY, posZ, posX, posY, posZ).grow(scale / 2, 0.1, scale / 2);
		List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, boundingBox);
		
			for(int i = 0; i < entities.size(); i++) {
				Entity hit = entities.get(i);
				if(hit instanceof EntityIMCircle)
					entities.remove(entities.get(i));
			}
				
			if(entities.isEmpty())
				return;
			
			for(int i = 0; i < entities.size(); i++) {
				Entity hit = entities.get(i);
				pushMagick(hit);
			}
			
			done = true;
		}
	}
	
	@Override
	protected void entityInit() {
		this.dataManager.register(Size, Float.valueOf(1));
		this.dataManager.register(red, Float.valueOf(1));
		this.dataManager.register(green, Float.valueOf(1));
		this.dataManager.register(blue, Float.valueOf(1));
	}

	private void pushMagick(Entity hit)
	{
		 if (!this.world.isRemote && !worked.contains(hit)) {
			 NBTTagCompound newMagickNBT = LoadMagick.copy();
			 newMagickNBT.setUniqueId("EntityHit", hit.getUniqueID());
			 MagickCompiler newMagick = new MagickCompiler();
			 newMagick.pushMagickData(newMagickNBT, LoadSelect, this.owner, this.doEntity, this.doBlock);
			 worked.add(hit);
		 }
	}
	
    public float getMySize()
    {
        return this.dataManager.get(Size).floatValue();
    }
    
    private void setRender(float size)
    {
        this.dataManager.set(Size, Float.valueOf(size));
    }
	
    public float getColorRed()
    {
        return this.dataManager.get(red).floatValue();
    }
    
    public float getColorBlue()
    {
        return this.dataManager.get(green).floatValue();
    }
    
    public float getColorGreen()
    {
        return this.dataManager.get(blue).floatValue();
    }
    
    private void setRenderColor(float particleRedIn, float particleGreenIn, float particleBlueIn)
    {
        this.dataManager.set(red, Float.valueOf(particleRedIn));
        this.dataManager.set(green, Float.valueOf(particleGreenIn));
        this.dataManager.set(blue, Float.valueOf(particleBlueIn));
    }
    
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
	}

	public void setColor(float particleRedIn, float particleGreenIn, float particleBlueIn) {
		this.particleRedIn = particleRedIn;
		this.particleGreenIn = particleGreenIn;
		this.particleBlueIn = particleBlueIn;
	}
}
