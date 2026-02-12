package net.dusty_dusty.cts_compats.common.block.onTopBlocks;

import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.dusty_dusty.cts_compats.common.PropertiesUtil;
import net.dusty_dusty.cts_compats.common.block.interfaces.BlockCopyWrapper;
import net.dusty_dusty.cts_compats.common.block.interfaces.IAssignable;
import net.dusty_dusty.cts_compats.common.block.interfaces.IOnTopCopy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class BasicOnTopBlock extends Block implements IAssignable, IOnTopCopy {
    private final ArrayList<BlockCheckWrapper> blockTypes = new ArrayList<>();
    final PlacementRule PLACEMENT_RULE;
    private final VoxelShape shape;
    private final Block originalBlock;

    public BasicOnTopBlock( Block originalBlock, VoxelShape shape ) {
        super( PropertiesUtil.copyAndOffsetOnTopBlockProperties( originalBlock ) );
        this.PLACEMENT_RULE = PlacementRule.DEFAULT;
        this.originalBlock = originalBlock;
        this.shape = shape;
    }
    public BasicOnTopBlock( Block originalBlock, VoxelShape shape, List<BlockCheckWrapper> placeableTypes ) {
        super( PropertiesUtil.copyAndOffsetOnTopBlockProperties( originalBlock ) );
        this.PLACEMENT_RULE = PlacementRule.CUSTOM;
        this.blockTypes.addAll( placeableTypes );
        this.originalBlock = originalBlock;
        this.shape = shape;
    }

    boolean mayPlaceOn( BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos ) {
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
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return shape;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, BlockPos pPos) {
        BlockPos belowPos = pPos.below();
        BlockState belowState = pLevel.getBlockState( belowPos );
        return mayPlaceOn( belowState, pLevel, belowPos );
    }
}
