package net.dusty_dusty.cts_compats.mods.biomesOPlenty;

import net.dusty_dusty.cts_compats.common.registry.AbstractColorRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPBaseRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

public class BOPColorRegistry extends AbstractColorRegistry {
    @Override
    public void onColorHandlerEventBlock(RegisterColorHandlersEvent.Block event) {
        event.register(getFoliageColor(), BOPBaseRegistry.BUSH_ON_TOP.get() );
        event.register(getGrassColor(),
                BOPBaseRegistry.MOSSY_BLACK_SAND_SLAB.get(),
                BOPBaseRegistry.WHITE_PETALS_ON_TOP.get(),
                BOPBaseRegistry.SPROUT_ON_TOP.get(),
                BOPBaseRegistry.CLOVER_ON_TOP.get(),
                BOPBaseRegistry.BARLEY_ON_TOP.get()
        );
    }

    @Override
    public void onColorHandlerEventItem(RegisterColorHandlersEvent.Item event) {
        BlockColors blockColors = event.getBlockColors();

        event.register((itemstack, tintIndex) -> {
            BlockState state = Blocks.GRASS.defaultBlockState();
            return blockColors.getColor(state, null, null, tintIndex);
        },
                BOPBaseRegistry.MOSSY_BLACK_SAND_SLAB.get()
        );
    }
}
