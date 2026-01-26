package net.dusty_dusty.cts_compats.common;

import net.minecraft.world.level.block.Block;

public interface IAssignable extends IBlockCopy {

    default void assign() {
        AssignUtil.putOnTopVegetation( this.getOriginBlock(), (Block) this );
        AssignUtil.putVegetationOnTopItem( this.getOriginalItem(), (Block) this );
    }

}
