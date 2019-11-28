 package testmod.seccult.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.client.FX.ThunderFX;
import testmod.seccult.entity.EntityAdvanceLaser;
import testmod.seccult.entity.EntityLightingThing;
import testmod.seccult.entity.EntityShieldFX;
import testmod.seccult.entity.SpiritManager;
import testmod.seccult.entity.livings.EntityEoW;
import testmod.seccult.entity.livings.EntityEoWHead;
import testmod.seccult.entity.livings.EntitySpirit;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.implementation.ImplementationFocused;
import testmod.seccult.magick.implementation.ImplementationProjectile;
import testmod.seccult.potions.ModPotions;
import testmod.seccult.world.gen.DimensionMagic;
import testmod.seccult.world.gen.TestTeleporter;
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

			//protection(player);
			//if(player.dimension != DimensionMagic.SPIRIT_ID)
				//player.changeDimension(DimensionMagic.SPIRIT_ID);
			
			/*Entity entity = ImplementationFocused.getEntityLookedAt(player, 120);
			if(entity != null && world.isRemote)
			{
				
			Vec3d vec = player.getLookVec();
			Particle p = new ThunderFX(world, player.posX, player.posY + player.getEyeHeight(), player.posZ, entity.posX, entity.posY + entity.height / 2, entity.posZ, player.rotationYaw, 
					player.rotationPitch, 2);
			Minecraft.getMinecraft().effectRenderer.addEffect(p);
			}
			*/
			//restoreSpirits(world, player);
			//iceBeam(player);
			//spawnLight(player);
			spawnEOW(player);
			player.getHeldItem(hand).shrink(1);
			//spawnGen(world, player);
			//spawnGenBlock(world, player);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	} 
	
	public void protection(EntityPlayer player)
	{
		EntityShieldFX shield = new EntityShieldFX(player.world);
		shield.setOwner(player, 60, 1);
		shield.setPosition(player.posX, player.posY, player.posZ);
		if(!player.world.isRemote)
			player.world.spawnEntity(shield);
	}
	
	public void restoreSpirits(World world, EntityPlayer player)
	{
		List<Entity> entity = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(5));
		for(int i = 0; i < entity.size(); i++)
		{
			if(entity.get(i) instanceof EntitySpirit)
			{
				EntitySpirit s = (EntitySpirit)entity.get(i);
				SpiritManager.restore(s);
			}
		}
	}
	
	public void iceBeam(Entity e)
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
		//new WorldGenLakes(Blocks.AIR).generate(world, rand, position);
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
		boolean nag = Seccult.rand.nextBoolean();
		boolean nag_ = Seccult.rand.nextBoolean();
		double x = nag?player.posX + 65:player.posX - 65;
		double y = player.posY - 65;
		double z = nag_?player.posZ + 65:player.posZ - 65;
		
		Entity entity = null;
		 entity = EntityList.createEntityByIDFromName(TEOWres, player.getEntityWorld());
	        EntityEoWHead EOW = (EntityEoWHead) entity;
            entity.setLocationAndAngles(x, y, z, -player.rotationYaw, -player.rotationPitch);
            EOW.rotationYawHead = EOW.rotationYaw;
            EOW.renderYawOffset = EOW.rotationYaw;
            EOW.onInitialSpawn(player.getEntityWorld().getDifficultyForLocation(new BlockPos(EOW)), (IEntityLivingData)null);
		if(!player.getEntityWorld().isRemote) {
			player.getEntityWorld().spawnEntity(EOW);
		}
	}
}
