package kaptainwutax.seedcrackerX.finder.structure;

import com.seedfinding.mccore.util.math.Vec3i;
import com.seedfinding.mcfeature.structure.RegionStructure;
import kaptainwutax.seedcrackerX.Features;
import kaptainwutax.seedcrackerX.SeedCracker;
import kaptainwutax.seedcrackerX.cracker.DataAddedEvent;
import kaptainwutax.seedcrackerX.finder.Finder;
import kaptainwutax.seedcrackerX.render.Color;
import kaptainwutax.seedcrackerX.render.Cube;
import kaptainwutax.seedcrackerX.render.Cuboid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MansionFinder extends Finder {

    protected static List<BlockPos> SEARCH_POSITIONS = buildSearchPositions(CHUNK_POSITIONS, pos -> {
        if((pos.getX() & 15) != 0)return true;
        if((pos.getZ() & 15) != 0)return true;
        return false;
    });

    protected List<PieceFinder> finders = new ArrayList<>();
    protected Vec3i size = new Vec3i(16, 8, 16);

    public MansionFinder(World world, ChunkPos chunkPos) {
        super(world, chunkPos);

        for(Direction direction: Direction.values()) {
            PieceFinder finder = new PieceFinder(world, chunkPos, direction, this.size);

            finder.searchPositions = SEARCH_POSITIONS;

            buildStructure(finder);
            this.finders.add(finder);
        }
    }

    @Override
    public List<BlockPos> findInChunk() {
        Biome biome = this.world.getNoiseBiome((this.chunkPos.x << 2) + 2, 0, (this.chunkPos.z << 2) + 2);

        if(!biome.getGenerationSettings().isValidStart(Structure.WOODLAND_MANSION)) {
            return new ArrayList<>();
        }

        Map<PieceFinder, List<BlockPos>> result = this.findInChunkPieces();
        List<BlockPos> combinedResult = new ArrayList<>();

        result.forEach((pieceFinder, positions) -> {
            combinedResult.addAll(positions);

            positions.forEach(pos -> {
                RegionStructure.Data<?> data = Features.MANSION.at(this.chunkPos.x, this.chunkPos.z);

                if(SeedCracker.get().getDataStorage().addBaseData(data, DataAddedEvent.POKE_STRUCTURES)) {
                    this.renderers.add(new Cuboid(pos, pieceFinder.getLayout(), new Color(102, 66, 33)));
                    this.renderers.add(new Cube(this.chunkPos.getWorldPosition().offset(0, pos.getY(), 0), new Color(102, 66, 33)));
                }
            });
        });

        return combinedResult;
    }

    public Map<PieceFinder, List<BlockPos>> findInChunkPieces() {
        Map<PieceFinder, List<BlockPos>> result = new HashMap<>();

        this.finders.forEach(pieceFinder -> {
            result.put(pieceFinder, pieceFinder.findInChunk());
        });

        return result;
    }

    public void buildStructure(PieceFinder finder) {
        BlockState air = Blocks.AIR.defaultBlockState();
        BlockState cobblestone = Blocks.COBBLESTONE.defaultBlockState();
        BlockState birchPlanks = Blocks.BIRCH_PLANKS.defaultBlockState();
        BlockState redCarpet = Blocks.RED_CARPET.defaultBlockState();
        BlockState whiteCarpet = Blocks.WHITE_CARPET.defaultBlockState();

        finder.fillWithOutline(0, 0, 0, 15, 0, 15, birchPlanks, birchPlanks, false);
        finder.fillWithOutline(0, 0, 8, 6, 0, 12, null, null, false);
        finder.fillWithOutline(0, 0, 12, 9, 0, 15, null, null, false);
        finder.fillWithOutline(15, 0, 0, 15, 0, 15, cobblestone, cobblestone, false);
        finder.addBlock(Blocks.DARK_OAK_LOG.defaultBlockState(), 15, 0, 15);
        finder.addBlock(Blocks.DARK_OAK_LOG.defaultBlockState(), 15, 0, 7);
        finder.addBlock(Blocks.DARK_OAK_LOG.defaultBlockState(), 14, 0, 7);

        finder.fillWithOutline(9, 1, 0, 9, 1, 8, whiteCarpet, whiteCarpet, false);
        finder.addBlock(whiteCarpet, 8,1, 8);
        finder.fillWithOutline(13, 1, 0, 13, 1, 8, whiteCarpet, whiteCarpet, false);
        finder.addBlock(whiteCarpet, 14,1, 8);

        finder.fillWithOutline(10, 1, 0, 12, 1, 15, redCarpet, redCarpet, false);
    }

    @Override
    public boolean isValidDimension(DimensionType dimension) {
        return this.isOverworld(dimension);
    }

    public static List<Finder> create(World world, ChunkPos chunkPos) {
        List<Finder> finders = new ArrayList<>();
        finders.add(new MansionFinder(world, chunkPos));
        return finders;
    }

}

