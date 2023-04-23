package kaptainwutax.seedcrackerX.finder.structure;

import com.seedfinding.mcfeature.structure.RegionStructure;
import kaptainwutax.seedcrackerX.Features;
import kaptainwutax.seedcrackerX.SeedCracker;
import kaptainwutax.seedcrackerX.cracker.DataAddedEvent;
import kaptainwutax.seedcrackerX.finder.BlockFinder;
import kaptainwutax.seedcrackerX.finder.Finder;
import kaptainwutax.seedcrackerX.render.Color;
import kaptainwutax.seedcrackerX.render.Cube;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;

import java.util.ArrayList;
import java.util.List;

public class BuriedTreasureFinder extends BlockFinder {

    protected static List<BlockPos> SEARCH_POSITIONS = buildSearchPositions(CHUNK_POSITIONS, pos -> {
        //Buried treasure chests always generate at (9, 9) within a chunk.
        int localX = pos.getX() & 15;
        int localZ = pos.getZ() & 15;
        if(localX != 9 || localZ != 9)return true;
        if(pos.getY() > 90)return true;
        return false;
    });

    protected static final List<BlockState> CHEST_HOLDERS = new ArrayList<>();

    static {
        CHEST_HOLDERS.add(Blocks.SANDSTONE.defaultBlockState());
        CHEST_HOLDERS.add(Blocks.STONE.defaultBlockState());
        CHEST_HOLDERS.add(Blocks.ANDESITE.defaultBlockState());
        CHEST_HOLDERS.add(Blocks.GRANITE.defaultBlockState());
        CHEST_HOLDERS.add(Blocks.DIORITE.defaultBlockState());

        //Population can turn stone, andesite, granite and diorite into ores...
        CHEST_HOLDERS.add(Blocks.COAL_ORE.defaultBlockState());
        CHEST_HOLDERS.add(Blocks.IRON_ORE.defaultBlockState());
        CHEST_HOLDERS.add(Blocks.GOLD_ORE.defaultBlockState());

        //Ocean can turn stone into gravel.
        CHEST_HOLDERS.add(Blocks.GRAVEL.defaultBlockState());
    }

    public BuriedTreasureFinder(World world, ChunkPos chunkPos) {
        super(world, chunkPos, Blocks.CHEST);
        this.searchPositions = SEARCH_POSITIONS;
    }

    @Override
    public List<BlockPos> findInChunk() {
        Biome biome = this.world.getNoiseBiome((this.chunkPos.x << 2) + 2, 0, (this.chunkPos.z << 2) + 2);
        if(!biome.getGenerationSettings().isValidStart(Structure.BURIED_TREASURE))return new ArrayList<>();

        List<BlockPos> result = super.findInChunk();

        result.removeIf(pos -> {
            BlockState chest = world.getBlockState(pos);
            if(chest.getValue(ChestBlock.WATERLOGGED))return true;

            BlockState chestHolder = world.getBlockState(pos.below());
            if(!CHEST_HOLDERS.contains(chestHolder))return true;

            return false;
        });

        result.forEach(pos -> {
            RegionStructure.Data<?> data = Features.BURIED_TREASURE.at(this.chunkPos.x, this.chunkPos.z);

            if(SeedCracker.get().getDataStorage().addBaseData(data, DataAddedEvent.POKE_STRUCTURES)) {
                this.renderers.add(new Cube(pos, new Color(255, 255, 0)));
            }
        });

        return result;
    }

    @Override
    public boolean isValidDimension(DimensionType dimension) {
        return this.isOverworld(dimension);
    }

    public static List<Finder> create(World world, ChunkPos chunkPos) {
        List<Finder> finders = new ArrayList<>();
        finders.add(new BuriedTreasureFinder(world, chunkPos));
        return finders;
    }

}
