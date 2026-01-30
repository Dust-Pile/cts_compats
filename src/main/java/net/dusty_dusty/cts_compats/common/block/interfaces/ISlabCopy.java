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

    default CopyModelType getCopyModelType() {
        return CopyModelType.CUBE_ALL;
    }

    enum CopyModelType {
        CUBE_ALL("cube_all");

        private final String name;

        CopyModelType(String string ) {
            this.name = string;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

}
