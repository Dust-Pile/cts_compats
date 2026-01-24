package net.dusty_dusty.cts_compats.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PetalBlockOnTop extends PinkPetalsBlock {
    private final Item originalItem;

    public PetalBlockOnTop( Properties p, Item item ) {
        super( p );
        originalItem = item;
    }

    @Override
    public VoxelShape getShape( BlockState b, BlockGetter g, BlockPos p, CollisionContext c ) {
        return Block.box( 0.0D, -8.0D, 0.0D, 16.0D, -5.0D, 16.0D );
    }

    @Override
    public boolean canBeReplaced( BlockState state, BlockPlaceContext context ) {
        boolean isHandMatch = context.getItemInHand().is( originalItem );// || context.getItemInHand().is(this.asItem());
        return super.canBeReplaced(state, context) || ( !context.isSecondaryUseActive() && isHandMatch && state.getValue(AMOUNT) < 4 );
    }

    @Override
    public boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.getBlock() instanceof SlabBlock;
    }
}
