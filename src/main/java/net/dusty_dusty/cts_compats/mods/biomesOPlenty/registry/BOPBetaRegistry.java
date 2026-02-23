package net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry;

import biomesoplenty.api.block.BOPBlocks;
import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.BushBlockOnTop;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.FlowerBlockOnTop;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.PetalBlockOnTop;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.DoubleFlowerBOP;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.FoliageOnTopBOP;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.TinyCactusOnTop;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public final class BOPBetaRegistry {
    public static BOPBaseRegistry INSTANCE = BOPBaseRegistry.getInstance();
    public static BOPBaseRegistry getInstance() {
        INSTANCE.colorRegistry = Optional.of( new BOPBetaColorRegistry() );
        ENDSTONE_LIKE.add( getBlock( "algal_end_stone" ) );
        ENDSTONE_LIKE.add( getBlock( "unmapped_end_stone" ) );
        ENDSTONE_LIKE.add( getBlock( "null_end_stone" ) );
        return INSTANCE;
    }

    private static Block getBlock( String name ) {
        return ForgeRegistries.BLOCKS.getValue( ResourceLocation.fromNamespaceAndPath( IRegistry.BOP_MODID, name ) );
    }

    public static BlockCheckWrapper.Group ENDSTONE_LIKE = new BlockCheckWrapper.Group();
    static {
        ENDSTONE_LIKE.add( Blocks.END_STONE );
    }

    // On Top Things
    public static VoxelShape LEAF_PILE_SHAPE = Block.box( 0.0F, 0.0F, 0.0F, 16.0F, 4.0F, 16.0F );
    public static final RegistryObject<Block> RED_MAPLE_LEAVES_ON_TOP = INSTANCE.registerBlockCutout( "red_maple_leaves_on_top",
            () -> new FoliageOnTopBOP( BOPBlocks.RED_MAPLE_LEAF_PILE, LEAF_PILE_SHAPE ) );
    public static final RegistryObject<Block> ORANGE_MAPLE_LEAVES_ON_TOP = INSTANCE.registerBlockCutout( "orange_maple_leaves_on_top",
            () -> new FoliageOnTopBOP( BOPBlocks.ORANGE_MAPLE_LEAF_PILE, LEAF_PILE_SHAPE ) );
    public static final RegistryObject<Block> YELLOW_MAPLE_LEAVES_ON_TOP = INSTANCE.registerBlockCutout( "yellow_maple_leaves_on_top",
            () -> new FoliageOnTopBOP( BOPBlocks.YELLOW_MAPLE_LEAF_PILE, LEAF_PILE_SHAPE ) );
    public static final RegistryObject<Block> WILDFLOWER_ON_TOP = INSTANCE.registerBlockCutout( "wildflower_on_top",
            () -> new PetalBlockOnTop( BOPBlocks.WILDFLOWER ) );
    public static final RegistryObject<Block> WHITE_LAVENDER_ON_TOP = INSTANCE.registerBlockCutout( "white_lavender_on_top",
            () -> new FlowerBlockOnTop( BOPBlocks.WHITE_LAVENDER, BOPBaseRegistry.FlowerShapes.LARGE, BlockCheckWrapper.DIRT_AND_FARMLAND ) );
    public static final RegistryObject<Block> TALL_WHITE_LAVENDER_ON_TOP = INSTANCE.registerBlockCutout( "tall_white_lavender_on_top",
            () -> new DoubleFlowerBOP( BOPBlocks.TALL_WHITE_LAVENDER ) );
    public static final RegistryObject<Block> ENDBLOOM_ON_TOP = INSTANCE.registerBlockCutout( "endbloom_on_top",
            () -> new FlowerBlockOnTop( BOPBlocks.ENDBLOOM, Block.box( 2.0F, 0.0F, 2.0F, 14.0F, 6.0F, 14.0F ),
                    BOPBetaRegistry.ENDSTONE_LIKE.cloneAndAppend( BlockCheckWrapper.DIRT_AND_FARMLAND ) ) );
    public static final RegistryObject<Block> ENDERPHYTE_ON_TOP = INSTANCE.registerBlockCutout( "enderphyte_on_top",
            () -> new BushBlockOnTop( BOPBlocks.ENDERPHYTE, Block.box( 2.0F, -8.0F, 2.0F, 14.0F, 5.0F, 14.0F ),
                    BOPBetaRegistry.ENDSTONE_LIKE ) );
    public static final RegistryObject<Block> TINY_CACTUS_ON_TOP = INSTANCE.registerBlockCutout( "tiny_cactus_on_top",
            () -> new TinyCactusOnTop( BOPBlocks.TINY_CACTUS ) );

}
