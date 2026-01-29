package net.dusty_dusty.cts_compats.dataGen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.common.IBlockCopy;
import net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.PVJRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.LOGGER;

public class ModBlockStateProvider extends BlockStateProvider {
    final ExistingFileHelper existingFileHelper;

    public ModBlockStateProvider( PackOutput output, ExistingFileHelper existingFileHelper ) {
        super( output, CTSCompats.MODID, existingFileHelper );
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockCopy( (IBlockCopy) PVJRegistry.SHORTER_GRASS_ON_TOP.get() );
    }



    @SuppressWarnings("deprecation")
    private void simpleBlockCopy( IBlockCopy blockCopy ) {
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
            copyVariants( this.getVariantBuilder( (Block) blockCopy ), jsonObject );
        } else {
            copyMultipart( this.getMultipartBuilder( (Block) blockCopy ), jsonObject );
        }
    }

    private void copyVariants( VariantBlockStateBuilder builder, JsonObject jsonObject ) {
        JsonObject variants = jsonObject.getAsJsonObject( "variants" );
        Block block = builder.getOwner();

        for ( String key : variants.keySet() ) {
            VariantBlockStateBuilder.PartialBlockstate partial = builder.partialState();
            for ( String stateDef : key.split( "," ) ) {
                //noinspection rawtypes
                StateValPair pair = new StateValPair(block, stateDef.split( "=" ) );
                //noinspection unchecked
                partial.with( pair.state, pair.value );
            }

            try {
                Map<String, JsonElement> aModel = variants.getAsJsonObject( key ).asMap();
                ConfiguredModel configModel = new ConfiguredModel(
                        new BlockModelBuilder( ResourceLocation.parse( aModel.get( "model" ).getAsString() ), existingFileHelper ),
                        getOrDefault( () -> aModel.get( "x" ).getAsInt(), 0 ),
                        getOrDefault( () -> aModel.get( "y" ).getAsInt(), 0 ),
                        getOrDefault( () -> aModel.get( "uvlock" ).getAsBoolean(), false )
                );
                builder.addModels( partial, configModel );
            } catch ( Exception e ) {
                JsonArray stateArray = variants.getAsJsonArray( key );
                stateArray.forEach( element -> {
                    JsonObject aModel = element.getAsJsonObject();
                    ConfiguredModel configModel = new ConfiguredModel(
                            new BlockModelBuilder( ResourceLocation.parse( aModel.get( "model" ).getAsString() ), existingFileHelper ),
                            getOrDefault( () -> aModel.get( "x" ).getAsInt(), 0 ),
                            getOrDefault( () -> aModel.get( "y" ).getAsInt(), 0 ),
                            getOrDefault( () -> aModel.get( "uvlock" ).getAsBoolean(), false ),
                            getOrDefault( () -> aModel.get( "weight" ).getAsInt(), 1 )
                    );
                    builder.addModels( partial, configModel );
                } );
            }
        }
    }

    // TODO
    private void copyMultipart( MultiPartBlockStateBuilder builder, JsonObject jsonObject ) {
        LOGGER.error( "Attempting to use ModBlockStateProvider.copyMultipart without implementation" );
    }



    private <T> T getOrDefault( Supplier<T> attemptedReturn, T defaultReturn ) {
        try {
            return attemptedReturn.get();
        } catch (Exception error) {
            return defaultReturn;
        }
    }

    // Util for unpacking state and value from files
    private static class StateValPair<T extends Comparable<T>> {
        final Property<T> state;
        final T value;

        StateValPair ( Block b, String[] stateValue ) {
            Property<T> tempState = null;
            T tempVal = null;
            try {
                //noinspection unchecked
                tempState = (Property<T>) b.getStateDefinition().getProperty( stateValue[0] );
                //noinspection DataFlowIssue,OptionalGetWithoutIsPresent
                tempVal = tempState.getValue( stateValue[1] ).get();
            } catch ( Exception e ) {
                LOGGER.error( "CTS_COMPATS: {}", e.toString() );
            }

            state = tempState;
            value = tempVal;
        }
    }

}
