package net.dusty_dusty.dataGen;

import com.google.gson.JsonObject;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.common.block.DoublePlantOnTop;
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
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;
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
        autoFromRegistry( VanillaRegistry.COMPAT_BLOCKS );
        autoFromRegistry( PVJRegistry.COMPAT_BLOCKS );
        // simpleBlockCopy( (IOnTopCopy) PVJRegistry.SHORTER_GRASS_ON_TOP.get() );
    }



    private void slabBlockCopy( ISlabCopy blockCopy ) {

    }

    @SuppressWarnings("deprecation")
    private void simpleBlockCopy( IOnTopCopy blockCopy ) {
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
            util.copyMultipart( this.getMultipartBuilder( (Block) blockCopy ), jsonObject, (Block) blockCopy );
        }
    }

    private void autoFromRegistry( DeferredRegister<Block> blocks ) {
        for ( RegistryObject<Block> entry : blocks.getEntries() ) {
            if ( entry.get() instanceof IOnTopCopy ) {
                LOGGER.info( "Creating blockstate json for {}", entry.get() );
                if ( entry.get() instanceof DoublePlantOnTop ) {

                } else {
                    simpleBlockCopy( (IOnTopCopy) entry.get() );
                }
            }
        }
    }
}
