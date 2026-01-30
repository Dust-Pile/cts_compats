package net.dusty_dusty.dataGen;

import com.google.gson.JsonObject;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.common.block.DoublePlantOnTop;
import net.dusty_dusty.cts_compats.common.interfaces.IBlockCopy;
import net.dusty_dusty.cts_compats.common.interfaces.IOnTopCopy;
import net.dusty_dusty.cts_compats.common.interfaces.ISlabCopy;
import net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.PVJRegistry;
import net.dusty_dusty.cts_compats.mods.vanilla.VanillaRegistry;
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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.json.Json;
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
        // Automatic Gen
        autoFromRegistry( VanillaRegistry.COMPAT_BLOCKS );
        autoFromRegistry( PVJRegistry.COMPAT_BLOCKS );

        // Manual Gen
        slabFromCubeAll( (ISlabCopy) VanillaRegistry.DRIPSTONE_SLAB.get() );
    }

    @SuppressWarnings("deprecation")
    private void slabFromCubeAll(ISlabCopy blockCopy ) {
        ResourceLocation origLoc = BuiltInRegistries.BLOCK.getKey( blockCopy.getOriginBlock() );
        ResourceLocation loc = BuiltInRegistries.BLOCK.getKey( (Block) blockCopy );
        JsonObject jsonObject = getBlockJson( blockCopy, "models/block" );
        if ( jsonObject == null ) {
            return;
        }

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

    private void autoFromRegistry( DeferredRegister<Block> blocks ) {
        for ( RegistryObject<Block> entry : blocks.getEntries() ) {
            Block block = entry.get();
            if ( block instanceof IOnTopCopy ) {
                LOGGER.info( "Creating blockstate json for {}", block );
                if ( block instanceof DoublePlantOnTop ) {
                    // TODO: Automate Double Plant Model
                    copyItemModel( (IBlockCopy) block );
                } else {
                    simpleBlockCopy( (IOnTopCopy) block );
                }
            }
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
