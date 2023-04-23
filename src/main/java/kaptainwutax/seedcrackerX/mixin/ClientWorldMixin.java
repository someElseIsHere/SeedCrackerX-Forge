package kaptainwutax.seedcrackerX.mixin;

import kaptainwutax.seedcrackerX.SeedCracker;
import kaptainwutax.seedcrackerX.profile.config.ConfigScreen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.Biomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {

    @Shadow public abstract DynamicRegistries registryAccess();

    @Inject(method = "disconnect", at = @At("HEAD"))
    private void disconnect(CallbackInfo ci) {
        SeedCracker.get().setActive(ConfigScreen.getConfig().isActive());
        SeedCracker.get().reset();
    }

    @Inject(method = "getUncachedNoiseBiome", at = @At("HEAD"), cancellable = true)
    private void getGeneratorStoredBiome(int x, int y, int z, CallbackInfoReturnable<Biome> ci) {
        Optional<Biome> biome = registryAccess().registry(Registry.BIOME_REGISTRY).get().getOptional(Biomes.THE_VOID);
        ci.setReturnValue(biome.orElse(BiomeRegistry.THE_VOID));
    }

}
