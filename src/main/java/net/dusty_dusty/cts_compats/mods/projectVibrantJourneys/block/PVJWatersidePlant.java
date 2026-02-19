package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.block;

import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.DoubleWatersidePlantOnTop;
import net.minecraft.world.level.block.Block;

public class PVJWatersidePlant extends DoubleWatersidePlantOnTop {
    public PVJWatersidePlant( Block originalBlock ) {
        super( originalBlock, false, BlockCheckWrapper.WATER_PLANT_PLACEABLE );
    }
}
