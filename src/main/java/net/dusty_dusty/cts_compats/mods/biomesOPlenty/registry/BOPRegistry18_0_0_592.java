package net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry;

import biomesoplenty.api.block.BOPBlocks;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.BurningBlossomOnTop;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.FlowerOnTopBOP;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class BOPRegistry18_0_0_592 {
    private static final BOPBaseRegistry INSTANCE = BOPBaseRegistry.getInstance();

    public static BOPBaseRegistry getInstance() {
        return INSTANCE;
    }

    // Flowers
    public static final RegistryObject<Block> ROSE_ON_TOP = INSTANCE.registerBlockCutout( "rose_on_top",
            () -> new FlowerOnTopBOP( IRegistry.getBlock( BOPBlocks.ROSE ) ) );
    public static final RegistryObject<Block> PINK_DAFFODIL_ON_TOP = INSTANCE.registerBlockCutout( "pink_daffodil_on_top",
            () -> new FlowerOnTopBOP( IRegistry.getBlock( BOPBlocks.PINK_DAFFODIL ) ) );
    public static final RegistryObject<Block> PINK_HIBISCUS_ON_TOP = INSTANCE.registerBlockCutout( "pink_hibiscus_on_top",
            () -> new FlowerOnTopBOP( IRegistry.getBlock( BOPBlocks.PINK_HIBISCUS ) ) );
    public static final RegistryObject<Block> LAVENDER_ON_TOP = INSTANCE.registerBlockCutout( "lavender_on_top",
            () -> new FlowerOnTopBOP( IRegistry.getBlock( BOPBlocks.LAVENDER ) ) );
    public static final RegistryObject<Block> VIOLET_ON_TOP = INSTANCE.registerBlockCutout( "violet_on_top",
            () -> new FlowerOnTopBOP( IRegistry.getBlock( BOPBlocks.VIOLET ) ) );
    public static final RegistryObject<Block> ORANGE_COSMOS_ON_TOP = INSTANCE.registerBlockCutout( "orange_cosmos_on_top",
            () -> new FlowerOnTopBOP( IRegistry.getBlock( BOPBlocks.ORANGE_COSMOS ) ) );
    public static final RegistryObject<Block> WILDFLOWER_ON_TOP = INSTANCE.registerBlockCutout( "wildflower_on_top",
            () -> new FlowerOnTopBOP( IRegistry.getBlock( BOPBlocks.WILDFLOWER ) ) );
    public static final RegistryObject<Block> WILTED_LILY_ON_TOP = INSTANCE.registerBlockCutout( "wilted_lily_on_top",
            () -> new FlowerOnTopBOP( IRegistry.getBlock( BOPBlocks.WILTED_LILY ) ) );
    public static final RegistryObject<Block> GLOWFLOWER_ON_TOP = INSTANCE.registerBlockCutout( "glowflower_on_top",
            () -> new FlowerOnTopBOP( IRegistry.getBlock( BOPBlocks.GLOWFLOWER ) ) );
    public static final RegistryObject<Block> BURNING_BLOSSOM_ON_TOP = INSTANCE.registerBlockCutout( "burning_blossom_on_top",
            () -> new BurningBlossomOnTop( IRegistry.getBlock( BOPBlocks.BURNING_BLOSSOM ) ) );
}
