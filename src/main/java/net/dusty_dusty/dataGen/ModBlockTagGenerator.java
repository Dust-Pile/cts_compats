package net.dusty_dusty.dataGen;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.RegistryManager;
import net.dusty_dusty.cts_compats.common.TagReference;
import net.dusty_dusty.cts_compats.common.block.interfaces.IBlockCopyForge;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.DoubleFlowerOnTop;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.DoublePlantOnTop;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPBaseRegistry;
import net.dusty_dusty.cts_compats.mods.vanilla.VanillaRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
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
        IntrinsicTagAppender<Block> tall_decorations = this.tag( TagReference.TALL_DECORATIONS);
        // IntrinsicTagAppender<Block> mineable_hoe = this.tag(BlockTags.MINEABLE_WITH_HOE );

        this.tag( BlockTags.MINEABLE_WITH_PICKAXE ).add(
                BOPBaseRegistry.BRIMSTONE_SLAB.get(),
                BOPBaseRegistry.BRIMSTONE_BUD_ON_TOP.get(),
                BOPBaseRegistry.DRIED_SALT_SLAB.get(),
                BOPBaseRegistry.BRIMSTONE_FUMAROLE_ON_TOP.get(),

                VanillaRegistry.DRIPSTONE_SLAB.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_AXE ).add(
                BOPBaseRegistry.FLESH_SLAB.get(),
                BOPBaseRegistry.POROUS_FLESH_SLAB.get(),
                BOPBaseRegistry.EYEBULB_ON_TOP.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL ).add(
                BOPBaseRegistry.MOSSY_BLACK_SAND_SLAB.get(),
                BOPBaseRegistry.BLACK_SAND_SLAB.get(),
                BOPBaseRegistry.ORANGE_SAND_SLAB.get(),
                BOPBaseRegistry.WHITE_SAND_SLAB.get()
        );

        this.tag( Tags.Blocks.NEEDS_WOOD_TOOL ).add(
                BOPBaseRegistry.BRIMSTONE_FUMAROLE_ON_TOP.get(),
                BOPBaseRegistry.BRIMSTONE_SLAB.get(),

                VanillaRegistry.DRIPSTONE_SLAB.get()
        );

        // TODO: Tool requirement tags!!!
        // TODO: Replaceable tag!!!

        RegistryManager.forEachRegistry( registry -> {
            registry.getRegistryBlocks().forEach( blockRegister -> {
                Block block = blockRegister.get();
                Block originBlock = ( (IBlockCopyForge) block ).getOriginBlock();
                if ( block instanceof DoublePlantOnTop && !( block instanceof DoubleFlowerOnTop ) ) {
                    tall_decorations.add( originBlock );
                }
            });
        });

    }
}
