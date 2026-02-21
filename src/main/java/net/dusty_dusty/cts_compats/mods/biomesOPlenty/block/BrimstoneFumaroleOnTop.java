package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import net.dusty_dusty.cts_compats.common.block.onTopBlocks.BasicOnTopBlock;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.BOPRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BrimstoneFumaroleOnTop extends BasicOnTopBlock {
    protected static final VoxelShape SHAPE_TOP = Block.box(4.0F, 0.0F, 4.0F, 12.0F, 8.0F, 12.0F);
    protected static final VoxelShape SHAPE_BOTTOM = Block.box(2.0F, -8.0F, 2.0F, 14.0F, 0.0F, 14.0F);
    protected static final VoxelShape FULL_SHAPE;

    public BrimstoneFumaroleOnTop( Block originalBlock ) {
        super( originalBlock, FULL_SHAPE, BOPRegistry.PlaceType.BRIMSTONE );
    }

    public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, Entity entity) {
        if (!entity.fireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.hurt( level.damageSources().inFire(), 1.0F);
        }

        super.stepOn(level, pos, state, entity);
    }

    public void animateTick(@NotNull BlockState stateIn, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull RandomSource rand) {
        super.animateTick(stateIn, worldIn, pos, rand);
        if (worldIn.getBlockState(pos.above()).isAir()) {
            worldIn.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, (double)pos.getX() + (double)0.5F + (rand.nextDouble() - rand.nextDouble()) / (double)6.0F, (double)pos.getY() + (double)1.0F, (double)pos.getZ() + (double)0.5F + (rand.nextDouble() - rand.nextDouble()) / (double)6.0F, 0.0F, 0.02, 0.0F);
            if (rand.nextInt(6) == 0) {
                for(int i = 0; i < 5; ++i) {
                    worldIn.addParticle(ParticleTypes.LAVA, (double)pos.getX() + (double)0.5F + (rand.nextDouble() - rand.nextDouble()) / (double)6.0F, (double)pos.getY() + (double)1.0F, (double)pos.getZ() + (double)0.5F + (rand.nextDouble() - rand.nextDouble()) / (double)6.0F, 0.0F, 0.0F, 0.0F);
                }
            }
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isPathfindable(@NotNull BlockState p_154341_, @NotNull BlockGetter p_154342_, @NotNull BlockPos p_154343_, @NotNull PathComputationType p_154344_) {
        return false;
    }

    static {
        FULL_SHAPE = Shapes.or(SHAPE_TOP, SHAPE_BOTTOM);
    }
}
