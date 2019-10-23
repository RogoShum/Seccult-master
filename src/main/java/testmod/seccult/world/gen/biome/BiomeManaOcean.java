package testmod.seccult.world.gen.biome;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import testmod.seccult.entity.livings.flying.EntityAirTentacle;
import testmod.seccult.entity.livings.water.EntityBoneShark;
import testmod.seccult.entity.livings.water.EntityFish;
import testmod.seccult.entity.livings.water.EntityJellyfish;
import testmod.seccult.entity.livings.water.EntityRockShellLeviathan;
import testmod.seccult.entity.livings.water.EntityWaterTentacle;
import testmod.seccult.world.gen.SeccultBiomeRegistries;

public class BiomeManaOcean extends BiomeOcean{

    private final BiomeManaOcean.Type type;
	
	public BiomeManaOcean(BiomeManaOcean.Type typeIn,BiomeProperties properties) {
		super(properties);
		this.type = typeIn;
		//topBlock = Blocks.WATER.getDefaultState();
		// fillerBlock = Blocks.WATER.getDefaultState();

		/*this.decorator = new BiomeDecorator()
				{
			
				};*/

		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityAirTentacle.class, 10, 2, 3));
	}
	
	@Override
	public int getWaterColorMultiplier() {
		return  -16711681;
	}
	
	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return  -16711681;
	}
	
	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		return  -16711681;
	}
	
    public static enum Type
    {
        SIDE,
        NORMAL,
        DEEP;
    }
}
