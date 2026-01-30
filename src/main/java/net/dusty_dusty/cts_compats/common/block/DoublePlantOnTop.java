package net.dusty_dusty.cts_compats.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DoublePlantOnTop extends BushBlockOnTop {
    protected static final VoxelShape SHAPE = Block.box( 0.0, -8.0, 0.0, 16.0, 24.0, 16.0 );

    public DoublePlantOnTop(Block block) {
        super( (DoublePlantBlock) block, SHAPE );
    }
}
