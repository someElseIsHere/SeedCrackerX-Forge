package kaptainwutax.seedcrackerX.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.Vec3;

public class Cuboid extends Renderer {

    private final Line[] edges = new Line[12];
    public BlockPos start;
    public Vec3i size;
    public BlockPos pos;

    public Cuboid() {
        this(BlockPos.ZERO, BlockPos.ZERO, Color.WHITE);
    }

    public Cuboid(BlockPos pos) {
        this(pos, new BlockPos(1, 1, 1), Color.WHITE);
    }

    public Cuboid(BlockPos start, BlockPos end, Color color) {
        this(start, new Vec3i(end.getX() - start.getX(), end.getY() - start.getY(), end.getZ() - start.getZ()), color);
    }

    public Cuboid(BoundingBox box, Color color) {
        this(new BlockPos(box.minX(), box.minY(), box.minZ()), new BlockPos(box.maxX(), box.maxY(), box.maxZ()), color);
    }

    public Cuboid(BlockPos start, Vec3i size, Color color) {
        this.start = start;
        this.size = size;
        this.pos = this.start.offset(this.size.getX() / 2, this.size.getY() / 2, this.size.getZ() / 2);
        this.edges[0] = new Line(toVec3d(this.start), toVec3d(this.start.offset(this.size.getX(), 0, 0)), color);
        this.edges[1] = new Line(toVec3d(this.start), toVec3d(this.start.offset(0, this.size.getY(), 0)), color);
        this.edges[2] = new Line(toVec3d(this.start), toVec3d(this.start.offset(0, 0, this.size.getZ())), color);
        this.edges[3] = new Line(toVec3d(this.start.offset(this.size.getX(), 0, this.size.getZ())), toVec3d(this.start.offset(this.size.getX(), 0, 0)), color);
        this.edges[4] = new Line(toVec3d(this.start.offset(this.size.getX(), 0, this.size.getZ())), toVec3d(this.start.offset(this.size.getX(), this.size.getY(), this.size.getZ())), color);
        this.edges[5] = new Line(toVec3d(this.start.offset(this.size.getX(), 0, this.size.getZ())), toVec3d(this.start.offset(0, 0, this.size.getZ())), color);
        this.edges[6] = new Line(toVec3d(this.start.offset(this.size.getX(), this.size.getY(), 0)), toVec3d(this.start.offset(this.size.getX(), 0, 0)), color);
        this.edges[7] = new Line(toVec3d(this.start.offset(this.size.getX(), this.size.getY(), 0)), toVec3d(this.start.offset(0, this.size.getY(), 0)), color);
        this.edges[8] = new Line(toVec3d(this.start.offset(this.size.getX(), this.size.getY(), 0)), toVec3d(this.start.offset(this.size.getX(), this.size.getY(), this.size.getZ())), color);
        this.edges[9] = new Line(toVec3d(this.start.offset(0, this.size.getY(), this.size.getZ())), toVec3d(this.start.offset(0, 0, this.size.getZ())), color);
        this.edges[10] = new Line(toVec3d(this.start.offset(0, this.size.getY(), this.size.getZ())), toVec3d(this.start.offset(0, this.size.getY(), 0)), color);
        this.edges[11] = new Line(toVec3d(this.start.offset(0, this.size.getY(), this.size.getZ())), toVec3d(this.start.offset(this.size.getX(), this.size.getY(), this.size.getZ())), color);
    }

    @Override
    public void render(PoseStack matrixStack, VertexConsumer vertexConsumer, Vec3 cameraPos) {
        if (this.start == null || this.size == null || this.edges == null) return;

        for (Line edge : this.edges) {
            if (edge == null) continue;
            edge.render(matrixStack, vertexConsumer, cameraPos);
        }
    }

    @Override
    public BlockPos getPos() {
        return pos;
    }

}
