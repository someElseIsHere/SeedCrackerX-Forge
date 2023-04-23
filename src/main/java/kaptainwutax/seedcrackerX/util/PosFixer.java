package kaptainwutax.seedcrackerX.util;

import com.seedfinding.mccore.util.math.Vec3i;
import net.minecraft.util.math.BlockPos;

public class PosFixer {
    public static Vec3i swap(BlockPos pos){
        return new Vec3i(pos.getX(), pos.getY(), pos.getZ());
    }
}
