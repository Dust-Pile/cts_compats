package net.dusty_dusty.cts_compats.common;

import net.countered.terrainslabs.block.ModSlabsMap;
import net.countered.terrainslabs.callbacks.RegisterCallbacks;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class AssignUtil {

    public static void putOnTopVegetation( Block key, Block value ) {
        String[] keyStrings = getIdComponents( key );
        String[] valueStrings = getIdComponents( value );
        ModSlabsMap.putOnTopVegetationFromString( keyStrings[0], keyStrings[1], valueStrings[0], valueStrings[1] );
    }
    public static void putTerrainSlab( Block key, Block value ) {
        String[] keyStrings = getIdComponents( key );
        String[] valueStrings = getIdComponents( value );
        ModSlabsMap.putTerrainSlabFromString( keyStrings[0], keyStrings[1], valueStrings[0], valueStrings[1] );
    }
    public static void putBlockBelowReplacement( Block key, Block value ) {
        String[] keyStrings = getIdComponents( key );
        String[] valueStrings = getIdComponents( value );
        ModSlabsMap.putBlockBelowReplacementFromString( keyStrings[0], keyStrings[1], valueStrings[0], valueStrings[1] );
    }
    public static void putTopSlabReplacement( Block key, Block value ) {
        String[] keyStrings = getIdComponents( key );
        String[] valueStrings = getIdComponents( value );
        ModSlabsMap.putTopSlabReplacementFromString( keyStrings[0], keyStrings[1], valueStrings[0], valueStrings[1] );
    }
    public static void putVegetaitonOnTopItem( Item item, Block block ) {
        String[] keyStrings = getIdComponents( item );
        String[] valueStrings = getIdComponents( block );
        RegisterCallbacks.putVegetaitonOnTopItemFromString( keyStrings[0], keyStrings[1], valueStrings[0], valueStrings[1] );
    }

//    private static String getBlockModId( Block block ) {
//        return block.getDescriptionId().split("\\.")[1];
//    }
//    private static String getBlockName( Block block ) {
//        return block.getDescriptionId().split("\\.")[2];
//    }
    private static String[] getIdComponents( Block block ) {
        String[] strs = block.getDescriptionId().split("\\.");
        return new String[] { strs[1], strs[2] };
    }
    private static String[] getIdComponents( Item item ) {
        String[] strs = item.getDescriptionId().split("\\.");
        return new String[] { strs[1], strs[2] };
    }

}
