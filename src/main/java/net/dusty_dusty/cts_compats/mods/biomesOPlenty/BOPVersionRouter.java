package net.dusty_dusty.cts_compats.mods.biomesOPlenty;

import net.dusty_dusty.cts_compats.common.registry.AbstractVersionRouter;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPBaseRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPRegistry18_0_0_592;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("Convert2MethodRef")
public final class BOPVersionRouter extends AbstractVersionRouter {
    private static final Map<VersionFilter, Supplier<IRegistry>> VERSION_MAP = new HashMap<>();
    static {
        VERSION_MAP.put( VersionFilter.acceptsExactly( "18.0.0.592" ), () -> BOPRegistry18_0_0_592.getInstance() );
        VERSION_MAP.put( VersionFilter.acceptLaterThanExclusive( "18.0.0.592" ), () -> BOPBaseRegistry.getInstance() );
    }

    private static final BOPVersionRouter INSTANCE = new BOPVersionRouter( BOP_MODID, VERSION_MAP );
    public static BOPVersionRouter getInstance() {
        return INSTANCE;
    }

    private BOPVersionRouter( String modid, Map<VersionFilter, Supplier<IRegistry>> versionFilter ) {
        super( modid, versionFilter );
    }
}
