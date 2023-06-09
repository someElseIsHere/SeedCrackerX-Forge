package kaptainwutax.seedcrackerX.finder;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import kaptainwutax.seedcrackerX.SeedCracker;
import kaptainwutax.seedcrackerX.profile.FinderConfig;
import kaptainwutax.seedcrackerX.profile.config.ConfigScreen;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FinderQueue {

    private final static FinderQueue INSTANCE = new FinderQueue();
    public static ExecutorService SERVICE = Executors.newFixedThreadPool(5);

    public RenderType renderType = ConfigScreen.getConfig().getRENDER();
    public FinderConfig finderProfile = new FinderConfig();

    private FinderQueue() {
        this.clear();
    }

    public static FinderQueue get() {
        return INSTANCE;
    }

    public void onChunkData(World world, ChunkPos chunkPos) {
        if(!SeedCracker.get().isActive())return;

        this.finderProfile.getActiveFinderTypes().forEach(type -> {
            SERVICE.submit(() -> {
               try {
                   List<Finder> finders = type.finderBuilder.build(world, chunkPos);

                   finders.forEach(finder -> {
                       if(finder.isValidDimension(world.dimensionType())) {
                           finder.findInChunk();
                           this.finderProfile.addFinder(type, finder);
                       }
                   });
               } catch(Exception e) {
                   e.printStackTrace();
               }
            });
        });
    }

    public void renderFinders(MatrixStack matrixStack) {
        if(this.renderType == RenderType.OFF)return;

        RenderSystem.pushMatrix();
        RenderSystem.multMatrix(matrixStack.last().pose());

        GlStateManager._disableTexture();

        //Makes it render through blocks.
        if(this.renderType == RenderType.XRAY) {
            GlStateManager._disableDepthTest();
        }

        this.finderProfile.getActiveFinders().forEach(finder -> {
            if(finder.shouldRender()) {
                finder.render();
            }
        });

        RenderSystem.popMatrix();
    }

    public void clear() {
        this.renderType = ConfigScreen.getConfig().getRENDER();
        this.finderProfile = new FinderConfig();
    }

    public enum RenderType {
        OFF, ON, XRAY
    }

}
