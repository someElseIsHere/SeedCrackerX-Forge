package kaptainwutax.seedcrackerX.finder.structure;

import com.seedfinding.mccore.util.math.Vec3i;
import com.seedfinding.mcfeature.structure.RegionStructure;
import kaptainwutax.seedcrackerX.Features;
import kaptainwutax.seedcrackerX.SeedCracker;
import kaptainwutax.seedcrackerX.cracker.DataAddedEvent;
import kaptainwutax.seedcrackerX.finder.Finder;
import kaptainwutax.seedcrackerX.render.Color;
import net.minecraft.block.*;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.RedstoneSide;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JunglePyramidFinder extends AbstractTempleFinder {

    public JunglePyramidFinder(World world, ChunkPos chunkPos) {
        super(world, chunkPos, new Vec3i(12, 10, 15));
    }

    @Override
    public List<BlockPos> findInChunk() {
        Map<PieceFinder, List<BlockPos>> result = super.findInChunkPieces();
        List<BlockPos> combinedResult = new ArrayList<>();

        result.forEach((pieceFinder, positions) -> {
            combinedResult.addAll(positions);

            positions.forEach(pos -> {
                RegionStructure.Data<?> data = Features.JUNGLE_PYRAMID.at(this.chunkPos.x, this.chunkPos.z);

                if(SeedCracker.get().getDataStorage().addBaseData(data, DataAddedEvent.POKE_STRUCTURES)) {
                    this.addRenderers(pieceFinder, pos, new Color(255, 0, 255));
                }
            });
        });

        return combinedResult;
    }

    @Override
    protected Structure<?> getStructureFeature() {
        return Structure.JUNGLE_TEMPLE;
    }

    @Override
    public void buildStructure(PieceFinder finder) {
        BlockState eastStairs = Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue(StairsBlock.FACING, Direction.EAST);
        BlockState westStairs = Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue(StairsBlock.FACING, Direction.WEST);
        BlockState southStairs = Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue(StairsBlock.FACING, Direction.SOUTH);
        BlockState northStairs = Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue(StairsBlock.FACING, Direction.NORTH);
        finder.addBlock(northStairs, 5, 9, 6);
        finder.addBlock(northStairs, 6, 9, 6);
        finder.addBlock(southStairs, 5, 9, 8);
        finder.addBlock(southStairs, 6, 9, 8);
        finder.addBlock(northStairs, 4, 0, 0);
        finder.addBlock(northStairs, 5, 0, 0);
        finder.addBlock(northStairs, 6, 0, 0);
        finder.addBlock(northStairs, 7, 0, 0);
        finder.addBlock(northStairs, 4, 1, 8);
        finder.addBlock(northStairs, 4, 2, 9);
        finder.addBlock(northStairs, 4, 3, 10);
        finder.addBlock(northStairs, 7, 1, 8);
        finder.addBlock(northStairs, 7, 2, 9);
        finder.addBlock(northStairs, 7, 3, 10);
        finder.addBlock(eastStairs, 4, 4, 5);
        finder.addBlock(westStairs, 7, 4, 5);
        finder.addBlock((Blocks.TRIPWIRE_HOOK.defaultBlockState().setValue(TripWireHookBlock.FACING, Direction.EAST)).setValue(TripWireHookBlock.ATTACHED, true), 1, -3, 8);
        finder.addBlock((Blocks.TRIPWIRE_HOOK.defaultBlockState().setValue(TripWireHookBlock.FACING, Direction.WEST)).setValue(TripWireHookBlock.ATTACHED, true), 4, -3, 8);
        finder.addBlock(((Blocks.TRIPWIRE.defaultBlockState().setValue(TripWireBlock.EAST, true)).setValue(TripWireBlock.WEST, true)).setValue(TripWireBlock.ATTACHED, true), 2, -3, 8);
        finder.addBlock(((Blocks.TRIPWIRE.defaultBlockState().setValue(TripWireBlock.EAST, true)).setValue(TripWireBlock.WEST, true)).setValue(TripWireBlock.ATTACHED, true), 3, -3, 8);
        BlockState blockState_5 = (Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedstoneWireBlock.NORTH, RedstoneSide.SIDE)).setValue(RedstoneWireBlock.SOUTH, RedstoneSide.SIDE);
        finder.addBlock(Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedstoneWireBlock.SOUTH, RedstoneSide.SIDE), 5, -3, 7);
        finder.addBlock(blockState_5, 5, -3, 6);
        finder.addBlock(blockState_5, 5, -3, 5);
        finder.addBlock(blockState_5, 5, -3, 4);
        finder.addBlock(blockState_5, 5, -3, 3);
        finder.addBlock(blockState_5, 5, -3, 2);
        finder.addBlock((Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedstoneWireBlock.NORTH, RedstoneSide.SIDE)).setValue(RedstoneWireBlock.EAST, RedstoneSide.SIDE), 5, -3, 1);
        finder.addBlock(Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedstoneWireBlock.EAST, RedstoneSide.SIDE), 4, -3, 1);
        finder.addBlock(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 3, -3, 1);

        finder.addBlock(Blocks.VINE.defaultBlockState().setValue(VineBlock.SOUTH, true), 3, -2, 2);
        finder.addBlock((Blocks.TRIPWIRE_HOOK.defaultBlockState().setValue(TripWireHookBlock.FACING, Direction.NORTH)).setValue(TripWireHookBlock.ATTACHED, true), 7, -3, 1);
        finder.addBlock((Blocks.TRIPWIRE_HOOK.defaultBlockState().setValue(TripWireHookBlock.FACING, Direction.SOUTH)).setValue(TripWireHookBlock.ATTACHED, true), 7, -3, 5);
        finder.addBlock(((Blocks.TRIPWIRE.defaultBlockState().setValue(TripWireBlock.NORTH, true)).setValue(TripWireBlock.SOUTH, true)).setValue(TripWireBlock.ATTACHED, true), 7, -3, 2);
        finder.addBlock(((Blocks.TRIPWIRE.defaultBlockState().setValue(TripWireBlock.NORTH, true)).setValue(TripWireBlock.SOUTH, true)).setValue(TripWireBlock.ATTACHED, true), 7, -3, 3);
        finder.addBlock(((Blocks.TRIPWIRE.defaultBlockState().setValue(TripWireBlock.NORTH, true)).setValue(TripWireBlock.SOUTH, true)).setValue(TripWireBlock.ATTACHED, true), 7, -3, 4);
        finder.addBlock(Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedstoneWireBlock.EAST, RedstoneSide.SIDE), 8, -3, 6);
        finder.addBlock((Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedstoneWireBlock.WEST, RedstoneSide.SIDE)).setValue(RedstoneWireBlock.SOUTH, RedstoneSide.SIDE), 9, -3, 6);
        finder.addBlock((Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedstoneWireBlock.NORTH, RedstoneSide.SIDE)).setValue(RedstoneWireBlock.SOUTH, RedstoneSide.UP), 9, -3, 5);
        finder.addBlock(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 9, -3, 4);
        finder.addBlock(Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedstoneWireBlock.NORTH, RedstoneSide.SIDE), 9, -2, 4);

        finder.addBlock(Blocks.VINE.defaultBlockState().setValue(VineBlock.EAST, true), 8, -1, 3);
        finder.addBlock(Blocks.VINE.defaultBlockState().setValue(VineBlock.EAST, true), 8, -2, 3);

        finder.addBlock(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 9, -3, 2);
        finder.addBlock(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 8, -3, 1);
        finder.addBlock(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 4, -3, 5);
        finder.addBlock(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 5, -2, 5);
        finder.addBlock(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 5, -1, 5);
        finder.addBlock(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 6, -3, 5);
        finder.addBlock(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 7, -2, 5);
        finder.addBlock(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 7, -1, 5);
        finder.addBlock(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 8, -3, 5);
        finder.addBlock(Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 8, -2, 11);
        finder.addBlock(Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 9, -2, 11);
        finder.addBlock(Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 10, -2, 11);
        BlockState blockState_6 = (Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACING, Direction.NORTH)).setValue(LeverBlock.FACE, AttachFace.WALL);
        finder.addBlock(blockState_6, 8, -2, 12);
        finder.addBlock(blockState_6, 9, -2, 12);
        finder.addBlock(blockState_6, 10, -2, 12);
        finder.addBlock(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 10, -2, 9);
        finder.addBlock(Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedstoneWireBlock.NORTH, RedstoneSide.SIDE), 8, -2, 9);
        finder.addBlock(Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedstoneWireBlock.SOUTH, RedstoneSide.SIDE), 8, -2, 10);
        finder.addBlock(Blocks.REDSTONE_WIRE.defaultBlockState(), 10, -1, 9);
        finder.addBlock(Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBlock.FACING, Direction.UP), 9, -2, 8);
        finder.addBlock(Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBlock.FACING, Direction.WEST), 10, -2, 8);
        finder.addBlock(Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBlock.FACING, Direction.WEST), 10, -1, 8);
        finder.addBlock(Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.FACING, Direction.NORTH), 10, -2, 10);
    }

    public static List<Finder> create(World world, ChunkPos chunkPos) {
        List<Finder> finders = new ArrayList<>();
        finders.add(new JunglePyramidFinder(world, chunkPos));
        return finders;
    }
    
}
