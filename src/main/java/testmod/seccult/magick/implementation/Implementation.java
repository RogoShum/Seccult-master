package testmod.seccult.magick.implementation;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public abstract class Implementation {
	private final String nbtName;
	private final String shortName;
	private Entity entity;
	private BlockPos block;
	protected boolean doEntity;
	protected boolean doBlock;
	
	public Implementation(String nbtName, String shortName) {
		this.nbtName = nbtName;
		this.shortName = shortName;
	}
	
	public abstract void getTarget(Entity player);
	
	public String getShortName() {
		return shortName;
	}

	public String getNbtName() {
		return nbtName;
	}

	protected void setEntity(Entity e)
	{
		this.entity = e;
	}
	
	protected void setBlock(BlockPos b)
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
	
	public Entity getEntity() {
		return entity;
	}
	
	public BlockPos getBlock() {
		return block;
	}
}
