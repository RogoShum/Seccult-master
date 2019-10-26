package testmod.seccult.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.blocks.SpellProgrammer;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.ImplementationHandler;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkPlayerMagickData;

public class SpellProgrammerDemo extends Container
{
	private SpellProgrammer programmer;

	
    public SpellProgrammerDemo(SpellProgrammer programmer,EntityPlayer player)
    {
        super();
        this.programmer = programmer;
        PlayerDataHandler.get(player).addMagickData(ModMagicks.GetMagickIDByString(ImplementationHandler.ProjectileI));
        PlayerDataHandler.get(player).addMagickData(ModMagicks.GetMagickIDByString(ImplementationHandler.SelfI));
        PlayerDataHandler.get(player).addMagickData(ModMagicks.GetMagickIDByString(ImplementationHandler.SelectBlockI));
        PlayerDataHandler.get(player).addMagickData(ModMagicks.GetMagickIDByString(ImplementationHandler.SelectEntityI));
        if(player instanceof EntityPlayerMP)
        NetworkHandler.getNetwork().sendTo(new NetworkPlayerMagickData(PlayerDataHandler.get(player).getAllMagickData(), player.getUniqueID(), 
        		PlayerDataHandler.get(player).getTrueControlAbility(), PlayerDataHandler.get(player).getTrueManaStrengh()), (EntityPlayerMP)player);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return null;
    }

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		
		return canPlayerInteract(playerIn);
	}
	
	public boolean canPlayerInteract(EntityPlayer player) {
		return !player.isDead && player.getDistanceSq((double)this.programmer.getX() + 0.5D, (double)this.programmer.getY() + 0.5D, (double)this.programmer.getZ() + 0.5D) <= 64.0D;
	}
}