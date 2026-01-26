package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.block;

import dev.orderedchaos.projectvibrantjourneys.common.blocks.SmallCactusBlock;
import net.dusty_dusty.cts_compats.common.AssignUtil;
import net.dusty_dusty.cts_compats.common.IAssignable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallCactusOnTop extends SmallCactusBlock implements IAssignable {
    protected static final VoxelShape SHAPE = Block.box((double)2.0F, (double)-8.0F, (double)2.0F, (double)14.0F, (double)5.0F, (double)14.0F);
    private final Block originalBlock;

    public SmallCactusOnTop(Block originalBlock) {
        super( BlockBehaviour.Properties.copy( originalBlock ) );
        this.originalBlock = originalBlock;
    }

    public void assign() {
        AssignUtil.putOnTopVegetation( originalBlock, this );
        AssignUtil.putVegetaitonOnTopItem( originalBlock.asItem(), this );
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.getBlock() instanceof SlabBlock;
    }
}
