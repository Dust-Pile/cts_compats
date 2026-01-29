package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.block;

import dev.orderedchaos.projectvibrantjourneys.common.blocks.FallenLeavesBlock;
import net.dusty_dusty.cts_compats.common.interfaces.IAssignable;
import net.dusty_dusty.cts_compats.common.interfaces.IOnTopCopy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FallenLeavesOnTop extends FallenLeavesBlock implements IAssignable, IOnTopCopy {
    private final VoxelShape SHAPE = Block.box(0.01, -8.0F, 0.0F, 16.0F, -6.01, 16.0F);
    private final Block originalBlock;

    public FallenLeavesOnTop( Block orininalBlock ) {
        super( BlockBehaviour.Properties.copy( orininalBlock ) );
        this.originalBlock = orininalBlock;
    }

    public Block getOriginBlock() {
        return originalBlock;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @SuppressWarnings("unused")
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.getBlock() instanceof SlabBlock;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }
}
