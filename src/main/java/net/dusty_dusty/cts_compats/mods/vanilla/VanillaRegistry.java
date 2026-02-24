package net.dusty_dusty.cts_compats.mods.vanilla;

import net.dusty_dusty.cts_compats.common.block.*;
import net.dusty_dusty.cts_compats.common.block.interfaces.IAssignable;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.*;
import net.dusty_dusty.cts_compats.common.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.common.registry.IColorRegistry;
import net.dusty_dusty.cts_compats.mods.vanilla.block.SweetBerryBushOnTop;
import net.dusty_dusty.cts_compats.mods.vanilla.block.WitherRoseOnTop;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

@SuppressWarnings({"unused"})
public final class VanillaRegistry extends AbstractRegistry {
    private static final VanillaRegistry INSTANCE = new VanillaRegistry( "minecraft" );
    protected VanillaRegistry(String modId) {
        super(modId);
    }

    public static AbstractRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<IColorRegistry> getColorRegistry() {
        return Optional.of( new VanillaColorRegistry() );
    }

    // Unique
    public static final RegistryObject<Block> DRIPSTONE_SLAB =
            INSTANCE.registerBlock( "dripstone_slab", () -> new CustomSlabBlock( Blocks.DRIPSTONE_BLOCK ) );
    public static final RegistryObject<Block> PINK_PETALS_ON_TOP =
            INSTANCE.registerBlock( "pink_petals_on_top", () -> new PetalBlockOnTop( Blocks.PINK_PETALS ) );
    public static final RegistryObject<Block> TALL_GRASS_ON_TOP =
            INSTANCE.registerBlock( "tall_grass_on_top", () -> new DoublePlantOnTop( Blocks.TALL_GRASS ) );
    public static final RegistryObject<Block> LARGE_FERN_ON_TOP =
            INSTANCE.registerBlock( "large_fern_on_top", () -> new DoublePlantOnTop( Blocks.LARGE_FERN ) );
    public static final RegistryObject<Block> SWEET_BERRY_BUSH_ON_TOP =
            INSTANCE.registerBlock( "sweet_berry_bush_on_top", () -> new SweetBerryBushOnTop( Blocks.SWEET_BERRY_BUSH ) );

    // Light Sources
    public static final RegistryObject<Block> TORCH_ON_TOP =
            INSTANCE.registerBlock( "torch_on_top", () -> new TorchOnTop( Blocks.TORCH, ParticleTypes.FLAME ) );
    public static final RegistryObject<Block> SOUL_TORCH_ON_TOP =
            INSTANCE.registerBlock( "soul_torch_on_top", () -> new TorchOnTop( Blocks.SOUL_TORCH, ParticleTypes.SOUL ) );
    public static final RegistryObject<Block> LANTERN_ON_TOP =
            INSTANCE.registerBlock( "lantern_on_top", () -> new LanternOnTop( Blocks.LANTERN ) );
    public static final RegistryObject<Block> SOUL_LANTERN_ON_TOP =
            INSTANCE.registerBlock( "soul_lantern_on_top", () -> new LanternOnTop( Blocks.SOUL_LANTERN ) );

    // Fungus
    public static final VoxelShape fungusShape = Block.box(4.0D, -8.0D, 4.0D, 12.0D, 1.0D, 12.0D);
    public static final RegistryObject<Block> CRIMSON_FUNGUS_ON_TOP =
            INSTANCE.registerBlock( "crimson_fungus_on_top", () -> new BushBlockOnTop( Blocks.CRIMSON_FUNGUS, fungusShape ) );
    public static final RegistryObject<Block> WARPED_FUNGUS_ON_TOP =
            INSTANCE.registerBlock( "warped_fungus_on_top", () -> new BushBlockOnTop( Blocks.WARPED_FUNGUS, fungusShape ) );

    // Flowers
    public static final RegistryObject<Block> RED_TULIP_ON_TOP =
            INSTANCE.registerBlock( "red_tulip_on_top", () -> new FlowerBlockOnTop( Blocks.RED_TULIP ) );
    public static final RegistryObject<Block> ORANGE_TULIP_ON_TOP =
            INSTANCE.registerBlock( "orange_tulip_on_top", () -> new FlowerBlockOnTop( Blocks.ORANGE_TULIP ) );
    public static final RegistryObject<Block> PINK_TULIP_ON_TOP =
            INSTANCE.registerBlock( "pink_tulip_on_top", () -> new FlowerBlockOnTop( Blocks.PINK_TULIP ) );
    public static final RegistryObject<Block> WHITE_TULIP_ON_TOP =
            INSTANCE.registerBlock( "white_tulip_on_top", () -> new FlowerBlockOnTop( Blocks.WHITE_TULIP ) );
    public static final RegistryObject<Block> LILY_OF_THE_VALLEY_ON_TOP =
            INSTANCE.registerBlock( "lily_of_the_valley_on_top", () -> new FlowerBlockOnTop( Blocks.LILY_OF_THE_VALLEY ) );
    public static final RegistryObject<Block> OXEYE_DAISY_ON_TOP =
            INSTANCE.registerBlock( "oxeye_daisy_on_top", () -> new FlowerBlockOnTop( Blocks.OXEYE_DAISY ) );
    public static final RegistryObject<Block> ALLIUM_ON_TOP =
            INSTANCE.registerBlock( "allium_on_top", () -> new FlowerBlockOnTop( Blocks.ALLIUM ) );
    public static final RegistryObject<Block> BLUE_ORCHID_ON_TOP =
            INSTANCE.registerBlock( "blue_orchid_on_top", () -> new FlowerBlockOnTop( Blocks.BLUE_ORCHID ) );
    public static final RegistryObject<Block> WITHER_ROSE_ON_TOP =
            INSTANCE.registerBlock( "wither_rose_on_top", () -> new WitherRoseOnTop( Blocks.WITHER_ROSE ) );
    public static final RegistryObject<Block> TORCHFLOWER_ON_TOP =
            INSTANCE.registerBlock( "torchflower_on_top", () -> new FlowerBlockOnTop( Blocks.TORCHFLOWER ) );

    // Tall Flowers
    public static final RegistryObject<Block> PITCHER_PLANT_ON_TOP =
            INSTANCE.registerBlock( "pitcher_plant_on_top", () -> new DoublePlantOnTop( Blocks.PITCHER_PLANT ) );
    public static final RegistryObject<Block> PEONY_ON_TOP =
            INSTANCE.registerBlock( "peony_on_top", () -> new DoubleFlowerOnTop( Blocks.PEONY ) );
    public static final RegistryObject<Block> ROSE_BUSH_ON_TOP =
            INSTANCE.registerBlock( "rose_bush_on_top", () -> new DoubleFlowerOnTop( Blocks.ROSE_BUSH ) );
    public static final RegistryObject<Block> SUNFLOWER_ON_TOP =
            INSTANCE.registerBlock( "sunflower_on_top", () -> new DoubleFlowerOnTop( Blocks.SUNFLOWER ) );
    public static final RegistryObject<Block> LILAC_ON_TOP =
            INSTANCE.registerBlock( "lilac_on_top", () -> new DoubleFlowerOnTop( Blocks.LILAC ) );

    @Override
    protected void assignExtras() {
        IAssignable.AssignUtil.putOnTopVegetation( Blocks.MOSS_CARPET, Blocks.AIR );
    }
}
