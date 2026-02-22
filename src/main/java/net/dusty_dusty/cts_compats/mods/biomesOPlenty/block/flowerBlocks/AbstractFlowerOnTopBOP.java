package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.flowerBlocks;

import net.dusty_dusty.cts_compats.common.block.onTopBlocks.FlowerBlockOnTop;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractFlowerOnTopBOP extends FlowerBlockOnTop {
    protected static final VoxelShape SHORT = Block.box(2.0F, 0.0F, 2.0F, 14.0F, 6.0F, 14.0F);
    protected static final VoxelShape NORMAL = Block.box(5.0F, 0.0F, 5.0F, 11.0F, 10.0F, 11.0F);
    protected static final VoxelShape MEDIUM = Block.box(3.0F, 0.0F, 3.0F, 13.0F, 12.0F, 13.0F);
    protected static final VoxelShape LARGE = Block.box(1.0F, 0.0F, 1.0F, 15.0F, 14.0F, 15.0F);
    private final MobEffect stewEffect;
    private final int stewEffectDuration;

    public AbstractFlowerOnTopBOP( Block originalBlock, MobEffect stewEffect, int stewEffectDuration ) {
        super( originalBlock );
        this.stewEffect = stewEffect;
        this.stewEffectDuration = stewEffectDuration;
    }

    @Override
    public @Nullable PlacementRule getPlacementRule() {
        return PlacementRule.CUSTOM;
    }

    @Override
    public @NotNull MobEffect getSuspiciousEffect() {
        return this.stewEffect;
    }

    public int getEffectDuration() {
        return this.stewEffectDuration;
    }
}
