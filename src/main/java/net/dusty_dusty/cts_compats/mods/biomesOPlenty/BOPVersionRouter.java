package net.dusty_dusty.cts_compats.mods.biomesOPlenty;

import net.dusty_dusty.cts_compats.common.registry.AbstractVersionRouter;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;
import net.dusty_dusty.cts_compats.common.registry.Version;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPBaseRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPBetaRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("Convert2MethodRef")
public final class BOPVersionRouter extends AbstractVersionRouter {
    private static final Map<Version.Filter, Supplier<IRegistry>> VERSION_MAP = new HashMap<>();
    static {
        VERSION_MAP.put( Version.Filter.acceptsExactly( "18.0.0.592" ), () -> BOPBaseRegistry.getInstance() );
        VERSION_MAP.put( Version.Filter.acceptLaterThanInclusive( "19.0.0.96" ), () -> BOPBetaRegistry.getInstance() );
    }

    private static final BOPVersionRouter INSTANCE = new BOPVersionRouter( BOP_MODID, VERSION_MAP );
    public static BOPVersionRouter getInstance() {
        return INSTANCE;
    }

    private BOPVersionRouter( String modid, Map<Version.Filter, Supplier<IRegistry>> versionFilter ) {
        super( modid, versionFilter );
    }
}
