package net.dusty_dusty.cts_compats.common.block.interfaces;

import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockCopyWrapper implements IBlockCopyForge {
    private final IBlockCopy blockCopy;

    public BlockCopyWrapper ( IBlockCopy blockCopy ) {
        this.blockCopy = blockCopy;
    }

    @Override
    public Block getOriginBlock() {
        if ( blockCopy instanceof IBlockCopyForge blockCopyForge ) {
            return blockCopyForge.getOriginBlock();
        }
        String[] locationParts = blockCopy.getOriginBlockStrings();
        return ForgeRegistries.BLOCKS.getValue(
                ResourceLocation.fromNamespaceAndPath( locationParts[0], locationParts[1] ) );
    }

    @Override
    public BlockCopyType getCopyType() {
        return null;
    }
}
