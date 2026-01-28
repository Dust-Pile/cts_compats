package net.dusty_dusty.cts_compats.common;

import net.countered.terrainslabs.block.ModSlabsMap;
import net.countered.terrainslabs.callbacks.RegisterCallbacks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.Optional;

public class AssignUtil {
    public static final VoxelShape FULL_BLOCK_ON_SLAB = Block.box( 0.0D, -8.0D, 0.0D, 16.0D, 8.0D, 16.0D );

    // Checks 9 locations to ensure failure chance is abysmal
    public static BlockBehaviour.Properties autoOnTopOffset( BlockBehaviour.Properties props, Block block ) {
        if ( props.offsetFunction.isEmpty() ) {
            return assignOnTopOfSlabOffset( props, BlockBehaviour.OffsetType.NONE );
        }

        ArrayList<Vec3> vectors = planeUtil( props, block );

        for ( Vec3 vec : vectors ) {
            if ( vec.y != 0.0 ) {
                return assignOnTopOfSlabOffset( props, BlockBehaviour.OffsetType.XYZ );
            }
        }

        for ( Vec3 vec : vectors ) {
            if ( vec.z != 0.0 || vec.x != 0.0 ) {
                return assignOnTopOfSlabOffset( props, BlockBehaviour.OffsetType.XYZ );
            }
        }

        return assignOnTopOfSlabOffset( props, BlockBehaviour.OffsetType.NONE );
    }

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

    public static BlockBehaviour.Properties assignOnTopOfSlabOffset( BlockBehaviour.Properties props, BlockBehaviour.OffsetType pOffsetType ) {
        switch (pOffsetType) {
            case XYZ:
                props.offsetFunction = Optional.of((blockState, blockGetter, blockPos) -> {
                    Block block = blockState.getBlock();
                    long i = Mth.getSeed(blockPos.getX(), 0, blockPos.getZ());
                    double d0 = ((double)((float)(i >> 4 & 15L) / 15.0F) - 1.0D) * (double)block.getMaxVerticalOffset();
                    float f = block.getMaxHorizontalOffset();
                    double d1 = Mth.clamp(((double)((float)(i & 15L) / 15.0F) - 0.5D) * 0.5D, -f, f);
                    double d2 = Mth.clamp(((double)((float)(i >> 8 & 15L) / 15.0F) - 0.5D) * 0.5D, -f, f);
                    return new Vec3(d1, d0 - 8.0D, d2);
                });
                break;
            case XZ:
                props.offsetFunction = Optional.of((blockState, blockGetter, blockPos) -> {
                    Block block = blockState.getBlock();
                    long i = Mth.getSeed(blockPos.getX(), 0, blockPos.getZ());
                    float f = block.getMaxHorizontalOffset();
                    double d0 = Mth.clamp(((double)((float)(i & 15L) / 15.0F) - 0.5D) * 0.5D, -f, f);
                    double d1 = Mth.clamp(((double)((float)(i >> 8 & 15L) / 15.0F) - 0.5D) * 0.5D, -f, f);
                    return new Vec3(d0, -8.0D, d1);
                });
                break;
            default:
                props.offsetFunction = Optional.of( (blockState, blockGetter, blockPos) ->
                        new Vec3( 0.0D, -8.0D, 0.0D ));
        }

        return props;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static ArrayList<Vec3> planeUtil(BlockBehaviour.Properties props, Block block ) {
        ArrayList<Vec3> vectors = new ArrayList<>();
        for ( int i = -1; i < 2; i++ ) {
            for ( int j = -1; j < 2; j++ ) {
                vectors.add( props.offsetFunction.get().evaluate( block.defaultBlockState(), null, new BlockPos( i, 0, j ) ) );
            }
        }

        return vectors;
    }
}
