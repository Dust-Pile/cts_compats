package net.dusty_dusty.cts_compats.mods.vanilla;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

import static net.dusty_dusty.cts_compats.common.ColorRegistryUtil.getGrassColor;

public class VanillaColorRegistry {

    public static void onColorHandlerEventBlock( RegisterColorHandlersEvent.Block event ) {
        event.register( getGrassColor(), VanillaRegistry.TALL_GRASS_ON_TOP.get() );
        event.register( getGrassColor(), VanillaRegistry.LARGE_FERN_ON_TOP.get() );

        event.register( getGrassColor(), VanillaRegistry.PINK_PETALS_ON_TOP.get() );
    }

    public static void onColorHandlerEventItem( RegisterColorHandlersEvent.Item event ) {
        BlockColors blockColors = event.getBlockColors();

        event.register( (itemstack, tintIndex) -> {
            BlockState state = Blocks.OAK_LEAVES.defaultBlockState();
            return blockColors.getColor(state, null, null, tintIndex);
        }, VanillaRegistry.TALL_GRASS_ON_TOP.get() );

        event.register( (itemstack, tintIndex) -> {
            BlockState state = Blocks.OAK_LEAVES.defaultBlockState();
            return blockColors.getColor(state, null, null, tintIndex);
        }, VanillaRegistry.LARGE_FERN_ON_TOP.get() );
    }
}
