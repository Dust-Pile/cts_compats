package net.dusty_dusty.cts_compats.mods.meadow;

import net.dusty_dusty.cts_compats.common.block.CustomSlabBlock;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.DoubleFlowerOnTop;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.DoublePlantOnTop;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.FlowerBlockOnTop;
import net.dusty_dusty.cts_compats.common.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.common.registry.IColorRegistry;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.satisfy.meadow.core.registry.ObjectRegistry;

import java.util.Optional;

@SuppressWarnings("unused")
public class MeadowRegistry extends AbstractRegistry {
    private static final MeadowRegistry INSTANCE = new MeadowRegistry( IRegistry.MEADOW_MODID );
    public static MeadowRegistry getInstance() {
        return INSTANCE;
    }

    protected MeadowRegistry(String modId) {
        super(modId);
    }


    // Slab
    public static RegistryObject<Block> LIMESTONE_SLAB = INSTANCE.registerBlock( "limestone_slab",
            () -> new CustomSlabBlock( ObjectRegistry.LIMESTONE.get() ) );

    // On Top Things
    public static RegistryObject<Block> ALPINE_POPPY_ON_TOP = INSTANCE.registerBlock( "alpine_poppy_on_top",
            () -> new FlowerBlockOnTop( ObjectRegistry.ALPINE_POPPY.get() ) );
    public static RegistryObject<Block> DELPHINIUM_ON_TOP = INSTANCE.registerBlock( "delphinium_on_top",
            () -> new FlowerBlockOnTop( ObjectRegistry.DELPHINIUM.get() ) );
    public static RegistryObject<Block> SAXIFRAGE_ON_TOP = INSTANCE.registerBlock( "saxifrage_on_top",
            () -> new FlowerBlockOnTop( ObjectRegistry.SAXIFRAGE.get() ) );
    public static RegistryObject<Block> ENZIAN_ON_TOP = INSTANCE.registerBlock( "enzian_on_top",
            () -> new FlowerBlockOnTop( ObjectRegistry.ENZIAN.get() ) );
    public static RegistryObject<Block> FIRE_LILY_ON_TOP = INSTANCE.registerBlock( "fire_lily_on_top",
            () -> new FlowerBlockOnTop( ObjectRegistry.FIRE_LILY.get() ) );
    public static RegistryObject<Block> ERIOPHORUM_ON_TOP = INSTANCE.registerBlock( "eriophorum_on_top",
            () -> new FlowerBlockOnTop( ObjectRegistry.ERIOPHORUM.get() ) );
    public static RegistryObject<Block> TALL_ERIOPHORUM_ON_TOP = INSTANCE.registerBlock( "eriophorum_tall_on_top",
            () -> new DoubleFlowerOnTop( ObjectRegistry.ERIOPHORUM_TALL.get() ) );
    public static RegistryObject<Block> SMALL_FIR_ON_TOP = INSTANCE.registerBlock( "small_fir_on_top",
            () -> new DoublePlantOnTop( ObjectRegistry.SMALL_FIR.get() ) );


    @Override
    public Optional<IColorRegistry> getColorRegistry() {
        return Optional.of( new MeadowColorRegistry() );
    }
}
