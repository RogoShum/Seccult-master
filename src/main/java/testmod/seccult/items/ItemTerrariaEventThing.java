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
import testmod.seccult.entity.EntityAdvanceLaser;
import testmod.seccult.entity.EntityLightingThing;
import testmod.seccult.entity.livings.EntityEoW;
import testmod.seccult.entity.livings.insect.EntityWorm;
import testmod.seccult.init.ModBlocks;
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
			iceBeam(player);
			//spawnLight(player);
			//spawnEOW(player);
			//spawnGen(world, player);
			//spawnGenBlock(world, player);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	} 
	
	public  void iceBeam(Entity e)
	{
			EntityAdvanceLaser laser = new EntityAdvanceLaser(e.world);
			laser.setOwner(e);
			laser.setDamage(5);
			laser.setPositionAndRotation(e.posX, e.posY + e.getEyeHeight(), e.posZ, e.rotationYaw, e.rotationPitch);
			laser.setWidth(0.5F);
			laser.red = 0;
			laser.blue = 1;
			laser.green = 1;
			laser.setSnow();
			if(!e.world.isRemote)
				e.world.spawnEntity(laser);
	}
	
	private void spawnGenBlock(World world, EntityPlayer player)
	{
		BlockPos position = player.getPosition().add(2, -2, 2);
		world.setBlockState(position, ModBlocks.Mush_Gen.getDefaultState());
	}
	
	private void spawnGen(World world, EntityPlayer player)
	{
		Random rand = new Random();
		BlockPos position = player.getPosition().add(2, -2, 2);
		new WorldGenCave(Blocks.AIR).generate(world, rand, position);
	}
	
	private void spawnLight(EntityPlayer player) {
		double x = player.posX;
		double y = player.posY;
		double z = player.posZ;
		
		Entity entity = null;
		 entity = EntityList.createEntityByIDFromName(LightThing, player.getEntityWorld());
	     EntityLightingThing l = (EntityLightingThing) entity;
	     l.setOwner(player);
          entity.setLocationAndAngles(x, y, z, -player.rotationYaw, -player.rotationPitch);
		if(!player.getEntityWorld().isRemote) {
			player.getEntityWorld().spawnEntity(l);
		}
	}
	
	private void spawnEOW(EntityPlayer player) {
		double x = player.posX + 40;
		double y = player.posY - 20;
		double z = player.posZ + 40;
		
		Entity entity = null;
		 entity = EntityList.createEntityByIDFromName(EOWres, player.getEntityWorld());
	        EntityEoW EOW = (EntityEoW) entity;
        	EOW.setSpawnAmount(10);
            entity.setLocationAndAngles(x, y, z, -player.rotationYaw, -player.rotationPitch);
            EOW.rotationYawHead = EOW.rotationYaw;
            EOW.renderYawOffset = EOW.rotationYaw;
            EOW.onInitialSpawn(player.getEntityWorld().getDifficultyForLocation(new BlockPos(EOW)), (IEntityLivingData)null);
		if(!player.getEntityWorld().isRemote) {
			player.getEntityWorld().spawnEntity(EOW);
		}
	}
}
