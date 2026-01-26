package net.dusty_dusty.cts_compats.mods.vanilla.block;

import net.dusty_dusty.cts_compats.common.AssignUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SweetBerryBushOnTop extends SweetBerryBushBlock {
    private final Block originalBlock;
    private static final VoxelShape SAPLING_SHAPE = Block.box(3.0D, -8.0D, 3.0D, 13.0D, 0.0D, 13.0D);
    private static final VoxelShape MID_GROWTH_SHAPE = Block.box(1.0D, -8.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    private static final VoxelShape FULL_BLOCK = Block.box(0.0D, -8.0D, 0.0D, 16.0D, 8.0D, 16.0D);

    public SweetBerryBushOnTop(Block originalBlock) {
        super( BlockBehaviour.Properties.copy( originalBlock ) );
        this.originalBlock = originalBlock;
    }

    public Block getOriginBlock() {
        return originalBlock;
    }

    public void assign() {
        AssignUtil.putOnTopVegetation( originalBlock, this );
        AssignUtil.putVegetaitonOnTopItem( Items.SWEET_BERRIES, this );
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(AGE) == 0) {
            return SAPLING_SHAPE;
        } else {
            return pState.getValue(AGE) < 3 ? MID_GROWTH_SHAPE : FULL_BLOCK;
        }
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
