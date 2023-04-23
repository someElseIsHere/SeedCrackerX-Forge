package kaptainwutax.seedcrackerX.mixin;

import net.minecraft.world.server.ChunkManager;
import net.minecraft.world.server.ServerChunkProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerChunkProvider.class)
public class ServerChunkManagerMixin {

	@Shadow @Final public ChunkManager chunkMap;

	@Inject(method = "getTickingGenerated", at = @At("HEAD"), cancellable = true)
	public void getTotalChunksLoadedCount(CallbackInfoReturnable<Integer> ci) {
		//if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
		//	int count = this.chunkMap.getTotalChunksLoadedCount();
		//	if(count < 441)ci.setReturnValue(441);
		//}
	}

}
