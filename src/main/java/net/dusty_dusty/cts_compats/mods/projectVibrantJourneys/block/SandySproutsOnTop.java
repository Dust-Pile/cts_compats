package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.block;

import net.dusty_dusty.cts_compats.common.block.onTopBlocks.PetalBlockOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.PlantType;

public class SandySproutsOnTop extends PetalBlockOnTop {
    public SandySproutsOnTop( Block originalBlock ) {
        super( originalBlock );
        this.registerDefaultState( this.stateDefinition.any().setValue(FACING, Direction.NORTH) );
    }

    public PlantType getPlantType( BlockGetter world, BlockPos pos) {
        return PlantType.DESERT;
    }
}
