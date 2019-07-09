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

	public CopyMagick(String nbtName, boolean hasDetailedText) {
		super(nbtName, hasDetailedText);
	}

	@Override
	void toEntity() {
		NBTTagCompound entityNBT = new NBTTagCompound();
	    ResourceLocation className = EntityList.getKey(entity.getClass());
	  	if(className != null) 
	  	{
	  		entityNBT.setString("id", className.toString());
	  	}
	  	else if(entity instanceof EntityItem)
	  	{
	  	   entityNBT.setString("id", EntityList.getKey(EntityItem.class).toString());
	  	}
	  	
	  	entity.writeToNBT(entityNBT);
	  	entityNBT.setUniqueId("UUID", UUID.randomUUID());
	  	if(!(entityNBT.getString("id").equals(""))) {
	    Entity entity = EntityList.createEntityFromNBT(entityNBT, this.entity.world);
	    
	    if(entity != null) 
	    {
	        entity.readFromNBT(entityNBT);
	        //System.out.println(entityNBT);
	        entity.setPositionAndRotation(this.entity.posX, this.entity.posY, this.entity.posZ, this.entity.rotationYaw, this.entity.rotationPitch);
	        if(!this.entity.world.isRemote)
	        	this.entity.world.spawnEntity(entity);
	    }
	  	}
	}

	@Override
	void toBlock() 
	{
		Block newblock = player.world.getBlockState(block).getBlock();
        TileEntity tile = player.world.getTileEntity(block);
        /*NBTTagCompound tag = null;
        if (tile != null) {
        	NBTTagCompound newTag = new NBTTagCompound();
        	tile.writeToNBT(newTag);
        	tag = newTag;
        	System.out.println(tag);
        }
		
		if(tag != null)
		{
			System.out.println("QwQ");
			NBTTagCompound data = (NBTTagCompound) tag.getTag("ForgeData");
			NBTTagCompound caps = (NBTTagCompound) tag.getTag("ForgeCaps");
			newItem.getEntityData().setTag("ForgeData", data);
			newItem.getEntityData().setTag("ForgeCaps", caps);
		}*/
        ItemStack item = new ItemStack(newblock);
		EntityItem newItem = new EntityItem(player.world, block.getX(), block.getY(), block.getZ(), item);
		if(!this.player.world.isRemote)
        	this.player.world.spawnEntity(newItem);
	}

	@Override
	void MagickFX() 
	{
	}

	@Override
	public int getColor() {
		return ModMagicks.CopyMagickColor;
	}

}
