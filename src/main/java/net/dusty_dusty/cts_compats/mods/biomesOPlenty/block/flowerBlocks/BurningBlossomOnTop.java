package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.flowerBlocks;

import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.dusty_dusty.cts_compats.common.block.interfaces.BlockCopyWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BurningBlossomOnTop extends FlowerOnTopBOP {
    public BurningBlossomOnTop(Block originalBlock) {
        super( originalBlock );
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext selectionContext) {
        VoxelShape shape = NORMAL;
        Vec3 vec3 = state.getOffset(worldIn, pos);
        return shape.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader worldIn, BlockPos pos) {
        Block ground = worldIn.getBlockState(pos.below()).getBlock();
        if ( !( ground instanceof SlabBlock && ground instanceof IBlockCopy slab ) ) {
            return false;
        }

        ground = new BlockCopyWrapper( slab ).getOriginBlock();
        BlockState groundState = ground.defaultBlockState();

        return ground == Blocks.NETHERRACK || ground == Blocks.SOUL_SAND || ground == Blocks.SOUL_SOIL
                || ground == Blocks.CRIMSON_NYLIUM || ground == Blocks.WARPED_NYLIUM
                || groundState.is(BlockTags.DIRT) || groundState.is(Blocks.FARMLAND );
    }

    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(@NotNull BlockState stateIn, @NotNull Level worldIn, @NotNull BlockPos pos, Entity entityIn) {
        if ( entityIn.getType() != EntityType.HOGLIN && entityIn.getType() != EntityType.PIGLIN && entityIn.getType() != EntityType.PIGLIN_BRUTE ) {
            if (!entityIn.fireImmune()) {
                entityIn.setRemainingFireTicks(entityIn.getRemainingFireTicks() + 1);
                if (entityIn.getRemainingFireTicks() == 0) {
                    entityIn.setSecondsOnFire(1);
                }
            }

            entityIn.hurt(worldIn.damageSources().inFire(), 1.0F);
        }

        super.entityInside(stateIn, worldIn, pos, entityIn);
    }

    public void animateTick(@NotNull BlockState stateIn, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull RandomSource rand) {
        super.animateTick(stateIn, worldIn, pos, rand);
        if (rand.nextInt(8) == 0) {
            worldIn.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5 + (rand.nextDouble() - rand.nextDouble()) / 4.0, pos.getY() + 0.25, pos.getZ() + 0.5 + (rand.nextDouble() - rand.nextDouble()) / 4.0, 0.0F, 0.0F, 0.0F);
        }
        if (rand.nextInt(4) == 0) {
            worldIn.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5 + (rand.nextDouble() - rand.nextDouble()) / 4.0, pos.getY() + 0.25, pos.getZ() + 0.5 + (rand.nextDouble() - rand.nextDouble()) / 4.0, 0.0F, 0.0F, 0.0F);
        }
    }
}
