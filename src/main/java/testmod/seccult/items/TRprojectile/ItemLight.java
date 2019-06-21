package testmod.seccult.items.TRprojectile;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.entity.EntityLight;
import testmod.seccult.entity.EntityLmr;
import testmod.seccult.items.ProjectileBase;

public class ItemLight extends ProjectileBase
{
	public ItemLight(String name) {
		super(name);
	}
	
	@Nonnull
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
		if(player.world.isRemote) {
			EntityLmr entitylmr = new EntityLmr(player.world, player, 2, 2, 2);
			player.world.spawnEntity(entitylmr);
		}
    }
	
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if (entity instanceof EntityLight) {
			((EntityLight) entity).ChangeToTest(player);
		}
		return super.onLeftClickEntity(stack, player, entity);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
			player.setActiveHand(hand);
			if(!player.world.isRemote) {
				
				EntityLmr entitylmr = new EntityLmr(player.world, player, 2, 2, 2);
				player.world.spawnEntity(entitylmr);
				MakeLightTest(world, player);
			}
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	} 
	
	private void MakeLightTest(World world, EntityPlayer player) {
        if(world.isRemote) {
	        double range = 128D;
	        List<EntityLight> connections = world.getEntitiesWithinAABB(EntityLight.class, player.getEntityBoundingBox().grow(range, range, range));
	        Iterator<EntityLight> possibleConnections = connections.iterator();
	        while(possibleConnections.hasNext()) {
	        	EntityLight possibleConnection = (EntityLight)possibleConnections.next();
	        	possibleConnection.ChangeToTest(player);
	            	break;
	            }
	        }
        }
}