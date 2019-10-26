package testmod.seccult.items;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.lwjgl.util.glu.Disk;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.ClientProxy;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.client.gui.GuiElementLoader;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.MagickCompiler;
import testmod.seccult.magick.active.Magick;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkPlayerWandData;

public class ItemWand extends ItemBase{
	public static final ResourceLocation wand_prefix = new ResourceLocation(Seccult.MODID, "wandstyle");
	
	public NBTTagList MagickList;
	public int doCircle;
	private float scale;
	public float[] staffColor = {1,1,1};
	private static ResourceLocation sphere = new ResourceLocation("seccult:textures/spell/sphere.png");
	private static ResourceLocation circle = new ResourceLocation("seccult:textures/spell/circle.png");
	private static ResourceLocation moon = new ResourceLocation("seccult:textures/spell/moon.png");
	private static ResourceLocation ball = new ResourceLocation("seccult:textures/spell/ball.png");
	private static ResourceLocation star = new ResourceLocation("seccult:textures/spell/star.png");
	private static ResourceLocation square = new ResourceLocation("seccult:textures/spell/square.png");
	
	public ItemWand(String name) {
		super(name);
		this.maxStackSize = 1;
		this.setMaxDamage(0);
		this.addPropertyOverride(wand_prefix, new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return getWandS(stack) ? stack.getTagCompound().getInteger("WandStyle") : -1F;
			}
		});
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
			ItemStack stack = player.getHeldItem(hand);
			player.setActiveHand(hand);

	        if (!world.isRemote && MagickList != null)
	        {
	        	int slot = stack.getTagCompound().getInteger("Slot");
	            if (player.isSneaking())
	            {
	            		stack.getTagCompound().setInteger("Slot", slot + 1);
	            	if(slot > MagickList.tagCount() - 2)
	            		stack.getTagCompound().setInteger("Slot", 0);
	            }
	            else
	            {
	            	MagickCompiler ma = new MagickCompiler();
	            	ma.pushMagickData(MagickList.getCompoundTagAt(slot), player);
	            	this.staffColor = ma.getColor();
	            }
	            
	            double[] pos = new double[3], vec = new double[3];
				pos[0] = player.posX - player.width / 2;
				pos[1] = player.posY + player.height / 2;
				pos[2] = player.posZ - player.width / 2;
				doCircle = 0;
	            NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, this.staffColor, 0.3F, 100));
	            
	            return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand));
	        }

	        return ActionResult.newResult(EnumActionResult.FAIL, player.getHeldItem(hand));
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 7200;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		super.onUsingTick(stack, player, count);
		 doCircle++;
		 
		 if(!player.world.isRemote && doCircle > 20 && player.ticksExisted % 2 == 0)
		 {
			 if (this.MagickList != null)
		     {
				 int slot = player.getHeldItemMainhand().getTagCompound().getInteger("Slot");
				 MagickCompiler ma = new MagickCompiler();
				 ma.pushMagickData(this.MagickList.getCompoundTagAt(slot), player);
				 this.staffColor = ma.getColor();
		     }
		        
			 double[] pos = new double[3], vec = new double[3];
			 pos[0] = player.posX - player.width / 2;
			 pos[1] = player.posY + player.height / 2;
			 pos[2] = player.posZ - player.width / 2;
			 NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, this.staffColor, 0.3F, 100));
		 }
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
		doCircle = 0;
	}
	
	@SideOnly(Side.CLIENT)
	public void render(EntityPlayer player, float partTicks) {
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partTicks - renderManager.viewerPosX;
		double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partTicks - renderManager.viewerPosY;
		double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partTicks - renderManager.viewerPosZ;

		float scale = this.scale += 0.01F;
		if(scale > 1.5F)
			scale = 1.5F;

		if(player.isHandActive() && doCircle > 10)
			renderSpellCircle(doCircle + partTicks, scale, x, y, z, staffColor);
		else
			this.scale = 0;
	}


	public static void renderSpellCircle(float time, float s1, double x, double y, double z, float[] colorVal) {
		TextureManager tex = Minecraft.getMinecraft().getTextureManager();
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y + 0.01, z);
		GlStateManager.rotate(90F, 1F, 0F, 0F);
	    GlStateManager.rotate(time, 0, 0, -1);
		GlStateManager.disableCull();
		GlStateManager.disableLighting();

		float red = colorVal[0];
        float green = colorVal[1];
        float blue = colorVal[2];
        
		GlStateManager.pushMatrix();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
	    GlStateManager.color(red, green, blue);
	    GlStateManager.translate(x, y + 0.01, z);
	    tex.bindTexture(circle);
	    Disk disk = new Disk();
	    disk.setTextureFlag(true);
	    disk.draw(0, s1 * 1.5F, 16, 16);    
	    GlStateManager.popMatrix();
        
		/*GlStateManager.pushMatrix();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
	    GlStateManager.color(red, green, blue);
	    GlStateManager.translate(x, y + 0.01, z);
	    tex.bindTexture(sphere);
	    Disk disk = new Disk();
	    disk.setTextureFlag(true);
	    disk.draw(0, s1 * 1.5F, 16, 16);    
	    GlStateManager.popMatrix();
	    
		GlStateManager.pushMatrix();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
	    GlStateManager.color(red, green, blue);
	    GlStateManager.translate(x - s1 * 1.2F, y + 0.01, z);
	    tex.bindTexture(moon);
	    disk.draw(0, s1, 16, 16);
	    GlStateManager.popMatrix();
	    
		GlStateManager.pushMatrix();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
	    GlStateManager.color(red, green, blue);
	    GlStateManager.translate(x + s1 * 1.2F, y + 0.01, z);
	    tex.bindTexture(ball);
	    disk.draw(0, s1, 16, 16);
	    GlStateManager.popMatrix();
	    
		GlStateManager.pushMatrix();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
	    GlStateManager.color(red, green, blue);
	    GlStateManager.translate(x, y + 0.01, z);
	    tex.bindTexture(star);
	    disk.draw(0, s1, 16, 16);
	    GlStateManager.popMatrix();
	    
	    for(int i = 0; i < 3; ++i)
	    {
	    	GlStateManager.pushMatrix();
	    	GlStateManager.rotate(60 * i, 0, 0, 1);
	    	GlStateManager.enableBlend();
	    	GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
	    	GlStateManager.color(red, green, blue);
	    	GlStateManager.translate(x, y + 0.01, z);
	    	tex.bindTexture(square);
	    	disk.draw(0, s1 * 1.1F, 16, 16);
	    	GlStateManager.popMatrix();
	    }
		*/
		GlStateManager.enableCull();
		GlStateManager.popMatrix();
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		boolean hasUUID = stack.getTagCompound().hasKey("UUIDLeast") && stack.getTagCompound().hasKey("UUIDMost");
		UUID id = null;
		if(hasUUID)
		{
			id = new UUID(stack.getTagCompound().getLong("UUIDMost"), stack.getTagCompound().getLong("UUIDLeast"));
		}
		
		if(!worldIn.isRemote && entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			
			PlayerData data = PlayerDataHandler.get(player);
			MagickList = data.getAllMagick();
			
			if(MagickList != null && !MagickList.hasNoTags()) {
			NBTTagCompound MagickNBT = MagickList.getCompoundTagAt(stack.getTagCompound().getInteger("Slot"));
			NBTTagList LoadMagick = MagickNBT.getTagList("Magick", 10);
			NBTTagCompound Magicknbt = LoadMagick.getCompoundTagAt(0);
			
			Magick magick = ModMagicks.getMagickFromName(ModMagicks.GetMagickStringByID(Magicknbt.getInteger("Magick")));
			if(magick!= null)
			{
				int color = magick.getColor();
				stack.getTagCompound().setInteger("MagickColor", color);
			}

			}
			else
			{
				stack.getTagCompound().setInteger("MagickColor", 0);
			}
		}
		if(entityIn instanceof EntityPlayer && (!hasUUID || (hasUUID && id != entityIn.getUniqueID())))
		{
			
			EntityPlayer player = (EntityPlayer) entityIn;
			
			PlayerData data = PlayerDataHandler.get(player);
			if(!worldIn.isRemote)
			NetworkHandler.getNetwork().sendTo(new NetworkPlayerWandData(data.getColor2(), data.getColor3(), data.getColor4(), data.getWandStyle()), (EntityPlayerMP) player); 
			setWandStyle(stack, data.getColor2(), data.getColor3(), data.getColor4());
			setWandS(stack, data.getWandStyle());
			setLastPlayerUUID(stack, player.getUniqueID());
			if(!stack.getTagCompound().hasKey("Slot"))
				stack.getTagCompound().setInteger("Slot", 0);
		}
	}

    protected NBTTagList newFloatNBTList(float... numbers)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (float f : numbers)
        {
            nbttaglist.appendTag(new NBTTagFloat(f));
        }

        return nbttaglist;
    }
	
	private void setWandStyle(ItemStack stack, int color2, int color3, int color4)
	{
		NBTTagList list = new NBTTagList();
		
		for(int i = 2; i < 5; i++) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("number", i);
			switch(i)
			{
				case 2:
					nbt.setInteger("color", color2);
					break;
				
				case 3:
					nbt.setInteger("color", color3);
					break;
					
				case 4:
					nbt.setInteger("color", color4);
					break;
			}
			list.appendTag(nbt);
		}
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setTag("WandColor", list);
	}
	
	public static int getMagickColor(ItemStack stack)
	{
		if(!stack.hasTagCompound())
			return 1;
		NBTTagCompound nbt = stack.getTagCompound();
		if(!nbt.hasKey("MagickColor"))
			return 1;
		return nbt.getInteger("MagickColor");
	}
	
	public static int getWandStyle(ItemStack stack, int tintIndex)
	{
		if(stack.getTagCompound() == null)
			return 0;
		NBTTagList list = stack.getTagCompound().getTagList("WandColor", 10);
		if(list != null)
		{
			for(int i = 0; i < list.tagCount(); i++)
			{
				if(list.getCompoundTagAt(i).getInteger("number") == tintIndex) {
					return list.getCompoundTagAt(i).getInteger("color");
				}
			}
		}
		return 0;
	}
	
	public static UUID getLastPlayerUUID(ItemStack stack)
	{
		if(stack.getTagCompound() == null)
			return null;
		if(stack.getTagCompound().hasKey("UUIDLeast"))
		{
			long least = stack.getTagCompound().getLong("UUIDLeast");
			long most = stack.getTagCompound().getLong("UUIDMost");
			UUID id = new UUID(most, least);
			return id;
		}
		
		return null;
	}
	
	public void setLastPlayerUUID(ItemStack stack, UUID ID) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setLong("UUIDLeast", ID.getLeastSignificantBits());
		stack.getTagCompound().setLong("UUIDMost", ID.getMostSignificantBits());
	}
	
	@Nullable
	private static boolean getWandS(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey("WandStyle");
	}
	
	public void setWandS(ItemStack stack, int ID) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setInteger("WandStyle", ID);
	}
}
