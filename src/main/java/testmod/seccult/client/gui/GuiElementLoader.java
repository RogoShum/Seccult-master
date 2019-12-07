package testmod.seccult.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import testmod.seccult.Seccult;
import testmod.seccult.blocks.SpellProgrammer;

public class GuiElementLoader implements IGuiHandler
{
	public static final int GUI_DEMO = 1;
	public static final int GUI_Accessories = 2;
	public static final int GUI_SpellSelect = 3;
	public static final int GUI_NoteBook = 4;

    public GuiElementLoader()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(Seccult.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        case GUI_DEMO:
            return new SpellProgrammerDemo((SpellProgrammer) world.getBlockState(new BlockPos(x, y, z)).getBlock(), player);
        case GUI_Accessories:
            return new AccessoriesContainer(player);
        case GUI_SpellSelect:
            return new SpellSelect(player);

        default:
            return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        case GUI_DEMO:
            return new SpellProgrammerGui(new SpellProgrammerDemo((SpellProgrammer) world.getBlockState(new BlockPos(x, y, z)).getBlock(), player), player);
        case GUI_Accessories:
            return new AccessoriesGui(new AccessoriesContainer(player));
        case GUI_SpellSelect:
            return new SpellSelectGui(player);
        case GUI_NoteBook:
            return new GuiNoteBook(player, player.getHeldItemMainhand());
        default:
            return null;
        }
    }
}
