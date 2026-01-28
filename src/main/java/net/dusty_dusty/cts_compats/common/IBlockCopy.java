package net.dusty_dusty.cts_compats.common;

import net.dusty_dusty.cts_compats.common.block.CustomSlabBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface IBlockCopy {

    Block getOriginBlock();

    default Item getOriginalItem() {
        return this.getOriginBlock().asItem();
    }

    default BlockCopyType getCopyType() {
        return this instanceof CustomSlabBlock ? BlockCopyType.SLAB : BlockCopyType.ON_TOP;
    }

    default CustomSlabBlock getDuelSlab() {
        throw new RuntimeException( "Called \"IBlockCopy.getDuelSlab()\", but method is not implemented." );
    }

    enum BlockCopyType {
        ON_TOP,
        SLAB,
        DUEL_SLAB
    }

}
