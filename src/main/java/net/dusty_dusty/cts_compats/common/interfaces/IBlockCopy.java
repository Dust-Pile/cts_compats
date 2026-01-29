package net.dusty_dusty.cts_compats.common.interfaces;

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

    default boolean isSlabBlock() {
        return getCopyType() == BlockCopyType.SLAB;
    }

    default boolean isOnTopBlock() {
        return getCopyType() == BlockCopyType.ON_TOP;
    }

    enum BlockCopyType {
        ON_TOP("on_top"),
        SLAB("slab");

        final private String name;

        BlockCopyType( String string ) {
            this.name = string;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

}
