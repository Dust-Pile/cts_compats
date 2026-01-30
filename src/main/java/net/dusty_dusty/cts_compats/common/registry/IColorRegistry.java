package net.dusty_dusty.cts_compats.common.registry;

import net.minecraftforge.client.event.RegisterColorHandlersEvent;

public interface IColorRegistry {

    void onColorHandlerEventBlock( RegisterColorHandlersEvent.Block event );

    void onColorHandlerEventItem( RegisterColorHandlersEvent.Item event );

}
