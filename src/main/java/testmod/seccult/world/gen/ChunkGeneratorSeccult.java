package testmod.seccult.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockFalling;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenLakes;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.world.gen.plant.WorldGenCave;

public class ChunkGeneratorSeccult implements IChunkGenerator {
	
	private final World world;
	private final WorldType terrainType;
	private final boolean generateStructures;
	private final Random rand;
	private NoiseGeneratorOctaves minLimitPerlinNoise, maxLimitPerlinNoise, mainPerlinNoise;
	public NoiseGeneratorOctaves scaleNoise, depthNoise;
	private NoiseGeneratorPerlin surfaceNoise;
	private double[] depthBuffer = new double[256];
	private final double[] heightMap;
	private Biome[] biomesForGeneration;
	private final float[] biomeWeights;
	double[] mainNoiseRegion, minLimitRegion, maxLimitRegion, depthRegion;
	
	public ChunkGeneratorSeccult(World world, boolean generateStructures, long seed) {
		this.world = world;
		this.generateStructures = generateStructures;
		this.terrainType = world.getWorldInfo().getTerrainType();
		this.rand = new Random(seed);
		this.minLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
		this.maxLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
		this.mainPerlinNoise = new NoiseGeneratorOctaves(this.rand, 8);
		this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);
		this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
		this.depthNoise = new NoiseGeneratorOctaves(this.rand, 16);
		this.heightMap = new double[825];
		this.biomeWeights = new float[25];
		
		for(int i = -2; i <= 2; ++i) {
			for(int j = -2; j <=2; ++j) {
				float f = 10.0F / MathHelper.sqrt((float)(i * i + j * j) + .2F);
				this.biomeWeights[i + 2 + (j + 2) * 5] = f;
			}
		}
	}
	
	public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomes) {
		this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (double)(x * 16), (double)(z * 16), 16, 16, .0625D, .0625D, 1D);
		
		for(int i = 0; i < 16; ++i) {
			for(int j = 0; j < 16; ++j) {
				Biome biome = biomes[j + i * 16];
				biome.genTerrainBlocks(this.world, this.rand, primer, x * 16 + i, z * 16 + j, this.depthBuffer[j + i * 16]);
			}
		}
	}
	
	@Override
	public Chunk generateChunk(int x, int z) {
		this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
		ChunkPrimer chunkprimer = new ChunkPrimer();
		this.setBlocksInChunk(x, z, chunkprimer);
		this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
		this.replaceBiomeBlocks(x, z, chunkprimer, biomesForGeneration);
		new MapGenCaves().generate(this.world, x, z, chunkprimer);
		Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
		byte[] abyte = chunk.getBiomeArray();
		
		for(int i = 0; i < abyte.length; ++i) abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
		chunk.generateSkylightMap();
		
		return chunk;
	}
	
	public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
		this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
		this.generateHeightmap(x * 4, 0, z * 4);
		
		for(int i = 0; i < 4; ++i) {
			int j = i * 5,
				k = (i + 1) * 5;
			
			for(int l = 0; l < 4; ++l) {
				int i1 = (j + l) * 33,
					j1 = (j + l + 1) * 33,
					k1 = (k + l) * 33,
					l1 = (k + l + 1) * 33;
				
				for(int i2 = 0; i2 < 32; ++i2) {
					double d0 = .125D,
						   d1 = this.heightMap[i1 + i2], 
						   d2 = this.heightMap[j1 + i2],
						   d3 = this.heightMap[k1 + i2],
						   d4 = this.heightMap[l1 + i2],
						   d5 = (this.heightMap[i1 + i2 + 1] - d1) * d0, 
						   d6 = (this.heightMap[j1 + i2 + 1] - d2) * d0,
						   d7 = (this.heightMap[k1 + i2 + 1] - d3) * d0,
						   d8 = (this.heightMap[l1 + i2 + 1] - d4) * d0;
					
					for(int j2 = 0; j2 < 8; ++j2) {
						double d9 = .25D,
							   d10 = d1,
							   d11 = d2,
							   d12 = (d3 - d1) * d9,
							   d13 = (d4 - d2) * d9;
						
						for(int k2 = 0; k2 < 4; ++k2) {
							double d14 = .25D,
								   d16 = (d11 - d10) * d14,
								   lvt_45_1 = d10 - d16;
							
							for(int l2 = 0; l2 < 4; ++l2) {
								if((lvt_45_1 += d16) > 0.0D)
									primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, Blocks.STONE.getDefaultState());
								else if (i2 * 8 + j2 < 63)
									primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, Blocks.WATER.getDefaultState());
							}
							
							d10 += d12;
							d11 += d13;
						}
						
						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}
	
	private void generateHeightmap(int x, int y, int z) {
		this.depthRegion = this.depthNoise.generateNoiseOctaves(this.depthRegion, x, z, 5, 5, 200F, 200F, .5F);
		float f = 684.412F,
			  f1 = 684.412F;
		this.mainNoiseRegion = this.mainPerlinNoise.generateNoiseOctaves(this.mainNoiseRegion, x, y, z, 5, 33, 5, (double)(f / 80.0F), (double)(f1 / 160.0F), (double)(f / 80.0F));
		this.minLimitRegion = this.minLimitPerlinNoise.generateNoiseOctaves(this.minLimitRegion, x, y, z, 5, 33, 5, (double)f, (double)f1, (double)f);
		this.maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseOctaves(this.maxLimitRegion, x, y, z, 5, 33, 5, (double)f, (double)f1, (double)f);
		int i = 0,
			j = 0;
		
		for(int k = 0; k < 5; ++k) {
			for(int l = 0; l < 5; ++l) {
				float f2 = 0.0F,
					  f3 = 0.0F,
					  f4 = 0.0F;
				int i1 = 2;
				Biome biome = this.biomesForGeneration[k + 2 + (l + 2) * 10];
				
				for(int j1 = -2; j1 <= 2; ++j1) {
					for(int k1 = -2; k1 <= 2; ++k1) {
						Biome biome1 = this.biomesForGeneration[k + j1 + 2 + (l + k1 + 2) * 10];
						float f5 = 0.0F + biome1.getBaseHeight() * 1.0F,
							  f6 = 0.0F + biome1.getHeightVariation() * 1.0F;
						
						if(this.terrainType == WorldType.AMPLIFIED && f5 > 0.0F) {
							f5 = 1.0F + f5 * 2.0F;
							f6 = 1.0F + f6 * 4.0F;
						}
						
						float f7 = this.biomeWeights[j1 + 2 + (k1 + 2) * 5] / (f5 + 2.0F);
						
						if(biome1.getBaseHeight() > biome.getBaseHeight()) f7 /= 2.0F;
						
						f2 += f6 * f7;
						f3 += f5 * f7;
						f4 += f7;
					}
				}
				
				f2 /= f4;
				f3 /= f4;
				f2 = f2 * .9F + .1F;
				f3 = (f3 * 4.0F - 1.0F) / 8.0F;
				double d7 = this.depthRegion[j] / 8000.0D;
				
				if(d7 < 0) d7 = -d7 * .3D;
				d7 = d7 * 3D - 2.0D;
				if(d7 < 0) {
					d7 /= 2.0D;
					
					if(d7 < -1.0D) d7 = -1D;
					
					d7 /= 1.4D; d7 /= 2.0D;
				} else {
					if(d7 > 1.0D) d7 = 1D;
					d7 /= 8.0D;
				}
				
				++j;
				double d8 = (double)f3,
					   d9 = (double)f2;
				d8 = d8 + d7 * .2D;
				d8 = d8 * 8.5D / 8.0D;
				double d0 = 8.5D + d8 * 4D;
				
				for(int l1 = 0; l1 < 33; ++l1) {
					double d1 = ((double)l1 - d0) * 12D * 128D / 256D / d9;
					
					if(d1 < 0D) d1 *= 4D;
					
					double d2 = this.minLimitRegion[i] / 512D,
						   d3 = this.maxLimitRegion[i] / 512D,
						   d4 = (this.mainNoiseRegion[i] / 10D + 1D) / 2D,
						   d5 = MathHelper.clampedLerp(d2, d3, d4) - d1;
					
					if (l1 > 29) {
						double d6 = (double)((float)(l1 - 29) / 3F);
						d5 = d5 * (1D - d6) + -10D * d6;
					}
					
					this.heightMap[i] = d5;
					++i;
				}
			}
		}
	}

	@Override
	public void populate(int x, int z) {
		BlockFalling.fallInstantly = true;
		int i = x * 16;
		int j = z * 16;
		BlockPos pos = new BlockPos(i, 0, j);
		Biome biome = this.world.getBiome(pos.add(16, 0, 16));
		this.rand.setSeed(this.world.getSeed());
		long k = this.rand.nextLong() / 2L * 2L + 1L,
			 l = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed((long)x * k + (long)z * l ^ this.world.getSeed());
		
		int i1 = this.rand.nextInt(16) + 8,
			j1 = this.rand.nextInt(256),
			k1 = this.rand.nextInt(16) + 8;
		//System.out.println("Found Mushroom Cave");
		//if(biome.getBiomeClass() == SeccultBiomeRegistries.mana_Mushroom_Cave.getBiomeClass())
		//{

		//}
		
		biome.decorate(world, rand, new BlockPos(i, 0, j));
		WorldEntitySpawner.performWorldGenSpawning(world, biome, i + 8, j + 8, 16, 16, rand);
		
		BlockFalling.fallInstantly = false;
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return this.generateStructures;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		Biome biome = this.world.getBiome(pos);
		return biome.getSpawnableList(creatureType);
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		return false;
	}
}
