package net.dusty_dusty.cts_compats.mixins;

import net.countered.terrainslabs.callbacks.RegisterCallbacks;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

import static com.mojang.text2speech.Narrator.LOGGER;
import static net.dusty_dusty.cts_compats.core.CompatSlabsMap.compatOnTopVegitationMap;

@Mixin( RegisterCallbacks.class )
public class MixinRegisterCallbacks {

    private static Block currentVegitation;
    private static boolean isCompatVegitation = false;

    @Redirect( method = "placeVegetationOnTop", at = @At(
            value = "INVOKE",
            target = "Ljava/util/Map;containsKey(Ljava/lang/Object;)Z"
    ), remap = false )
    private static boolean vegetationContainsKeyProxyPlus( Map instance, Object o ) {
        if ( compatOnTopVegitationMap.containsKey( ( Block ) o ) ) {
            currentVegitation = ( Block ) o;
            isCompatVegitation = true;
            return true;
        }

        isCompatVegitation = false;
        return instance.containsKey( o ) ;
    }

    @Redirect( method = "placeVegetationOnTop", at = @At(
            value = "INVOKE",
            target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
    ), remap = false )
    private static Object vegitationGetProxy( Map instance, Object o ) {
        if ( isCompatVegitation ) {
            return compatOnTopVegitationMap.get( currentVegitation );
        }
        return instance.get( o );
    }

    @Redirect( method = "placeBottomSlab", at = @At(
            value = "INVOKE",
            target = "Ljava/util/Map;containsKey(Ljava/lang/Object;)Z"
    ), remap = false )
    private static boolean vegetationContainsKeyProxy( Map instance, Object o ) {
        return false;//instance.containsKey( o ) || compatOnTopVegitationMap.containsKey( ( Block ) o );
    }

}