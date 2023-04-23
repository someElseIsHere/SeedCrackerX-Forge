package kaptainwutax.seedcrackerX.render;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public abstract class Renderer {

    protected Minecraft mc = Minecraft.getInstance();

    public abstract void render();

    public abstract BlockPos getPos();

    protected Vector3d toVec3d(BlockPos pos) {
        return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
    }

}
