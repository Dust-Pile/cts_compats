package net.dusty_dusty.cts_compats.common.block.onTopBlocks;

import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DoubleWaterPlantOnTop extends DoublePlantOnTop {
    public DoubleWaterPlantOnTop(Block originalBlock) {
        super(originalBlock);
    }
    public DoubleWaterPlantOnTop( Block originalBlock, List<BlockCheckWrapper> placeableTypes ) {
        super( originalBlock, placeableTypes );
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return super.mayPlaceOn( pState, pLevel, pPos ) && pState.getValue( BlockStateProperties.WATERLOGGED );
    }
}
