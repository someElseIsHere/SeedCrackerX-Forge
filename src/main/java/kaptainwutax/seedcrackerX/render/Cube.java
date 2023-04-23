package kaptainwutax.seedcrackerX.render;

import com.seedfinding.mccore.util.math.Vec3i;
import net.minecraft.util.math.BlockPos;

public class Cube extends Cuboid {

    public Cube() {
        this(BlockPos.ZERO, Color.WHITE);
    }

    public Cube(BlockPos pos) {
        this(pos, Color.WHITE);
    }

    public Cube(BlockPos pos, Color color) {
        super(pos, new Vec3i(1, 1, 1), color);
    }

    @Override
    public BlockPos getPos() {
        return this.start;
    }

}
