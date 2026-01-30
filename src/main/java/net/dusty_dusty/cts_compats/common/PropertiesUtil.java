package net.dusty_dusty.cts_compats.common;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Optional;

public class PropertiesUtil {
    private static final double OFFSET_VAL = -0.5;

    public static BlockBehaviour.Properties copyAndOffsetOnTopBlockProperties( Block block ) {
        return autoOnTopOffset( BlockBehaviour.Properties.copy( block ), block );
    }

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
                return assignOnTopOfSlabOffset( props, BlockBehaviour.OffsetType.XZ );
            }
        }

        return assignOnTopOfSlabOffset( props, BlockBehaviour.OffsetType.NONE );
    }

    public static BlockBehaviour.Properties assignOnTopOfSlabOffset(BlockBehaviour.Properties props, BlockBehaviour.OffsetType pOffsetType ) {
        switch (pOffsetType) {
            case XYZ:
                props.offsetFunction = Optional.of((blockState, blockGetter, blockPos) -> {
                    Block block = blockState.getBlock();
                    long i = Mth.getSeed(blockPos.getX(), 0, blockPos.getZ());
                    double d0 = ((double)((float)(i >> 4 & 15L) / 15.0F) - 1.0D) * (double)block.getMaxVerticalOffset();
                    float f = block.getMaxHorizontalOffset();
                    double d1 = Mth.clamp(((double)((float)(i & 15L) / 15.0F) - 0.5D) * 0.5D, -f, f);
                    double d2 = Mth.clamp(((double)((float)(i >> 8 & 15L) / 15.0F) - 0.5D) * 0.5D, -f, f);
                    return new Vec3(d1, d0 + OFFSET_VAL, d2);
                });
                break;
            case XZ:
                props.offsetFunction = Optional.of((blockState, blockGetter, blockPos) -> {
                    Block block = blockState.getBlock();
                    long i = Mth.getSeed(blockPos.getX(), 0, blockPos.getZ());
                    float f = block.getMaxHorizontalOffset();
                    double d0 = Mth.clamp(((double)((float)(i & 15L) / 15.0F) - 0.5D) * 0.5D, -f, f);
                    double d1 = Mth.clamp(((double)((float)(i >> 8 & 15L) / 15.0F) - 0.5D) * 0.5D, -f, f);
                    return new Vec3(d0, OFFSET_VAL, d1);
                });
                break;
            default:
                props.offsetFunction = Optional.of( (blockState, blockGetter, blockPos) ->
                        new Vec3( 0.0D, OFFSET_VAL, 0.0D ));
        }

        return props;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static ArrayList<Vec3> planeUtil( BlockBehaviour.Properties props, Block block ) {
        ArrayList<Vec3> vectors = new ArrayList<>();
        for ( int i = -1; i < 2; i++ ) {
            for ( int j = -1; j < 2; j++ ) {
                vectors.add( props.offsetFunction.get().evaluate( block.defaultBlockState(), null, new BlockPos( i, 0, j ) ) );
            }
        }

        return vectors;
    }
}
