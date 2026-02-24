package net.dusty_dusty.cts_compats.mods.vanillaBackport;

import com.blackgear.vanillabackport.core.forge.emissive.EmissiveModelWrapper;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.common.registry.IEmissiveRegistry;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;

import java.util.Map;

class VanillaBackportEmissiveRegistry implements IEmissiveRegistry {
    private static final String[] EMISSIVE_BLOCKS = new String[]{ "open_eyeblossom_on_top", "firefly_bush_on_top" };
    private static final String EMISSIVE_SUFFIX = "_emissive";

    @Override
    public void onModelBake(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModels();

        for(String blockName : EMISSIVE_BLOCKS) {
            ModelResourceLocation blockModelLocation = new ModelResourceLocation( ResourceLocation.fromNamespaceAndPath(
                    CTSCompats.MODID, blockName ), "");
            BakedModel baseModel = modelRegistry.get( blockModelLocation );
            if ( baseModel != null ) {
                ResourceLocation emissiveModelLocation = ResourceLocation.fromNamespaceAndPath(
                        "minecraft", "block/" + blockName.replace( "_on_top", "" ) + EMISSIVE_SUFFIX );
                BakedModel emissiveModel = modelRegistry.get( emissiveModelLocation );
                if (emissiveModel != null) {
                    BakedModel wrappedModel = new EmissiveModelWrapper( baseModel, emissiveModel );
                    modelRegistry.put( blockModelLocation, wrappedModel );
                }
            }
        }
    }

    @Override
    public void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event) {}
}
