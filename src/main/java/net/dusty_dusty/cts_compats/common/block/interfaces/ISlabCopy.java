package net.dusty_dusty.cts_compats.common.block.interfaces;

public interface ISlabCopy extends IBlockCopy {

    @Override
    default BlockCopyType getCopyType() {
        return BlockCopyType.SLAB;
    }

    @Override
    default boolean isSlabBlock() {
        return true;
    }

    @Override
    default boolean isOnTopBlock() {
        return false;
    }

}
