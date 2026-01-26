package net.dusty_dusty.cts_compats.mods.vanilla.block;

import net.dusty_dusty.cts_compats.common.block.BushBlockOnTop;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PitcherPlantOnTop extends BushBlockOnTop {
    protected static final VoxelShape SHAPE = Block.box(0.0, -8.0, 0.0, 16.0, 20.0, 16.0);

    public PitcherPlantOnTop(Block block) {
        super(block, SHAPE);
    }
}
