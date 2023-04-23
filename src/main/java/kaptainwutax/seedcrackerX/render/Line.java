package kaptainwutax.seedcrackerX.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class Line extends Renderer {

    public Vector3d start;
    public Vector3d end;
    public Color color;

    public Line() {
        this(Vector3d.ZERO, Vector3d.ZERO, Color.WHITE);
    }

    public Line(Vector3d start, Vector3d end) {
        this(start, end, Color.WHITE);
    }

    public Line(Vector3d start, Vector3d end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    @Override
    public void render() {
        if(this.start == null || this.end == null || this.color == null)return;

        Vector3d camPos = this.mc.gameRenderer.getMainCamera().getPosition();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();

        //This is how thick the line is.
        GlStateManager._lineWidth(2.0f);
        buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);

        //Put the start and end vertices in the buffer.
        this.putVertex(buffer, camPos, this.start);
        this.putVertex(buffer, camPos, this.end);

        //Draw it all.
        tessellator.end();
    }

    protected void putVertex(BufferBuilder buffer, Vector3d camPos, Vector3d pos) {
        buffer.vertex(
                pos.x() - camPos.x,
                pos.y() - camPos.y,
                pos.z() - camPos.z
        ).color(
                this.color.getFRed(),
                this.color.getFGreen(),
                this.color.getFBlue(),
                1.0F
        ).endVertex();
    }

    @Override
    public BlockPos getPos() {
        double x = (this.end.x() - this.start.x()) / 2 + this.start.x();
        double y = (this.end.y() - this.start.y()) / 2 + this.start.y();
        double z = (this.end.z() - this.start.z()) / 2 + this.start.z();
        return new BlockPos(x, y, z);
    }

}
