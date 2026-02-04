package net.dusty_dusty.cts_compats.mods.biomesOPlenty;

import net.dusty_dusty.cts_compats.common.registry.AbstractVersionRouter;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("Convert2MethodRef")
public final class BOPVersionRouter extends AbstractVersionRouter {
    private static final Map<String, Supplier<IRegistry>> VERSION_MAP = new HashMap<>();
    static {
        VERSION_MAP.put( "*", () -> BOPRegistry.getInstance() );
    }

    private static final BOPVersionRouter INSTANCE = new BOPVersionRouter( "biomesoplenty", VERSION_MAP );
    public static BOPVersionRouter getInstance() {
        return INSTANCE;
    }

    private BOPVersionRouter( String modid, Map<String, Supplier<IRegistry>> versionFilter ) {
        super( modid, versionFilter );
    }
}
