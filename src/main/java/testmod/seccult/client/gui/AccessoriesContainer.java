package testmod.seccult.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
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
        
        for (int cc = 0; cc < 2; ++cc)
        {
        	for (int i = 0; i < 4; ++i)
        	{
        		
        		this.addSlotToContainer(Slot[i + cc * 4] = new SlotItemHandler(items, i + cc * 4, 38 + i * 32, 17 + cc * 35)
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
        }
        
        for (int i = 0; i < 9; ++i)
        {
        	this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 141));
        }
        
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 83 + i * 18));
            }
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
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}
	
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < Slot.length)
            {
                if (!this.mergeItemStack(itemstack1, Slot.length, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, Slot.length, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
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
