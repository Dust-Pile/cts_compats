package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.block;

import net.dusty_dusty.cts_compats.common.block.BushBlockOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.PlantType;

public class BeachGrassOnTop extends BushBlockOnTop {
    public BeachGrassOnTop( Block originalBlock ) {
        super( originalBlock, Block.box(2, -8, 2, 14, 5, 14) );
    }

    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.DESERT;
    }
}
