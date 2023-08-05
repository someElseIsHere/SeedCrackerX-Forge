package kaptainwutax.seedcrackerX.finder;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import kaptainwutax.seedcrackerX.config.Config;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class FinderQueue {

    private final static FinderQueue INSTANCE = new FinderQueue();
    public static ExecutorService SERVICE = Executors.newFixedThreadPool(5);

    public FinderControl finderControl = new FinderControl();

    private FinderQueue() {
        this.clear();
    }

    public static FinderQueue get() {
        return INSTANCE;
    }

    public void onChunkData(Level world, ChunkPos chunkPos) {
        if (!Config.get().active) return;

        getActiveFinderTypes().forEach(type -> {
            SERVICE.submit(() -> {
                try {
                    List<Finder> finders = type.finderBuilder.build(world, chunkPos);

                    finders.forEach(finder -> {
                        if (finder.isValidDimension(world.dimensionType())) {
                            finder.findInChunk();
                            this.finderControl.addFinder(type, finder);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }

    public void renderFinders(PoseStack matrixStack, Camera camera) {
        if (Config.get().render == Config.RenderType.OFF) return;

        matrixStack.pushPose();

        Vec3 camPos = camera.getPosition();

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();

        if (Config.get().render == Config.RenderType.XRAY) {
            RenderSystem.disableDepthTest();
        }
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.lineWidth(2.0f);

        buffer.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        this.finderControl.getActiveFinders().forEach(finder -> {
            if (finder.shouldRender()) {
                finder.render(matrixStack, buffer, camPos);
            }
        });

        if (buffer.building()) {
            tessellator.end();
        }
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();

        matrixStack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    public List<Finder.Type> getActiveFinderTypes() {
        return Arrays.stream(Finder.Type.values())
                .filter(type -> type.enabled.get())
                .collect(Collectors.toList());
    }

    public void clear() {
        this.finderControl = new FinderControl();
    }

}
