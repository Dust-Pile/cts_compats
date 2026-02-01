package net.dusty_dusty.cts_compats.common.block.interfaces;

import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public class BlockCopyWrapper implements IBlockCopyForge {
    private final IBlockCopy blockCopy;

    public BlockCopyWrapper ( IBlockCopy blockCopy ) {
        this.blockCopy = blockCopy;
    }
    public BlockCopyWrapper ( String modid, String path ) {
        this.blockCopy = (IBlockCopy) ForgeRegistries.BLOCKS.getValue(
                ResourceLocation.fromNamespaceAndPath( modid, path ) );
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

    public static class SlabCopyWrapper extends BlockCopyWrapper implements ISlabCopy {
        public SlabCopyWrapper(IBlockCopy blockCopy) {
            super(blockCopy);
        }
        public SlabCopyWrapper( String modid, String path ) {
            super( modid, path );
        }
    }

    public static class OnTopCopyWrapper extends BlockCopyWrapper implements ISlabCopy {
        public OnTopCopyWrapper(IBlockCopy blockCopy) {
            super(blockCopy);
        }
        public OnTopCopyWrapper( String modid, String path ) {
            super( modid, path );
        }
    }
}
