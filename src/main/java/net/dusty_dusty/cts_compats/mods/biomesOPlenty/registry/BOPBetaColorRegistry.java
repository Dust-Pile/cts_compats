package net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

public class BOPBetaColorRegistry extends BOPBaseColorRegistry {
    @Override
    public void onColorHandlerEventBlock(RegisterColorHandlersEvent.Block event) {
        super.onColorHandlerEventBlock( event );
        //event.register(getFoliageColor(),  );
        //event.register(getGrassColor(), );
    }

    @Override
    public void onColorHandlerEventItem(RegisterColorHandlersEvent.Item event) {
        super.onColorHandlerEventItem( event );
        BlockColors blockColors = event.getBlockColors();

//        event.register((itemstack, tintIndex) -> {
//                    BlockState state = Blocks.GRASS.defaultBlockState();
//                    return blockColors.getColor(state, null, null, tintIndex);
//                }, );
    }
}
