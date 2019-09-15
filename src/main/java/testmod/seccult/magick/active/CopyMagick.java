package testmod.seccult.magick.active;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.init.ModMagicks;

public class CopyMagick extends Magick{

	public CopyMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}

	@Override
	void toEntity() {
		for(int i = 0; i < this.strengh; i++)
		{
			Entity enti = CopyMagick.copyEntity(this.entity);
			if(enti != null) 
			{
				enti.setPositionAndRotation(this.entity.posX, this.entity.posY, this.entity.posZ, this.entity.rotationYaw, this.entity.rotationPitch);
				if(!this.entity.world.isRemote)
					this.entity.world.spawnEntity(enti);
			}
		}
	}

	public static Entity copyEntity(Entity preEntity)
	{
		NBTTagCompound entityNBT = new NBTTagCompound();
	    ResourceLocation className = EntityList.getKey(preEntity.getClass());
	  	if(className != null) 
	  	{
	  		entityNBT.setString("id", className.toString());
	  	}
	  	else if(preEntity instanceof EntityItem)
	  	{
	  	   entityNBT.setString("id", EntityList.getKey(EntityItem.class).toString());
	  	}
	  	UUID id = UUID.randomUUID();
	  	preEntity.writeToNBT(entityNBT);
	  	entityNBT.setUniqueId("UUID", id);
	  	if(!(entityNBT.getString("id").equals(""))) {
	    Entity entity = EntityList.createEntityFromNBT(entityNBT, preEntity.world);
	    if(entity != null)
	    	entity.readFromNBT(entityNBT);
	    	return  entity;
	  	}
	  	return null;
	}
	
	@Override
	void toBlock() 
	{
		if(this.entity != null)
		{
			return;
		}
		
			Block newblock = player.world.getBlockState(block).getBlock();
			TileEntity tile = player.world.getTileEntity(block);
			NBTTagCompound tag = null;
			if (tile != null) {
				NBTTagCompound newTag = new NBTTagCompound();
				tile.writeToNBT(newTag);
				
				NBTTagCompound newTag1 = new NBTTagCompound();
				newTag1.setTag("BlockEntityTag", newTag);
				
				tag = newTag1;
				
			}
		
			ItemStack item = new ItemStack(newblock);
			EntityItem newItem = new EntityItem(player.world, block.getX(), block.getY() + 1, block.getZ(), item);
			if(tag != null)
			{
				NBTTagCompound oldTag = new NBTTagCompound();
				newItem.writeToNBT(oldTag);
				
				if(oldTag.hasKey("Item"))
					oldTag.getCompoundTag("Item").setTag("tag", tag);
					
				newItem.readFromNBT(oldTag);
			}
			for(int i = 0; i < this.strengh; i++)
			{
				if(!this.player.world.isRemote)
					this.player.world.spawnEntity(newItem);
			}
	}
	
	@Override
	void MagickFX() 
	{
	}

	@Override
	public int getColor() {
		return ModMagicks.CopyMagickColor;
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
