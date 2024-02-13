package kaptainwutax.seedcrackerX.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import kaptainwutax.seedcrackerX.render.RenderQueue;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(method = "renderLevel", at = @At("HEAD"))
    private void renderWorldStart(float delta, long time, MatrixStack matrixStack, CallbackInfo ci) {
        RenderQueue.get().setTrackRender(matrixStack);
    }

    @Inject(method = "renderLevel", at = @At("TAIL"))
    private void renderWorldFinish(float delta, long time, MatrixStack matrixStack, CallbackInfo ci) {
        RenderQueue.get().setTrackRender(null);
    }

}
