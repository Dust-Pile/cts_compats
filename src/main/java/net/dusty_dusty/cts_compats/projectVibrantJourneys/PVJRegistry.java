package net.dusty_dusty.cts_compats.projectVibrantJourneys;

import dev.orderedchaos.projectvibrantjourneys.core.registry.PVJBlocks;
import net.dusty_dusty.cts_compats.core.CompatSlabsMap;
import net.dusty_dusty.cts_compats.projectVibrantJourneys.blocks.ShortGrassOnTop;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.dusty_dusty.cts_compats.CTSCompats.MODID;

public class PVJRegistry {

    public static PVJRegistry INSTANCE;
    private static boolean isRegistered = false;

    public static void register( IEventBus modEventBus ) {
        if ( !isRegistered ) {
            COMPAT_BLOCKS.register( modEventBus );
            INSTANCE = new PVJRegistry();
            isRegistered = true;
        }
    }

    PVJRegistry () {}

    private static final DeferredRegister<Block> COMPAT_BLOCKS = DeferredRegister.create( ForgeRegistries.BLOCKS, MODID );

    public static final RegistryObject<Block> SHORTER_GRASS_ON_TOP = COMPAT_BLOCKS.register( "shorter_grass_on_top",
            () -> { return new ShortGrassOnTop( BlockBehaviour.Properties.copy( PVJBlocks.SHORT_GRASS.get() )); });

    public void assign() {
        assignVegetation();
    }

    private void assignVegetation() {
        CompatSlabsMap.mapVegitation( PVJBlocks.SHORT_GRASS, SHORTER_GRASS_ON_TOP );
    }

}
