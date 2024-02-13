package kaptainwutax.seedcrackerX.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.registry.Registry;

public class BiomeFixer {

	public static kaptainwutax.biomeutils.Biome swap(net.minecraft.world.biome.Biome biome) {
		return kaptainwutax.biomeutils.Biome.REGISTRY.get(Minecraft.getInstance().getConnection()
				.registryAccess().registry(Registry.BIOME_REGISTRY).get().getId(biome));
	}
}
