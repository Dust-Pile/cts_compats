package net.dusty_dusty.cts_compats;

import net.dusty_dusty.cts_compats.common.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.common.registry.IColorRegistry;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;
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
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.LOGGER;

public class RegistryManager {
    final IEventBus MOD_EVENT_BUS;
    final ArrayList<AbstractRegistry> REGISTRIES = new ArrayList<>();

    private static RegistryManager INSTANCE;
    public static RegistryManager getInstance() {
        return INSTANCE;
    }

    protected RegistryManager(IEventBus modEventBus ) throws IllegalAccessException {
        if ( INSTANCE != null ) {
            throw new IllegalAccessException( "RegistryHolder can only be instantiated Once" );
        }
        this.MOD_EVENT_BUS = modEventBus;
        modEventBus.addListener( this::commonSetup );
        register( VanillaRegistry.getInstance() );
        INSTANCE = this;
    }

    public static void forEachRegistry( Consumer<AbstractRegistry> consumer ) {
        INSTANCE.REGISTRIES.forEach( consumer );
    }

    public boolean register( String modid, Supplier<AbstractRegistry> registrySupplier ) {
        return runModCompat( modid, () -> register( registrySupplier.get() ) );
    }
    private void register( AbstractRegistry registry ) {
        REGISTRIES.add( registry );
        registry.register( MOD_EVENT_BUS );
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        REGISTRIES.forEach( IRegistry::assign );
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

    private static boolean runModCompat( String modid, Runnable initializer ) {
        if ( ModList.get().isLoaded( modid ) ) {
            LOGGER.info( "Loading Runnable for {}.", modid );
            initializer.run();
            return true;
        }
        return false;
    }

}
