package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys;

import dev.orderedchaos.projectvibrantjourneys.core.registry.PVJBlocks;
import net.dusty_dusty.cts_compats.common.interfaces.IAssignable;
import net.dusty_dusty.cts_compats.common.block.PetalBlockOnTop;
import net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.MODID;

@SuppressWarnings("unused")
public class PVJRegistry {

    public static final DeferredRegister<Block> COMPAT_BLOCKS = DeferredRegister.create( ForgeRegistries.BLOCKS, MODID );
    public static final DeferredRegister<Item> COMPAT_ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MODID );

    // Unique
    public static final RegistryObject<Block> SHORTER_GRASS_ON_TOP = registerBlock( "shorter_grass_on_top",
            () -> new ShortGrassOnTop( PVJBlocks.SHORT_GRASS.get() ) );
    public static final RegistryObject<Block> SMALL_CACTUS_ON_TOP = registerBlock( "small_cactus_on_top",
            () -> new SmallCactusOnTop( PVJBlocks.SMALL_CACTUS.get() ) );
    public static final RegistryObject<Block> BEACH_GRASS_ON_TOP = registerBlock( "beach_grass_on_top",
            () -> new BeachGrassOnTop( PVJBlocks.BEACH_GRASS.get() ) );
    public static final RegistryObject<Block> PRICKLY_BUSH_ON_TOP = registerBlock( "prickly_bush_on_top",
            () -> new ThornsBlockOnTop( PVJBlocks.PRICKLY_BUSH.get() ) );
    public static final RegistryObject<Block> GLOWCAP_ON_TOP = registerBlock( "glowcap_on_top",
            () -> new GlowcapOnTop( PVJBlocks.GLOWCAP.get() ) );

    // Fallen Leaves
    public static final RegistryObject<Block> FALLEN_LEAVES_ON_TOP = registerBlock( "fallen_leaves_on_top",
            () -> new FallenLeavesOnTop( PVJBlocks.FALLEN_LEAVES.get() ) );
    public static final RegistryObject<Block> DEAD_FALLEN_LEAVES_ON_TOP = registerBlock( "dead_fallen_leaves_on_top",
            () -> new FallenLeavesOnTop( PVJBlocks.DEAD_FALLEN_LEAVES.get() ) );

    // Petals
    public static final RegistryObject<Block> PURPLE_WILDFLOWERS_ON_TOP = registerBlock( "purple_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.PURPLE_WILDFLOWERS.get() ) );
    public static final RegistryObject<Block> BLUE_WILDFLOWERS_ON_TOP = registerBlock( "blue_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.BLUE_WILDFLOWERS.get() ) );
    public static final RegistryObject<Block> MIXED_WILDFLOWERS_ON_TOP = registerBlock( "mixed_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.MIXED_WILDFLOWERS.get() ) );
    public static final RegistryObject<Block> ORANGE_WILDFLOWERS_ON_TOP = registerBlock( "orange_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.ORANGE_WILDFLOWERS.get() ) );
    public static final RegistryObject<Block> YELLOW_WILDFLOWERS_ON_TOP = registerBlock( "yellow_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.YELLOW_WILDFLOWERS.get() ) );
    public static final RegistryObject<Block> SANDY_SPROUTS_ON_TOP = registerBlock( "sandy_sprouts_on_top",
            () -> new SandySproutsOnTop( PVJBlocks.SANDY_SPROUTS.get() ) );
    public static final RegistryObject<Block> WHITE_WILDFLOWERS_ON_TOP = registerBlock( "white_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.WHITE_WILDFLOWERS.get() ) );



    @SuppressWarnings("unchecked")
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block ) {
        RegistryObject<T> output = COMPAT_BLOCKS.register( name, block );
        registerBlockItem( name, ( RegistryObject<Block> ) output);
        return output;
    }

    @SuppressWarnings("UnusedReturnValue")
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<Block> block ) {
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
