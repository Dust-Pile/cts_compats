package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.BushBlockOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TinyCactusOnTop extends BushBlockOnTop {
    public TinyCactusOnTop( Block originalBlock ) {
        super(originalBlock, Block.box(3.0F, 0.0F, 3.0F, 13.0F, 13.0F, 13.0F), BlockCheckWrapper.SAND_AND_DIRT );
    }

    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(@NotNull BlockState p_51148_, @NotNull Level p_51149_, @NotNull BlockPos p_51150_, @NotNull Entity p_51151_) {
        if (p_51151_ instanceof Player playerEntity) {
            playerEntity.hurt(p_51149_.damageSources().cactus(), 1.0F);
        }

    }
}
