package testmod.seccult.items;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import testmod.seccult.entity.EntityBlackVelvetHell;

public class ItemBlackVelvetHell extends ItemBase{
	public ItemBlackVelvetHell(String name) {
		super(name);
		this.maxStackSize = 1;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
			player.setActiveHand(hand);
			List<Entity> list = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(80D));
		    Entity entity = null;    
		    if ((list != null) && (list.size() > 0))
		    {
		      for (int j1 = 0; j1 < list.size(); ++j1)
		      {
		        entity = (Entity)list.get(j1);
		        if (entity != null)
		        {
		          spawnBVH(player, entity);
		        }
		      }
		    }
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	private void spawnBVH(EntityPlayer player, Entity entitygoal) 
	{
		Entity entity = null;
        entity = EntityList.createEntityByIDFromName(BVH, player.getEntityWorld());
        EntityBlackVelvetHell BVH = (EntityBlackVelvetHell) entity;
        BVH.setPrisoner(entitygoal);
        BVH.setOwner(player);
            entity.setLocationAndAngles(entitygoal.posX, entitygoal.posY, entitygoal.posZ, -player.rotationYaw, -player.rotationPitch);
		if(!player.getEntityWorld().isRemote) {
			player.getEntityWorld().spawnEntity(BVH);
		}
	}
}
