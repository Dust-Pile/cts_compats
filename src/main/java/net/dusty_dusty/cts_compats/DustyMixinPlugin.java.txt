package net.dusty_dusty.cts_compats;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public final class DustyMixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad( String s ) {
        CTSCompats.LOGGER.info( "CTS_COMPATS: Loading MixinConfigPlugin" );
    }

    @Override
    public boolean shouldApplyMixin( String targetClassName, String mixinClassName ) {
        // LoadingModList.get().getModFileById(modid) != null
        CTSCompats.LOGGER.info("Applied Mixin {} to {}", mixinClassName, targetClassName );
        return true;
    }

    public String getRefMapperConfig() {return null;}
    public void acceptTargets(Set<String> set, Set<String> set1) {}
    public List<String> getMixins() {return null;}
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {}
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {}
}
