package net.dusty_dusty.cts_compats.common.block.interfaces;

import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface IBlockCopyForge extends IBlockCopy {

    Block getOriginBlock();

    @Override
    default String[] getOriginBlockStrings() {
        String[] strings = this.getOriginBlock().getDescriptionId().split("\\.");
        return new String[] { strings[1], strings[2] };
    };

    default Item getOriginalItem() {
        return this.getOriginBlock().asItem();
    }

}
