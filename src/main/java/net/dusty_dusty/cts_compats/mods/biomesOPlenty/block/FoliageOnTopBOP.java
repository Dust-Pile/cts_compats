package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPReference;
import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.dusty_dusty.cts_compats.common.block.interfaces.BlockCopyWrapper;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.BushBlockOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FoliageOnTopBOP extends BushBlockOnTop implements IPlantable {
    protected static final VoxelShape NORMAL = Block.box( 2.0F, -8.0F, 2.0F, 14.0F, 5.0F, 14.0F );
    protected static final VoxelShape SHORT = Block.box( 1.0F, -8.0F, 1.0F, 15.0F, -1.0F, 15.0F );

    public FoliageOnTopBOP(Block originalBlock ) {
        super(originalBlock, originalBlock == BOPReference.DESERT_GRASS ? SHORT : NORMAL );
    }

    public void playerDestroy(Level worldIn, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity te, @NotNull ItemStack stack) {
        if (!worldIn.isClientSide && stack.getItem() == Items.SHEARS) {
            player.awardStat(Stats.BLOCK_MINED.get(this));
            player.causeFoodExhaustion(0.005F);
            popResource(worldIn, pos, new ItemStack(this));
        } else {
            super.playerDestroy(worldIn, player, pos, state, te, stack);
        }
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        Block ground = worldIn.getBlockState(pos.below()).getBlock();
        if ( !( ground instanceof SlabBlock && ground instanceof IBlockCopy ) ) {
            return false;
        }

        Block block = new BlockCopyWrapper( (IBlockCopy) state.getBlock() ).getOriginBlock();
        BlockState blockState = block.withPropertiesOf(state);
        ground = new BlockCopyWrapper( (IBlockCopy) ground ).getOriginBlock();

        if (block == BOPReference.SPROUT ) {
            return true;
        } else if (block == BOPReference.DUNE_GRASS ) {
            return ground == Blocks.SAND || ground == Blocks.RED_SAND || ground == BOPReference.WHITE_SAND
                    || ground == BOPReference.ORANGE_SAND || ground == BOPReference.BLACK_SAND;
        } else if (block != BOPReference.DESERT_GRASS && block != BOPReference.DEAD_GRASS ) {
            return blockState.is(BlockTags.DIRT) || blockState.is(Blocks.FARMLAND);
        } else {
            return ground == BOPReference.DRIED_SALT || ground == Blocks.GRAVEL || ground == Blocks.SAND
                    || ground == Blocks.RED_SAND || ground == BOPReference.WHITE_SAND
                    || ground == BOPReference.ORANGE_SAND || ground == BOPReference.BLACK_SAND
                    || ground == Blocks.NETHERRACK || blockState.is(BlockTags.DIRT) || blockState.is(Blocks.FARMLAND );
        }
    }

    @Override
    public PlacementRule getPlacementRule() {
        Block block = this.getOriginBlock();
        if ( block == BOPReference.SPROUT || block == BOPReference.DUNE_GRASS
                || block == BOPReference.DESERT_GRASS || block == BOPReference.DEAD_GRASS
        ) {
            return PlacementRule.CUSTOM;
        }
        return PlacementRule.DEFAULT;
    }
}
