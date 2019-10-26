package testmod.seccult.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.FX.PentagonFX;
import testmod.seccult.events.ModEventHandler;
import testmod.seccult.init.ModItems;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.ImplementationHandler;
import testmod.seccult.magick.MagickCompiler;
import testmod.seccult.magick.active.Magick;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.world.gen.SeccultBiomeRegistries;

public class ItemStaff extends ItemBase{
	public List<Magick> DamageMagick = new ArrayList<Magick>();
	public List<Magick> ControlMagick = new ArrayList<Magick>();
	
	public int Strength;
	
	public ItemStaff(String name) {
		super(name);
		
		if(name == "defence_staff")
			this.setMaxDamage(Seccult.rand.nextInt(900) + 300);
		else
			this.setMaxDamage(Seccult.rand.nextInt(100) + 20);
		
		DamageMagick.add(ModMagicks.getMagickFromName(ModMagicks.DamageMagick));
		DamageMagick.add(ModMagicks.getMagickFromName(ModMagicks.ElectroMagick));
		DamageMagick.add(ModMagicks.getMagickFromName(ModMagicks.FlameMagick));
		DamageMagick.add(ModMagicks.getMagickFromName(ModMagicks.PosionMagick));
		
		ControlMagick.add(ModMagicks.getMagickFromName(ModMagicks.CatchTheSoulMagick));
		ControlMagick.add(ModMagicks.getMagickFromName(ModMagicks.FloatingMagick));
		ControlMagick.add(ModMagicks.getMagickFromName(ModMagicks.LoseMindMagick));
		ControlMagick.add(ModMagicks.getMagickFromName(ModMagicks.WhiteAlbumMagick));
		ControlMagick.add(ModMagicks.getMagickFromName(ModMagicks.FrozenMagick));
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

		if(this.getMagickData(stack) == null && this == ModItems.DamageStaff)
		{
			int[] Imple_1 = {ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI), 0, 0};
			int[] Imple_2 = {ModMagicks.GetMagickIDByString(ImplementationHandler.FocuseI), 32, 0};
			Magick Dagick = DamageMagick.get(Seccult.rand.nextInt(DamageMagick.size()));
			
			if(Seccult.rand.nextInt(5) == 0)
			{
				int[] Imple_3 = {ModMagicks.GetMagickIDByString(ImplementationHandler.CircleI), 3, 0};
			
				this.pushMagick(stack, Magick.getMagickTag(ModMagicks.GetMagickIDByString(Dagick.getNbtName()), getStrengthRandom(), Seccult.rand.nextInt(6) + 1, Imple_1, Imple_2, Imple_3));
			}
			else
			{
				this.pushMagick(stack, Magick.getMagickTag(ModMagicks.GetMagickIDByString(Dagick.getNbtName()), getStrengthRandom(), Seccult.rand.nextInt(6) + 1, Imple_1, Imple_2));
			}
		}
		
		if(this.getMagickData(stack) == null && this == ModItems.DefenceStaff)
		{
			int[] Imple_1 = {ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI), 0, 0};

			this.pushMagick(stack, Magick.getMagickTag(ModMagicks.GetMagickIDByString(ModMagicks.ProtectMagick), getStrengthRandom(), Seccult.rand.nextInt(6) + 1, Imple_1));
		}
		
		if(this.getMagickData(stack) == null && this == ModItems.AlterationStaff)
		{
			int[] Imple_1 = {ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI), 0, 0};
			int[] Imple_2 = {ModMagicks.GetMagickIDByString(ImplementationHandler.FocuseI), 32, 0};
			Magick Aagick = ControlMagick.get(Seccult.rand.nextInt(ControlMagick.size()));
			if(Seccult.rand.nextInt(5) == 0)
			{
				int[] Imple_3 = {ModMagicks.GetMagickIDByString(ImplementationHandler.CircleI), 3, 0};
			
				this.pushMagick(stack, Magick.getMagickTag(ModMagicks.GetMagickIDByString(Aagick.getNbtName()), getStrengthRandom(), Seccult.rand.nextInt(6) + 1, Imple_1, Imple_2, Imple_3));
			}
			else
			{
				this.pushMagick(stack, Magick.getMagickTag(ModMagicks.GetMagickIDByString(Aagick.getNbtName()), getStrengthRandom(), Seccult.rand.nextInt(6) + 1, Imple_1, Imple_2));
			}
		}
	}
	
	private int getStrengthRandom()
	{
		if(Seccult.rand.nextInt(5) == 0)
			return 20;
		
		if(Seccult.rand.nextInt(3) == 0)
			return 7;

			return 3;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);
		//if(this == ModItems.DefenceStaff && this.getMagickData(player.getHeldItem(hand)) != null)
			//this.TickMagick(this.getMagickData(player.getHeldItem(hand)), player);
		particle(world, player);
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		super.onUsingTick(stack, player, count);
	}

	public void particle(World world, EntityPlayer player)
	{
		if(this.getMagickData(player.getHeldItemMainhand()) != null)
		{
			float[] color = ItemStaff.getMagickColor(player.getHeldItemMainhand());
			if(color != null && !player.world.isRemote)
			{
				for (int i = 0; i < 5; i++) {
					double tx = player.posX + (player.world.rand.nextFloat() * 0.1F);
					double ty = player.posY + (player.world.rand.nextFloat() * 0.1F) + player.height;
					double tz = player.posZ + (player.world.rand.nextFloat() * 0.1F);
					double motionX = 0.25F - 0.5 * player.world.rand.nextFloat();
					double motionY = 0.25F - 0.5 * player.world.rand.nextFloat();
					double motionZ = 0.25F - 0.5 * player.world.rand.nextFloat();

					Vec3d handVec = player.getLookVec().rotateYaw(-0.65F);
					Vec3d right = new Vec3d(tx, ty, tz).addVector(handVec.x, handVec.y, handVec.z);

			    	double[] vec = {motionX / 50, motionY / 50, motionZ / 50};
					double[] pos = {right.x, right.y, right.z};
					NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, player.world.rand.nextFloat() * 1.5F, 2));
				}
			}
		}
		
		if(this.getMagickData(player.getHeldItemOffhand()) != null)
		{
			float[] color = ItemStaff.getMagickColor(player.getHeldItemOffhand());

			if(color != null && !player.world.isRemote)
				for (int i = 0; i < 5; i++) {
					double tx = player.posX + (player.world.rand.nextFloat() * 0.1F);
					double ty = player.posY + (player.world.rand.nextFloat() * 0.1F) + player.height;
					double tz = player.posZ + (player.world.rand.nextFloat() * 0.1F);
					double motionX = 0.25F - 0.5 * player.world.rand.nextFloat();
					double motionY = 0.25F - 0.5 * player.world.rand.nextFloat();
					double motionZ = 0.25F - 0.5 * player.world.rand.nextFloat();

					Vec3d handVec = player.getLookVec().rotateYaw(0.65F);
					Vec3d right = new Vec3d(tx, ty, tz).addVector(handVec.x, handVec.y, handVec.z);
					
			    	double[] vec = {motionX / 50, motionY / 50, motionZ / 50};
					double[] pos = {right.x, right.y, right.z};
					NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, player.world.rand.nextFloat() * 1.5F, 2));
				}
		}
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if(this.getMagickData(stack) != null)
		{
			this.TickMagick(this.getMagickData(stack), entityLiving);
			if(entityLiving instanceof EntityPlayer)
			{
				if(!((EntityPlayer) entityLiving).isCreative())
				stack.attemptDamageItem(1, Item.itemRand, null);

				if(stack.getItemDamage() > stack.getMaxDamage())
					stack.shrink(1);
			}
			
		}
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}
	
	private void TickMagick(NBTTagCompound itemStack, EntityLivingBase player)
	{
		MagickCompiler compiler = new MagickCompiler();
		compiler.dontCost = true;
		compiler.pushMagickData(itemStack, player);
		ModEventHandler.playerData.getCompiler().add(compiler);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		if(this != ModItems.DefenceStaff)
		return 30;
		
		return 1;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	public NBTTagCompound getMagickData(ItemStack stack)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("Magick_Data"))
		{
			return stack.getTagCompound().getCompoundTag("Magick_Data");
		}

		return null;
	}
	
	public static float[] getMagickColor(ItemStack stack)
	{
		float[] color = null;
		if(stack.getTagCompound() == null) return null;
		NBTTagCompound magickNbt = stack.getTagCompound().getCompoundTag("Magick_Data");

		NBTTagList LoadMagick = magickNbt.getTagList("Magick", 10);

		NBTTagCompound MagickNBT = LoadMagick.getCompoundTagAt(0);

			Magick magick = ModMagicks.getMagickFromName(
			ModMagicks.GetMagickStringByID(
			MagickNBT.getInteger("Magick")));
			
		if(magick != null)
		{
			color = magick.getRGB();
		}
		
		return color;
	}
	
	public void pushMagick(ItemStack stack, NBTTagCompound magick)
	{
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		stack.getTagCompound().setTag("Magick_Data", magick);
	}
	
	public void clearMagick(ItemStack stack)
	{
		if(!stack.hasTagCompound())
			return;
		
		if(stack.getTagCompound().hasKey("Magick_Data"))
			stack.getTagCompound().removeTag("Magick_Data");
	}
}
