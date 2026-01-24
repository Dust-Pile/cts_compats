package net.dusty_dusty.cts_compats.common.block;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
    public boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.getBlock() instanceof SlabBlock;
    }

    @Override
    public boolean canBeReplaced( BlockState state, BlockPlaceContext context ) {
        boolean isHandMatch = context.getItemInHand().is( originalItem );// || context.getItemInHand().is(this.asItem());
        return super.canBeReplaced(state, context) || ( !context.isSecondaryUseActive() && isHandMatch && state.getValue(AMOUNT) < 4 );
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        int amount = pState.getValue( AMOUNT );

        CTSCompats.LOGGER.info( "Triggered Petal Block Use ({})", pPlayer.getItemInHand( pHand ).is( originalItem ) );

        if ( !pPlayer.getItemInHand( pHand ).is( originalItem ) ) {
            return InteractionResult.PASS;
        } else if ( amount == 4 ) {
            return InteractionResult.FAIL;
        } else {
            pLevel.setBlock( pHit.getBlockPos(), pState.setValue( BlockStateProperties.FLOWER_AMOUNT, amount + 1 ), 0 );
            pLevel.playSound( pPlayer, pHit.getBlockPos(), SoundEvents.PINK_PETALS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F );

            if ( !pPlayer.isCreative() ) {
                pPlayer.getItemInHand( pHand ).shrink( 1 );
            }
            return InteractionResult.sidedSuccess( pLevel.isClientSide );
        }
    }
}
