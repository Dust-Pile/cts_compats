package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.flowerBlocks;

import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPReference;
import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.dusty_dusty.cts_compats.common.block.interfaces.BlockCopyWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FlowerOnTopOldBOP extends AbstractFlowerOnTopBOP {
    public FlowerOnTopOldBOP( Block originalBlock ) {
        super( originalBlock );
    }

    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader worldIn, BlockPos pos) {
        Block ground = worldIn.getBlockState(pos.below()).getBlock();
        if ( !( ground instanceof SlabBlock && ground instanceof IBlockCopy slab ) ) {
            return false;
        }

        Block block = this.getOriginBlock();
        ground = new BlockCopyWrapper( slab ).getOriginBlock();
        BlockState groundState = ground.defaultBlockState();

        if (block == BOPReference.WILDFLOWER ) {
            return ground == Blocks.SAND || ground == Blocks.RED_SAND || ground == BOPReference.WHITE_SAND
                    || ground == BOPReference.ORANGE_SAND || ground == BOPReference.BLACK_SAND
                    || groundState.is(BlockTags.DIRT) || groundState.is(Blocks.FARMLAND);
        } else {
            return ground == Blocks.NETHERRACK || ground == Blocks.SOUL_SAND || ground == Blocks.SOUL_SOIL
                    || ground == Blocks.CRIMSON_NYLIUM || ground == Blocks.WARPED_NYLIUM
                    || groundState.is(BlockTags.DIRT) || groundState.is(Blocks.FARMLAND );
        }
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext selectionContext) {
        VoxelShape shape = NORMAL;
        if ( this.getOriginBlock() == BOPReference.LAVENDER
                || this.getOriginBlock() == BOPReference.PINK_HIBISCUS
        ) {
            shape = LARGE;
        } else if ( this.getOriginBlock() == BOPReference.PINK_DAFFODIL || this.getOriginBlock() == BOPReference.WILDFLOWER
                || this.getOriginBlock() == BOPReference.GLOWFLOWER || this.getOriginBlock() == BOPReference.WILTED_LILY
        ) {
            shape = MEDIUM;
        } else if ( this.getOriginBlock() == BOPReference.VIOLET ) {
            shape = SHORT;
        }

        Vec3 vec3 = state.getOffset(worldIn, pos);
        return shape.move(vec3.x, vec3.y, vec3.z);
    }
}
