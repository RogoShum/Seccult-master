package testmod.seccult.items;

import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.EntityEoW;
import testmod.seccult.world.gen.plant.WorldGenCave;

public class ItemTerrariaEventThing extends ItemBase{

	public ItemTerrariaEventThing(String name) {
		super(name);
		this.maxStackSize = 10;
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
			player.setActiveHand(hand);
			//spawnEOW(player);
			spawnGen(world, player);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	} 
	
	private void spawnGen(World world, EntityPlayer player)
	{
		Random rand = new Random();
		BlockPos position = player.getPosition().add(2, -2, 2);
		new WorldGenCave(Blocks.AIR).generate(world, rand, position);
	}
	
	private void spawnEOW(EntityPlayer player) {
		double x = player.posX + 40;
		double y = player.posY - 20;
		double z = player.posZ + 40;
		
		Entity entity = null;
        entity = EntityList.createEntityByIDFromName(EOWres, player.getEntityWorld());
        EntityEoW EOW = (EntityEoW) entity;
            entity.setLocationAndAngles(x, y, z, -player.rotationYaw, -player.rotationPitch);
            EOW.rotationYawHead = EOW.rotationYaw;
            EOW.renderYawOffset = EOW.rotationYaw;
            EOW.onInitialSpawn(player.getEntityWorld().getDifficultyForLocation(new BlockPos(EOW)), (IEntityLivingData)null);
		if(!player.getEntityWorld().isRemote) {
			player.getEntityWorld().spawnEntity(EOW);
		}
	}
}
