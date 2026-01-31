package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import biomesoplenty.init.ModParticles;
import biomesoplenty.init.ModTags;
import net.dusty_dusty.cts_compats.common.block.BasicOnTopBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class PusBubbleOnTop extends BasicOnTopBlock {
    protected static final VoxelShape SHAPE = Block.box(3.0F, 0.0F, 3.0F, 13.0F, 8.0F, 13.0F);

    public PusBubbleOnTop( Block originalBlock ) {
        super( originalBlock, SHAPE );
    }

    @Override
    public @NotNull BlockState updateShape(BlockState p_51032_, @NotNull Direction p_51033_, @NotNull BlockState p_51034_, @NotNull LevelAccessor p_51035_, @NotNull BlockPos p_51036_, @NotNull BlockPos p_51037_) {
        return !p_51032_.canSurvive(p_51035_, p_51036_) ? net.minecraft.world.level.block.Blocks.AIR.defaultBlockState() : super.updateShape(p_51032_, p_51033_, p_51034_, p_51035_, p_51036_, p_51037_);
    }

    @Override
    public void onProjectileHit(Level p_57381_, @NotNull BlockState p_57382_, BlockHitResult p_57383_, @NotNull Projectile p_57384_) {
        p_57381_.destroyBlock(p_57383_.getBlockPos(), false);
        spawnParticles(p_57381_, p_57383_.getBlockPos());
    }

    @Override
    public void attack(@NotNull BlockState p_55467_, @NotNull Level p_55468_, @NotNull BlockPos p_55469_, @NotNull Player p_55470_) {
        spawnParticles(p_55468_, p_55469_);
        super.attack(p_55467_, p_55468_, p_55469_, p_55470_);
    }

    public void wasExploded(@NotNull Level p_54184_, @NotNull BlockPos p_54185_, @NotNull Explosion p_54186_) {
        if (p_54184_ instanceof ServerLevel) {
            spawnParticles(p_54184_, p_54185_);
        }

    }

    @Override
    public void entityInside(@NotNull BlockState stateIn, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            worldIn.destroyBlock(pos, false);
            spawnParticles(worldIn, pos);
        }

    }

    public static void spawnParticles(Level p_55480_, BlockPos pos) {
        RandomSource rand = p_55480_.random;

        for(int i = 0; i < 10; ++i) {
            p_55480_.addParticle(ModParticles.PUS.get(), (double)pos.getX() + (double)0.5F + (rand.nextDouble() - rand.nextDouble()) / (double)8.0F, (double)pos.getY() + (double)0.25F, (double)pos.getZ() + (double)0.5F + (rand.nextDouble() - rand.nextDouble()) / (double)8.0F, 0.0F, 0.0F, 0.0F);
        }

    }

    @Override
    public boolean canBeReplaced(@NotNull BlockState p_53910_, @NotNull BlockPlaceContext p_53911_) {
        return true;
    }
}
