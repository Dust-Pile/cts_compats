package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPBaseRegistry;
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
import java.util.List;

public class FoliageOnTopBOP extends BushBlockOnTop implements IPlantable {
    public static final VoxelShape FOLIAGE_SHAPE = Block.box( 2.0F, -8.0F, 2.0F, 14.0F, 5.0F, 14.0F );
    public static final VoxelShape SHORT_FOLIAGE_SHAPE = Block.box( 1.0F, -8.0F, 1.0F, 15.0F, -1.0F, 15.0F );
    public static BlockCheckWrapper.Group DEAD_GRASS =
            BOPBaseRegistry.PlaceType.BOP_SAND.cloneAndAppend( BlockCheckWrapper.DIRT_AND_FARMLAND );
    static {
        DEAD_GRASS.add( Blocks.NETHERITE_BLOCK );
        DEAD_GRASS.add( BOPReference.DRIED_SALT );
        DEAD_GRASS.add( Blocks.GRAVEL );
    }

    public FoliageOnTopBOP( Block originalBlock, VoxelShape shape ) {
        super( originalBlock, shape );
    }
    public FoliageOnTopBOP( Block originalBlock, VoxelShape shape, List<? extends BlockCheckWrapper> placeableTypes ) {
        super( originalBlock, shape, placeableTypes );
    }

    public void playerDestroy(Level worldIn, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity te, @NotNull ItemStack stack) {
        if (!worldIn.isClientSide && stack.getItem() == Items.SHEARS) {
            player.awardStat(Stats.BLOCK_MINED.get( this.getOriginBlock() ) );
            player.causeFoodExhaustion(0.005F);
            popResource(worldIn, pos, new ItemStack( this.getOriginalItem() ) );
        } else {
            super.playerDestroy(worldIn, player, pos, state, te, stack);
        }
    }
}
