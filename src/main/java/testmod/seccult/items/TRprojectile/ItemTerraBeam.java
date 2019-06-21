package testmod.seccult.items.TRprojectile;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.items.ProjectileBase;

public class ItemTerraBeam extends ProjectileBase
{
	public static ResourceLocation projectile_prefix = new ResourceLocation(Seccult.MODID, "projectile");
	public ItemTerraBeam(String name) {
		super(name);
		this.addPropertyOverride(projectile_prefix, new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return getprojectileID(stack) ? stack.getTagCompound().getInteger("projectileID") : -1F;
			}
		});
	}
	
	@Nullable
	private static boolean getprojectileID(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey("projectileID");
	}
	
	public void setprojectileID(ItemStack stack, int ID) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setInteger("projectileID", ID);
	}
}