package kaptainwutax.seedcrackerX.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public abstract class Renderer {

    protected Minecraft mc = Minecraft.getInstance();

    public abstract void render(PoseStack matrixStack, VertexConsumer vertexConsumer, Vec3 cameraPos);

    public abstract BlockPos getPos();

    protected Vec3 toVec3d(BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

}
