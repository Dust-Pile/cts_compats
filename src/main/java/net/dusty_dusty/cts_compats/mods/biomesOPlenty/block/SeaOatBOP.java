package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.DoublePlantOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.PlantType;

import java.util.ArrayList;

public class SeaOatBOP extends DoublePlantOnTop {
    public SeaOatBOP( Block originalBlock ) {
        super( originalBlock, new BlockCheckWrapper.Group( Blocks.SAND ) );
    }

    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.DESERT;
    }
}
