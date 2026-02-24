package net.dusty_dusty.cts_compats.mods.vanillaBackport.block;

import com.blackgear.vanillabackport.client.level.sound.AmbientDesertBlockSoundsPlayer;
import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.BushBlockOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class DryGrassOnTop extends BushBlockOnTop {
    public DryGrassOnTop( Block originalBlock, VoxelShape SHAPE ) {
        super( originalBlock, SHAPE, new BlockCheckWrapper.Group( BlockTags.DEAD_BUSH_MAY_PLACE_ON ) );
    }

    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random ) {
        AmbientDesertBlockSoundsPlayer.playAmbientDryGrassSounds(level, pos, random);
    }
}
