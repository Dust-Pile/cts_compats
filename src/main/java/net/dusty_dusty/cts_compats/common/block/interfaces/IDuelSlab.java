package net.dusty_dusty.cts_compats.common.block.interfaces;

import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IDuelSlab extends ISlabCopy {

    Block getDuel();

    default Block getDuelOriginBlock() {
        return getDuelSlab().getOriginBlock();
    }

    default ISlabCopy getDuelSlab() {
        return new BlockCopyWrapper.SlabCopyWrapper( (IBlockCopy) getDuel() );
    }

    static boolean areDuel( Block block, Block block2 ) {
        if ( block instanceof IDuelSlab duel ) {
            return duel.getDuel().equals( block2 );
        }
        if ( block2 instanceof IDuelSlab duel ) {
            return duel.getDuel().equals( block );
        }
        return false;
    }
    static boolean areDuel( BlockState state, BlockState state2 ) {
        return areDuel( state.getBlock(), state2.getBlock() );
    }

}
