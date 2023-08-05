package kaptainwutax.seedcrackerX.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import kaptainwutax.seedcrackerX.finder.FinderQueue;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {


    @Shadow @Final private Camera mainCamera;

    @Inject(method = "renderLevel", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", args = {"ldc=hand"}))
    private void renderWorldHand(float p_109090_, long p_109091_, PoseStack p_109092_, CallbackInfo ci) {
        FinderQueue.get().renderFinders(p_109092_, mainCamera);
    }

}
