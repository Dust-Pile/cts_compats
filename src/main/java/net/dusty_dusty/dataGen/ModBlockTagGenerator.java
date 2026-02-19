package net.dusty_dusty.dataGen;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.RegistryManager;
import net.dusty_dusty.cts_compats.common.TagReference;
import net.dusty_dusty.cts_compats.common.block.interfaces.IBlockCopyForge;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.DoubleFlowerOnTop;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.DoublePlantOnTop;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    final ExistingFileHelper existingFileHelper;

    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CTSCompats.MODID, existingFileHelper);
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    protected void addTags( HolderLookup.Provider pProvider ) {
        IntrinsicTagAppender<Block> tagAppender = this.tag( TagReference.TALL_DECORATIONS);
        RegistryManager.forEachRegistry( registry -> {
            registry.getRegistryBlocks().forEach( blockRegister -> {
                Block block = blockRegister.get();
                Block originBlock = ( (IBlockCopyForge) block ).getOriginBlock();
                if ( block instanceof DoublePlantOnTop && !( block instanceof DoubleFlowerOnTop ) ) {
                    tagAppender.add( originBlock );
                }
            });
        });

    }
}
