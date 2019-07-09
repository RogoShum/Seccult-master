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
		if(!hasAccessories(player, itemStack)) return;
		madeInHeaven(player);
	}
	
	private void madeInHeaven(EntityPlayer player)
	{
		player.ticksExisted += 20;
		if(!player.isSneaking())
		{
		player.motionX += player.motionX * 0.80;
		player.motionY += player.motionY * 0.1;
		player.motionZ += player.motionZ * 0.80;
		}
		if(player.ticksExisted % 200 == 0 && player.getFoodStats().getFoodLevel() > -10)
		player.getFoodStats().addStats(-1, -1);
		entity = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(80));
		for(int i = 0; i < entity.size(); i++)
		{
			Entity e = entity.get(i);
			if(e instanceof EntityLivingBase)
				continue;
			e.motionX += e.motionX * 0.5;
			e.motionY += e.motionY * 0.5;
			e.motionZ += e.motionZ * 0.5;
		}
	}
}
