package testmod.seccult.world.gen;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionMagic{
	public static final int MAGIC_ID = 12450;
	public static final DimensionType MAGIC = DimensionType.register("the_magick", "_source", MAGIC_ID, MagickWorldProvider.class, false);
	
	public static final int SPIRIT_ID = 1444;
	public static final DimensionType SPIRIT = DimensionType.register("the_magick", "_spirit", SPIRIT_ID, SpiritWorldProvider.class, false);
	
	public static void register()
	{
		DimensionManager.registerDimension(MAGIC_ID, MAGIC);
		DimensionManager.registerDimension(SPIRIT_ID, SPIRIT);
	}
}
