package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import net.dusty_dusty.cts_compats.common.block.onTopBlocks.DoublePlantOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.PlantType;

public class DoublePlantBOP extends DoublePlantOnTop {
    public DoublePlantBOP(Block originalBlock) {
        super(originalBlock);
    }

    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.PLAINS;
    }
}
