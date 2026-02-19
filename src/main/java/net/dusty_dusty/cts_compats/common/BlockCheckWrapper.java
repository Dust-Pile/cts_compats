package net.dusty_dusty.cts_compats.common;

import dev.orderedchaos.projectvibrantjourneys.common.tags.ForgeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlockCheckWrapper {
    public static final ArrayList<BlockCheckWrapper> SAND_AND_DIRT = new ArrayList<>();
    public static ArrayList<BlockCheckWrapper> WATER_PLANT_PLACEABLE = new ArrayList<>();
    static {
        SAND_AND_DIRT.add( new BlockCheckWrapper( BlockTags.SAND ) );
        SAND_AND_DIRT.add( new BlockCheckWrapper( BlockTags.DIRT ) );

        WATER_PLANT_PLACEABLE.add( new BlockCheckWrapper( BlockTags.DIRT ) );
        WATER_PLANT_PLACEABLE.add( new BlockCheckWrapper( BlockTags.SAND ) );
        WATER_PLANT_PLACEABLE.add( new BlockCheckWrapper( ForgeTags.GRAVEL ) );
        WATER_PLANT_PLACEABLE.add( new BlockCheckWrapper( ForgeTags.SAND ) );
        WATER_PLANT_PLACEABLE.add( new BlockCheckWrapper( Blocks.CLAY ) );
        WATER_PLANT_PLACEABLE.add( new BlockCheckWrapper( BlockTags.BIG_DRIPLEAF_PLACEABLE ) );
    }

    private final Optional<Block> blockOption;
    private final Optional<TagKey<Block>> blockTagOption;

    public BlockCheckWrapper( Block block ) {
        this.blockOption = Optional.of( block );
        this.blockTagOption = Optional.empty();
    }
    public BlockCheckWrapper( TagKey<Block> blockTag ) {
        this.blockTagOption = Optional.of( blockTag );
        this.blockOption = Optional.empty();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public boolean check(BlockState state ) {
        return blockOption.map(state::is).orElseGet(() -> state.is(blockTagOption.get()));
    }

    public static boolean checkAll( BlockState state, List<BlockCheckWrapper> blockChecks ) {
        for ( BlockCheckWrapper blockCheck : blockChecks ) {
            if ( blockCheck.check( state ) ) {
                return true;
            }
        }
        return false;
    }
}
