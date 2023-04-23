package kaptainwutax.seedcrackerX.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import kaptainwutax.seedcrackerX.SeedCracker;
import kaptainwutax.seedcrackerX.cracker.DataAddedEvent;
import kaptainwutax.seedcrackerX.cracker.HashedSeedData;
import kaptainwutax.seedcrackerX.finder.FinderQueue;
import kaptainwutax.seedcrackerX.init.ClientCommands;
import kaptainwutax.seedcrackerX.util.Log;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SChunkDataPacket;
import net.minecraft.network.play.server.SCommandListPacket;
import net.minecraft.network.play.server.SJoinGamePacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow private ClientWorld level;

    @Shadow private CommandDispatcher<ISuggestionProvider> commands;

    @Inject(method = "handleLevelChunk", at = @At(value = "TAIL"))
    private void onChunkData(SChunkDataPacket packet, CallbackInfo ci) {
        int chunkX = packet.getX();
        int chunkZ = packet.getZ();
        FinderQueue.get().onChunkData(this.level, new ChunkPos(chunkX, chunkZ));
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "<init>", at = @At("RETURN"))
    public void onInit(Minecraft p_i46300_1_, Screen p_i46300_2_, NetworkManager p_i46300_3_, GameProfile p_i46300_4_, CallbackInfo ci) {
        ClientCommands.registerCommands((CommandDispatcher<CommandSource>)(Object)this.commands);
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "handleCommands", at = @At("TAIL"))
    public void onOnCommandTree(SCommandListPacket packet, CallbackInfo ci) {
        ClientCommands.registerCommands((CommandDispatcher<CommandSource>)(Object)this.commands);
    }

    @Inject(method = "handleLogin", at = @At(value = "TAIL"))
    public void onGameJoin(SJoinGamePacket packet, CallbackInfo ci) {
        //PRE-1.16 SUPPORTED GENERATOR TYPES
        //GeneratorTypeData generatorTypeData = new GeneratorTypeData(packet.getGeneratorType());

        //Log.warn("Fetched the generator type [" +
        //        I18n.translate(generatorTypeData.getGeneratorType().getStoredName()).toUpperCase() + "].");

        //if(!SeedCracker.get().getDataStorage().addGeneratorTypeData(generatorTypeData)) {
        //    Log.error("THIS GENERATOR IS NOT SUPPORTED!");
        //    Log.error("Overworld biome search WILL NOT run.");
        //}

        HashedSeedData hashedSeedData = new HashedSeedData(packet.getSeed());

        if(SeedCracker.get().getDataStorage().addHashedSeedData(hashedSeedData, DataAddedEvent.POKE_BIOMES)) {
            Log.warn("Fetched hashed world seed [" + hashedSeedData.getHashedSeed() + "].");
        }
    }

    @Inject(method = "handleRespawn", at = @At(value = "TAIL"))
    public void onPlayerRespawn(SRespawnPacket packet, CallbackInfo ci) {
        HashedSeedData hashedSeedData = new HashedSeedData(packet.getSeed());

        if(SeedCracker.get().getDataStorage().addHashedSeedData(hashedSeedData, DataAddedEvent.POKE_BIOMES)) {
            Log.warn("Fetched hashed world seed [" + hashedSeedData.getHashedSeed() + "].");
        }
    }

}
