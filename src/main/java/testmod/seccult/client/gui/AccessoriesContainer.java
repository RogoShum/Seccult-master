package testmod.seccult.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import testmod.seccult.api.accessorie.PlayerAccessorieHandler;
import testmod.seccult.api.accessorie.PlayerAccessorieHandler.PlayerAccessorie;
import testmod.seccult.items.Accessories.ItemAccessories;

public class AccessoriesContainer extends Container{

	private ItemStackHandler items = new ItemStackHandler(8);
    protected Slot[] Slot = new Slot[8];
	
	public AccessoriesContainer(EntityPlayer player) {
        super();
        
        for (int i = 0; i < 4; ++i)
        {
            this.addSlotToContainer(Slot[i] = new SlotItemHandler(items, i, 38 + i * 32, 17)
            {
                @Override
                public boolean isItemValid(ItemStack stack)
                {
                    return stack != null && stack.getItem() instanceof ItemAccessories && super.isItemValid(stack);
                }

                @Override
                public int getItemStackLimit(ItemStack stack)
                {
                    return 16;
                }
            });
        }
        
        for (int i = 0; i < 4; ++i)
        {
            this.addSlotToContainer(Slot[i + 4] = new SlotItemHandler(items, i + 4, 38 + i * 32, 52)
            {
                @Override
                public boolean isItemValid(ItemStack stack)
                {
                    return stack != null && stack.getItem() instanceof ItemAccessories && super.isItemValid(stack);
                }

                @Override
                public int getItemStackLimit(ItemStack stack)
                {
                    return 16;
                }
            });
        }
        
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 83 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 141));
        }
        
		PlayerAccessorie accessories = PlayerAccessorieHandler.get(player);
		ItemStack[] item = accessories.getAccessories();
		
		for(int i = 0; i < 8; i++)
		{
			if(item[i] != null) {
			Slot[i].putStack(item[i]);
			}
		}
	}
	
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return null;
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		ItemStack[] newstack = new ItemStack[8];
		for(int i = 0; i < 8; i++)
		{
			if(Slot[i] != null && Slot[i].getStack().getItem() instanceof ItemAccessories) {
				newstack[i] = Slot[i].getStack();
			}
		}
		PlayerAccessorie accessories = PlayerAccessorieHandler.get(playerIn);
		accessories.setAccessorie(newstack);
	}
	
}
