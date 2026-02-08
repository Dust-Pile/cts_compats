package net.dusty_dusty.cts_compats.common.registry;

import net.dusty_dusty.cts_compats.common.block.interfaces.IAssignable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.MODID;

public abstract class AbstractRegistry implements IRegistry {
    public final DeferredRegister<Block> COMPAT_BLOCKS = DeferredRegister.create( ForgeRegistries.BLOCKS, MODID );
    public final DeferredRegister<Item> COMPAT_ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MODID );

    @SuppressWarnings("unchecked")
    protected <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block ) {
        RegistryObject<T> output = this.COMPAT_BLOCKS.register( name, block );
        this.registerBlockItem( name, ( RegistryObject<Block> ) output);
        return output;
    }

    protected RegistryObject<Item> registerBlockItem(String name, RegistryObject<Block> block ) {
        return this.COMPAT_ITEMS.register( name, () -> new BlockItem( block.get(), new Item.Properties() ) );
    }

    public void register( IEventBus modEventBus ) {
        this.COMPAT_BLOCKS.register( modEventBus );
        this.COMPAT_ITEMS.register( modEventBus );
    }

    public void assign() {
        this.COMPAT_BLOCKS.getEntries().forEach( entry -> {
            Block block = entry.get();
            if ( block instanceof IAssignable) {
                ( (IAssignable) block ).assign();
            }
        });
    }

}
