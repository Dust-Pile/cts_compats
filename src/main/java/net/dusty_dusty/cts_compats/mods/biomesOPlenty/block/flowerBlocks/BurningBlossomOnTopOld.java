package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.flowerBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BurningBlossomOnTopOld extends FlowerOnTopOldBOP {
    public BurningBlossomOnTopOld(Block originalBlock) {
        super(originalBlock);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void entityInside(@NotNull BlockState stateIn, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull Entity entityIn) {
        if ((entityIn.getType() != EntityType.HOGLIN) && (entityIn.getType() != EntityType.PIGLIN) && (entityIn.getType() != EntityType.PIGLIN_BRUTE)) {
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
            worldIn.addParticle(ParticleTypes.FLAME,  pos.getX() +  0.5F + ((rand.nextDouble() - rand.nextDouble()) /  4.0F), pos.getY() + 0.75F,  pos.getZ() +  0.5F + ((rand.nextDouble() - rand.nextDouble()) /  4.0F), 0.0F, 0.0F, 0.0F);
        }
        if (rand.nextInt(4) == 0) {
            worldIn.addParticle(ParticleTypes.SMOKE,  pos.getX() +  0.5F + ((rand.nextDouble() - rand.nextDouble()) /  4.0F), pos.getY() + 0.75F,  pos.getZ() +  0.5F + ((rand.nextDouble() - rand.nextDouble()) /  4.0F), 0.0F, 0.0F, 0.0F);
        }
    }
}
