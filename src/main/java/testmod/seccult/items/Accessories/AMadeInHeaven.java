package testmod.seccult.items.Accessories;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import testmod.seccult.entity.projectile.TRprojectileBase;
import testmod.seccult.init.ModItems;

public class AMadeInHeaven extends ItemAccessories{
	private List<Entity> entity = null;
	public AMadeInHeaven(String name) {
		super(name);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if(!hasAccessories(player, ModItems.A_MADE_IN_HEAVEN)) return;
		madeInHeaven(player);
		//dummyBody(player);
	}
	
	private void dummyBody(EntityPlayer player)
	{
		player.noClip = true;
	}
	
	private void madeInHeaven(EntityPlayer player)
	{
		player.ticksExisted += 20;
		player.hurtResistantTime = 0;
		if(player.getFoodStats().getFoodLevel() > -10)
		player.getFoodStats().addStats(-1, -1);
		entity = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(80));
		for(int i = 0; i < entity.size(); i++)
		{
			Entity e = entity.get(i);
			if(e instanceof EntityLivingBase)
				continue;
			if(!e.getEntityData().hasKey("MadeInHeaven") || (e.getEntityData().hasKey("MadeInHeaven") && !e.getEntityData().getBoolean("MadeInHeaven")))
			{
				Entity newEntity = NewEra(e);
				if(!(newEntity instanceof TRprojectileBase))
				player.world.spawnEntity(newEntity);
			}
		}
	}
	
	private Entity NewEra(Entity e)
	{
		if(e instanceof TRprojectileBase) 
		{
			TRprojectileBase tr = (TRprojectileBase) e;
			tr.setAttribute(tr.getDamage(), tr.getSpeed() * 3, tr.getPlayer(), tr.getID());
			tr.getEntityData().setBoolean("MadeInHeaven", true);
			return tr;
		}
		
		ResourceLocation className = EntityList.getKey(e.getClass());
		Entity entity = EntityList.createEntityByIDFromName(className, e.world);
		NBTTagCompound nbt = new NBTTagCompound();
		e.writeToNBT(nbt);
		nbt.setTag("Motion", this.newDoubleNBTList(e.motionX * 3, e.motionY * 3, e.motionZ* 3));
		nbt.setUniqueId("UUID", UUID.randomUUID());
		entity.readFromNBT(nbt);
		entity.getEntityData().setBoolean("MadeInHeaven", true);
		e.setDead();
		return entity;
	}
	
    protected NBTTagList newDoubleNBTList(double... numbers)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (double d0 : numbers)
        {
            nbttaglist.appendTag(new NBTTagDouble(d0));
        }

        return nbttaglist;
    }
}
