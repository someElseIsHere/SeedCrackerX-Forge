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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IglooFinder extends AbstractTempleFinder {

    protected static List<BlockPos> SEARCH_POSITIONS = buildSearchPositions(CHUNK_POSITIONS, pos -> {
        return false;
    });

    public IglooFinder(World world, ChunkPos chunkPos) {
        super(world, chunkPos, new Vec3i(7, 5, 8));

        //Igloos are weird.
        this.finders.forEach(finder -> {
            finder.searchPositions = SEARCH_POSITIONS;
        });
    }

    @Override
    public List<BlockPos> findInChunk() {
        Map<PieceFinder, List<BlockPos>> result = this.findInChunkPieces();
        List<BlockPos> combinedResult = new ArrayList<>();

        result.forEach((pieceFinder, positions) -> {
            combinedResult.addAll(positions);

            positions.forEach(pos -> {
                RegionStructure.Data<?> data = Features.IGLOO.at(this.chunkPos.x, this.chunkPos.z);

                if(SeedCracker.get().getDataStorage().addBaseData(data, DataAddedEvent.POKE_STRUCTURES)) {
                    this.renderers.add(new Cuboid(pos, pieceFinder.getLayout(), new Color(0, 255, 255)));
                    this.renderers.add(new Cube(pos, new Color(0, 255, 255)));
                }
            });
        });

        return combinedResult;
    }

    @Override
    public void buildStructure(PieceFinder finder) {
        BlockState air = Blocks.AIR.defaultBlockState();
        BlockState snow = Blocks.SNOW_BLOCK.defaultBlockState();
        BlockState ice = Blocks.ICE.defaultBlockState();

        for(int y = 0; y < 3; y++) {
            finder.addBlock(snow, 2, y, 0);
            finder.addBlock(snow, 2, y, 1);
            finder.addBlock(snow, 1, y, 2);
            finder.addBlock(snow, 0, y, 3);
            finder.addBlock(snow, 0, y, 4);
            finder.addBlock(ice, 0, 1, 4);
            finder.addBlock(snow, 0, y, 5);
            finder.addBlock(snow, 1, y, 6);
            finder.addBlock(snow, 2, y, 7);

            finder.addBlock(snow, 3, y, 7);

            finder.addBlock(snow, 4, y, 0);
            finder.addBlock(snow, 4, y, 1);
            finder.addBlock(snow, 5, y, 2);
            finder.addBlock(snow, 6, y, 3);
            finder.addBlock(snow, 6, y, 4);
            finder.addBlock(ice, 6, 1, 4);
            finder.addBlock(snow, 6, y, 5);
            finder.addBlock(snow, 5, y, 6);
            finder.addBlock(snow, 4, y, 7);
        }
    }

    @Override
    protected Structure<?> getStructureFeature() {
        return Structure.IGLOO;
    }

    public static List<Finder> create(World world, ChunkPos chunkPos) {
        List<Finder> finders = new ArrayList<>();
        finders.add(new IglooFinder(world, chunkPos));
        return finders;
    }

}
