package net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry;

import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.init.ModTags;
import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.*;
import net.dusty_dusty.cts_compats.common.block.CustomSlabBlock;
import net.dusty_dusty.cts_compats.common.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.common.registry.IColorRegistry;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.BOPColorRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.*;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class BOPRegistry extends AbstractRegistry {
    private static final BOPRegistry INSTANCE = new BOPRegistry( IRegistry.BOP_MODID );
    private static final ArrayList<RegistryObject<Block>> cutoutRender = new ArrayList<>();
    protected BOPRegistry(String modId) {
        super(modId);
    }

    public static BOPRegistry getInstance() {
        return INSTANCE;
    }

    public static class PlaceType {
        public static ArrayList<BlockCheckWrapper> FLESH = new ArrayList<>();
        public static ArrayList<BlockCheckWrapper> BLACKSTONE = new ArrayList<>();
        public static ArrayList<BlockCheckWrapper> BRIMSTONE = new ArrayList<>();
        static {
            FLESH.add( new BlockCheckWrapper( ModTags.Blocks.FLESH_DECORATION_PLACEABLE ) );
            BLACKSTONE.add( new BlockCheckWrapper( ModTags.Blocks.BLACKSTONE_DECORATION_PLACEABLE ) );
            BRIMSTONE.add( new BlockCheckWrapper( ModTags.Blocks.BRIMSTONE_DECORATION_PLACEABLE ) );
        }
    }

    @Override
    public Optional<IColorRegistry> getColorRegistry() {
        return Optional.of( new BOPColorRegistry() );
    }

    // Overworld Blocks
    public static final RegistryObject<Block> WHITE_SAND_SLAB = INSTANCE.registerBlock( "white_sand_slab",
            () -> new SandSlabBlockBOP( BOPBlocks.WHITE_SAND.get() ) );
    public static final RegistryObject<Block> ORANGE_SAND_SLAB = INSTANCE.registerBlock( "orange_sand_slab",
            () -> new SandSlabBlockBOP( BOPBlocks.ORANGE_SAND.get() ) );
    public static final RegistryObject<Block> BLACK_SAND_SLAB = INSTANCE.registerBlock( "black_sand_slab",
            () -> new SandSlabBlockBOP( BOPBlocks.BLACK_SAND.get() ) );
    // TODO: mossy black sand texture
    public static final RegistryObject<Block> MOSSY_BLACK_SAND_SLAB = INSTANCE.registerBlock( "mossy_black_sand_slab",
            () -> new MossyBlackSandSlab( BOPBlocks.MOSSY_BLACK_SAND.get() ) );
    public static final RegistryObject<Block> DRIED_SALT_SLAB = INSTANCE.registerBlock( "dried_salt_slab",
            () -> new DriedSaltSlab( BOPBlocks.DRIED_SALT.get() ) );

    // Overworld On Top Plants
        // Foliage
    public static final RegistryObject<Block> SPROUT_ON_TOP = INSTANCE.registerBlockCutout( "sprout_on_top",
            () -> new FoliageOnTopBOP( BOPBlocks.SPROUT.get() ) );
    public static final RegistryObject<Block> BUSH_ON_TOP = INSTANCE.registerBlockCutout( "bush_on_top",
            () -> new FoliageOnTopBOP( BOPBlocks.BUSH.get() ) );
    public static final RegistryObject<Block> DUNE_GRASS_ON_TOP = INSTANCE.registerBlockCutout( "dune_grass_on_top",
            () -> new FoliageOnTopBOP( BOPBlocks.DUNE_GRASS.get() ) );
    public static final RegistryObject<Block> DEAD_GRASS_ON_TOP = INSTANCE.registerBlockCutout( "dead_grass_on_top",
            () -> new FoliageOnTopBOP( BOPBlocks.DEAD_GRASS.get() ) );
    public static final RegistryObject<Block> DESERT_GRASS_ON_TOP = INSTANCE.registerBlockCutout( "desert_grass_on_top",
            () -> new FoliageOnTopBOP( BOPBlocks.DESERT_GRASS.get() ) );
    public static final RegistryObject<Block> TUNDRA_SHRUB_ON_TOP = INSTANCE.registerBlockCutout( "tundra_shrub_on_top",
            () -> new FoliageOnTopBOP( BOPBlocks.TUNDRA_SHRUB.get() ) );
    public static final RegistryObject<Block> TOADSTOOL_ON_TOP = INSTANCE.registerBlockCutout( "toadstool_on_top",
            () -> new BushBlockOnTop( BOPBlocks.TOADSTOOL.get(), Block.box( 5.0D, -8.0D, 5.0D, 11.0D, -2.0D, 11.0D ) ) );
        // Petals
    public static final RegistryObject<Block> CLOVER_ON_TOP = INSTANCE.registerBlockCutout( "clover_on_top",
            () -> new PetalBlockOnTop( BOPBlocks.CLOVER.get() ) );
    public static final RegistryObject<Block> WHITE_PETALS_ON_TOP = INSTANCE.registerBlockCutout( "white_petals_on_top",
            () -> new PetalBlockOnTop( BOPBlocks.WHITE_PETALS.get() ) );
        // Flowers
    public static final RegistryObject<Block> ROSE_ON_TOP = INSTANCE.registerBlockCutout( "rose_on_top",
            () -> new FlowerOnTopBOP( BOPBlocks.ROSE.get() ) );
    public static final RegistryObject<Block> PINK_DAFFODIL_ON_TOP = INSTANCE.registerBlockCutout( "pink_daffodil_on_top",
            () -> new FlowerOnTopBOP( BOPBlocks.PINK_DAFFODIL.get() ) );
    public static final RegistryObject<Block> PINK_HIBISCUS_ON_TOP = INSTANCE.registerBlockCutout( "pink_hibiscus_on_top",
            () -> new FlowerOnTopBOP( BOPBlocks.PINK_HIBISCUS.get() ) );
    public static final RegistryObject<Block> LAVENDER_ON_TOP = INSTANCE.registerBlockCutout( "lavender_on_top",
            () -> new FlowerOnTopBOP( BOPBlocks.LAVENDER.get() ) );
    public static final RegistryObject<Block> VIOLET_ON_TOP = INSTANCE.registerBlockCutout( "violet_on_top",
            () -> new FlowerOnTopBOP( BOPBlocks.VIOLET.get() ) );
    public static final RegistryObject<Block> ORANGE_COSMOS_ON_TOP = INSTANCE.registerBlockCutout( "orange_cosmos_on_top",
            () -> new FlowerOnTopBOP( BOPBlocks.ORANGE_COSMOS.get() ) );
    public static final RegistryObject<Block> WILDFLOWER_ON_TOP = INSTANCE.registerBlockCutout( "wildflower_on_top",
            () -> new FlowerOnTopBOP( BOPBlocks.WILDFLOWER.get() ) );
    public static final RegistryObject<Block> WILTED_LILY_ON_TOP = INSTANCE.registerBlockCutout( "wilted_lily_on_top",
            () -> new FlowerOnTopBOP( BOPBlocks.WILTED_LILY.get() ) );
    public static final RegistryObject<Block> GLOWFLOWER_ON_TOP = INSTANCE.registerBlockCutout( "glowflower_on_top",
            () -> new FlowerOnTopBOP( BOPBlocks.GLOWFLOWER.get() ) );
    public static final RegistryObject<Block> BURNING_BLOSSOM_ON_TOP = INSTANCE.registerBlockCutout( "burning_blossom_on_top",
            () -> new BurningBlossomOnTop( BOPBlocks.BURNING_BLOSSOM.get() ) );
        // Tall plants / flowers
    public static final RegistryObject<Block> SEA_OATS_ON_TOP = INSTANCE.registerBlockCutout( "sea_oats_on_top",
            () -> new SeaOatBOP( BOPBlocks.SEA_OATS.get() ) );
    public static final RegistryObject<Block> BARLEY_ON_TOP = INSTANCE.registerBlockCutout( "barley_on_top",
            () -> new DoublePlantBOP( BOPBlocks.BARLEY.get() ) );
    public static final RegistryObject<Block> GOLDENROD_ON_TOP = INSTANCE.registerBlockCutout( "goldenrod_on_top",
            () -> new DoubleFlowerBOP( BOPBlocks.GOLDENROD.get() ) );
    public static final RegistryObject<Block> TALL_LAVENDER_ON_TOP = INSTANCE.registerBlockCutout( "tall_lavender_on_top",
            () -> new DoubleFlowerBOP( BOPBlocks.TALL_LAVENDER.get() ) );
    public static final RegistryObject<Block> BLUE_HYDRANGEA_ON_TOP = INSTANCE.registerBlockCutout( "blue_hydrangea_on_top",
            () -> new DoubleFlowerBOP( BOPBlocks.BLUE_HYDRANGEA.get() ) );
    public static final RegistryObject<Block> ICY_IRIS_ON_TOP = INSTANCE.registerBlockCutout( "icy_iris_on_top",
            () -> new DoubleFlowerBOP( BOPBlocks.ICY_IRIS.get() ) );
    public static final RegistryObject<Block> CATTAIL_ON_TOP = INSTANCE.registerBlockCutout( "cattail_on_top",
            () -> new DoubleWaterPlantOnTop( BOPBlocks.CATTAIL.get(), BlockCheckWrapper.WATER_PLANT_PLACEABLE ) );
    public static final RegistryObject<Block> REED_ON_TOP = INSTANCE.registerBlockCutout( "reed_on_top",
            () -> new DoubleWaterPlantOnTop( BOPBlocks.REED.get(), BlockCheckWrapper.WATER_PLANT_PLACEABLE ) );
    public static final RegistryObject<Block> WATERGRASS_ON_TOP = INSTANCE.registerBlockCutout( "watergrass_on_top",
            () -> new DoubleWaterPlantOnTop( BOPBlocks.WATERGRASS.get(), BlockCheckWrapper.WATER_PLANT_PLACEABLE ) );

    // Nether Blocks
    public static final RegistryObject<Block> FLESH_SLAB = INSTANCE.registerBlock( "flesh_slab",
            () -> new FleshSlab( BOPBlocks.FLESH.get() ) );
    public static final RegistryObject<Block> POROUS_FLESH_SLAB = INSTANCE.registerBlock( "porous_flesh_slab",
            () -> new FleshSlab( BOPBlocks.POROUS_FLESH.get() ) );
    public static final RegistryObject<Block> BRIMSTONE_SLAB = INSTANCE.registerBlock( "brimstone_slab",
            () -> new CustomSlabBlock( BOPBlocks.BRIMSTONE.get() ) );

    // Nether On Top "Things"
        // Visceral Heap
    public static final RegistryObject<Block> HAIR_ON_TOP = INSTANCE.registerBlockCutout( "hair_on_top",
            () -> new BasicOnTopBlock( BOPBlocks.HAIR.get(), Block.box(1.0F, -8.0F, 1.0F, 15.0F, 4.0F, 15.0F), PlaceType.FLESH ) );
    public static final RegistryObject<Block> PUS_BUBBLE_ON_TOP = INSTANCE.registerBlockCutout( "pus_bubble_on_top",
            () -> new PusBubbleOnTop( BOPBlocks.PUS_BUBBLE.get() ) );
    public static final RegistryObject<Block> EYEBULB_ON_TOP = INSTANCE.registerBlockCutout( "eyebulb_on_top",
            () -> new DoublePlantOnTop( BOPBlocks.EYEBULB.get(), PlaceType.FLESH ) );
        // Blackstone
    public static final RegistryObject<Block> BLACKSTONE_BULB_ON_TOP = INSTANCE.registerBlockCutout( "blackstone_bulb_on_top",
            () -> new BasicOnTopBlock( BOPBlocks.BLACKSTONE_BULB.get(), Block.box(1.0F, -8.0F, 1.0F, 15.0F, 0.0F, 15.0F), PlaceType.BLACKSTONE ) );
    public static final RegistryObject<Block> BLACKSTONE_SPINES_ON_TOP = INSTANCE.registerBlockCutout( "blackstone_spines_on_top",
            () -> new BasicOnTopBlock( BOPBlocks.BLACKSTONE_SPINES.get(), Block.box(1.0F, -8.0F, 1.0F, 15.0F, 0.0F, 15.0F), PlaceType.BLACKSTONE ) );
        // Brimstone
    public static final RegistryObject<Block> BRIMSTONE_BUD_ON_TOP = INSTANCE.registerBlockCutout( "brimstone_bud_on_top",
            () -> new BrimstoneBudOnTop( BOPBlocks.BRIMSTONE_BUD.get() ) );
    public static final RegistryObject<Block> BRIMSTONE_FUMAROLE_ON_TOP = INSTANCE.registerBlock( "brimstone_fumarole_on_top",
            () -> new BrimstoneFumaroleOnTop( BOPBlocks.BRIMSTONE_FUMAROLE.get() ) );
    public static final RegistryObject<Block> BRIMSTONE_CLUSTER_ON_TOP = INSTANCE.registerBlockCutout( "brimstone_cluster_on_top",
            () -> new BrimstoneClusterOnTop( BOPBlocks.BRIMSTONE_CLUSTER.get() ) );



    @SuppressWarnings("removal")
    public static void setRenderTypes() {
        ItemBlockRenderTypes.setRenderLayer( MOSSY_BLACK_SAND_SLAB.get(), RenderType.cutoutMipped() );
        cutoutRender.forEach( blockRegistryObject ->
                ItemBlockRenderTypes.setRenderLayer( blockRegistryObject.get(), RenderType.cutout() ));
    }

    @SuppressWarnings("unchecked")
    protected <T extends Block> RegistryObject<T> registerBlockCutout(String name, Supplier<T> block ) {
        RegistryObject<T> output = registerBlock( name, block );
        cutoutRender.add( (RegistryObject<Block>) output );
        return output;
    }

}
