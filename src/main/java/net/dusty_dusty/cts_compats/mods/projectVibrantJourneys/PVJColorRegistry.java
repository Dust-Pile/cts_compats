package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys;

import net.dusty_dusty.cts_compats.common.registry.AbstractColorRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

public class PVJColorRegistry extends AbstractColorRegistry {

    public void onColorHandlerEventBlock( RegisterColorHandlersEvent.Block event ) {
        event.register(getGrassColor(), PVJRegistry.SHORTER_GRASS_ON_TOP.get());
        event.register(getFoliageColor(), PVJRegistry.FALLEN_LEAVES_ON_TOP.get());
        // Block Only
        event.register(getGrassColor(), PVJRegistry.BLUE_WILDFLOWERS_ON_TOP.get());
        event.register(getGrassColor(), PVJRegistry.ORANGE_WILDFLOWERS_ON_TOP.get());
        event.register(getGrassColor(), PVJRegistry.PURPLE_WILDFLOWERS_ON_TOP.get());
        event.register(getGrassColor(), PVJRegistry.YELLOW_WILDFLOWERS_ON_TOP.get());
        event.register(getGrassColor(), PVJRegistry.WHITE_WILDFLOWERS_ON_TOP.get());
        event.register(getGrassColor(), PVJRegistry.MIXED_WILDFLOWERS_ON_TOP.get());
    }

    public void onColorHandlerEventItem( RegisterColorHandlersEvent.Item event ) {
        BlockColors blockColors = event.getBlockColors();

        event.register((itemstack, tintIndex) -> {
            BlockState state = Blocks.OAK_LEAVES.defaultBlockState();
            return blockColors.getColor(state, null, null, tintIndex);
        }, PVJRegistry.FALLEN_LEAVES_ON_TOP.get());

        event.register( (itemstack, tintIndex) -> {
            BlockState state = Blocks.OAK_LEAVES.defaultBlockState();
            return blockColors.getColor(state, null, null, tintIndex);
        }, PVJRegistry.SHORTER_GRASS_ON_TOP.get() );
    }
}
