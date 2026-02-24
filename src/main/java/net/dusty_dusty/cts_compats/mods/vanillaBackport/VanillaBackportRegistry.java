package net.dusty_dusty.cts_compats.mods.vanillaBackport;

import com.blackgear.vanillabackport.common.level.blocks.EyeblossomBlock;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.dusty_dusty.cts_compats.common.block.CustomSlabBlock;
import net.dusty_dusty.cts_compats.common.block.interfaces.IAssignable;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.BushBlockOnTop;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.PetalBlockOnTop;
import net.dusty_dusty.cts_compats.common.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.common.registry.IColorRegistry;
import net.dusty_dusty.cts_compats.common.registry.IEmissiveRegistry;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;
import net.dusty_dusty.cts_compats.mods.vanillaBackport.block.DryGrassOnTop;
import net.dusty_dusty.cts_compats.mods.vanillaBackport.block.EyeblossomOnTop;
import net.dusty_dusty.cts_compats.mods.vanillaBackport.block.FireflyBushOnTop;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

// TODO: Emissives

@SuppressWarnings("unused")
public class VanillaBackportRegistry extends AbstractRegistry {
    private static final VanillaBackportRegistry INSTANCE = new VanillaBackportRegistry( IRegistry.VB_MODID );
    public static VanillaBackportRegistry getInstance() {
        return INSTANCE;
    }

    protected VanillaBackportRegistry(String modId) {
        super(modId);
    }


    // Slab
    public static final RegistryObject<Block> PALE_MOSS_SLAB = INSTANCE.registerBlock( "pale_moss_slab",
            () -> new CustomSlabBlock( ModBlocks.PALE_MOSS_BLOCK.get() ) );

    // On Top Things
    public static final RegistryObject<Block> BUSH_ON_TOP = INSTANCE.registerBlockCutout( "vanilla_bush_on_top",
            () -> new BushBlockOnTop( ModBlocks.BUSH.get(), Block.box( 0.0, -8.0, 0.0, 16.0, 5.0, 16.0 ) ) );
    public static final RegistryObject<Block> CACTUS_FLOWER_ON_TOP = INSTANCE.registerBlockCutout( "cactus_flower_on_top",
            () -> new BushBlockOnTop( ModBlocks.CACTUS_FLOWER.get(), Block.box(1.0F, -8.0F, 1.0F, 15.0F, 4.0F, 15.0F),
                    BlockCheckWrapper.ALWAYS.asGroup() ) );
    public static final RegistryObject<Block> FIREFLY_BUSH_ON_TOP = INSTANCE.registerBlockCutout( "firefly_bush_on_top",
            () -> new FireflyBushOnTop( ModBlocks.FIREFLY_BUSH.get() ) );
    public static final RegistryObject<Block> SHORT_DRY_GRASS_ON_TOP = INSTANCE.registerBlockCutout( "short_dry_grass_on_top",
            () -> new DryGrassOnTop( ModBlocks.SHORT_DRY_GRASS.get(), Block.box(2.0D, -8.0D, 2.0D, 14.0D, 5.0D, 14.0D) ) );
    public static final RegistryObject<Block> TALL_DRY_GRASS_ON_TOP = INSTANCE.registerBlockCutout( "tall_dry_grass_on_top",
            () -> new DryGrassOnTop( ModBlocks.TALL_DRY_GRASS.get(), Block.box(1.0F, -8.0F, 1.0F, 15.0F, 8.0F, 15.0F) ) );

    public static final RegistryObject<Block> LEAF_LITTER_ON_TOP = INSTANCE.registerBlockCutout( "leaf_litter_on_top",
            () -> new PetalBlockOnTop( ModBlocks.LEAF_LITTER.get() ) );
    public static final RegistryObject<Block> WILDFLOWERS_ON_TOP = INSTANCE.registerBlockCutout( "vanilla_wildflowers_on_top",
            () -> new PetalBlockOnTop( ModBlocks.WILDFLOWERS.get() ) );

    public static final RegistryObject<Block> OPEN_EYEBLOSSOM_ON_TOP = INSTANCE.registerBlockCutout( "open_eyeblossom_on_top",
            () -> new EyeblossomOnTop( EyeblossomBlock.Type.OPEN, ModBlocks.OPEN_EYEBLOSSOM.get() ) );
    public static final RegistryObject<Block> CLOSED_EYEBLOSSOM_ON_TOP = INSTANCE.registerBlockCutout( "closed_eyeblossom_on_top",
            () -> new EyeblossomOnTop( EyeblossomBlock.Type.CLOSED, ModBlocks.CLOSED_EYEBLOSSOM.get() ) );


    @Override
    public void assignExtras() {
        IAssignable.AssignUtil.putOnTopVegetation( ModBlocks.PALE_MOSS_CARPET.get(), Blocks.AIR );
    }

    @Override
    public Optional<IColorRegistry> getColorRegistry() {
        return Optional.of( new VanillaBackportColorRegistry() );
    }

    @Override
    public Optional<IEmissiveRegistry> getEmissiveRegistry() {
        return Optional.of( new VanillaBackportEmissiveRegistry() );
    }
}
