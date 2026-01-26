package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys;

import dev.orderedchaos.projectvibrantjourneys.core.registry.PVJBlocks;
import dev.orderedchaos.projectvibrantjourneys.core.registry.PVJItems;
import net.dusty_dusty.cts_compats.common.IAssignable;
import net.dusty_dusty.cts_compats.common.block.PetalBlockOnTop;
import net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.block.ShortGrassOnTop;
import net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.block.SmallCactusOnTop;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.MODID;

public class PVJRegistry {

    protected static final DeferredRegister<Block> COMPAT_BLOCKS = DeferredRegister.create( ForgeRegistries.BLOCKS, MODID );
    protected static final DeferredRegister<Item> COMPAT_ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MODID );

    // Unique
    public static final RegistryObject<Block> SHORTER_GRASS_ON_TOP = registerBlock( "shorter_grass_on_top",
            () -> new ShortGrassOnTop( PVJBlocks.SHORT_GRASS.get() ) );
    public static final RegistryObject<Block> SMALL_CACTUS_ON_TOP = registerBlock( "small_cactus_on_top",
            () -> new SmallCactusOnTop( PVJBlocks.SMALL_CACTUS.get() ) );

    // Petals
    public static final RegistryObject<Block> PURPLE_WILDFLOWERS_ON_TOP = registerBlock( "purple_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.PURPLE_WILDFLOWERS.get(), PVJItems.PURPLE_WILDFLOWERS ) );
    public static final RegistryObject<Block> BLUE_WILDFLOWERS_ON_TOP = registerBlock( "blue_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.BLUE_WILDFLOWERS.get(), PVJItems.BLUE_WILDFLOWERS ) );
    public static final RegistryObject<Block> MIXED_WILDFLOWERS_ON_TOP = registerBlock( "mixed_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.MIXED_WILDFLOWERS.get(), PVJItems.MIXED_WILDFLOWERS ) );
    public static final RegistryObject<Block> ORANGE_WILDFLOWERS_ON_TOP = registerBlock( "orange_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.ORANGE_WILDFLOWERS.get(), PVJItems.ORANGE_WILDFLOWERS ) );
    public static final RegistryObject<Block> YELLOW_WILDFLOWERS_ON_TOP = registerBlock( "yellow_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.YELLOW_WILDFLOWERS.get(), PVJItems.YELLOW_WILDFLOWERS ) );
    public static final RegistryObject<Block> SANDY_SPROUTS_ON_TOP = registerBlock( "sandy_sprouts_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.SANDY_SPROUTS.get(), PVJItems.SANDY_SPROUTS ) );
    public static final RegistryObject<Block> WHITE_WILDFLOWERS_ON_TOP = registerBlock( "white_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.WHITE_WILDFLOWERS.get(), PVJItems.WHITE_WILDFLOWERS ) );



    private static <T extends Block> RegistryObject<T> registerBlock( String name, Supplier<T> block ) {
        RegistryObject<T> output = COMPAT_BLOCKS.register( name, block );
        registerBlockItem( name, ( RegistryObject<Block> ) output);
        return output;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem( String name, RegistryObject<Block> block ) {
        return COMPAT_ITEMS.register( name, () -> new BlockItem( block.get(), new Item.Properties() ) );
    }

    public static void register( IEventBus modEventBus ) {
        COMPAT_BLOCKS.register( modEventBus );
        COMPAT_ITEMS.register( modEventBus );
    }

    public static void assign() {
        COMPAT_BLOCKS.getEntries().forEach( entry -> {
            Block block = entry.get();
            if ( block instanceof IAssignable) {
                ( (IAssignable) block ).assign();
            }
        });
    }
}
