package net.dusty_dusty.cts_compats.common.registry;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.mods.vanilla.VanillaRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.LOGGER;

public final class RegistryManager {
    final IEventBus MOD_EVENT_BUS;
    final ArrayList<IRegistry> REGISTRIES = new ArrayList<>();

    private static RegistryManager INSTANCE;
    public static RegistryManager getInstance() {
        return INSTANCE;
    }

    public RegistryManager(IEventBus modEventBus ) throws IllegalAccessException {
        if ( INSTANCE != null ) {
            throw new IllegalAccessException( "RegistryHolder can only be instantiated Once" );
        }
        this.MOD_EVENT_BUS = modEventBus;
        modEventBus.addListener( this::commonSetup );
        register( VanillaRegistry.getInstance() );
        INSTANCE = this;
    }

    public boolean register( String modid, Supplier<IRegistry> registrySupplier ) {
        return runModCompat( modid, () -> {
            register( registrySupplier.get() );
        } );
    }
    private void register( IRegistry registry ) {
        REGISTRIES.add( registry );
        registry.register( MOD_EVENT_BUS );
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        REGISTRIES.forEach( IRegistry::assign );
    }

    @Mod.EventBusSubscriber( modid = CTSCompats.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
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

    private static boolean runModCompat( String modid, Runnable initializer ) {
        if ( ModList.get().isLoaded( modid ) ) {
            LOGGER.info( "Loading Runnable for {}.", modid );
            initializer.run();
            return true;
        }
        return false;
    }

}
