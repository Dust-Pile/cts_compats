package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.block;

import dev.orderedchaos.projectvibrantjourneys.common.blocks.SmallCactusBlock;
import net.dusty_dusty.cts_compats.common.PropertiesUtil;
import net.dusty_dusty.cts_compats.common.interfaces.IAssignable;
import net.dusty_dusty.cts_compats.common.interfaces.IOnTopCopy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SmallCactusOnTop extends SmallCactusBlock implements IAssignable, IOnTopCopy {
    protected static final VoxelShape SHAPE = Block.box(2.0F, -8.0F, 2.0F, 14.0F, 5.0F, 14.0F);
    private final Block originalBlock;

    public SmallCactusOnTop(Block originalBlock) {
        super( PropertiesUtil.copyAndOffsetOnTopBlockProperties( originalBlock ) );
        this.originalBlock = originalBlock;
    }

    public Block getOriginBlock() {
        return originalBlock;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return pState.getBlock() instanceof SlabBlock;
    }
}
