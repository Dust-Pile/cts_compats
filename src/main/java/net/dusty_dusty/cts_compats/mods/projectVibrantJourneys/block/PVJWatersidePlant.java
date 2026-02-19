package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.block;

import dev.orderedchaos.projectvibrantjourneys.common.tags.ForgeTags;
import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.DoubleWatersidePlantOnTop;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;

public class PVJWatersidePlant extends DoubleWatersidePlantOnTop {
    public static ArrayList<BlockCheckWrapper> WATER_PLANT_PLACEABLE = new ArrayList<>();
    static {
        WATER_PLANT_PLACEABLE.add( new BlockCheckWrapper( BlockTags.DIRT ) );
        WATER_PLANT_PLACEABLE.add( new BlockCheckWrapper( BlockTags.SAND ) );
        WATER_PLANT_PLACEABLE.add( new BlockCheckWrapper( ForgeTags.GRAVEL ) );
        WATER_PLANT_PLACEABLE.add( new BlockCheckWrapper( ForgeTags.SAND ) );
        WATER_PLANT_PLACEABLE.add( new BlockCheckWrapper( Blocks.CLAY ) );
        WATER_PLANT_PLACEABLE.add( new BlockCheckWrapper( BlockTags.BIG_DRIPLEAF_PLACEABLE ) );
    }

    public PVJWatersidePlant( Block originalBlock ) {
        super( originalBlock, false, WATER_PLANT_PLACEABLE );
    }
}
