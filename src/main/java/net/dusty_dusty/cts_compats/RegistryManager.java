package net.dusty_dusty.cts_compats;

import net.dusty_dusty.cts_compats.common.registry.IColorRegistry;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.BOPRegistry;
import net.dusty_dusty.cts_compats.mods.vanilla.VanillaRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.LOGGER;

public final class RegistryManager {
    final IEventBus MOD_EVENT_BUS;
    final ArrayList<IRegistry> REGISTRIES = new ArrayList<>();

    static final Map<String, String> LOADED_VERSIONS = new HashMap<>();
    static final Map<String, IRegistry> LOADED_MODS = new HashMap<>();
    private static RegistryManager INSTANCE;
    public static RegistryManager getInstance() {
        return INSTANCE;
    }

    public static IRegistry getRegistry( String modId ) {
        return LOADED_MODS.get( modId );
    }
    public static String getVersion( String modId ) {
        return LOADED_VERSIONS.get( modId );
    }

    RegistryManager(IEventBus modEventBus ) throws IllegalAccessException {
        if ( INSTANCE != null ) {
            throw new IllegalAccessException( "RegistryManager can only be instantiated once" );
        }

        this.MOD_EVENT_BUS = modEventBus;
        modEventBus.addListener( this::commonSetup );
        modEventBus.addListener( this::clientSetup );

        register( "minecraft", VanillaRegistry.getInstance() );
        INSTANCE = this;
    }

    public static void forEachRegistry( Consumer<IRegistry> consumer ) {
        INSTANCE.REGISTRIES.forEach( consumer );
    }
    public static void forEachRegistryAndID( final BiConsumer<String, IRegistry> biConsumer ) {
        LOADED_MODS.forEach( biConsumer::accept );
    }

    public boolean register( String modId, Supplier<IRegistry> registrySupplier ) {
        return runModCompat( modId, () -> register( modId, registrySupplier.get() ) );
    }
    private void register( String modId, IRegistry registry ) {
        REGISTRIES.add( registry );
        LOADED_MODS.put( modId, registry );
        registry.register( MOD_EVENT_BUS );
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        REGISTRIES.forEach( IRegistry::assign );
    }

    @SuppressWarnings("Convert2MethodRef") // Method Reference loads class. Unacceptable.
    private void clientSetup(final FMLClientSetupEvent event) {
        runModCompat( IRegistry.BOP_MODID, () -> BOPRegistry.setRenderTypes() );
    }

    @Mod.EventBusSubscriber( modid = CTSCompats.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        public static void onColorHandlerEventBlock(RegisterColorHandlersEvent.Block event) {
            INSTANCE.REGISTRIES.forEach( registry -> {
                Optional<IColorRegistry> colorRegistry = registry.getColorRegistry();
                colorRegistry.ifPresent(iColorRegistry -> iColorRegistry.onColorHandlerEventBlock(event));
            } );
        }

        @SubscribeEvent
        public static void onColorHandlerEventItem(RegisterColorHandlersEvent.Item event) {
            INSTANCE.REGISTRIES.forEach( registry -> {
                Optional<IColorRegistry> colorRegistry = registry.getColorRegistry();
                colorRegistry.ifPresent(iColorRegistry -> iColorRegistry.onColorHandlerEventItem( event ));
            } );
        }
    }

    private static boolean runModCompat( String modid, Runnable register ) {
        if ( ModList.get().isLoaded( modid ) ) {
            LOADED_VERSIONS.put( modid, ModList.get().getModFileById( modid ).versionString() );
            LOGGER.info( "Loading Runnable for {} version {}.", modid, LOADED_VERSIONS.get( modid ) );
            register.run();
            return true;
        }
        return false;
    }

}
