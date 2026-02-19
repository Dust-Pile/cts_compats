package net.dusty_dusty.cts_compats.common.block.onTopBlocks;

import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DoubleWatersidePlantOnTop extends DoublePlantOnTop {
    private final boolean IS_EXCLUSIVE;

    public DoubleWatersidePlantOnTop( Block originalBlock, boolean isExclusive ) {
        super(originalBlock);
        this.IS_EXCLUSIVE = isExclusive;
    }
    public DoubleWatersidePlantOnTop(Block originalBlock, boolean isExclusive, List<BlockCheckWrapper> placeableTypes ) {
        super( originalBlock, placeableTypes );
        this.IS_EXCLUSIVE = isExclusive;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
       if ( !super.mayPlaceOn( pState, pLevel, pPos )
               || pState.getValue( BlockStateProperties.WATERLOGGED ) && IS_EXCLUSIVE
       ) {
           return false;
       }

       return pState.getValue( BlockStateProperties.WATERLOGGED )
               || isWaterlogged( pLevel.getBlockState( pPos.below() ) )

               || isWaterlogged( pLevel.getBlockState( pPos.north() ) )
               || isWaterlogged( pLevel.getBlockState( pPos.south() ) )
               || isWaterlogged( pLevel.getBlockState( pPos.east() ) )
               || isWaterlogged( pLevel.getBlockState( pPos.west() ) )

               || isWaterlogged( pLevel.getBlockState( pPos.below().north() ) )
               || isWaterlogged( pLevel.getBlockState( pPos.below().south() ) )
               || isWaterlogged( pLevel.getBlockState( pPos.below().east() ) )
               || isWaterlogged( pLevel.getBlockState( pPos.below().west() ) );
    }

    boolean isWaterlogged( BlockState state ) {
        return state.hasProperty( BlockStateProperties.WATERLOGGED )
                && state.getValue( BlockStateProperties.WATERLOGGED );
    }
}
