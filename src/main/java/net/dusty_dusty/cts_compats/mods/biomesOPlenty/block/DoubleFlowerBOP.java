package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import net.dusty_dusty.cts_compats.common.block.onTopBlocks.DoubleFlowerOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.PlantType;

public class DoubleFlowerBOP extends DoubleFlowerOnTop {
    public DoubleFlowerBOP(Block originalBlock) {
        super(originalBlock);
    }

    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.PLAINS;
    }
}
