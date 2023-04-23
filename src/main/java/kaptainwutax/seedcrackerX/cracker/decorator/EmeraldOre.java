package kaptainwutax.seedcrackerX.cracker.decorator;


import com.seedfinding.mcbiome.biome.Biome;
import com.seedfinding.mcbiome.biome.Biomes;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.state.Dimension;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mccore.version.VersionMap;

public class EmeraldOre extends Decorator<Decorator.Config, EmeraldOre.Data> {

	public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
			.add(MCVersion.v1_13, new Config(4, 14))
			.add(MCVersion.v1_16, new Config(6, 14));

	public EmeraldOre(MCVersion version) {
		super(CONFIGS.getAsOf(version), version);
	}

	public EmeraldOre(Config config) {
		super(config, null);
	}

	@Override
	public String getName() {
		return "emerald_ore";
	}

	@Override
	public boolean canStart(Data data, long structureSeed, ChunkRand rand) {
		super.canStart(data, structureSeed, rand);

		int bound = rand.nextInt(6) + 3;

		for(int i = 0; i < bound; i++) {
			int x, y, z;

			if(this.getVersion().isOlderThan(MCVersion.v1_15)) {
				x = rand.nextInt(16);
				y = rand.nextInt(28) + 4;
				z = rand.nextInt(16);
			} else {
				x = rand.nextInt(16);
				z = rand.nextInt(16);
				y = rand.nextInt(28) + 4;
			}

			if(y == data.blockY && x == data.offsetX && z == data.offsetZ) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isValidDimension(Dimension dimension) {
		return dimension == Dimension.OVERWORLD;
	}

	@Override
	public Dimension getValidDimension() {
		return Dimension.OVERWORLD;
	}

	@Override
	public boolean isValidBiome(Biome biome) {
		return biome == Biomes.GRAVELLY_MOUNTAINS || biome == Biomes.MODIFIED_GRAVELLY_MOUNTAINS
				|| biome == Biomes.MOUNTAINS || biome == Biomes.WOODED_MOUNTAINS || biome == Biomes.MOUNTAIN_EDGE;
	}

	public Data at(int blockX, int blockY, int blockZ, Biome biome) {
		return new Data(this, blockX, blockY, blockZ, biome);
	}

	public static class Data extends Decorator.Data<EmeraldOre> {
		public final int offsetX;
		public final int blockY;
		public final int offsetZ;

		public Data(EmeraldOre feature, int blockX, int blockY, int blockZ, Biome biome) {
			super(feature, blockX >> 4, blockZ >> 4, biome);
			this.offsetX = blockX & 15;
			this.blockY = blockY;
			this.offsetZ = blockZ & 15;
		}
	}

}
