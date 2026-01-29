package net.dusty_dusty.cts_compats.dataGen;

import com.google.gson.JsonObject;
import com.sun.jdi.InvalidTypeException;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.common.interfaces.IOnTopCopy;
import net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.PVJRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.io.IOException;

import static net.dusty_dusty.cts_compats.CTSCompats.LOGGER;

public class ModBlockStateProvider extends BlockStateProvider {
    final ExistingFileHelper existingFileHelper;
    final BlockStateUtil util;

    protected ModBlockStateProvider( PackOutput output, ExistingFileHelper existingFileHelper ) {
        super( output, CTSCompats.MODID, existingFileHelper );
        this.existingFileHelper = existingFileHelper;
        util = new BlockStateUtil( existingFileHelper );
    }

    @Override
    protected void registerStatesAndModels() {
        try {
            simpleBlockCopy( (IOnTopCopy) PVJRegistry.SHORTER_GRASS_ON_TOP.get() );
        } catch (InvalidTypeException e) {
            throw new RuntimeException(e);
        }
    }



    @SuppressWarnings("deprecation")
    private void simpleBlockCopy( IOnTopCopy blockCopy ) throws InvalidTypeException {
        ResourceLocation loc = BuiltInRegistries.BLOCK.getKey( blockCopy.getOriginBlock() );
        Resource blockStateJson;
        JsonObject jsonObject;

        try {
            blockStateJson = existingFileHelper.getResource( loc, PackType.CLIENT_RESOURCES, ".json", "blockstates" );
            jsonObject = GsonHelper.parse( blockStateJson.openAsReader() );
        } catch ( IOException e ) {
            LOGGER.warn( "CTS_COMPATS: {}", e.toString() );
            return;
        }

        if ( jsonObject.has( "variants" ) ) {
            util.copyVariants( this.getVariantBuilder( (Block) blockCopy ), jsonObject );
        } else {
            util.copyMultipart( this.getMultipartBuilder( (Block) blockCopy ), jsonObject );
        }
    }
}
