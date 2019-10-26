package testmod.seccult.entity.livings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.entity.EntityUsingMagicHelper;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.ImplementationHandler;
import testmod.seccult.magick.MagickCompilerForEntity;
import testmod.seccult.magick.active.ArrowClowCardMagick;
import testmod.seccult.magick.active.AttackingMagic;
import testmod.seccult.magick.active.ControllerMagic;
import testmod.seccult.magick.active.DefenceMagic;
import testmod.seccult.magick.active.Magick;

public class EntityBase extends EntityLiving implements EntityUsingMagicHelper{
	protected float swing;
	protected boolean swingUp;	
	
	private static final DataParameter<Byte> Day = EntityDataManager.<Byte>createKey(EntityBase.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> Sleep = EntityDataManager.<Byte>createKey(EntityBase.class, DataSerializers.BYTE);
	
	public int swingLimit;
	
	protected int tick = this.ticksExisted;
	public boolean isTRboss;
	
	protected EntityLivingBase target; 
	
	protected int[] magickData = {0};
	
	protected int[] emptyMagickData = {0};
	
	protected Magick leftHandMagick;
	protected Magick rightHandMagick;
	
	public EntityBase(World worldIn) {
		super(worldIn);
	}

	
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(Day, Byte.valueOf((byte)0));
        this.dataManager.register(Sleep, Byte.valueOf((byte)0));
    }
	
	public void addMagickData(int id)
	{	
		if(hasMagick(id))
			return;
		int[] NewData = new int[magickData.length + 1];
		for(int i = 0; i < magickData.length; i++)
		{
			NewData[i] = magickData[i];
		}
		NewData[magickData.length] = id;
		magickData = NewData;
	}
    
	public boolean hasMagick(int id)
	{
		for(int i = 0; i < magickData.length; i++)
		{
			if(magickData[i] == id)
			return true;
		}
		
		return false;
	}
	
    public boolean getIsDayTime()
    {
        return (((Byte)this.dataManager.get(Day)).byteValue() & 1) != 0;
    }

    public void setIsDayTime(boolean isDay)
    {
        byte b0 = ((Byte)this.dataManager.get(Day)).byteValue();

        if (isDay)
        {
            this.dataManager.set(Day, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.dataManager.set(Day, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
    public boolean getIsSleeping()
    {
        return (((Byte)this.dataManager.get(Sleep)).byteValue() & 1) != 0;
    }

    public void setIsSleeping(boolean isSleep)
    {
        byte b0 = ((Byte)this.dataManager.get(Sleep)).byteValue();

        if (isSleep)
        {
            this.dataManager.set(Sleep, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.dataManager.set(Sleep, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
	@Override
	public void onUpdate() {
		super.onUpdate();
		doSwing();

	}
	
	public boolean canSeeTarget(Entity e)
    {
    	return	canSeeTarget(e.posX, e.posY + e.getEyeHeight(), e.posZ);
    }
	
    public boolean canSeeTarget(double pX, double pY, double pZ)
    {
    	return (this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY + this.getEyeHeight(), this.posZ), new Vec3d(pX, pY, pZ), false) == null);
    }
	
	protected void doSwing()
	{
		float motion = (float) (Math.abs(this.motionX) + Math.abs(this.motionY) + Math.abs(this.motionZ));
		
		if(swingUp)
		{
			if(motion > 0)
				swing += (motion + 1) * 10;
			else if (this.world.isDaytime())
				swing++;
			else if (this.ticksExisted % 3 ==0)
				swing++;
		}
		else
		{
			if(motion > 0)
				swing -= (motion + 1) * 10;
			else if (this.world.isDaytime())
				swing--;
			else if (this.ticksExisted % 3 ==0)
				swing--;
		}
		
		if(swing > this.swingLimit)
			swingUp = false;
		else if(swing < -this.swingLimit)
			swingUp = true;
	}
	
	protected EntityPlayer Aura(Entity hitEntity) {
       if(hitEntity instanceof EntityPlayer) {
    	   EntityPlayer player = (EntityPlayer) hitEntity;
    	   if(this.ticksExisted % 20 == 0)
  				System.out.println("it's player");
    	   return player;
       }
	return null;
	}
	
	protected void turn(int dis)
	{
		BlockPos pos = this.getPosition().add(LookX() * dis, LookY() * dis, LookZ() * dis);
		if(!this.world.isAirBlock(pos) || this.world.getBlockState(pos).getBlock() == Blocks.LAVA || this.world.getBlockState(pos.up()).getBlock() == Blocks.FIRE || this.world.getBlockState(pos).getBlock() == Blocks.FLOWING_LAVA)
		{
			this.rotationYaw += this.rand.nextInt(50);
			this.motionX += this.LookX() / 10;
			this.motionY += this.LookY() / 10;
			this.motionZ += this.LookZ() / 10;
		}
	}
	
	protected void turn(float dis)
	{
		BlockPos pos = this.getPosition().add(LookX() * dis, LookY() * dis, LookZ() * dis);
		if(this.world.getBlockState(pos).getBlock() == Blocks.LAVA || this.world.getBlockState(pos.up()).getBlock() == Blocks.FIRE || this.world.getBlockState(pos).getBlock() == Blocks.FLOWING_LAVA)
		{
			this.rotationYaw += this.rand.nextInt(50);
			this.motionX += this.LookX() / 10;
			this.motionY += this.LookY() / 10;
			this.motionZ += this.LookZ() / 10;
		}
	}
	
	public boolean getIfCloestTarget(Entity self, Entity target, Entity prevTarget)
	{
		return getIfCloestTarget(self, target.posX, target.posY, target.posZ, prevTarget.posX, prevTarget.posY, prevTarget.posZ);
	}
	
	public boolean getIfCloestTarget(Entity self, BlockPos target, BlockPos prevTarget)
	{
		return getIfCloestTarget(self, target.getX(), target.getY(), target.getZ(), prevTarget.getX(), prevTarget.getY(), prevTarget.getZ());
	}
	
	public boolean getIfCloestTarget(Entity self, double x, double y, double z, double x2, double y2, double z2)
	{
		boolean var = false;
		if(self.getDistance(x, y, z) < self.getDistance(x2, y2, z2))
			var = true;
		return var;
	}
	
	public double LookX()
	{
		return this.getLookVec().x;
	}
	
	public double LookY()
	{
		return this.getLookVec().y;
	}
	
	public double LookZ()
	{
		return this.getLookVec().z;
	}
	
	public float getSwing()
	{
		return this.swing;
	}
	
	protected void Moveto(double x, double y, double z, float speed) {
        this.motionX += (Math.signum(x - this.posX) - this.motionX) * speed;
        this.motionY += (Math.signum(y - this.posY) - this.motionY) * speed;
        this.motionZ += (Math.signum(z - this.posZ) - this.motionZ) * speed;
	}
	
	protected void AwayFrom(int x, int y, int z, double speed) {
        this.motionX -= (Math.signum(x - this.posX) - this.motionX) * speed;
        this.motionY -= (Math.signum(y - this.posY) - this.motionY) * speed / 20;
        this.motionZ -= (Math.signum(z - this.posZ) - this.motionZ) * speed;
	}
	
	protected void AwayFrom(double x, double y, double z, float speed) {
        this.motionX -= (Math.signum(x - this.posX) - this.motionX) * speed;
        this.motionY -= (Math.signum(y - this.posY) - this.motionY) * speed / 20;
        this.motionZ -= (Math.signum(z - this.posZ) - this.motionZ) * speed;
	}
	
	//copy from dragon
	protected boolean destroyBlocksInAABB(AxisAlignedBB p_70972_1_)
    {
        int i = MathHelper.floor(p_70972_1_.minX);
        int j = MathHelper.floor(p_70972_1_.minY);
        int k = MathHelper.floor(p_70972_1_.minZ);
        int l = MathHelper.floor(p_70972_1_.maxX);
        int i1 = MathHelper.floor(p_70972_1_.maxY);
        int j1 = MathHelper.floor(p_70972_1_.maxZ);
        boolean flag = false;
        boolean flag1 = false;

        for (int k1 = i; k1 <= l; ++k1)
        {
            for (int l1 = j; l1 <= i1; ++l1)
            {
                for (int i2 = k; i2 <= j1; ++i2)
                {
                    BlockPos blockpos = new BlockPos(k1, l1, i2);
                    IBlockState iblockstate = this.world.getBlockState(blockpos);
                    Block block = iblockstate.getBlock();

                    if (!block.isAir(iblockstate, this.world, blockpos) && iblockstate.getMaterial() != Material.FIRE)
                    {
                        if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this))
                        {
                            flag = true;
                        }
                        else if (block.canEntityDestroy(iblockstate, this.world, blockpos, this) && net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this, blockpos, iblockstate))
                        {
                            if (block != Blocks.COMMAND_BLOCK && block != Blocks.REPEATING_COMMAND_BLOCK && block != Blocks.CHAIN_COMMAND_BLOCK && block != Blocks.IRON_BARS && !(block instanceof BlockLiquid))
                            {
                                flag1 = this.world.setBlockToAir(blockpos) || flag1;
                            }
                            else
                            {
                                flag = true;
                            }
                        }
                        else
                        {
                            flag = true;
                        }
                    }
                }
            }
        }

        if (flag1)
        {
            double d0 = p_70972_1_.minX + (p_70972_1_.maxX - p_70972_1_.minX) * (double)this.rand.nextFloat();
            double d1 = p_70972_1_.minY + (p_70972_1_.maxY - p_70972_1_.minY) * (double)this.rand.nextFloat();
            double d2 = p_70972_1_.minZ + (p_70972_1_.maxZ - p_70972_1_.minZ) * (double)this.rand.nextFloat();
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }

        return flag;
    }

	@Override
	public void getAttackingMagic(DoYouGotMagickHand hand) 
	{
		Magick magic = randomSwitchMagic(EnumType.Attacking);
		if(magic != null)
		{
			if(hand == DoYouGotMagickHand.LeftHand)
				leftHandMagick = magic;
			
			if(hand == DoYouGotMagickHand.RightHand)
				rightHandMagick = magic;
		}
	}

	@Override
	public void getDefenceMagic(DoYouGotMagickHand hand) 
	{
		Magick magic = randomSwitchMagic(EnumType.Defence);
		if(magic != null)
			rightHandMagick = magic;
	}

	@Override
	public void getControlMagic(DoYouGotMagickHand hand) 
	{
		Magick magic = randomSwitchMagic(EnumType.Control);
		if(magic != null)
		{
			if(hand == DoYouGotMagickHand.LeftHand)
				leftHandMagick = magic;
			
			if(hand == DoYouGotMagickHand.RightHand)
				rightHandMagick = magic;
		}
	}
	
	public boolean doFocusMagick(DoYouGotMagickHand hand, Entity entity, int strength, int attribute)
	{
		if(hand == DoYouGotMagickHand.LeftHand)
		if(this.leftHandMagick != null && this.leftHandMagick instanceof AttackingMagic)
		{
			int distance = (int) this.getDistance(entity); 
			
			if(this.leftHandMagick instanceof ArrowClowCardMagick)
			{
				int MagickId = ModMagicks.GetMagickIDByString(this.leftHandMagick.getNbtName());
				int ImpleId = ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI);
				
				int[] imple = {ImpleId};
				int[] imple_strength = {0};
				int[] imple_attribute = {0};
				
				MagickCompilerForEntity compiler = new MagickCompilerForEntity(this, MagickId, strength, attribute, imple, imple_strength, imple_attribute);
				return compiler.Compile();
			}
			else
			{
				int MagickId = ModMagicks.GetMagickIDByString(this.leftHandMagick.getNbtName());
				int ImpleId = ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI);
				int ImpleId_1 = ModMagicks.GetMagickIDByString(ImplementationHandler.FocuseI);
				
				int[] imple = {ImpleId, ImpleId_1};
				int[] imple_strength = {0, distance + 3};
				int[] imple_attribute = {0, 0};
				
				MagickCompilerForEntity compiler = new MagickCompilerForEntity(this, MagickId, strength, attribute, imple, imple_strength, imple_attribute);
				return compiler.Compile();
			}
		}
		
		if(hand == DoYouGotMagickHand.RightHand)
			if(this.rightHandMagick != null && this.rightHandMagick instanceof AttackingMagic)
			{
				if(this.rightHandMagick instanceof ArrowClowCardMagick)
				{
					int MagickId = ModMagicks.GetMagickIDByString(this.leftHandMagick.getNbtName());
					int ImpleId = ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI);
					
					int[] imple = {ImpleId};
					int[] imple_strength = {0};
					int[] imple_attribute = {0};
					
					MagickCompilerForEntity compiler = new MagickCompilerForEntity(this, MagickId, strength, attribute, imple, imple_strength, imple_attribute);
					return compiler.Compile();
				}
				else
				{
				int distance = (int) this.getDistance(entity); 
				
				int MagickId = ModMagicks.GetMagickIDByString(this.rightHandMagick.getNbtName());
				int ImpleId = ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI);
				int ImpleId_1 = ModMagicks.GetMagickIDByString(ImplementationHandler.FocuseI);
				
				int[] imple = {ImpleId, ImpleId_1};
				int[] imple_strength = {0, distance + 3};
				int[] imple_attribute = {0, 0};
				
				MagickCompilerForEntity compiler = new MagickCompilerForEntity(this, MagickId, strength, attribute, imple, imple_strength, imple_attribute);
				return compiler.Compile();
				}
			}
		
		return false;
	}
	
	public boolean doControlMagick(DoYouGotMagickHand hand, Entity entity, int strength, int attribute)
	{
		if(hand == DoYouGotMagickHand.LeftHand)
		if(this.leftHandMagick != null && this.leftHandMagick instanceof ControllerMagic)
		{
			int distance = (int) this.getDistance(entity); 
			
			int MagickId = ModMagicks.GetMagickIDByString(this.leftHandMagick.getNbtName());
			int ImpleId = ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI);
			int ImpleId_1 = ModMagicks.GetMagickIDByString(ImplementationHandler.FocuseI);
			
			int[] imple = {ImpleId, ImpleId_1};
			int[] imple_strength = {0, distance + 3};
			int[] imple_attribute = {0, 0};
			
			MagickCompilerForEntity compiler = new MagickCompilerForEntity(this, MagickId, strength, attribute, imple, imple_strength, imple_attribute);
			return compiler.Compile();
		}
		
		if(hand == DoYouGotMagickHand.RightHand)
			if(this.rightHandMagick != null && this.rightHandMagick instanceof ControllerMagic)
			{
				int distance = (int) this.getDistance(entity); 
				
				int MagickId = ModMagicks.GetMagickIDByString(this.rightHandMagick.getNbtName());
				int ImpleId = ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI);
				int ImpleId_1 = ModMagicks.GetMagickIDByString(ImplementationHandler.FocuseI);
				
				int[] imple = {ImpleId, ImpleId_1};
				int[] imple_strength = {0, distance + 3};
				int[] imple_attribute = {0, 0};
				
				MagickCompilerForEntity compiler = new MagickCompilerForEntity(this, MagickId, strength, attribute, imple, imple_strength, imple_attribute);
				return compiler.Compile();
			}
		
		return false;
	}
	
	public boolean doCircleMagick(DoYouGotMagickHand hand, Entity entity, int strength, int attribute)
	{
		if(hand == DoYouGotMagickHand.LeftHand)
		if(this.leftHandMagick != null && this.leftHandMagick instanceof AttackingMagic)
		{
			int distance = (int) this.getDistance(entity); 
			
			int MagickId = ModMagicks.GetMagickIDByString(this.leftHandMagick.getNbtName());
			int ImpleId = ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI);
			int ImpleId_1 = ModMagicks.GetMagickIDByString(ImplementationHandler.FocuseI);
			int ImpleId_2 = ModMagicks.GetMagickIDByString(ImplementationHandler.CircleI);
			
			int[] imple = {ImpleId, ImpleId_1, ImpleId_2};
			int[] imple_strength = {0, distance + 3, 5};
			int[] imple_attribute = {0, 0, 0};
			
			MagickCompilerForEntity compiler = new MagickCompilerForEntity(this, MagickId, strength, attribute, imple, imple_strength, imple_attribute);
			return compiler.Compile();
		}
		
		if(hand == DoYouGotMagickHand.RightHand)
			if(this.rightHandMagick != null && this.rightHandMagick instanceof AttackingMagic)
			{
				int distance = (int) this.getDistance(entity); 
				
				int MagickId = ModMagicks.GetMagickIDByString(this.rightHandMagick.getNbtName());
				int ImpleId = ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI);
				int ImpleId_1 = ModMagicks.GetMagickIDByString(ImplementationHandler.FocuseI);
				int ImpleId_2 = ModMagicks.GetMagickIDByString(ImplementationHandler.CircleI);
				
				int[] imple = {ImpleId, ImpleId_1, ImpleId_2};
				int[] imple_strength = {0, distance + 3, 5};
				int[] imple_attribute = {0, 0, 0};
				
				MagickCompilerForEntity compiler = new MagickCompilerForEntity(this, MagickId, strength, attribute, imple, imple_strength, imple_attribute);
				return compiler.Compile();
			}
		
		return false;
	}
	
	public boolean doProtectMagick(int strength, int attribute)
	{
		if(this.rightHandMagick != null && this.rightHandMagick instanceof DefenceMagic)
		{
			int MagickId = ModMagicks.GetMagickIDByString(this.rightHandMagick.getNbtName());
			int ImpleId = ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI);
			
			int[] imple = {ImpleId};
			int[] imple_strength = {0};
			int[] imple_attribute = {0};
			
			MagickCompilerForEntity compiler = new MagickCompilerForEntity(this, MagickId, strength, attribute, imple, imple_strength, imple_attribute);
			return compiler.Compile();
		}
		
		return false;
	}
	
	public boolean doPoisionMagick(int strength, int attribute)
	{
				int MagickId = ModMagicks.GetMagickIDByString(ModMagicks.PosionMagick);
				int ImpleId = ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI);
				int ImpleId_1 = ModMagicks.GetMagickIDByString(ImplementationHandler.ProjectileI);
				
				int[] imple = {ImpleId, ImpleId_1};
				int[] imple_strength = {0, 2};
				int[] imple_attribute = {0, 0};
				
				MagickCompilerForEntity compiler = new MagickCompilerForEntity(this, MagickId, strength, attribute, imple, imple_strength, imple_attribute);
				return compiler.Compile();
	}
	
	@Override
	public Magick randomSwitchMagic(EnumType type) {
		
		if(this.magickData == null || this.magickData == this.emptyMagickData)
			return null;
		
		int randomChance = this.magickData.length * 2;
		
		for(int i = 0; i < randomChance; ++i)
		{
			int order = this.rand.nextInt(this.magickData.length);
			int id = this.magickData[order];

			Magick magick = ModMagicks.getMagickFromName(ModMagicks.GetMagickStringByID(id));

			if(type == EnumType.Attacking && magick instanceof AttackingMagic)
			{
				return magick;
			}
			
			if(type == EnumType.Defence && magick instanceof DefenceMagic)
			{
				return magick;
			}
			
			if(type == EnumType.Control && magick instanceof ControllerMagic)
			{
				return magick;
			}
		}
		
		return null;
	}
}
