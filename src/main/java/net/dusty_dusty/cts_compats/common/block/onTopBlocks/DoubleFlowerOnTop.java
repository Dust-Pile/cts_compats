package net.dusty_dusty.cts_compats.common.block.onTopBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DoubleFlowerOnTop extends DoublePlantOnTop implements BonemealableBlock {

    public DoubleFlowerOnTop(Block originalBlock) {
        super(originalBlock);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader p_256234_, @NotNull BlockPos p_57304_, @NotNull BlockState p_57305_, boolean p_57306_) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level p_222573_, @NotNull RandomSource p_222574_, @NotNull BlockPos p_222575_, @NotNull BlockState p_222576_) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel p_222568_, @NotNull RandomSource p_222569_, @NotNull BlockPos p_222570_, @NotNull BlockState p_222571_) {
        popResource(p_222568_, p_222570_, new ItemStack(this));
    }
}
