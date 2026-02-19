package net.dusty_dusty.cts_compats.common;

import net.countered.terrainslabs.TerrainSlabs;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public final class TagReference {
    private static final ResourceKey<Registry<Block>> blocksKey = ForgeRegistries.BLOCKS.getRegistryKey();

    public static final TagKey<Block> TALL_FLOWERS = TagKey.create( blocksKey,
            ResourceLocation.fromNamespaceAndPath( "minecraft", "tall_flowers") );
    public static final TagKey<Block> DOUBLE_TALL_PLANTS = TagKey.create( blocksKey,
            ResourceLocation.fromNamespaceAndPath( TerrainSlabs.MOD_ID, "double_tall_plants" ) );
}
