package net.dusty_dusty.cts_compats.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DoublePlantOnTop extends BushBlockOnTop {
    protected static final VoxelShape SHAPE = Block.box(0.0, -8.0, 0.0, 16.0, 24.0, 16.0);

    public DoublePlantOnTop(Block block) {
        super( block, SHAPE );
    }
}
