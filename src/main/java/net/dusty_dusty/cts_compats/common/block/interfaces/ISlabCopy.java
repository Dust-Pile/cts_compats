package net.dusty_dusty.cts_compats.common.block.interfaces;

public interface ISlabCopy extends IBlockCopyForge {

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
        return CopyModelType.CUBE;
    }

    enum CopyModelType {
        CUBE("cube"),
        TINTED_OVERLAY("tinted_overlay");

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
