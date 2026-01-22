package net.dusty_dusty.cts_compats.projectVibrantJourneys.block;

import dev.orderedchaos.projectvibrantjourneys.common.blocks.ShortGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShortGrassOnTop extends ShortGrassBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0, -8.0, 2.0, 14.0, 5.0, 14.0);

    public ShortGrassOnTop(Properties props) {
        super( props );
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
         return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
      return pState.getBlock() instanceof SlabBlock;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }

}
