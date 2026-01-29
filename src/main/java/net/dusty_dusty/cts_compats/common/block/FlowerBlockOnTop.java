package net.dusty_dusty.cts_compats.common.block;

import net.dusty_dusty.cts_compats.common.interfaces.IAssignable;
import net.dusty_dusty.cts_compats.common.interfaces.IOnTopCopy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FlowerBlockOnTop extends FlowerBlock implements IAssignable, IOnTopCopy {
    private static final VoxelShape SHAPE = Block.box(5.0D, -8.0D, 5.0D, 11.0D, 2.0D, 11.0D);
    private final Block originalBlock;

    public FlowerBlockOnTop(Block originalBlock) {
        super(( (FlowerBlock) originalBlock )::getSuspiciousEffect, ( (FlowerBlock) originalBlock ).getEffectDuration(),
                BlockBehaviour.Properties.copy( originalBlock ) );
        this.originalBlock = originalBlock;
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
    protected boolean mayPlaceOn(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return pState.getBlock() instanceof SlabBlock;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }
}
