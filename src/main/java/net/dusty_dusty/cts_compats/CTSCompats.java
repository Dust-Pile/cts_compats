package net.dusty_dusty.cts_compats;

import com.mojang.logging.LogUtils;
import net.dusty_dusty.cts_compats.common.block.interfaces.IOnTopCopy;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.BOPRegistry;
import net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.PVJRegistry;
import net.dusty_dusty.cts_compats.mods.vanilla.VanillaRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(CTSCompats.MODID)
public class CTSCompats
{
    public static final String MODID = "cts_compats";
    public static final Logger LOGGER = LogUtils.getLogger();
    public final RegistryManager registryManager;

    protected static final String PVJ_MODID = "projectvibrantjourneys";
    protected static final String BOP_MODID = "biomesoplenty";

    @SuppressWarnings("Convert2MethodRef")
    public CTSCompats(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        try {
            registryManager = new RegistryManager( modEventBus );
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        modEventBus.addListener( this::clientSetup );

        MinecraftForge.EVENT_BUS.register(this);

        // Compats, must use lambda to avoid class loading
        registryManager.register( PVJ_MODID, () -> PVJRegistry.getInstance() );
        registryManager.register( BOP_MODID, () -> BOPRegistry.getInstance() );
    }

    @SuppressWarnings("removal")
    private void clientSetup(final FMLClientSetupEvent event) {
        // Have to do it this way if I want to copy vanilla blockstates. Gives better resourcepack compatibility.
        for( RegistryObject<Block> blockRegister : VanillaRegistry.getInstance().COMPAT_BLOCKS.getEntries() ) {
            if ( blockRegister.get() instanceof IOnTopCopy ) {
                ItemBlockRenderTypes.setRenderLayer( blockRegister.get(), RenderType.cutout() );
            }
        }
    }
}
