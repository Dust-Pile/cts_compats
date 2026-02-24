package net.dusty_dusty.cts_compats.mods.ImmersiveWeathering;

import net.dusty_dusty.cts_compats.common.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.common.registry.IColorRegistry;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;

import java.util.Optional;

public class WeatheringRegistry extends AbstractRegistry {
    private static final WeatheringRegistry INSTANCE = new WeatheringRegistry( IRegistry.IW_MODID );
    public static WeatheringRegistry getInstance() {
        return INSTANCE;
    }

    protected WeatheringRegistry( String modId ) {
        super(modId);
    }





    @Override
    public Optional<IColorRegistry> getColorRegistry() {
        return Optional.of( new WeatheringColorRegistry() );
    }
}
