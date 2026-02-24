package net.dusty_dusty.cts_compats.common.block.onTopBlocks;

import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.dusty_dusty.cts_compats.common.block.interfaces.BlockCopyWrapper;
import net.dusty_dusty.cts_compats.common.block.interfaces.IAssignable;
import net.dusty_dusty.cts_compats.common.block.interfaces.IOnTopCopy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import net.dusty_dusty.cts_compats.common.PropertiesUtil;

import java.util.ArrayList;
import java.util.List;

public class BushBlockOnTop extends BushBlock implements IAssignable, IOnTopCopy {
    private final ArrayList<BlockCheckWrapper> blockTypes = new ArrayList<>();
    final PlacementRule PLACEMENT_RULE;
    private final VoxelShape SHAPE;
    final Block originalBlock;

    public BushBlockOnTop( Block originalBlock, VoxelShape SHAPE) {
        super( PropertiesUtil.copyAndOffsetOnTopBlockProperties( originalBlock ) );
        this.PLACEMENT_RULE = PlacementRule.DEFAULT;
        this.originalBlock = originalBlock;
        this.SHAPE = SHAPE;
    }
    public BushBlockOnTop(Block originalBlock, VoxelShape SHAPE, List<? extends BlockCheckWrapper> placeableTypes ) {
        super( PropertiesUtil.copyAndOffsetOnTopBlockProperties( originalBlock ) );
        this.PLACEMENT_RULE = PlacementRule.CUSTOM;
        this.blockTypes.addAll( placeableTypes );
        this.originalBlock = originalBlock;
        this.SHAPE = SHAPE;
    }

    @Override
    protected boolean mayPlaceOn( BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos ) {
        Block block = pState.getBlock();
        if ( block instanceof SlabBlock && block instanceof IBlockCopy copy ) {
            BlockCopyWrapper blockCopy = new BlockCopyWrapper( copy );
            return matchesOriginType( blockCopy.getOriginBlock().defaultBlockState() );
        }
        return false;
    }

    @Override
    public PlacementRule getPlacementRule() {
        return this.PLACEMENT_RULE;
    }

    private boolean matchesOriginType( BlockState state ) {
        return !this.hasPlacementRule( "custom" ) || BlockCheckWrapper.checkAll( state, this.blockTypes );
    }

    public Block getOriginBlock() {
        return originalBlock;
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }
}
