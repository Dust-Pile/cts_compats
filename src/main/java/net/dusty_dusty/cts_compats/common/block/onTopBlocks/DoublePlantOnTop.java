package net.dusty_dusty.cts_compats.common.block.onTopBlocks;

import net.dusty_dusty.cts_compats.common.PropertiesUtil;
import net.dusty_dusty.cts_compats.common.block.interfaces.IAssignable;
import net.dusty_dusty.cts_compats.common.block.interfaces.IOnTopCopy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class DoublePlantOnTop extends DoublePlantBlock implements IAssignable, IOnTopCopy {
    private final Block originalBlock;

    public DoublePlantOnTop( Block originalBlock ) {
        super( PropertiesUtil.copyAndOffsetOnTopBlockProperties( originalBlock ) );
        this.originalBlock = originalBlock;
    }

    @Override
    public Block getOriginBlock() {
        return originalBlock;
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return AssignUtil.FULL_BLOCK_ON_SLAB;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return pState.getBlock() instanceof SlabBlock;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        if (pState.getValue(HALF) != DoubleBlockHalf.UPPER) {
            return mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
        } else {
            BlockState blockstate = pLevel.getBlockState(pPos.below());
            if (pState.getBlock() != this) {
                return mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
            } else {
                return blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
            }
        }
    }
}
