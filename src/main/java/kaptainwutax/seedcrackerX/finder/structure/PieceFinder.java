package kaptainwutax.seedcrackerX.finder.structure;

import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.block.BlockMirror;
import com.seedfinding.mccore.util.block.BlockRotation;
import com.seedfinding.mccore.util.math.Vec3i;
import kaptainwutax.seedcrackerX.finder.Finder;
import kaptainwutax.seedcrackerX.util.PosFixer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.*;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PieceFinder extends Finder {

    protected Map<BlockPos, BlockState> structure = new LinkedHashMap<>();
    private BlockBox boundingBox;
    protected List<BlockPos> searchPositions = new ArrayList<>();

    protected Direction facing;
    private Mirror mirror;
    private Rotation rotation;

    protected int width;
    protected int height;
    protected int depth;

    private boolean debug;

    public PieceFinder(World world, ChunkPos chunkPos, Direction facing, Vec3i size) {
        super(world, chunkPos);

        this.setOrientation(facing);
        this.width = size.getX();
        this.height = size.getY();
        this.depth = size.getZ();

        if(this.facing.getAxis() == Direction.Axis.Z) {
            this.boundingBox = new BlockBox(
                    0, 0, 0,
                    size.getX() - 1, size.getY() - 1, size.getZ() - 1
            );
        } else {
            this.boundingBox = new BlockBox(
                    0, 0, 0,
                    size.getZ() - 1, size.getY() - 1, size.getX() - 1
            );
        }
    }

    public Vec3i getLayout() {
        if(this.facing.getAxis() != Direction.Axis.Z) {
            return new Vec3i(this.depth, this.height, this.width);
        }

        return new Vec3i(this.width, this.height, this.depth);
    }

    @Override
    public List<BlockPos> findInChunk() {
        List<BlockPos> result = new ArrayList<>();

        if(this.structure.isEmpty()) {
            return result;
        }

        //FOR DEBUGGING PIECES.
        if(this.debug) {
            Minecraft.getInstance().execute(() -> {
                int y = this.rotation.ordinal() * 10 + this.mirror.ordinal() * 20 + 120;

                if (this.chunkPos.x % 2 == 0 && this.chunkPos.z % 2 == 0) {
                    this.structure.forEach((pos, state) -> {
                        this.world.setBlock(this.chunkPos.getWorldPosition().offset(pos).offset(0, y, 0), state, 0);
                    });
                }
            });
        }

        for(BlockPos center: this.searchPositions) {
            boolean found = true;

            for(Map.Entry<BlockPos, BlockState> entry: this.structure.entrySet()) {
                BlockPos pos = this.chunkPos.getWorldPosition().offset(center.offset(entry.getKey()));
                BlockState state = this.world.getBlockState(pos);

                //Blockstate may change when it gets placed in the world, that's why it's using the block here.
                if(entry.getValue() != null && !state.getBlock().equals(entry.getValue().getBlock())) {
                    found = false;
                    break;
                }
            }

            if(found) {
                result.add(this.chunkPos.getWorldPosition().offset(center));
            }
        }

        return result;
    }

    public void setOrientation(Direction facing) {
        this.facing = facing;

        if(facing == null) {
            this.rotation = Rotation.NONE;
            this.mirror = Mirror.NONE;
        } else {
            switch(facing) {
                case SOUTH:
                    this.mirror = Mirror.LEFT_RIGHT;
                    this.rotation = Rotation.NONE;
                    break;
                case WEST:
                    this.mirror = Mirror.LEFT_RIGHT;
                    this.rotation = Rotation.CLOCKWISE_90;
                    break;
                case EAST:
                    this.mirror = Mirror.NONE;
                    this.rotation = Rotation.CLOCKWISE_90;
                    break;
                default:
                    this.mirror = Mirror.NONE;
                    this.rotation = Rotation.NONE;
            }
        }

    }

    protected int applyXTransform(int x, int z) {
        if (this.facing == null) {
            return x;
        } else {
            switch(this.facing) {
                case NORTH:
                case SOUTH:
                    return this.boundingBox.minX + x;
                case WEST:
                    return this.boundingBox.maxX - z;
                case EAST:
                    return this.boundingBox.minX + z;
                default:
                    return x;
            }
        }
    }

    protected int applyYTransform(int y) {
        return this.facing == null ? y : y + this.boundingBox.minY;
    }

    protected int applyZTransform(int x, int z) {
        if (this.facing == null) {
            return z;
        } else {
            switch(this.facing) {
                case NORTH:
                    return this.boundingBox.maxZ - z;
                case SOUTH:
                    return this.boundingBox.minZ + z;
                case WEST:
                case EAST:
                    return this.boundingBox.minZ + x;
                default:
                    return z;
            }
        }
    }

    protected BlockState getBlockAt(int ox, int oy, int oz) {
        int x = this.applyXTransform(ox, oz);
        int y = this.applyYTransform(oy);
        int z = this.applyZTransform(ox, oz);
        BlockPos pos = new BlockPos(x, y, z);

        return !this.boundingBox.contains(PosFixer.swap(pos)) ?
                Blocks.AIR.defaultBlockState() :
                this.structure.getOrDefault(pos, Blocks.AIR.defaultBlockState());
    }

    protected void fillWithOutline(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState outline, BlockState inside, boolean onlyReplaceAir) {
        for(int y = minY; y <= maxY; ++y) {
            for(int x = minX; x <= maxX; ++x) {
                for(int z = minZ; z <= maxZ; ++z) {
                    if(!onlyReplaceAir || !this.getBlockAt(x, y, z).isAir()) {
                        if(y != minY && y != maxY && x != minX && x != maxX && z != minZ && z != maxZ) {
                            this.addBlock(inside, x, y, z);
                        } else {
                            this.addBlock(outline, x, y, z);
                        }
                    }
                }
            }
        }

    }

    protected void addBlock(BlockState state, int x, int y, int z) {
        BlockPos pos = new BlockPos(
                this.applyXTransform(x, z),
                this.applyYTransform(y),
                this.applyZTransform(x, z)
        );

        if(this.boundingBox.contains(PosFixer.swap(pos))) {
            if(state == null) {
                this.structure.remove(pos);
                return;
            }

            if (this.mirror != Mirror.NONE) {
                state = state.mirror(this.mirror);
            }

            if (this.rotation != Rotation.NONE) {
                state = state.rotate(this.rotation);
            }


            this.structure.put(pos, state);
        }
    }

    @Override
    public boolean isValidDimension(DimensionType dimension) {
        return true;
    }

    public void setDebug() {
        this.debug = true;
    }

}
