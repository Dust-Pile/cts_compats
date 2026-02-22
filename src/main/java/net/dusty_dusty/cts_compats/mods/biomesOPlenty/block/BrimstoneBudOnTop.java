package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import net.dusty_dusty.cts_compats.common.block.onTopBlocks.BasicOnTopBlock;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class BrimstoneBudOnTop extends BasicOnTopBlock {
    public BrimstoneBudOnTop( Block originalBlock ) {
        super( originalBlock, Block.box( 2.0F, -8.0F, 2.0F, 14.0F, -5.0F, 14.0F ),
                BOPRegistry.PlaceType.BRIMSTONE
        );
    }

    public @NotNull BlockState updateShape(BlockState p_51032_, @NotNull Direction p_51033_, @NotNull BlockState p_51034_, @NotNull LevelAccessor p_51035_, @NotNull BlockPos p_51036_, @NotNull BlockPos p_51037_) {
        return !p_51032_.canSurvive(p_51035_, p_51036_) ? net.minecraft.world.level.block.Blocks.AIR.defaultBlockState() : super.updateShape(p_51032_, p_51033_, p_51034_, p_51035_, p_51036_, p_51037_);
    }

    public boolean canBeReplaced(@NotNull BlockState p_53910_, @NotNull BlockPlaceContext p_53911_) {
        return true;
    }
}
