package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.block;

import dev.orderedchaos.projectvibrantjourneys.common.blocks.GlowcapBlock;
import net.dusty_dusty.cts_compats.common.PropertiesUtil;
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

public class GlowcapOnTop extends GlowcapBlock implements IAssignable, IOnTopCopy {
    protected static final VoxelShape SHAPE = Block.box(5.0D, -8.0D, 5.0D, 11.0D, -2.0D, 11.0D);
    private final Block originalBlock;

    public GlowcapOnTop( Block originalBlock ) {
        super( PropertiesUtil.copyAndOffsetOnTopBlockProperties( originalBlock ) );
        this.originalBlock = originalBlock;
    }

    public Block getOriginBlock() {
        return originalBlock;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
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
