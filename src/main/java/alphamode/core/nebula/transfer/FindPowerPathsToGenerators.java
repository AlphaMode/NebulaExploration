package alphamode.core.nebula.transfer;

import alphamode.core.nebula.api.Machine;
import alphamode.core.nebula.api.Node;
import alphamode.core.nebula.tags.NebulaTags;
import alphamode.core.nebula.util.Util;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Optional;

public class FindPowerPathsToGenerators {
    public ConcurrentSet<PowerPath> paths = new ConcurrentSet<>();
    //Previous pos contains all the previous locations that pipes have already branched out to, as to not repeat ourselves and not do an infinte loop.
    public ConcurrentSet<BlockPos> previousPos = new ConcurrentSet<>();
    public static final int MAX_RECURSION_DEPTH = 3000;
    int recursionDepth = 0;

    public void findTargets(World world, PowerPath powerPath) {
        Optional<BlockPos>[] optionals = getAroundTarget(world, powerPath.currentPos);
        for (Optional<BlockPos> optional : optionals) {
            if (optional.isEmpty() || previousPos.contains(optional.get())) {
//                optional.ifPresent(blockPos -> System.out.println(world.getBlockState(blockPos)));
                continue;
            }
            previousPos.add(optional.get());
            recursionDepth++;
            //Prevents the recursive pipe algorthim from causing a stack overflow error if we go 3000 recursion too deep.
            if (recursionDepth > MAX_RECURSION_DEPTH) {
                System.out.println("hit max recursion");
                return;
            }
            PowerPath temp = new PowerPath(optional.get(), powerPath);
            if (isValidEndpoint(world, temp.currentPos, powerPath.currentPos)) {
//                System.out.println(world.getBlockState(temp.currentPos));
//                System.out.println(temp.currentPos);
                paths.add(temp);
            } else {
                //System.out.println(world.getBlockState(temp.currentPos));
                findTargets(world, temp);
            }
        }
        //paths.stream().forEach((block) -> System.out.println(world.getBlockState(block.currentPos)));
    }

    Optional<BlockPos>[] getAroundTarget(World world, BlockPos pos) {
        Optional<BlockPos>[] positions = new Optional[6];
        int i = 1;
        positions[0] = getIfMatches(world, pos.add(i, 0, 0));
        positions[1] = getIfMatches(world, pos.add(0, i, 0));
        positions[2] = getIfMatches(world, pos.add(0, 0, i));
        positions[3] = getIfMatches(world, pos.add(-i, 0, 0));
        positions[4] = getIfMatches(world, pos.add(0, -i, 0));
        positions[5] = getIfMatches(world, pos.add(0, 0, -i));
//        for(int i = 0; i < Direction.values().length; i++) {
//            positions[i] = getIfMatches(world, pos.offset(Direction.values()[i]));
//        }
        return positions;
    }

    Optional<BlockPos> getIfMatches(World world, BlockPos pos) {
        if (isValidCarrier(world, pos)) {
            //System.out.println(world.getBlockState(pos).getBlock());
            return Optional.of(pos);
        }
        return Optional.empty();
    }

    private boolean isValidCarrier(World world, BlockPos pos) {
//        return world.getBlockState(pos).getBlock() instanceof IPowerBlock && (((IPowerBlock)world.getBlockState(pos).getBlock()).getPowerType() == PowerBlockType.PIPE);
        return (world.getBlockState(pos).isIn(NebulaTags.PIPE_CONNECTS_TO));
        //return (world.getBlockState(pos).isIn(ModScienceTags.PIPE) || world.getBlockState(pos).isIn(ModScienceTags.GENERATOR));
        //return world.getBlockState(pos).getBlock() instanceof IPowerBlock && (((IPowerBlock) world.getBlockState(pos).getBlock()).getPowerType() == PowerBlockType.PIPE || ((IPowerBlock) world.getBlockState(pos).getBlock()).getPowerType() == PowerBlockType.GENERATOR);
    }

    private boolean isValidEndpoint(World world, BlockPos pos, BlockPos previousPos) {
        Direction direction = Direction.fromVector(previousPos.subtract(pos));
        return world.getBlockState(pos).contains(Util.getSideFromDirection(direction)) && world.getBlockState(pos).get(Util.getSideFromDirection(direction)).equals(Side.OUT);
        //return world.getBlockState(pos).isIn(ModScienceTags.GENERATOR);
        //return world.getBlockState(pos).getBlock() instanceof IPowerBlock && (((IPowerBlock) world.getBlockState(pos).getBlock()).getPowerType() == PowerBlockType.GENERATOR);
    }

}
