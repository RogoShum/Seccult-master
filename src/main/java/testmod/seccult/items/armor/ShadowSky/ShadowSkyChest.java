package testmod.seccult.items.armor.ShadowSky;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.FX.FogFX;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.armor.ShadowSkyArmor;
import testmod.seccult.items.armor.MagickArmor.CoreType;

public class ShadowSkyChest extends ShadowSkyArmor{

	public ShadowSkyChest(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(0.3F, 4, 3F);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if(!hasArmorSetItem(player))
			return;
	}
	
	public static boolean hasShadowHelmet(EntityPlayer player) {
		return hasArmorSetItem(player, 0, ModItems.SHADOW_SKY_HELMET);
	}
}
