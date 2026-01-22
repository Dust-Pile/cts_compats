package net.dusty_dusty.cts_compats.projectVibrantJourneys;

import com.ibm.icu.util.CodePointTrie;
import dev.orderedchaos.projectvibrantjourneys.core.registry.PVJBlocks;
import net.dusty_dusty.cts_compats.core.AssignUtil;
import net.dusty_dusty.cts_compats.projectVibrantJourneys.block.ShortGrassOnTop;
import net.dusty_dusty.cts_compats.projectVibrantJourneys.block.SmallCactusOnTop;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.MODID;

public class PVJRegistry {

    protected static final DeferredRegister<Block> COMPAT_BLOCKS = DeferredRegister.create( ForgeRegistries.BLOCKS, MODID );
    protected static final DeferredRegister<Item> COMPAT_ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MODID );

    public static final RegistryObject<Block> SHORTER_GRASS_ON_TOP = registerBlock( "shorter_grass_on_top",
            () -> new ShortGrassOnTop( BlockBehaviour.Properties.copy( PVJBlocks.SHORT_GRASS.get() ) ) );

    public static final RegistryObject<Block> SMALL_CACTUS_ON_TOP = registerBlock( "small_cactus_on_top",
            () -> new SmallCactusOnTop( BlockBehaviour.Properties.copy( PVJBlocks.SMALL_CACTUS.get() ) ) );


    private static <T extends Block> RegistryObject<T> registerBlock( String name, Supplier<T> block ) {
        RegistryObject<T> output = COMPAT_BLOCKS.register( name, block );
        registerBlockItem( name, ( RegistryObject<Block> ) output);
        return output;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem( String name, RegistryObject<Block> block ) {
        return PVJRegistry.COMPAT_ITEMS.register( name, () -> new BlockItem( block.get(), new Item.Properties() ) );
    }

    public static void register( IEventBus modEventBus ) {
        COMPAT_BLOCKS.register( modEventBus );
        COMPAT_ITEMS.register( modEventBus );
    }

    public static void assign() {
        AssignUtil.putOnTopVegetation( PVJBlocks.SHORT_GRASS.get(), PVJRegistry.SHORTER_GRASS_ON_TOP.get() );
        AssignUtil.putOnTopVegetation( PVJBlocks.SMALL_CACTUS.get(), PVJRegistry.SMALL_CACTUS_ON_TOP.get() );
    }

}
