package testmod.seccult.world.gen;

import com.ibm.icu.util.Calendar;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.IChunkGenerator;
import testmod.seccult.world.gen.biome.SeccultBiomeProvider;

public class MagickWorldProvider extends WorldProvider{
	@Override
	protected void init() {
		//this.biomeProvider = new BiomeProviderSingle(SeccultBiomeRegistries.mana_froest);
		try {
			this.biomeProvider = new SeccultBiomeProvider(this.world.getWorldInfo(), this.getSeed());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.hasSkyLight = true;
		this.doesWaterVaporize = false;
	}
	
	@Override
	public DimensionType getDimensionType() {
		return DimensionMagic.MAGIC;
	}

	/*@Override
	public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
		return new Vec3d(0.1803921, 0.0313725490196078, 0.329411764705);
	}*/
	
	/*@Override
	public void onWorldUpdateEntities() {
		super.onWorldUpdateEntities();
		//this.world.setWorldTime((Calendar.HOUR - 6) * 1000 + Calendar.MINUTE * (1000 / 60));
	}*/

	@Override
	public boolean isSurfaceWorld() {
		return true;
	}
	
	@Override
	public boolean canCoordinateBeSpawn(int x, int z) {
		return super.canCoordinateBeSpawn(x, z);
	}
	
	@Override
	public boolean canRespawnHere() {
		return true;
	}
	
	@Override
	public boolean doesXZShowFog(int x, int z) {
		return Math.abs(x) <= 256 && Math.abs(z) <= 256;
	}
	
	@Override
	public WorldBorder createWorldBorder() {
		return new WorldBorder();
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorSeccult(world, true, this.getSeed());
	}
}
