package net.dusty_dusty.cts_compats.mods.biomesOPlenty;

import net.dusty_dusty.cts_compats.common.registry.AbstractColorRegistry;
import net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.PVJRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

public class BOPColorRegistry extends AbstractColorRegistry {
    @Override
    public void onColorHandlerEventBlock(RegisterColorHandlersEvent.Block event) {
        event.register(getFoliageColor(), BOPRegistry.BUSH_ON_TOP.get() );
        event.register(getGrassColor(),
                BOPRegistry.MOSSY_BLACK_SAND_SLAB.get(),
                BOPRegistry.WHITE_PETALS_ON_TOP.get(),
                BOPRegistry.SPROUT_ON_TOP.get(),
                BOPRegistry.CLOVER_ON_TOP.get(),
                BOPRegistry.BARLEY_ON_TOP.get()
        );
    }

    @Override
    public void onColorHandlerEventItem(RegisterColorHandlersEvent.Item event) {
        BlockColors blockColors = event.getBlockColors();

        event.register((itemstack, tintIndex) -> {
            BlockState state = Blocks.GRASS.defaultBlockState();
            return blockColors.getColor(state, null, null, tintIndex);
        },
                BOPRegistry.MOSSY_BLACK_SAND_SLAB.get()
        );
    }
}
