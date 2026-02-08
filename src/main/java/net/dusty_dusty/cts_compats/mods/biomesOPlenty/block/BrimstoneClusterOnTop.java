package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import biomesoplenty.init.ModTags;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.DoublePlantOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;

public class BrimstoneClusterOnTop extends DoublePlantOnTop {
    protected static final VoxelShape SHAPE = Block.box(3.0F, -8.0F, 3.0F, 13.0F, 8.0F, 13.0F);
    protected static final VoxelShape SHAPE_TOP = Block.box(6.0F, -8.0F, 6.0F, 10.0F, 0.0F, 10.0F);

    public BrimstoneClusterOnTop(Block originalBlock) {
        super(originalBlock);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext selectionContext) {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER ? SHAPE_TOP : SHAPE;
    }

    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.NETHER;
    }

    public boolean isPathfindable(@NotNull BlockState p_154341_, @NotNull BlockGetter p_154342_, @NotNull BlockPos p_154343_, @NotNull PathComputationType p_154344_) {
        return false;
    }
}
