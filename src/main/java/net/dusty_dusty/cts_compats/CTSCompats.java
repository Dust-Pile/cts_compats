package net.dusty_dusty.cts_compats;

import com.mojang.logging.LogUtils;
import net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.PVJColorRegistry;
import net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.PVJRegistry;
import net.dusty_dusty.cts_compats.mods.vanilla.VanillaColorRegistry;
import net.dusty_dusty.cts_compats.mods.vanilla.VanillaRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CTSCompats.MODID)
public class CTSCompats
{
    public static final String MODID = "cts_compats";
    public static final Logger LOGGER = LogUtils.getLogger();

    private static final String PVJ_MODID = "projectvibrantjourneys";

    public CTSCompats(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        // Compats
        VanillaRegistry.register( modEventBus );
        runModCompat( PVJ_MODID, () -> PVJRegistry.register( modEventBus ) );
    }

    @SuppressWarnings("Convert2MethodRef")
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Must use lambda to avoid class loading
        VanillaRegistry.assign();
        runModCompat( PVJ_MODID, () -> PVJRegistry.assign() );
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {

        @SubscribeEvent
        public static void onColorHandlerEventBlock(RegisterColorHandlersEvent.Block event) {
            VanillaColorRegistry.onColorHandlerEventBlock( event );
            runModCompat( PVJ_MODID, () -> PVJColorRegistry.onColorHandlerEventBlock( event ) );
        }

        @SubscribeEvent
        public static void onColorHandlerEventItem(RegisterColorHandlersEvent.Item event) {
            VanillaColorRegistry.onColorHandlerEventItem( event );
            runModCompat( PVJ_MODID, () -> PVJColorRegistry.onColorHandlerEventItem( event ) );
        }
    }

    private static void runModCompat( String modid, Runnable initializer ) {
        if ( ModList.get().isLoaded( modid ) ) {
            LOGGER.info( "Loading Runnable for {}.", modid );
            initializer.run();
        }
    }
}
