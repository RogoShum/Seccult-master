package testmod.seccult.items.Accessories;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import testmod.seccult.entity.projectile.TRprojectileBase;
import testmod.seccult.init.ModItems;
import testmod.seccult.magick.implementation.ImplementationFocused;
import testmod.seccult.magick.magickState.StateManager;

public class AMedusaHead extends ItemAccessories{
	private List<Entity> entity = null;
	public AMedusaHead(String name) {
		super(name);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if(!hasAccessories(player, itemStack)) return;
		
		Medusa(player);
	}
	
	private void Medusa(EntityPlayer player)
	{
		Entity entity = ImplementationFocused.getEntityLookedAt(player, 120);
		
		if(entity != null)
		{
			StateManager.setState(entity, StateManager.KraftWork, 1, 1);
		}
	}
}
