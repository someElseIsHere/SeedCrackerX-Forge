package kaptainwutax.seedcrackerX.finder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;

import java.util.*;
import java.util.stream.Collectors;

public abstract class BlockFinder extends Finder {

    private Set<BlockState> targetBlockStates = new HashSet<>();
    protected List<BlockPos> searchPositions = new ArrayList<>();

    public BlockFinder(World world, ChunkPos chunkPos, Block block) {
        super(world, chunkPos);
        this.targetBlockStates.addAll(block.getStateDefinition().getPossibleStates());
    }

    public BlockFinder(World world, ChunkPos chunkPos, BlockState... blockStates) {
        super(world, chunkPos);
        this.targetBlockStates.addAll(Arrays.stream(blockStates).collect(Collectors.toList()));
    }

    @Override
    public List<BlockPos> findInChunk() {
        List<BlockPos> result = new ArrayList<>();
        IChunk chunk = this.world.getChunk(this.chunkPos.getWorldPosition());

        for(BlockPos blockPos: this.searchPositions) {
            BlockState currentState = chunk.getBlockState(blockPos);

            if(this.targetBlockStates.contains(currentState)) {
                result.add(this.chunkPos.getWorldPosition().offset(blockPos));
            }
        }
        return result;
    }

}
