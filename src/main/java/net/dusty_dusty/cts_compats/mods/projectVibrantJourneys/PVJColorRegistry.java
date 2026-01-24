package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

public class PVJColorRegistry {

    public static void onColorHandlerEventBlock( RegisterColorHandlersEvent.Block event ) {
        event.register( getGrassColor(), PVJRegistry.SHORTER_GRASS_ON_TOP.get() );
    }

    public static void onColorHandlerEventItem( RegisterColorHandlersEvent.Item event ) {
        BlockColors blockColors = event.getBlockColors();

        event.register( (itemstack, tintIndex) -> {
            BlockState state = Blocks.OAK_LEAVES.defaultBlockState();
            return blockColors.getColor(state, null, null, tintIndex);
        }, PVJRegistry.SHORTER_GRASS_ON_TOP.get() );
    }

    private static BlockColor getGrassColor() {
        return ( state, world, pos, tintIndex) -> (world != null && pos != null )
                ? BiomeColors.getAverageGrassColor( world, pos )
                : GrassColor.get( 0.5D, 1.0D );
    }
}
