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
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FlowerBlockOnTop extends FlowerBlock implements IAssignable, IOnTopCopy {
    private final ArrayList<BlockCheckWrapper> blockTypes = new ArrayList<>();
    final PlacementRule PLACEMENT_RULE;
    private final VoxelShape SHAPE;
    private final Block originalBlock;

    public FlowerBlockOnTop( Block originalBlock ) {
        this( originalBlock, Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D) );
    }
    public FlowerBlockOnTop( Block originalBlock, VoxelShape shape ) {
        super(( (FlowerBlock) originalBlock )::getSuspiciousEffect, ( (FlowerBlock) originalBlock ).getEffectDuration(),
                PropertiesUtil.copyAndOffsetOnTopBlockProperties( originalBlock ) );
        this.PLACEMENT_RULE = PlacementRule.DEFAULT;
        this.originalBlock = originalBlock;
        this.SHAPE = shape;
    }
    public FlowerBlockOnTop( Block originalBlock, VoxelShape shape, List<? extends BlockCheckWrapper> placeableTypes ) {
        super(( (FlowerBlock) originalBlock )::getSuspiciousEffect, ( (FlowerBlock) originalBlock ).getEffectDuration(),
                PropertiesUtil.copyAndOffsetOnTopBlockProperties( originalBlock ) );
        this.PLACEMENT_RULE = PlacementRule.CUSTOM;
        this.blockTypes.addAll( placeableTypes );
        this.originalBlock = originalBlock;
        this.SHAPE = shape;
    }

    public Block getOriginBlock() {
        return originalBlock;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
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

    private boolean matchesOriginType( BlockState state ) {
        return !this.hasPlacementRule( "custom" ) || BlockCheckWrapper.checkAll( state, this.blockTypes );
    }

    @Override
    public boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }

    @Override
    public PlacementRule getPlacementRule() {
        return this.PLACEMENT_RULE;
    }
}
