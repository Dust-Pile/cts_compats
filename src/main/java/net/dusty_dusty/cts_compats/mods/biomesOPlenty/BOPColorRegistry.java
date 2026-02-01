package net.dusty_dusty.cts_compats.mods.biomesOPlenty;

import net.dusty_dusty.cts_compats.common.registry.AbstractColorRegistry;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

public class BOPColorRegistry extends AbstractColorRegistry {
    @Override
    public void onColorHandlerEventBlock(RegisterColorHandlersEvent.Block event) {
        event.register(getFoliageColor(), BOPRegistry.BUSH_ON_TOP.get() );
        event.register(getGrassColor(), BOPRegistry.MOSSY_BLACK_SAND_SLAB.get());
        event.register(getGrassColor(), BOPRegistry.WHITE_PETALS_ON_TOP.get());
        event.register(getGrassColor(), BOPRegistry.SPROUT_ON_TOP.get());
        event.register(getGrassColor(), BOPRegistry.CLOVER_ON_TOP.get());
    }

    @Override
    public void onColorHandlerEventItem(RegisterColorHandlersEvent.Item event) {

    }
}
