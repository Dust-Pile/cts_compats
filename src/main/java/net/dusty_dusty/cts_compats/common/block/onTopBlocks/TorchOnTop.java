package net.dusty_dusty.cts_compats.common.block.onTopBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TorchOnTop extends BasicOnTopBlock {
    protected static final VoxelShape SHAPE = Block.box(6.0D, -8.0D, 6.0D, 10.0D, 2.0D, 10.0D);
    protected final ParticleOptions flameParticle;

    public TorchOnTop( Block originalBlock, VoxelShape shape, ParticleOptions pFlameParticle ) {
        super( originalBlock, shape );
        this.flameParticle = pFlameParticle;
    }
    public TorchOnTop( Block originalBlock, ParticleOptions pFlameParticle ) {
        this( originalBlock, SHAPE, pFlameParticle );
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull BlockState updateShape(@NotNull BlockState pState, @NotNull Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos) {
        return pFacing == Direction.DOWN && !this.canSurvive(pState, pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public boolean canSurvive( @NotNull BlockState pState, @NotNull LevelReader pLevel, BlockPos pPos ) {
        BlockState belowState = pLevel.getBlockState( pPos.below() );
        return belowState.getBlock() instanceof SlabBlock
                && belowState.getValue( SlabBlock.TYPE ).equals( SlabType.BOTTOM )
                && !belowState.getValue( BlockStateProperties.WATERLOGGED );
    }

    @Override
    public void animateTick(@NotNull BlockState pState, Level pLevel, BlockPos pPos, @NotNull RandomSource pRandom ) {
        double d0 = (double)pPos.getX() + 0.5D;
        double d1 = (double)pPos.getY() + 0.2D;
        double d2 = (double)pPos.getZ() + 0.5D;
        pLevel.addParticle( ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D );
        pLevel.addParticle( this.flameParticle, d0, d1, d2, 0.0D, 0.0D, 0.0D );
    }
}
