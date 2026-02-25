package net.dusty_dusty.cts_compats.mods.quark.block;

import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.BushBlockOnTop;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ChorusPlantOnTop extends BushBlockOnTop {
    public ChorusPlantOnTop( Block originalBlock ) {
        super( originalBlock, Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0 ), new BlockCheckWrapper.Group( Blocks.END_STONE ) );
    }
}
