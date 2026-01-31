package net.dusty_dusty.dataGen.util;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder.PartBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.LOGGER;

public record BlockStateCopyUtil( ExistingFileHelper existingFileHelper ) {

    @SuppressWarnings({"unchecked","rawtypes"})
    public void copyVariants( VariantBlockStateBuilder builder, JsonObject jsonObject ) {
        JsonObject variants = jsonObject.getAsJsonObject("variants");
        Block block = builder.getOwner();

        for ( String key : variants.keySet() ) {
            VariantBlockStateBuilder.PartialBlockstate partial = builder.partialState();
            if ( !key.isEmpty() ) {
                for ( String stateDef : key.split(",") ) {
                    StateValPair pair = new StateValPair( block, stateDef.split("=") );
                    partial = partial.with( pair.state, pair.getFirstValue() );
                }
            }

            builder.addModels( partial, buildModelOrModels( variants.get( key ), partial.modelForState()
            ).build() );
        }
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    public void copyMultipart(MultiPartBlockStateBuilder builder, JsonObject jsonObject, Block owner) {
        JsonArray parts = jsonObject.getAsJsonArray("multipart");
        for ( JsonElement part : parts ) {
            PartBuilder partBuilder = buildModelOrModels( part.getAsJsonObject().get( "apply" ), builder.part() ).addModel();

            JsonObject when = part.getAsJsonObject().getAsJsonObject( "when" );
            String orAnd = "AND";
            if ( when.has( "OR" ) || when.has( "AND" ) ) {
                // TODO: Idk if this works...
                if ( when.has( "OR" ) ) {
                    orAnd = "OR";
                    partBuilder.useOr();
                }
                JsonArray conditions = when.getAsJsonArray( orAnd );
                for ( JsonElement condition : conditions ) {
                    PartBuilder.ConditionGroup conditionGroup = partBuilder.nestedGroup();
                    JsonObject states = condition.getAsJsonObject();
                    for ( String state : states.keySet() ) {
                        String values = when.get( state ).getAsString();
                        StateValPair pair = new StateValPair( owner, state, values );
                        addConditionsFromList( partBuilder, conditionGroup, owner, pair.state, pair.values );
                    }
                }
            } else {
                for ( String state : when.keySet() ) {
                    String values = when.get( state ).getAsString();
                    StateValPair pair = new StateValPair( owner, state, values );
                    addConditionsFromList( partBuilder, owner, pair.state, pair.values );
                }
            }
        }
    }

    private <T> ConfiguredModel.Builder<T> buildModelOrModels( JsonElement modelOrModels, ConfiguredModel.Builder<T> builder ) {
        try {
            JsonObject aModel = modelOrModels.getAsJsonObject();
            builder.modelFile( new BlockModelBuilder( ResourceLocation.parse( aModel.get("model").getAsString() ), existingFileHelper) )
                    .rotationX( getOrDefault(() -> aModel.get("x").getAsInt(), 0) )
                    .rotationY( getOrDefault(() -> aModel.get("y").getAsInt(), 0) )
                    .uvLock( getOrDefault(() -> aModel.get("uvlock").getAsBoolean(), false) );
        } catch ( IllegalStateException e ) {
            JsonArray stateArray = modelOrModels.getAsJsonArray();
            for ( int i = 0; i < stateArray.size(); i++ ) {
                JsonObject aModel = stateArray.get( i ).getAsJsonObject();
                LOGGER.info(aModel.get("model").getAsString());
                builder.modelFile(new BlockModelBuilder(ResourceLocation.parse(aModel.get("model").getAsString()), existingFileHelper))
                        .rotationX(getOrDefault(() -> aModel.get("x").getAsInt(), 0))
                        .rotationY(getOrDefault(() -> aModel.get("y").getAsInt(), 0))
                        .uvLock(getOrDefault(() -> aModel.get("uvlock").getAsBoolean(), false))
                        .weight(getOrDefault(() -> aModel.get("weight").getAsInt(), 1));
                if ( i < stateArray.size() - 1 ) {
                    builder = builder.nextModel();
                }
            }
        }

        return builder;
    }

    private static <T> T getOrDefault(Supplier<T> attemptedReturn, T defaultReturn) {
        try {
            return attemptedReturn.get();
        } catch (Exception error) {
            return defaultReturn;
        }
    }

    private <T extends Comparable<T>> void addConditionsFromList(PartBuilder builder, Block owner, Property<T> prop, ArrayList<T> values) {
        Preconditions.checkNotNull(prop, "Property must not be null");
        Preconditions.checkNotNull(values, "Value list must not be null");
        Preconditions.checkArgument(!values.isEmpty(), "Value list must not be empty");
        Preconditions.checkArgument(!builder.conditions.containsKey(prop), "Cannot set condition for property \"%s\" more than once", prop.getName());
        Preconditions.checkArgument(builder.canApplyTo(owner), "IProperty %s is not valid for the block %s", prop, owner);
        Preconditions.checkState(builder.nestedConditionGroups.isEmpty(), "Can't have normal conditions if there are already nested condition groups");
        builder.conditions.putAll(prop, values);
    }
    private <T extends Comparable<T>> void addConditionsFromList(PartBuilder builder, PartBuilder.ConditionGroup group, Block owner, Property<T> prop, ArrayList<T> values) {
        Preconditions.checkNotNull(prop, "Property must not be null");
        Preconditions.checkNotNull(values, "Value list must not be null");
        Preconditions.checkArgument(!values.isEmpty(), "Value list must not be empty");
        Preconditions.checkArgument(!group.conditions.containsKey(prop), "Cannot set condition for property \"%s\" more than once", prop.getName());
        Preconditions.checkArgument(builder.canApplyTo(owner), "IProperty %s is not valid for the block %s", prop, owner);
        Preconditions.checkState(group.nestedConditionGroups.isEmpty(), "Can't have normal conditions if there are already nested condition groups");
        group.conditions.putAll(prop, values);
    }

    // Util for unpacking state and value from files and making the types compile
    private static class StateValPair<T extends Comparable<T>> {
        final Property<T> state;
        final ArrayList<T> values;

        @SuppressWarnings({"unchecked", "DataFlowIssue", "OptionalGetWithoutIsPresent"})
        StateValPair(Block b, String[] stateValue) {
            Property<T> tempState = null;
            ArrayList<T> tempVals = new ArrayList<>();
            try {
                tempState = (Property<T>) b.getStateDefinition().getProperty(stateValue[0]);
                for ( String valString : stateValue[1].split( "\\|" ) ) {
                    tempVals.add( tempState.getValue( valString ).get() );
                }
            } catch (Exception e) {
                LOGGER.error("CTS_COMPATS: {}", e.toString());
            }

            state = tempState;
            values = tempVals;
        }

        StateValPair( Block b, String state, String values ) {
            this( b, new String[] {state, values} );
        }

        T getFirstValue() {
            return values.get(0);
        }
    }
}
