package net.dusty_dusty.cts_compats.common.block.onTopBlocks;

import net.dusty_dusty.cts_compats.common.block.interfaces.IAssignable;
import net.dusty_dusty.cts_compats.common.block.interfaces.IOnTopCopy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import net.dusty_dusty.cts_compats.common.PropertiesUtil;

public class BushBlockOnTop extends BushBlock implements IAssignable, IOnTopCopy {
    private final VoxelShape shape;
    private final Block originalBlock;

    public BushBlockOnTop( Block originalBlock, VoxelShape shape ) {
        super( PropertiesUtil.copyAndOffsetOnTopBlockProperties( originalBlock ) );
        this.originalBlock = originalBlock;
        this.shape = shape;
    }

    public Block getOriginBlock() {
        return originalBlock;
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return shape;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return pState.getBlock() instanceof SlabBlock;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }
}
