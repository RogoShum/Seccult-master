package testmod.seccult.world.gen;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionMagic{
	
	public static final DimensionType MAGIC = DimensionType.register("the_magick", "_source", 100, MagickWorldProvider.class, false);
	
	public static void register()
	{
		DimensionManager.registerDimension(100, MAGIC);
	}
}
