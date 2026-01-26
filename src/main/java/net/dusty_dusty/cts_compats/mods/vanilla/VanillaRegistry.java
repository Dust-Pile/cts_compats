package net.dusty_dusty.cts_compats.mods.vanilla;

import net.dusty_dusty.cts_compats.common.IAssignable;
import net.dusty_dusty.cts_compats.common.block.*;
import net.dusty_dusty.cts_compats.mods.vanilla.block.PitcherPlantOnTop;
import net.dusty_dusty.cts_compats.mods.vanilla.block.SweetBerryBushOnTop;
import net.dusty_dusty.cts_compats.mods.vanilla.block.WitherRoseOnTop;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.MODID;

public class VanillaRegistry {

    protected static final DeferredRegister<Block> COMPAT_BLOCKS = DeferredRegister.create( ForgeRegistries.BLOCKS, MODID );
    protected static final DeferredRegister<Item> COMPAT_ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MODID );

    // Unique
    public static final RegistryObject<Block> DRIPSTONE_SLAB = registerBlock( "dripstone_slab",
            () -> new CustomSlabBlock( Blocks.DRIPSTONE_BLOCK ) );
    public static final RegistryObject<Block> PINK_PETALS_ON_TOP = registerBlock( "pink_petals_on_top",
            () -> new PetalBlockOnTop( Blocks.PINK_PETALS ) );
    public static final RegistryObject<Block> TALL_GRASS_ON_TOP = registerBlock( "tall_grass_on_top",
            () -> new DoublePlantOnTop( Blocks.TALL_GRASS ) );
    public static final RegistryObject<Block> SWEET_BERRY_BUSH_ON_TOP = registerBlock( "sweet_berry_bush_on_top",
            () -> new SweetBerryBushOnTop( Blocks.SWEET_BERRY_BUSH ) );

    // Fungus
    private static final VoxelShape fungusShape = Block.box(4.0D, -8.0D, 4.0D, 12.0D, 1.0D, 12.0D);
    public static final RegistryObject<Block> CRIMSON_FUNGUS_ON_TOP = registerBlock( "crimson_fungus_on_top",
            () -> new BushBlockOnTop( Blocks.CRIMSON_FUNGUS, fungusShape ) );
    public static final RegistryObject<Block> WARPED_FUNGUS_ON_TOP = registerBlock( "warped_fungus_on_top",
            () -> new BushBlockOnTop( Blocks.WARPED_FUNGUS, fungusShape ) );

    // Flowers
    public static final RegistryObject<Block> RED_TULIP_ON_TOP = registerBlock( "red_tulip_on_top",
            () -> new FlowerBlockOnTop( Blocks.RED_TULIP ) );
    public static final RegistryObject<Block> ORANGE_TULIP_ON_TOP = registerBlock( "orange_tulip_on_top",
            () -> new FlowerBlockOnTop( Blocks.ORANGE_TULIP ) );
    public static final RegistryObject<Block> PINK_TULIP_ON_TOP = registerBlock( "pink_tulip_on_top",
            () -> new FlowerBlockOnTop( Blocks.PINK_TULIP ) );
    public static final RegistryObject<Block> WHITE_TULIP_ON_TOP = registerBlock( "white_tulip_on_top",
            () -> new FlowerBlockOnTop( Blocks.WHITE_TULIP ) );
    public static final RegistryObject<Block> LILY_OF_THE_VALLEY_ON_TOP = registerBlock( "lily_of_the_valley_on_top",
            () -> new FlowerBlockOnTop( Blocks.LILY_OF_THE_VALLEY ) );
    public static final RegistryObject<Block> OXEYE_DAISY_ON_TOP = registerBlock( "oxeye_daisy_on_top",
            () -> new FlowerBlockOnTop( Blocks.OXEYE_DAISY ) );
    public static final RegistryObject<Block> ALLIUM_ON_TOP = registerBlock( "allium_on_top",
            () -> new FlowerBlockOnTop( Blocks.ALLIUM ) );
    public static final RegistryObject<Block> BLUE_ORCHID_ON_TOP = registerBlock( "blue_orchid_on_top",
            () -> new FlowerBlockOnTop( Blocks.BLUE_ORCHID ) );
    public static final RegistryObject<Block> WITHER_ROSE_ON_TOP = registerBlock( "wither_rose_on_top",
            () -> new WitherRoseOnTop( Blocks.WITHER_ROSE ) );
    public static final RegistryObject<Block> TORCHFLOWER_ON_TOP = registerBlock( "torchflower_on_top",
            () -> new FlowerBlockOnTop( Blocks.TORCHFLOWER ) );
    // Tall Flowers
    public static final RegistryObject<Block> PITCHER_PLANT_ON_TOP = registerBlock( "pitcher_plant_on_top",
            () -> new PitcherPlantOnTop( Blocks.PITCHER_PLANT ) );
    public static final RegistryObject<Block> PEONY_ON_TOP = registerBlock( "peony_on_top",
            () -> new DoublePlantOnTop( Blocks.PEONY ) );
    public static final RegistryObject<Block> ROSE_BUSH_ON_TOP = registerBlock( "rose_bush_on_top",
            () -> new DoublePlantOnTop( Blocks.ROSE_BUSH ) );
    public static final RegistryObject<Block> SUNFLOWER_ON_TOP = registerBlock( "sunflower_on_top",
            () -> new DoublePlantOnTop( Blocks.SUNFLOWER ) );
    public static final RegistryObject<Block> LILAC_ON_TOP = registerBlock( "lilac_on_top",
            () -> new DoublePlantOnTop( Blocks.LILAC ) );



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
