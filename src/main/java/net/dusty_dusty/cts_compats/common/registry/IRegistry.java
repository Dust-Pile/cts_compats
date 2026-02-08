package net.dusty_dusty.cts_compats.common.registry;

import net.minecraftforge.eventbus.api.IEventBus;

import java.util.Optional;

public interface IRegistry {

    void assign();

    void register( IEventBus modEventBus );

    default Optional<IColorRegistry> getColorRegistry() {
        return Optional.empty();
    }
}
