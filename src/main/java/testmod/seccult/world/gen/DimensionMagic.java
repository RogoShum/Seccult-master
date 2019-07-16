package testmod.seccult.world.gen;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionMagic{
	public static final int MAGIC_ID = 100;
	public static final DimensionType MAGIC = DimensionType.register("the_magick", "_source", MAGIC_ID, MagickWorldProvider.class, false);
	
	public static void register()
	{
		DimensionManager.registerDimension(MAGIC_ID, MAGIC);
	}
}
