package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.flowerBlocks;

import biomesoplenty.api.block.BOPBlocks;
import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.dusty_dusty.cts_compats.common.block.interfaces.BlockCopyWrapper;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FlowerOnTopBOP extends AbstractFlowerOnTopBOP {
    private final VoxelShape SHAPE;

    public FlowerOnTopBOP(Block originalBlock) {
        super( originalBlock, ( (FlowerBlock) originalBlock ).getSuspiciousEffect(),
                ( (FlowerBlock) originalBlock ).getEffectDuration()
        );
        if (originalBlock == IRegistry.getBlock( BOPBlocks.LAVENDER )
                || originalBlock == IRegistry.getBlock( BOPBlocks.WHITE_LAVENDER )
                || originalBlock == IRegistry.getBlock( BOPBlocks.PINK_HIBISCUS )
        ) {
            this.SHAPE = LARGE;
        } else if (originalBlock == IRegistry.getBlock( BOPBlocks.PINK_DAFFODIL )
                || originalBlock == IRegistry.getBlock( BOPBlocks.GLOWFLOWER )
                || originalBlock == IRegistry.getBlock( BOPBlocks.WILTED_LILY )
        ) {
            this.SHAPE = MEDIUM;
        } else if (originalBlock == IRegistry.getBlock( BOPBlocks.VIOLET )
                || originalBlock == IRegistry.getBlock( BOPBlocks.ENDBLOOM )
        ) {
            this.SHAPE = SHORT;
        } else {
            this.SHAPE = NORMAL;
        }
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext selectionContext) {
        Vec3 vec3 = state.getOffset(worldIn, pos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader worldIn, BlockPos pos) {
        Block ground = worldIn.getBlockState(pos.below()).getBlock();
        if ( !( ground instanceof SlabBlock && ground instanceof IBlockCopy slab ) ) {
            return false;
        }

        Block block = this.getOriginBlock();
        ground = new BlockCopyWrapper( slab ).getOriginBlock();
        BlockState groundState = ground.defaultBlockState();

        if ( block == IRegistry.getBlock( BOPBlocks.WILTED_LILY ) ) {
            return ground == IRegistry.getBlock( BOPBlocks.DRIED_SALT )
                    || groundState.is(BlockTags.DIRT) || groundState.is(Blocks.FARMLAND );
        } else if ( block != IRegistry.getBlock( BOPBlocks.ENDBLOOM ) ) {
            return groundState.is(BlockTags.DIRT) || groundState.is(Blocks.FARMLAND );
        } else {
            return ground == IRegistry.getBlock( BOPBlocks.ALGAL_END_STONE )
                    || ground == IRegistry.getBlock( BOPBlocks.UNMAPPED_END_STONE )
                    || ground == IRegistry.getBlock( BOPBlocks.NULL_END_STONE )
                    || ground == Blocks.END_STONE || groundState.is(BlockTags.DIRT) || groundState.is(Blocks.FARMLAND );
        }
    }
}
