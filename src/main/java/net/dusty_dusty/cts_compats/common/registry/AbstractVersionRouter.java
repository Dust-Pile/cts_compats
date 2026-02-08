package net.dusty_dusty.cts_compats.common.registry;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.RegistryManager;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public abstract class AbstractVersionRouter implements IRegistry {
    private final Map<String, Supplier<IRegistry>> VERSION_FILTER;
    final IRegistry REGISTRY;

    protected AbstractVersionRouter( String modid, Map<String, Supplier<IRegistry>> versionFilter ) {
        VERSION_FILTER = versionFilter;
        REGISTRY = getRegistryFromVersion( modid, RegistryManager.LOADED_MODS.get( modid ) );
    }

    private IRegistry getRegistryFromVersion( String modid, String version ) {
        Set<String> filters = VERSION_FILTER.keySet();
        for ( String filter : filters ) {
            if ( isInVersionFilter( filter, version ) ) {
                return VERSION_FILTER.get( filter ).get();
            }
        }
        String err = "No filter matches version " + version + " of mod " + modid + " in registered version router.";
        CTSCompats.LOGGER.error( err );
        throw new IllegalArgumentException( err );
    }

    // TODO: Add more rules as needed
    private static boolean isInVersionFilter( String filter, String version ) {
        return filter.contains("*");
    }

    public void register( IEventBus modEventBus ) {
        REGISTRY.register( modEventBus );
    }

    public void assign() {
        REGISTRY.assign();
    }

    public Optional<IColorRegistry> getColorRegistry() {
        return REGISTRY.getColorRegistry();
    }

    public Collection<RegistryObject<Block>> getRegistryBlocks() {
        return REGISTRY.getRegistryBlocks();
    }
}
