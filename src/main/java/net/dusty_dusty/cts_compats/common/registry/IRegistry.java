package net.dusty_dusty.cts_compats.common.registry;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.Optional;

public interface IRegistry {
    String PVJ_MODID = "projectvibrantjourneys";
    String BOP_MODID = "biomesoplenty";

    String getModID();

    void assign();

    void register( IEventBus modEventBus );

    Collection<RegistryObject<Block>> getRegistryBlocks();

    Optional<IColorRegistry> getColorRegistry();

    static Block getBlock( Object object ) {
        if ( object instanceof Block block ) {
            return block;
        } else if ( object instanceof RegistryObject<?> register ) {
            if ( register.get() instanceof Block block ) {
                return block;
            }
        }
        throw new IllegalArgumentException( "Object " + object + " is not a block or block registry object." );
    }
}
