package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.common.block.FlowerBlockBOP;
import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.dusty_dusty.cts_compats.common.block.interfaces.BlockCopyWrapper;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.FlowerBlockOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
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
import org.jetbrains.annotations.Nullable;

public class FlowerOnTopBOP extends FlowerBlockOnTop {
    protected static final VoxelShape SHORT = Block.box(2.0F, 0.0F, 2.0F, 14.0F, 6.0F, 14.0F);
    protected static final VoxelShape NORMAL = Block.box(5.0F, 0.0F, 5.0F, 11.0F, 10.0F, 11.0F);
    protected static final VoxelShape MEDIUM = Block.box(3.0F, 0.0F, 3.0F, 13.0F, 12.0F, 13.0F);
    protected static final VoxelShape LARGE = Block.box(1.0F, 0.0F, 1.0F, 15.0F, 14.0F, 15.0F);
    private final MobEffect stewEffect;
    private final int stewEffectDuration;

    public FlowerOnTopBOP(Block originalBlock) {
        super( originalBlock );
        stewEffect = ( (FlowerBlockBOP) originalBlock ).getSuspiciousEffect();
        stewEffectDuration = ( (FlowerBlockBOP) originalBlock ).getEffectDuration();
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        Block ground = worldIn.getBlockState(pos.below()).getBlock();
        if ( !( ground instanceof SlabBlock && ground instanceof IBlockCopy slab ) ) {
            return false;
        }

        Block block = this.getOriginBlock();
        ground = new BlockCopyWrapper( slab ).getOriginBlock();
        BlockState groundState = ground.defaultBlockState();

        if (block == BOPBlocks.WILDFLOWER.get()) {
            return ground == Blocks.SAND || ground == Blocks.RED_SAND || ground == BOPBlocks.WHITE_SAND.get()
                    || ground == BOPBlocks.ORANGE_SAND.get() || ground == BOPBlocks.BLACK_SAND.get()
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
        if ( this.getOriginBlock() == BOPBlocks.LAVENDER.get() || this.getOriginBlock() == BOPBlocks.PINK_HIBISCUS.get()) {
            shape = LARGE;
        } else if ( this.getOriginBlock() == BOPBlocks.PINK_DAFFODIL.get() || this.getOriginBlock() == BOPBlocks.WILDFLOWER.get() || this.getOriginBlock() == BOPBlocks.GLOWFLOWER.get() || this.getOriginBlock() == BOPBlocks.WILTED_LILY.get()) {
            shape = MEDIUM;
        } else if ( this.getOriginBlock() == BOPBlocks.VIOLET.get()) {
            shape = SHORT;
        }

        Vec3 vec3 = state.getOffset(worldIn, pos);
        return shape.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public @Nullable PlacementRule getPlacementRule() {
        return PlacementRule.CUSTOM;
    }

    @Override
    public @NotNull MobEffect getSuspiciousEffect() {
        return this.stewEffect;
    }

    public int getEffectDuration() {
        return this.stewEffectDuration;
    }
}
