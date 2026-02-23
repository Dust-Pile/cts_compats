package net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry;

import net.dusty_dusty.cts_compats.common.block.onTopBlocks.PetalBlockOnTop;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public final class BOPBetaRegistry {
    public static BOPBaseRegistry INSTANCE = BOPBaseRegistry.getInstance();
    public static BOPBaseRegistry getInstance() {
        INSTANCE.colorRegistry = Optional.of( new BOPBetaColorRegistry() );
        return INSTANCE;
    }

//    public static final RegistryObject<Block> WILDFLOWER_ON_TOP = INSTANCE.registerBlockCutout( "wildflower_on_top",
//            () -> new PetalBlockOnTop( BOPReference.WILDFLOWER ) );
}
