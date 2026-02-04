package net.dusty_dusty.cts_compats.common.registry;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.Optional;

public interface IRegistry {

    void assign();

    void register( IEventBus modEventBus );

    Collection<RegistryObject<Block>> getRegistryBlocks();

    Optional<IColorRegistry> getColorRegistry();
}
