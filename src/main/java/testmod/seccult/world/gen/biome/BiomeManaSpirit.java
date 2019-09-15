package testmod.seccult.world.gen.biome;

import java.util.Random;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.livings.flying.EntityBird;
import testmod.seccult.entity.livings.insect.EntityButterfly;
import testmod.seccult.entity.livings.water.EntityFish;
import testmod.seccult.init.ModBlocks;

public class BiomeManaSpirit extends Biome {
    private final BiomeManaSpirit.Type type;

    public BiomeManaSpirit(BiomeManaSpirit.Type typeIn, Biome.BiomeProperties properties)
    {
        super(properties);
        this.type = typeIn;
        this.spawnableCreatureList.clear();
        
        this.decorator.treesPerChunk = -999;
        this.decorator.flowersPerChunk = -999;
        this.decorator.grassPerChunk = -999;
        this.decorator.mushroomsPerChunk = -999;
        
        this.fillerBlock = ModBlocks.Unkonw.getDefaultState();
        this.topBlock =ModBlocks.Unkonw.getDefaultState();
        
        if (this.type == BiomeManaSpirit.Type.NORMAL)
        {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityWolf.class, 5, 4, 4));
        	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityBird.class, 40, 4, 4));
        	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityButterfly.class, 50, 4, 4));
        	this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntityFish.class, 50, 4, 4));
        }
    }

    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand) {
    	return null;
    }

    @Override
    public void plantFlower(World world, Random rand, BlockPos pos) {
    	
    }

    @Override
    public int getWaterColorMultiplier() {
    	return 10092512;
    }
    
    public Class <? extends Biome > getBiomeClass()
    {
        return BiomeManaSpirit.class;
    }

    @Override
    public int getSkyColorByTemp(float currentTemperature) {
    	return 10092512;
    }
    
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
        //int i = super.getGrassColorAtPos(pos);
        //return (i & 14221165) + 2634762 >> 1;
            return 682628;
    }

    public static enum Type
    {
        NORMAL,
        SKYTIM,
        SUMMEREST;
    }
}
