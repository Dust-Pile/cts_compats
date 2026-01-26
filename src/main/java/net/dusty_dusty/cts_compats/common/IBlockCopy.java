package net.dusty_dusty.cts_compats.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface IBlockCopy {

    Block getOriginBlock();

    default Item getOriginalItem() {
        return this.getOriginBlock().asItem();
    };

}
