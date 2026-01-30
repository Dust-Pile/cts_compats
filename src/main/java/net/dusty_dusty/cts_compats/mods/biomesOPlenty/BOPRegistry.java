package net.dusty_dusty.cts_compats.mods.biomesOPlenty;

import dev.orderedchaos.projectvibrantjourneys.core.config.compatibility.BOP;
import net.dusty_dusty.cts_compats.common.registry.AbstractRegistry;

public class BOPRegistry extends AbstractRegistry {
    private static final BOPRegistry INSTANCE = new BOPRegistry();
    public static BOPRegistry getInstance() {
        return INSTANCE;
    }
}
