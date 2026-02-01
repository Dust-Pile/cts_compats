package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import biomesoplenty.api.block.BOPBlocks;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.BushBlockOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FoliageOnTopBOP extends BushBlockOnTop implements IPlantable {
    protected static final VoxelShape NORMAL = Block.box(2.0F, -8.0F, 2.0F, 14.0F, 5.0F, 14.0F);
    protected static final VoxelShape SHORT = Block.box(1.0F, -8.0F, 1.0F, 15.0F, -1.0F, 15.0F);

    public FoliageOnTopBOP(Block originalBlock ) {
        super(originalBlock, originalBlock == BOPBlocks.DESERT_GRASS.get() ? SHORT : NORMAL);
    }

    public void playerDestroy(Level worldIn, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity te, @NotNull ItemStack stack) {
        if (!worldIn.isClientSide && stack.getItem() == Items.SHEARS) {
            player.awardStat(Stats.BLOCK_MINED.get(this));
            player.causeFoodExhaustion(0.005F);
            popResource(worldIn, pos, new ItemStack(this));
        } else {
            super.playerDestroy(worldIn, player, pos, state, te, stack);
        }

    }
}
