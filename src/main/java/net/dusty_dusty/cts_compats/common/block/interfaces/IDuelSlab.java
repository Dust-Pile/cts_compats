package net.dusty_dusty.cts_compats.common.block.interfaces;

import net.minecraft.world.level.block.Block;

public interface IDuelSlab extends ISlabCopy {

    Block getDuel();

    default ISlabCopy getDuelSlab() {
        return (ISlabCopy) getDuel();
    }

    static boolean areDuel( Block block, Block block2 ) {
        if ( ! (block instanceof ISlabCopy && block2 instanceof ISlabCopy ) ) {
            return false;
        }
        if ( block instanceof IDuelSlab duel ) {
            return duel.getDuelSlab().equals( block2 );
        }
        if ( block2 instanceof IDuelSlab duel ) {
            return duel.getDuelSlab().equals( block );
        }
        return false;
    }

}
