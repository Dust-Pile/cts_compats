package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys;

import dev.orderedchaos.projectvibrantjourneys.core.registry.PVJBlocks;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.DoublePlantOnTop;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.PetalBlockOnTop;
import net.dusty_dusty.cts_compats.common.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.common.registry.IColorRegistry;
import net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

@SuppressWarnings("unused")
public class PVJRegistry extends AbstractRegistry {
    private static final PVJRegistry INSTANCE = new PVJRegistry();
    public static PVJRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<IColorRegistry> getColorRegistry() {
        return Optional.of( new PVJColorRegistry() );
    }

    // Unique
    public static final RegistryObject<Block> SHORTER_GRASS_ON_TOP = INSTANCE.registerBlock( "shorter_grass_on_top",
            () -> new ShortGrassOnTop( PVJBlocks.SHORT_GRASS.get() ) );
    public static final RegistryObject<Block> SMALL_CACTUS_ON_TOP = INSTANCE.registerBlock( "small_cactus_on_top",
            () -> new SmallCactusOnTop( PVJBlocks.SMALL_CACTUS.get() ) );
    public static final RegistryObject<Block> BEACH_GRASS_ON_TOP = INSTANCE.registerBlock( "beach_grass_on_top",
            () -> new BeachGrassOnTop( PVJBlocks.BEACH_GRASS.get() ) );
    public static final RegistryObject<Block> PRICKLY_BUSH_ON_TOP = INSTANCE.registerBlock( "prickly_bush_on_top",
            () -> new ThornsBlockOnTop( PVJBlocks.PRICKLY_BUSH.get() ) );
    public static final RegistryObject<Block> GLOWCAP_ON_TOP = INSTANCE.registerBlock( "glowcap_on_top",
            () -> new GlowcapOnTop( PVJBlocks.GLOWCAP.get() ) );

    // Tall Plants
    public static final RegistryObject<Block> SEA_OATS_ON_TOP = INSTANCE.registerBlock( "vibrant_sea_oats_on_top",
            () -> new DoublePlantOnTop( PVJBlocks.SEA_OATS.get() ) );

    // Fallen Leaves
    public static final RegistryObject<Block> FALLEN_LEAVES_ON_TOP = INSTANCE.registerBlock( "fallen_leaves_on_top",
            () -> new FallenLeavesOnTop( PVJBlocks.FALLEN_LEAVES.get() ) );
    public static final RegistryObject<Block> DEAD_FALLEN_LEAVES_ON_TOP = INSTANCE.registerBlock( "dead_fallen_leaves_on_top",
            () -> new FallenLeavesOnTop( PVJBlocks.DEAD_FALLEN_LEAVES.get() ) );

    // Petals
    public static final RegistryObject<Block> PURPLE_WILDFLOWERS_ON_TOP = INSTANCE.registerBlock( "purple_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.PURPLE_WILDFLOWERS.get() ) );
    public static final RegistryObject<Block> BLUE_WILDFLOWERS_ON_TOP = INSTANCE.registerBlock( "blue_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.BLUE_WILDFLOWERS.get() ) );
    public static final RegistryObject<Block> MIXED_WILDFLOWERS_ON_TOP = INSTANCE.registerBlock( "mixed_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.MIXED_WILDFLOWERS.get() ) );
    public static final RegistryObject<Block> ORANGE_WILDFLOWERS_ON_TOP = INSTANCE.registerBlock( "orange_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.ORANGE_WILDFLOWERS.get() ) );
    public static final RegistryObject<Block> YELLOW_WILDFLOWERS_ON_TOP = INSTANCE.registerBlock( "yellow_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.YELLOW_WILDFLOWERS.get() ) );
    public static final RegistryObject<Block> SANDY_SPROUTS_ON_TOP = INSTANCE.registerBlock( "sandy_sprouts_on_top",
            () -> new SandySproutsOnTop( PVJBlocks.SANDY_SPROUTS.get() ) );
    public static final RegistryObject<Block> WHITE_WILDFLOWERS_ON_TOP = INSTANCE.registerBlock( "white_wildflowers_on_top",
            () -> new PetalBlockOnTop( PVJBlocks.WHITE_WILDFLOWERS.get() ) );
}
