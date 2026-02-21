package net.dusty_dusty.cts_compats.common.block.onTopBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class LanternOnTop extends BasicOnTopBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Shapes.or(Block.box(5.0D, -8.0D, 5.0D, 11.0D, -1.0D, 11.0D), Block.box(6.0D, -1.0D, 6.0D, 10.0D, 1.0D, 10.0D));

    public LanternOnTop(Block originalBlock, VoxelShape shape) {
        super( originalBlock, shape );
        this.registerDefaultState( this.stateDefinition.any()
                .setValue( WATERLOGGED, false )
                .setValue( HANGING, false )
        );
    }
    public LanternOnTop(Block originalBlock) {
        this(originalBlock, SHAPE);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());

        BlockState blockState = this.defaultBlockState();
        if ( blockState.canSurvive(pContext.getLevel(), pContext.getClickedPos() ) ) {
            return blockState.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER );
        }

        return null;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add( HANGING, WATERLOGGED );
    }

    public @NotNull BlockState updateShape(BlockState pState, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return pDirection == Direction.DOWN && !pState.canSurvive(pLevel, pPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, BlockPos pPos ) {
        if ( pState.getValue( HANGING ) ) {
            return false;
        }

        BlockState belowState = pLevel.getBlockState( pPos.below() );
        return belowState.getBlock() instanceof SlabBlock
                && belowState.getValue( SlabBlock.TYPE ).equals( SlabType.BOTTOM )
                && !belowState.getValue( BlockStateProperties.WATERLOGGED );
    }

    public @NotNull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public boolean isPathfindable(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull PathComputationType pType) {
        return false;
    }
}
