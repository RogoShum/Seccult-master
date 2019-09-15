package testmod.seccult.magick.implementation;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ImplementationStoreable extends Implementation{
	protected  NBTTagCompound LoadMagick = new NBTTagCompound();
	protected  NBTTagList LoadSelect = new NBTTagList();
	
	protected float scale;
	
	public ImplementationStoreable(String nbtName) {
		super(nbtName);
	}

	@Override
	public void getTarget() {
	}

	public void setData(NBTTagCompound magick, NBTTagList select, boolean doEntity, boolean doBlock)
	{
		this.LoadMagick = magick;
		this.LoadSelect = select;
		this.doEntity = doEntity;
		this.doBlock = doBlock;
	}
	
	public void setScale(float scale)
	{
		this.scale = scale;
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return false;
	}
	
	@Override
	public boolean doMagickNeedStrength() {
		return true;
	}
}
