package net.dusty_dusty.dataGen.loot;

import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.dusty_dusty.cts_compats.RegistryManager;
import net.dusty_dusty.cts_compats.common.block.interfaces.BlockCopyWrapper;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super( Set.of(), FeatureFlags.REGISTRY.allFlags() );
    }

    @Override
    protected void generate() {
        RegistryManager.forEachRegistryAndID( ( modId, registry ) -> {
            for (RegistryObject<Block> blockRegister : registry.getRegistryBlocks() ) {
                Block block = blockRegister.get();
                Block originBlock = new BlockCopyWrapper( (IBlockCopy) block ).getOriginBlock();

                LootTable.Builder builder = LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                        LootTableReference.lootTableReference( ResourceLocation.fromNamespaceAndPath(
                                modId, "blocks/" + originBlock.getDescriptionId().split( "\\." )[2]
                        ) ) )
                );
                this.add( block, builder );
            }
        } );
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return RegistryManager.getAllBlocks();
    }
}
