package net.dusty_dusty.cts_compats.mods.vanilla;

import dev.orderedchaos.projectvibrantjourneys.core.registry.PVJBlocks;
import dev.orderedchaos.projectvibrantjourneys.core.registry.PVJItems;
import net.dusty_dusty.cts_compats.core.AssignUtil;
import net.dusty_dusty.cts_compats.core.block.PetalBlockOnTop;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.MODID;

public class VanillaRegistry {

        protected static final DeferredRegister<Block> COMPAT_BLOCKS = DeferredRegister.create( ForgeRegistries.BLOCKS, MODID );
        protected static final DeferredRegister<Item> COMPAT_ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MODID );

        public static final RegistryObject<Block> PINK_PETALS_ON_TOP = registerBlock( "pink_petals_on_top",
                () -> new PetalBlockOnTop( BlockBehaviour.Properties.copy( Blocks.PINK_PETALS ), Items.PINK_PETALS ) );

        private static <T extends Block> RegistryObject<T> registerBlock( String name, Supplier<T> block ) {
            RegistryObject<T> output = COMPAT_BLOCKS.register( name, block );
            registerBlockItem( name, ( RegistryObject<Block> ) output);
            return output;
        }

        private static <T extends Block> RegistryObject<Item> registerBlockItem( String name, RegistryObject<Block> block ) {
            return COMPAT_ITEMS.register( name, () -> new BlockItem( block.get(), new Item.Properties() ) );
        }

        public static void register( IEventBus modEventBus ) {
            COMPAT_BLOCKS.register( modEventBus );
            COMPAT_ITEMS.register( modEventBus );
        }

        public static void assign() {
            AssignUtil.putOnTopVegetation( Blocks.PINK_PETALS, PINK_PETALS_ON_TOP.get() );
            AssignUtil.putVegetaitonOnTopItem( Items.PINK_PETALS, PINK_PETALS_ON_TOP.get() );
        }

}
