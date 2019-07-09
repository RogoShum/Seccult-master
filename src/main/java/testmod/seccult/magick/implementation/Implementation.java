package testmod.seccult.magick.implementation;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.ImplementationHandler;

public abstract class Implementation implements Cloneable{
	private final String nbtName;
	private List<Entity> entity;
	private List<BlockPos> block;
	protected Entity player;
	protected boolean doEntity;
	protected boolean doBlock;
	public float[] color = new float[3];
	
	protected int base;
	protected int addtion;
	
	public Implementation(String nbtName) {
		this.nbtName = nbtName;
		ImplementationHandler.addImplementation(this);
		ModMagicks.addImplementation(this);
	}
	
	public abstract void getTarget();

	public void setPlayer(Entity player)
	{
		this.player = player;
	}
	
	public String getNbtName() {
		return nbtName;
	}

	public void setAttribute(int b, int add)
	{
		this.base = b;
		this.addtion = add;
	}
	
	public void setEntity(List<Entity> e)
	{
		this.entity = e;
	}
	
	public void setBlock(List<BlockPos> b)
	{
		this.block = b;
	}
	
	public void doEntity()
	{
		doEntity = true;
	}
	
	public void doBlock()
	{
		doBlock = true;
	}

	public List<Entity> getEntity() {
		if(entity == null || entity.isEmpty())
		{
			return null;
		}
		return entity;
	}
	
	public List<BlockPos> getBlock() {
		return block;
	}
	
	public void transTarget(Implementation i)
	{
		i.setEntity(entity);
		i.setBlock(block);
	}
	
    @Override  
    public Implementation clone() {  
    	Implementation Implementation = null;  
        try{  
        	Implementation = (Implementation)super.clone();  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return Implementation;  
    }  
}
