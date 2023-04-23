package kaptainwutax.seedcrackerX.mixin;

import kaptainwutax.seedcrackerX.render.RenderQueue;
import net.minecraft.profiler.EmptyProfiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EmptyProfiler.class)
public abstract class DummyProfilerMixin {

    @Inject(method = "popPush(Ljava/lang/String;)V", at = @At("HEAD"))
    private void swap(String type, CallbackInfo ci) {
        RenderQueue.get().onRender(type);
    }

}
