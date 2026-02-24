package net.dusty_dusty.dataGen.loot;

import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.dusty_dusty.cts_compats.RegistryManager;
import net.dusty_dusty.cts_compats.common.block.interfaces.BlockCopyWrapper;
import net.dusty_dusty.cts_compats.common.block.interfaces.IBlockCopyForge;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.PetalBlockOnTop;
import net.dusty_dusty.cts_compats.common.registry.IRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPBaseRegistry;
import net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.PVJRegistry;
import net.dusty_dusty.cts_compats.mods.vanilla.VanillaRegistry;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public final class ModBlockLootTables extends BlockLootSubProvider {
    private static final LootPoolEntryContainer.Builder<?> GRASS_LOOT = LootTableReference.lootTableReference(
            ResourceLocation.fromNamespaceAndPath( "minecraft", "blocks/grass" ) );
    private static final boolean IS_ENABLED = true;
    public ModBlockLootTables() {
        super( Set.of(), FeatureFlags.REGISTRY.allFlags() );
    }

    @Override
    protected void generate() {
        if ( !IS_ENABLED ) {
            return;
        }

        RegistryManager.forEachRegistryAndID( ( modId, registry ) -> {
            for ( RegistryObject<Block> blockRegister : registry.getRegistryBlocks() ) {
                Block block = blockRegister.get();
                Block originBlock = new BlockCopyWrapper( (IBlockCopy) block ).getOriginBlock();

                if ( modId.equals( IRegistry.VB_MODID ) ) {
                    modId = "minecraft";
                }

                ResourceLocation parentLocation = ResourceLocation.fromNamespaceAndPath(
                        modId, "blocks/" + originBlock.getDescriptionId().split( "\\." )[2] );

                // Workaround so it thinks these exist
                // TODO: IMPORTANT NOTE!!! MAKE SURE TO DELETE THE EXTRA TABLES!!!
                this.add( originBlock, LootTable.lootTable() );

                if ( block instanceof PetalBlockOnTop ) {
                    this.add( block, this.createPetalsDrops( block, originBlock.asItem() ) );
                    continue;
                }

                if ( originBlock instanceof TallFlowerBlock || ( modId.equals( IRegistry.PVJ_MODID )
                        && originBlock instanceof DoublePlantBlock && !block.equals( PVJRegistry.WATERGRASS_ON_TOP.get() ) )
                        || block.equals( BOPBaseRegistry.CATTAIL_ON_TOP.get() )
                        || block.equals( VanillaRegistry.PITCHER_PLANT_ON_TOP.get() )
                ) {
                    this.add( block, tallPlantBuilder( block, simplePool(), LootItem.lootTableItem( originBlock.asItem() ) )
                            .setRandomSequence( parentLocation ) );
                    continue;
                }

                if ( originBlock instanceof DoublePlantBlock
                        && !block.equals( BOPBaseRegistry.BRIMSTONE_CLUSTER_ON_TOP.get() )
                        && !block.equals( BOPBaseRegistry.EYEBULB_ON_TOP.get() )
                ) {
                    LootTable.Builder builder = shearableTallPlantBuilder( block, originBlock.asItem() )
                            .setRandomSequence( parentLocation );
                    if ( block.equals( VanillaRegistry.TALL_GRASS_ON_TOP.get() )
                            || block.equals( VanillaRegistry.LARGE_FERN_ON_TOP.get() )
                    ) {
                        builder = builder.withPool( tallPlantPoolAppender( block,
                                simplePool().when( HAS_SHEARS.invert() ), GRASS_LOOT )
                        );
                    }
                    this.add( block, builder );
                    continue;
                }

                if ( block.equals( VanillaRegistry.SWEET_BERRY_BUSH_ON_TOP.get() ) ) {
                    this.add( block, berryBushBuilder( block ).setRandomSequence( parentLocation ) );
                    continue;
                }

                this.add( block, simpleReference( parentLocation ) );
            }
        } );

        this.add( Blocks.GRASS, LootTable.lootTable() );
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();
        if ( !IS_ENABLED ) {
            return blocks;
        }
        for ( Block block : RegistryManager.getAllBlocks() ) {
            blocks.add( ( (IBlockCopyForge) block ).getOriginBlock() );
            blocks.add( block );
        }

        blocks.add( Blocks.GRASS );
        return blocks;
    }



    static LootTable.Builder shearableTallPlantBuilder( Block block, Item item ) {
        return tallPlantBuilder( block, simplePool().when( HAS_SHEARS ), LootItem.lootTableItem( item ) );
    }

    static LootTable.Builder tallPlantBuilder( Block block, LootPool.Builder pool, LootPoolEntryContainer.Builder<?> loot ) {
        return LootTable.lootTable().withPool( tallPlantPoolAppender( block, pool, loot ) );
    }
    static LootPool.Builder tallPlantPoolAppender( Block block, LootPool.Builder pool, LootPoolEntryContainer.Builder<?> loot ) {
        return pool.add( loot ).when( ExplosionCondition.survivesExplosion() )
                .when( new LootItemBlockStatePropertyCondition.Builder( block ).setProperties(
                        StatePropertiesPredicate.Builder.properties().hasProperty(
                                DoublePlantBlock.HALF, DoubleBlockHalf.LOWER ) ) );
    }

    LootTable.Builder createPetalsDrops( Block pPetalBlock, Item lootItem ) {
        return LootTable.lootTable().withPool( simplePool().add( this.applyExplosionDecay(
                pPetalBlock, LootItem.lootTableItem( lootItem ).apply( IntStream.rangeClosed(1, 4).boxed().toList(),
                        (numPetals) -> SetItemCountFunction.setCount(ConstantValue
                                .exactly( (float) numPetals ) )
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pPetalBlock)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(PinkPetalsBlock.AMOUNT, numPetals)))))));
    }

    static LootTable.Builder simpleReference( ResourceLocation parent ) {
        return LootTable.lootTable().withPool( simplePool().add( LootTableReference.lootTableReference( parent ) ) ).setRandomSequence( parent );
    }

    static LootPool.Builder simplePool() {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F));
    }

    static LootTable.Builder berryBushBuilder(Block block ) {
        return LootTable.lootTable().withPool( simplePool().when( ExplosionCondition.survivesExplosion() )
                .add( LootItem.lootTableItem( ( (IBlockCopyForge) block ).getOriginalItem() ) )
                .when( new LootItemBlockStatePropertyCondition.Builder( block ).setProperties(
                        StatePropertiesPredicate.Builder.properties().hasProperty(
                                BlockStateProperties.AGE_3, 3 ) ) )
                .apply( SetItemCountFunction.setCount( UniformGenerator.between( 2.0F, 3.0F ) ) )
                .apply(ApplyBonusCount.addUniformBonusCount( Enchantments.BLOCK_FORTUNE, 1 ) )
        ).withPool( simplePool().when( ExplosionCondition.survivesExplosion() )
                .add( LootItem.lootTableItem( ( (IBlockCopyForge) block ).getOriginalItem() ) )
                .when( new LootItemBlockStatePropertyCondition.Builder( block ).setProperties(
                        StatePropertiesPredicate.Builder.properties().hasProperty(
                                BlockStateProperties.AGE_3, 2 ) ) )
                .apply( SetItemCountFunction.setCount( UniformGenerator.between( 1.0F, 2.0F ) ) )
                .apply(ApplyBonusCount.addUniformBonusCount( Enchantments.BLOCK_FORTUNE, 1 ) )
        );
    }
}
