package kaptainwutax.seedcrackerX.util;

import com.seedfinding.mcbiome.biome.Biome;
import com.seedfinding.mcbiome.biome.Biomes;
import net.minecraft.client.Minecraft;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;

public class BiomeFixer {

	public static Biome swap(net.minecraft.world.biome.Biome biome) {
		return Biomes.REGISTRY.get(Minecraft.getInstance().getConnection()
				.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getId(biome));
	}

	public static net.minecraft.world.biome.Biome swap(Biome biome) {
		return WorldGenRegistries.BIOME.byId(biome.getId());
	}

}
