package net.dusty_dusty.dataGen;

import com.google.gson.JsonObject;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.RegistryManager;
import net.dusty_dusty.cts_compats.common.block.DoublePlantOnTop;
import net.dusty_dusty.cts_compats.common.block.interfaces.IBlockCopy;
import net.dusty_dusty.cts_compats.common.block.interfaces.IOnTopCopy;
import net.dusty_dusty.cts_compats.common.block.interfaces.ISlabCopy;
import net.dusty_dusty.dataGen.util.BlockStateCopyUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.io.IOException;

import static net.dusty_dusty.cts_compats.CTSCompats.LOGGER;

public class ModBlockStateProvider extends BlockStateProvider {
    final ExistingFileHelper existingFileHelper;
    final BlockStateCopyUtil util;

    protected ModBlockStateProvider( PackOutput output, ExistingFileHelper existingFileHelper ) {
        super( output, CTSCompats.MODID, existingFileHelper );
        this.existingFileHelper = existingFileHelper;
        util = new BlockStateCopyUtil( existingFileHelper );
    }

    @Override
    protected void registerStatesAndModels() {
        RegistryManager.forEachRegistry( registry -> {
            for ( RegistryObject<Block> entry : registry.COMPAT_BLOCKS.getEntries() ) {
                Block block = entry.get();
                LOGGER.info( "Auto Generating files for {} if applicable", block );
                if ( block instanceof IOnTopCopy onTopCopy ) {
                    if ( onTopCopy instanceof DoublePlantOnTop ) {
                        // TODO: Automate Double Plant Model
                        copyItemModel( onTopCopy );
                    } else {
                        simpleBlockCopy( onTopCopy );
                    }
                } else if ( block instanceof ISlabCopy slabCopy && slabCopy instanceof SlabBlock &&
                        slabCopy.getCopyModelType() != ISlabCopy.CopyModelType.TINTED_OVERLAY )
                {
                    slabCopyFromCube( slabCopy );
                } else {
                    LOGGER.warn( "No Valid arrangement found for block {}", block );
                }
            }
        } );
    }


    @SuppressWarnings("deprecation")
    private void slabCopyFromCube(ISlabCopy blockCopy) {
        ResourceLocation origLoc = BuiltInRegistries.BLOCK.getKey( blockCopy.getOriginBlock() );
        ResourceLocation loc = BuiltInRegistries.BLOCK.getKey( (Block) blockCopy );
        JsonObject jsonObject = getBlockJson( blockCopy, "models/block" );
        if ( jsonObject == null ) {
            return;
        }
        if ( jsonObject.get( "parent" ).getAsString().contains("cube_all") ) {
            slabCopyFromCubeAll( blockCopy, jsonObject, loc, origLoc );
            return;
        }

        JsonObject textures = jsonObject.getAsJsonObject( "textures" );

        try {
            this.slabBlock( (SlabBlock) blockCopy, origLoc,
                    ResourceLocation.parse( textures.get( "side" ).getAsString() ),
                    ResourceLocation.parse( textures.get( "bottom" ).getAsString() ),
                    ResourceLocation.parse( textures.get( "top" ).getAsString() ) );
        } catch (Exception e) {
            LOGGER.error( e.toString() );
            return;
        }

        simpleBlockItem( (Block) blockCopy, new BlockModelBuilder( loc.withPrefix("block/"), existingFileHelper ) );
    }

    private void slabCopyFromCubeAll( ISlabCopy blockCopy, JsonObject jsonObject, ResourceLocation loc, ResourceLocation origLoc ) {
        this.slabBlock( (SlabBlock) blockCopy, origLoc,
                ResourceLocation.parse( jsonObject.getAsJsonObject( "textures" ).get( "all" ).getAsString() ) );

        simpleBlockItem( (Block) blockCopy, new BlockModelBuilder( loc.withPrefix("block/"), existingFileHelper ) );
    }

    private void simpleBlockCopy( IOnTopCopy blockCopy ) {
        JsonObject jsonObject = getBlockJson( blockCopy, "blockstates" );
        if ( jsonObject == null ) {
            return;
        }

        copyItemModel( blockCopy );

        if ( jsonObject.has( "variants" ) ) {
            util.copyVariants( this.getVariantBuilder( (Block) blockCopy ), jsonObject );
        } else {
            util.copyMultipart( this.getMultipartBuilder( (Block) blockCopy ), jsonObject, (Block) blockCopy );
        }
    }

    @SuppressWarnings("deprecation")
    private void copyItemModel( IBlockCopy blockCopy ) {
        ResourceLocation origItem = BuiltInRegistries.ITEM.getKey( blockCopy.getOriginBlock().asItem() );

        itemModels().getBuilder( "item/" + ((Block) blockCopy).asItem().getDescriptionId().split("\\.")[2] )
                .parent( new ItemModelBuilder( origItem.withPrefix("item/"), existingFileHelper ) );
    }

    @SuppressWarnings("deprecation")
    private JsonObject getBlockJson(IBlockCopy blockCopy, String prefix ) {
        ResourceLocation loc = BuiltInRegistries.BLOCK.getKey( blockCopy.getOriginBlock() );

        try {
            Resource modelJson = existingFileHelper.getResource( loc, PackType.CLIENT_RESOURCES, ".json", prefix);
            return GsonHelper.parse( modelJson.openAsReader() );
        } catch ( IOException e ) {
            LOGGER.warn( "CTS_COMPATS: {}", e.toString() );
            return null;
        }
    }
}
