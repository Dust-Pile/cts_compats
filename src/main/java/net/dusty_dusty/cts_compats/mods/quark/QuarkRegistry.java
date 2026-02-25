package net.dusty_dusty.cts_compats.mods.quark;

import net.dusty_dusty.cts_compats.common.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.common.registry.IColorRegistry;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;
import net.dusty_dusty.cts_compats.mods.quark.block.GlowLichenGrowthOnTop;
import net.dusty_dusty.cts_compats.mods.quark.block.GlowshroomOnTop;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.violetmoon.quark.content.world.module.GlimmeringWealdModule;

import java.util.Optional;

// TODO: Figure out chorus plants on top :sob:
public class QuarkRegistry extends AbstractRegistry {
    private static final QuarkRegistry INSTANCE = new QuarkRegistry( IRegistry.QUARK_MODID );
    public static QuarkRegistry getInstance() {
        return INSTANCE;
    }

    protected QuarkRegistry(String modId) {
        super(modId);
    }


    // On Top Plants
    public static final RegistryObject<Block> GLOW_SHROOM_ON_TOP = INSTANCE.registerBlock( "glow_shroom_on_top",
            () -> new GlowshroomOnTop( GlimmeringWealdModule.glow_shroom ) );
    public static final RegistryObject<Block> GLOW_LICHEN_GROWTH_ON_TOP = INSTANCE.registerBlock( "glow_lichen_growth_on_top",
            () -> new GlowLichenGrowthOnTop( GlimmeringWealdModule.glow_lichen_growth ) );
//    public static final RegistryObject<Block> CHORUS_WEEDS_ON_TOP = INSTANCE.registerBlockCutout( "chorus_weeds_on_top",
//            () -> new ChorusPlantOnTop( ChorusVegetationModule.chorus_weeds ) );
//    public static final RegistryObject<Block> CHORUS_TWIST_ON_TOP = INSTANCE.registerBlockCutout( "chorus_twist_on_top",
//            () -> new ChorusPlantOnTop( ChorusVegetationModule.chorus_twist ) );


    @Override
    public Optional<IColorRegistry> getColorRegistry() {
        return Optional.empty();
    }
}
