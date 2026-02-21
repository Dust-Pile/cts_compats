package net.dusty_dusty.cts_compats.common.block;

import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.dusty_dusty.cts_compats.common.block.interfaces.BlockCopyWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class SandSlabBlock extends CustomSlabBlock implements Fallable {
    public SandSlabBlock( Block block ) {
        super( block );
        this.registerDefaultState( this.defaultBlockState()
                .setValue( TYPE, SlabType.BOTTOM )
                .setValue( WATERLOGGED, false )
                .setValue( GENERATED, false ) );
    }

    public void onPlace(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pOldState, boolean pIsMoving) {
        pLevel.scheduleTick(pPos, this, this.getDelayAfterPlace());
    }

    public @NotNull BlockState updateShape(@NotNull BlockState pState, @NotNull Direction pFacing, @NotNull BlockState pFacingState, LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos) {
        pLevel.scheduleTick(pCurrentPos, this, this.getDelayAfterPlace());
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public void tick(@NotNull BlockState pState, ServerLevel pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
        if ( canFallThrough(pLevel.getBlockState(pPos.below())) && pPos.getY() >= pLevel.getMinBuildHeight()) {
            FallingBlockEntity.fall(pLevel, pPos, pState);
        }
    }

    // TODO: Implement IDuelSlab Capability
    private boolean canFallThrough( BlockState state ) {
        return FallingBlock.isFree( state )
                || ( state.is( this ) && state.getValue( TYPE ).equals( SlabType.BOTTOM ) );
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos blockPos = ctx.getClickedPos();
        BlockState blockState = ctx.getLevel().getBlockState(blockPos);
        if (blockState.is(this)) {
            return blockState.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, false );
        } else {
            FluidState fluidState = ctx.getLevel().getFluidState(blockPos);
            BlockState blockState2 = this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
            Direction direction = ctx.getClickedFace();
            return direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getClickedPos().getY() - (double)blockPos.getY() > 0.5))
                    ? blockState2
                    : blockState2.setValue(TYPE, SlabType.BOTTOM);
        }
    }

    protected int getDelayAfterPlace() {
        return 2;
    }

    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        getOriginBlock().animateTick( pState, pLevel, pPos, pRandom );
    }

    // TODO: Implement IDuelSlab Capability (Maybe)
    @Override
    public void onBrokenAfterFall(Level world, @NotNull BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        BlockState fallingBlockState = fallingBlockEntity.getBlockState();
        BlockState landedOnBlockState = world.getBlockState(pos);

        //No need to check state, would only trigger on bottom slab
        if ( landedOnBlockState.is(this) ) {
            Block originBlock = new BlockCopyWrapper((IBlockCopy) fallingBlockState.getBlock()).getOriginBlock();

            if (fallingBlockState.getValue(TYPE).equals(SlabType.DOUBLE)) {
                BlockState aboveState = world.getBlockState(pos.above());
                if (!(aboveState.is(BlockTags.REPLACEABLE) || aboveState.isAir() || aboveState.is(Blocks.WATER))) {
                    popResource(world, pos, new ItemStack(this.getOriginalItem()));
                    return;
                }

                world.setBlockAndUpdate(pos.above(), this.withPropertiesOf(landedOnBlockState)
                        .setValue(TYPE, SlabType.BOTTOM) );

                if ( landedOnBlockState.getValue(GENERATED) ) {
                    world.setBlockAndUpdate(pos, originBlock.withPropertiesOf( landedOnBlockState ) );
                } else {
                    world.setBlockAndUpdate(pos, this.defaultBlockState().setValue(TYPE, SlabType.DOUBLE));
                }
            } else {
                if ( landedOnBlockState.getValue(GENERATED) ) {
                    world.setBlockAndUpdate(pos, originBlock.withPropertiesOf(landedOnBlockState));
                } else {
                    world.setBlockAndUpdate(pos, this.defaultBlockState().setValue(TYPE, SlabType.DOUBLE));
                }
            }
            return;
        }

        // Loot if checks fail
        if ( fallingBlockState.getValue(TYPE).equals( SlabType.DOUBLE) ) {
            popResource(world, pos, new ItemStack(this.getOriginalItem(), 2));
        } else {
            popResource(world, pos, new ItemStack(this.getOriginalItem()));
        }
    }

    @Override
    public void onLand(@NotNull Level world, @NotNull BlockPos pos, BlockState fallingBlockState, @NotNull BlockState currentStateInPos, @NotNull FallingBlockEntity fallingBlockEntity) {
        if ( fallingBlockState.getValue( TYPE ) == SlabType.TOP ) {
            world.setBlockAndUpdate( pos, fallingBlockState.setValue( TYPE, SlabType.BOTTOM ) );
        }
    }
}
