package net.dusty_dusty.cts_compats.common.block.interfaces;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface IBlockCopy {

    Block getOriginBlock();

    default Item getOriginalItem() {
        return this.getOriginBlock().asItem();
    }

    BlockCopyType getCopyType();

    default boolean isSlabBlock() {
        return getCopyType() == BlockCopyType.SLAB;
    }

    default boolean isOnTopBlock() {
        return getCopyType() == BlockCopyType.ON_TOP;
    }

    enum BlockCopyType {
        ON_TOP("on_top"),
        SLAB("slab");

        private final String name;

        BlockCopyType( String string ) {
            this.name = string;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

}
