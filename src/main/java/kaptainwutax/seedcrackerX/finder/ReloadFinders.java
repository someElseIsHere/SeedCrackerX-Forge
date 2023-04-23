package kaptainwutax.seedcrackerX.finder;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.ChunkPos;

public class ReloadFinders {
    public Minecraft client = Minecraft.getInstance();
    public void reload() {
        int renderdistance = client.options.renderDistance;

        int playerChunkX = (int) (Math.round(client.player.getX()) >> 4);
        int playerChunkZ = (int) (Math.round(client.player.getZ()) >> 4);
        for(int i = playerChunkX - renderdistance;i < playerChunkX + renderdistance; i++) {
            for(int j = playerChunkZ - renderdistance;j < playerChunkZ + renderdistance; j++) {
                FinderQueue.get().onChunkData(client.level, new ChunkPos(i, j));
            }
        }
    }
}
