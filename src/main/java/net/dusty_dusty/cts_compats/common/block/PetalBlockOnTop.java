package net.dusty_dusty.cts_compats.common.block;

import net.dusty_dusty.cts_compats.common.AssignUtil;
import net.dusty_dusty.cts_compats.common.IAssignable;
import net.dusty_dusty.cts_compats.common.IBlockCopy;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;

public class PetalBlockOnTop extends PinkPetalsBlock implements IAssignable, IBlockCopy {
    private Item originalItem;
    private final RegistryObject<Item> originalItemRegister;
    private final Block originalBlock;

    public PetalBlockOnTop( Block originalBlock ) {
        super( BlockBehaviour.Properties.copy( originalBlock ) );
        this.originalBlock = originalBlock;
        originalItem = originalBlock.asItem();
        originalItemRegister = null;
    }

    public PetalBlockOnTop( Block originalBlock, RegistryObject<Item> originalItemRegister ) {
        super( BlockBehaviour.Properties.copy( originalBlock ) );
        this.originalBlock = originalBlock;
        this.originalItemRegister = originalItemRegister;
    }

    public Block getOriginBlock() {
        return originalBlock;
    }

    public void assign() {
        originalItem = originalItemRegister != null ? originalItemRegister.get() : originalItem;
        AssignUtil.putOnTopVegetation( originalBlock, this );
        AssignUtil.putVegetaitonOnTopItem( originalItem, this );
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
    public void performBonemeal(ServerLevel sLevel, RandomSource random, BlockPos pos, BlockState state) {
        int i = state.getValue(AMOUNT);
        if (i < 4) {
            sLevel.setBlock(pos, state.setValue(AMOUNT, i + 1), 2);
        } else {
            popResource(sLevel, pos, new ItemStack( originalItem ) );
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        int amount = pState.getValue( AMOUNT );

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
