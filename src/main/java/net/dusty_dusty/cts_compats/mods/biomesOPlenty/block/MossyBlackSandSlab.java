package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import biomesoplenty.api.block.BOPBlocks;
import net.dusty_dusty.cts_compats.common.block.SandSlabBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class MossyBlackSandSlab extends SandSlabBlock {

    public MossyBlackSandSlab(Block originalBlock ) {
        super( originalBlock );
    }

    @Override
    public CopyModelType getCopyModelType() {
        return CopyModelType.TINTED_OVERLAY;
    }

    public boolean canSustainPlant(@NotNull BlockState state, @NotNull BlockGetter world, BlockPos pos, @NotNull Direction facing, IPlantable plantable) {
        PlantType type = plantable.getPlantType(world, pos.relative(facing));
        return type == PlantType.PLAINS;
    }

    private static boolean canBeGrass(BlockState p_56824_, LevelReader p_56825_, BlockPos p_56826_) {
        BlockPos blockpos = p_56826_.above();
        BlockState blockstate = p_56825_.getBlockState(blockpos);
        if (blockstate.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LightEngine.getLightBlockInto(p_56825_, p_56824_, p_56826_, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(p_56825_, blockpos));
            return i < p_56825_.getMaxLightLevel();
        }
    }

    @Override
    public void randomTick(@NotNull BlockState p_222508_, @NotNull ServerLevel p_222509_, @NotNull BlockPos p_222510_, @NotNull RandomSource p_222511_) {
        if (!canBeGrass(p_222508_, p_222509_, p_222510_)) {
            if (!p_222509_.isAreaLoaded(p_222510_, 1)) {
                return;
            }

            p_222509_.setBlockAndUpdate(p_222510_, (BOPBlocks.BLACK_SAND.get()).defaultBlockState());
        }

    }
}
