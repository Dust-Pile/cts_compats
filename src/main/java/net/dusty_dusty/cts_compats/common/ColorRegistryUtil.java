package net.dusty_dusty.cts_compats.common;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;

public class ColorRegistryUtil {
    public static BlockColor getGrassColor() {
        return ( state, world, pos, tintIndex) -> (world != null && pos != null )
                ? BiomeColors.getAverageGrassColor( world, pos )
                : GrassColor.get( 0.5D, 1.0D );
    }

    public static BlockColor getFoliageColor() {
        return (state, world, pos, tintIndex) -> (world != null && pos != null)
                ? BiomeColors.getAverageFoliageColor(world, pos)
                : FoliageColor.getDefaultColor();
    }
}
