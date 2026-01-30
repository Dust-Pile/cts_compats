package net.dusty_dusty.cts_compats.common.block;

import net.dusty_dusty.cts_compats.common.block.interfaces.IAssignable;
import net.dusty_dusty.cts_compats.common.block.interfaces.ISlabCopy;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.SlabType;

public class CustomSlabBlock extends SlabBlock implements IAssignable, ISlabCopy {
    public static final BooleanProperty GENERATED;
    private final Block originalBlock;

    static {
        GENERATED = BooleanProperty.create("generated");
    }

    public Block getOriginBlock() {
        return originalBlock;
    }

    public CustomSlabBlock( Block originalBlock ) {
        super( BlockBehaviour.Properties.copy( originalBlock ) );
        this.originalBlock = originalBlock;
        this.registerDefaultState(this.defaultBlockState()
                .setValue( TYPE, SlabType.BOTTOM )
                .setValue( WATERLOGGED, Boolean.FALSE )
                .setValue( GENERATED, Boolean.FALSE ));
    }

    @Override
    protected void createBlockStateDefinition( StateDefinition.Builder<Block, BlockState> pBuilder ) {
        pBuilder.add( TYPE, WATERLOGGED, GENERATED );
    }
}
