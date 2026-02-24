package net.dusty_dusty.cts_compats.mods.vanillaBackport;

import com.blackgear.vanillabackport.client.api.color.LeafColors;
import net.dusty_dusty.cts_compats.common.registry.AbstractColorRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

class VanillaBackportColorRegistry extends AbstractColorRegistry {
    @Override
    public void onColorHandlerEventBlock(RegisterColorHandlersEvent.Block event) {
        event.register( getFoliageColor(), VanillaBackportRegistry.BUSH_ON_TOP.get() );
        event.register( getGrassColor(), VanillaBackportRegistry.WILDFLOWERS_ON_TOP.get() );
        event.register(
                (state, level, pos, tint) ->
                        level != null && pos != null ? LeafColors.getAverageDryFoliageColor(pos) : -10732494,
                VanillaBackportRegistry.LEAF_LITTER_ON_TOP.get()
        );

    }

    @Override
    public void onColorHandlerEventItem(RegisterColorHandlersEvent.Item event) {
        BlockColors blockColors = event.getBlockColors();

        event.register((itemstack, tintIndex) -> {
                    BlockState state = Blocks.OAK_LEAVES.defaultBlockState();
                    return blockColors.getColor(state, null, null, tintIndex);
                }, VanillaBackportRegistry.BUSH_ON_TOP.get() );
    }
}
