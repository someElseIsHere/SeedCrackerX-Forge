package kaptainwutax.seedcrackerX.mixin;

import kaptainwutax.seedcrackerX.SeedCracker;
import kaptainwutax.seedcrackerX.config.StructureSave;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class ClientWorldMixin extends Level {

    protected ClientWorldMixin(WritableLevelData p_270739_, ResourceKey<Level> p_270683_, RegistryAccess p_270200_, Holder<DimensionType> p_270240_, Supplier<ProfilerFiller> p_270692_, boolean p_270904_, boolean p_270470_, long p_270248_, int p_270466_) {
        super(p_270739_, p_270683_, p_270200_, p_270240_, p_270692_, p_270904_, p_270470_, p_270248_, p_270466_);
    }

    @Inject(method = "disconnect", at = @At("HEAD"))
    private void disconnect(CallbackInfo ci) {
        StructureSave.saveStructures(SeedCracker.get().getDataStorage().baseSeedData);
        SeedCracker.get().reset();
    }

    @Inject(method = "getUncachedNoiseBiome", at = @At("HEAD"), cancellable = true)
    private void getGeneratorStoredBiome(int p_205516_, int p_205517_, int p_205518_, CallbackInfoReturnable<Holder<Biome>> cir) {
        var biome = registryAccess().registryOrThrow(Registries.BIOME).getHolder(Biomes.THE_VOID);
        biome.ifPresent(cir::setReturnValue);
    }

}
