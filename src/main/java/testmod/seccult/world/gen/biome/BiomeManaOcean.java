package testmod.seccult.world.gen.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeOcean;
import testmod.seccult.entity.livings.water.EntityBoneShark;
import testmod.seccult.entity.livings.water.EntityFish;
import testmod.seccult.entity.livings.water.EntityJellyfish;
import testmod.seccult.entity.livings.water.EntityRockShellLeviathan;
import testmod.seccult.entity.livings.water.EntityWaterTentacle;

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
		this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntityFish.class, 50, 4, 10));
		this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntityJellyfish.class, 30, 4, 10));
		if(this.type == BiomeManaOcean.Type.NORMAL)
			this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntityBoneShark.class, 40, 1, 4));
		if(this.type == BiomeManaOcean.Type.DEEP)
		{
			this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntityWaterTentacle.class, 20, 1, 2));
			this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntityRockShellLeviathan.class, 20, 1, 1));
		}
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
