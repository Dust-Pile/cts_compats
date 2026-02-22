package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.flowerBlocks;

import net.dusty_dusty.cts_compats.RegistryManager;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.FlowerBlockOnTop;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;
import net.dusty_dusty.cts_compats.common.registry.Version;
import net.minecraft.world.level.block.Block;

public final class FlowerOnTopBOPUtil {
    static final Version.Filter filter = Version.Filter.acceptLaterThanExclusive( "18.0.0.592" );

    public static FlowerBlockOnTop newFlowerBlock( Block originalBlock ) {
        Version version = RegistryManager.getVersion( IRegistry.BOP_MODID );
        if ( filter.compareTo( version ) < 0 ) {
            return new FlowerOnTopOldBOP( originalBlock );
        } else {
            return new FlowerOnTopBOP( originalBlock );
        }
    }

    public static FlowerBlockOnTop newBurningBlossomOnTop( Block originalBlock ) {
        Version version = RegistryManager.getVersion( IRegistry.BOP_MODID );
        if ( filter.compareTo( version ) < 0 ) {
            return new BurningBlossomOnTopOld( originalBlock );
        } else {
            return new BurningBlossomOnTop( originalBlock );
        }
    }
}
