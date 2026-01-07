package net.dusty_dusty.cts_compats.core;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class CompatSlabsMap {

    public static Map<Block, Block > compatOnTopVegitationMap = new HashMap<>();

    public static Map< Block, Block > compatSlabMap = new HashMap<>();

    public static boolean mapVegitation( Block originalBlock, Block onTopBlock ) {
        if ( compatOnTopVegitationMap.containsKey( originalBlock ) ||
                compatOnTopVegitationMap.containsValue( onTopBlock )
        ) {
            return false;
        }

        compatOnTopVegitationMap.put( originalBlock, onTopBlock ) ;
        return true;
    }
    public static boolean mapVegitation( RegistryObject<Block> originalRegister, RegistryObject<Block> onTopRegister ) {
        return mapVegitation( originalRegister.get(), onTopRegister.get() );
    }

    public static boolean mapTerrainSlab( Block originalBlock, Block slabBlock ) {
        if ( compatSlabMap.containsKey( originalBlock ) ||
                compatSlabMap.containsValue( slabBlock )
        ) {
            return false;
        }

        compatSlabMap.put( originalBlock, slabBlock ) ;
        return true;
    }
}
