package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.block;

import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.DoubleWaterPlantOnTop;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class PVJTallWaterPlant extends DoubleWaterPlantOnTop {
    public PVJTallWaterPlant( Block originalBlock ) {
        super( originalBlock, BlockCheckWrapper.WATER_PLANT_PLACEABLE );
    }
}
