package net.dusty_dusty.cts_compats;

import com.mojang.logging.LogUtils;
import net.dusty_dusty.cts_compats.common.block.interfaces.IOnTopCopy;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;
import net.dusty_dusty.cts_compats.mods.ImmersiveWeathering.WeatheringRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPVersionRouter;
import net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.PVJRegistry;
import net.dusty_dusty.cts_compats.mods.vanilla.VanillaRegistry;
import net.dusty_dusty.cts_compats.mods.vanillaBackport.VanillaBackportRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod( CTSCompats.MODID )
public final class CTSCompats
{
    public static final String MODID = "cts_compats";
    public static final Logger LOGGER = LogUtils.getLogger();
    public final RegistryManager REGISTRY_MANAGER;

    @SuppressWarnings("Convert2MethodRef")
    public CTSCompats(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        try {
            REGISTRY_MANAGER = new RegistryManager( modEventBus );
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        modEventBus.addListener( this::clientSetup );
        modEventBus.addListener( this::registerCreativeTabItems );

        MinecraftForge.EVENT_BUS.register(this);

        // Compats, must use lambda to avoid class loading
        REGISTRY_MANAGER.register( IRegistry.PVJ_MODID, () -> PVJRegistry.getInstance() );
        REGISTRY_MANAGER.register( IRegistry.BOP_MODID, () -> BOPVersionRouter.getInstance() );
        REGISTRY_MANAGER.register( IRegistry.VB_MODID, () -> VanillaBackportRegistry.getInstance() );
        REGISTRY_MANAGER.register( IRegistry.IW_MODID, () -> WeatheringRegistry.getInstance() );
    }

    @SuppressWarnings("removal")
    private void clientSetup(final FMLClientSetupEvent event) {
        // Have to do it this way if I want to copy vanilla blockstates. Gives better resource pack compatibility.
        for( RegistryObject<Block> blockRegister : VanillaRegistry.getInstance().getRegistryBlocks() ) {
            if ( blockRegister.get() instanceof IOnTopCopy ) {
                ItemBlockRenderTypes.setRenderLayer( blockRegister.get(), RenderType.cutout() );
            }
        }
    }

    // TODO: Keep slab blocks in a separate cache
    private void registerCreativeTabItems( BuildCreativeModeTabContentsEvent event ) {
        // CTSCompats.LOGGER.info( "Creative Mode Tabs: {}", event.getTabKey().location() );
        if ( event.getTabKey().location().toString().equals( "terrainslabs:terrainslabs" ) ) {
            for ( Block block : RegistryManager.getAllBlocks() ) {
                if ( block instanceof SlabBlock) {
                    event.accept( block );
                }
            }
        }
    }
}
