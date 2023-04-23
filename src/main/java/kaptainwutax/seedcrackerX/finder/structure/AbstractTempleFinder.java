package kaptainwutax.seedcrackerX.finder.structure;

import com.seedfinding.mccore.util.math.Vec3i;
import kaptainwutax.seedcrackerX.finder.Finder;
import kaptainwutax.seedcrackerX.render.Color;
import kaptainwutax.seedcrackerX.render.Cube;
import kaptainwutax.seedcrackerX.render.Cuboid;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.structure.Structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractTempleFinder extends Finder {

    protected static List<BlockPos> SEARCH_POSITIONS = buildSearchPositions(CHUNK_POSITIONS, pos -> {
        if(pos.getX() != 0)return true;
        if(pos.getY() < 63)return true;
        if(pos.getZ() != 0)return true;
        return false;
    });

    protected List<PieceFinder> finders = new ArrayList<>();
    protected final Vec3i size;

    public AbstractTempleFinder(World world, ChunkPos chunkPos, Vec3i size) {
        super(world, chunkPos);

        Direction.Plane.HORIZONTAL.forEach(direction -> {
            PieceFinder finder = new PieceFinder(world, chunkPos, direction, size);

            finder.searchPositions = SEARCH_POSITIONS;

            buildStructure(finder);
            this.finders.add(finder);
        });

        this.size = size;
    }

    public List<BlockPos> findInChunkPiece(PieceFinder pieceFinder) {
        Biome biome = this.world.getNoiseBiome((this.chunkPos.x << 2) + 2, 0, (this.chunkPos.z << 2) + 2);

        if(!biome.getGenerationSettings().isValidStart(this.getStructureFeature())) {
            return new ArrayList<>();
        }

        return pieceFinder.findInChunk();
    }

    protected abstract Structure<?> getStructureFeature();

    public void addRenderers(PieceFinder pieceFinder, BlockPos ZERO, Color color) {
        this.renderers.add(new Cuboid(ZERO, pieceFinder.getLayout(), color));
        BlockPos chunkStart = new BlockPos(ZERO.getX() & -16, ZERO.getY(), ZERO.getZ() & -16);
        this.renderers.add(new Cube(chunkStart, color));
    }

    public Map<PieceFinder, List<BlockPos>> findInChunkPieces() {
        Map<PieceFinder, List<BlockPos>> result = new HashMap<>();

        this.finders.forEach(pieceFinder -> {
            result.put(pieceFinder, this.findInChunkPiece(pieceFinder));
        });

        return result;
    }

    public abstract void buildStructure(PieceFinder finder);

    @Override
    public boolean isValidDimension(DimensionType dimension) {
        return this.isOverworld(dimension);
    }
}
