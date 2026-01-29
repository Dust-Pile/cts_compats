package net.dusty_dusty.cts_compats.common.interfaces;

public interface IOnTopCopy extends IBlockCopy {

    @Override
    default BlockCopyType getCopyType() {
        return BlockCopyType.ON_TOP;
    }

    @Override
    default boolean isSlabBlock() {
        return false;
    }

    @Override
    default boolean isOnTopBlock() {
        return true;
    }

}
