package net.dusty_dusty.cts_compats.mods.quark.block;

import net.dusty_dusty.cts_compats.common.block.onTopBlocks.BushBlockOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GlowLichenGrowthOnTop extends BushBlockOnTop {
    public GlowLichenGrowthOnTop( Block originalBlock ) {
        super( originalBlock, Block.box( 5.0, 0.0, 5.0, 11.0, 6.0, 11.0 ) );
    }

    @Override
    public void animateTick(@NotNull BlockState stateIn, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull RandomSource rand) {
        super.animateTick(stateIn, worldIn, pos, rand);

        for(int i = 0; i < 10; ++i) {
            worldIn.addParticle(ParticleTypes.MYCELIUM, pos.getX() + (Math.random() - 0.5F) * 5.0F + 0.5F, pos.getY()
                    + (Math.random() - 0.5F) * 8.0F + 0.5F, pos.getZ() + (Math.random() - 0.5F) * 5.0F, 0.0F, 0.0F, 0.0F);
        }

        worldIn.addParticle(ParticleTypes.MYCELIUM, pos.getX() + (Math.random() - 0.5F) * 0.4 + 0.5F, pos.getY()
                + (Math.random() - 0.5F) * 0.3 + 0.3, pos.getZ() + (Math.random() - 0.5F) * 0.4, 0.0F, 0.0F, 0.0F);
    }

}
