package net.dusty_dusty.cts_compats.common.block.interfaces;

import net.countered.terrainslabs.block.ModSlabsMap;
import net.countered.terrainslabs.callbacks.RegisterCallbacks;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface IAssignable extends IBlockCopy {

    default void assign() {
        switch ( this.getCopyType() ) {
            case ON_TOP:
                AssignUtil.putOnTopVegetation( this.getOriginBlock(), (Block) this );
                AssignUtil.putVegetationOnTopItem( this.getOriginalItem(), (Block) this );
                break;
            case SLAB:
                AssignUtil.putTerrainSlab( this.getOriginBlock(), (Block) this );
                if ( this instanceof IDuelSlab ) {
                    IBlockCopy duel = ( (IDuelSlab) this).getDuelSlab();

                    AssignUtil.putTopSlabReplacement( (Block) this, (Block) duel );
                    AssignUtil.putBlockBelowReplacement( this.getOriginBlock(), duel.getOriginBlock() );
                }
                break;
        }
    }

    class AssignUtil {
        public static final VoxelShape FULL_BLOCK_ON_SLAB = Block.box( 0.0D, -8.0D, 0.0D, 16.0D, 8.0D, 16.0D );

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
        public static void putVegetationOnTopItem(Item item, Block block ) {
            String[] keyStrings = getIdComponents( item );
            String[] valueStrings = getIdComponents( block );
            RegisterCallbacks.putVegetaitonOnTopItemFromString( keyStrings[0], keyStrings[1], valueStrings[0], valueStrings[1] );
        }

        private static String[] getIdComponents( Block block ) {
            String[] strings = block.getDescriptionId().split("\\.");
            return new String[] { strings[1], strings[2] };
        }
        private static String[] getIdComponents( Item item ) {
            String[] strings = item.getDescriptionId().split("\\.");
            return new String[] { strings[1], strings[2] };
        }
    }

}
