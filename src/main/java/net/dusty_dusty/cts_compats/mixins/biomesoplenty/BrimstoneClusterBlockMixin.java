package net.dusty_dusty.cts_compats.mixins.biomesoplenty;

import biomesoplenty.common.block.BrimstoneClusterBlock;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin( BrimstoneClusterBlock.class )
public abstract class BrimstoneClusterBlockMixin extends BushBlock {
    // Hopefully private constructor is not an issue?
    private BrimstoneClusterBlockMixin(Properties pProperties, String diff ) {
        super(pProperties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }
}
