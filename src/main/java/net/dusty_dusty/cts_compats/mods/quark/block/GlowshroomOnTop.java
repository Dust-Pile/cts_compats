package net.dusty_dusty.cts_compats.mods.quark.block;

import net.dusty_dusty.cts_compats.common.block.onTopBlocks.BushBlockOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GlowshroomOnTop extends BushBlockOnTop {
    public GlowshroomOnTop( Block originalBlock ) {
        super( originalBlock, Block.box( 5.0, 0.0, 5.0, 11.0, 6.0, 11.0 ) );
    }

    public void animateTick(@NotNull BlockState stateIn, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull RandomSource rand) {
        super.animateTick(stateIn, worldIn, pos, rand);
        if (rand.nextInt(12) == 0 && worldIn.getBlockState(pos.above()).isAir()) {
            worldIn.addParticle(ParticleTypes.END_ROD, pos.getX() + 0.4 + rand.nextDouble() * 0.2,
                    pos.getY() + rand.nextDouble() * 0.1, pos.getZ() + 0.4 + rand.nextDouble() * 0.2,
                    (Math.random() - 0.5F) * 0.04, (1.0F + Math.random()) * 0.02, (Math.random() - 0.5F) * 0.04);
        }

    }
}
