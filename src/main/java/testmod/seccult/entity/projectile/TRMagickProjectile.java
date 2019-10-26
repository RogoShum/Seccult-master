package testmod.seccult.entity.projectile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.FX.PropraFX;

public class TRMagickProjectile extends TRprojectileBase{

	public TRMagickProjectile(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		particles();
		addLight();
		if (this.isDead) {
			BlockPos pos = new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ);
			this.world.checkLightFor(EnumSkyBlock.BLOCK, pos);
			}
	}
	
	private void addLight() {
		BlockPos pos = new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ);
		BlockPos pos1 = new BlockPos((int) this.posX - 1, (int) this.posY, (int) this.posZ);
		BlockPos pos2 = new BlockPos((int) this.posX + 1, (int) this.posY, (int) this.posZ);
		BlockPos pos3 = new BlockPos((int) this.posX, (int) this.posY - 1, (int) this.posZ);
		BlockPos pos4 = new BlockPos((int) this.posX, (int) this.posY + 1, (int) this.posZ);
		BlockPos pos5 = new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ - 1);
		BlockPos pos6 = new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ + 1);
		switch(this.getRenderSkin()) {
			case 2:
				this.world.setLightFor(EnumSkyBlock.BLOCK, pos, 7);
				break;
		}
		this.world.markBlockRangeForRenderUpdate((int) this.posX,
				(int) this.posY, (int) this.posX, 12, 12, 12);
		this.world.notifyLightSet(pos);
		this.world.checkLightFor(EnumSkyBlock.BLOCK, pos1);
		this.world.checkLightFor(EnumSkyBlock.BLOCK, pos2);
		this.world.checkLightFor(EnumSkyBlock.BLOCK, pos3);
		this.world.checkLightFor(EnumSkyBlock.BLOCK, pos4);
		this.world.checkLightFor(EnumSkyBlock.BLOCK, pos5);
		this.world.checkLightFor(EnumSkyBlock.BLOCK, pos6);
	}
	
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness()
    {
        return 5.0F;
    }
	
    @SideOnly(Side.CLIENT)
	private void particles() {
		float ran = this.rand.nextFloat() - 0.5F;
		if(ran > 0.3)
			ran = 0.3F;
		else if (ran < -0.3)
			ran = -0.3F;

		 double X = this.posX + ran;
		 double Y = this.posY + ran;
		 double Z = this.posZ + ran;
		 
		if(this.getRenderSkin() == 2)
		{	
			Particle a = new PropraFX(this.world, X , Y, Z, this.lookX(), this.lookY(), this.lookZ());
			switch(this.ticksExisted % 9) {
			case 0:			
				a.setRBGColorF(1, 0.1F, 0.5F);
				Minecraft.getMinecraft().effectRenderer.addEffect(a);
			break;
			case 1:
				a.setRBGColorF(0.2F, 0.4F, 1.0F);
				Minecraft.getMinecraft().effectRenderer.addEffect(a);
			break;
			case 2:
				a.setRBGColorF(1, 0.9F, 0.1F);
				Minecraft.getMinecraft().effectRenderer.addEffect(a);
			}
			
			if(this.isStick) {
	        	
				for(int ae = 0; ae < 10; ae++) {
					if(ran > 0.3)
						ran = 0.3F;
					else if (ran < -0.3)
						ran = -0.3F;
					
					a.setRBGColorF(1, 0.1F, 0.5F);
					Minecraft.getMinecraft().effectRenderer.addEffect(a);
					a.setRBGColorF(0.2F, 0.4F, 1.0F);
					Minecraft.getMinecraft().effectRenderer.addEffect(a);
					a.setRBGColorF(1, 0.9F, 0.1F);
					Minecraft.getMinecraft().effectRenderer.addEffect(a);
				}
				
				this.setDead();
			}
		}
	}
}
