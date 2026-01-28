package net.dusty_dusty.cts_compats.mods.projectVibrantJourneys.block;

import net.dusty_dusty.cts_compats.common.AssignUtil;
import net.dusty_dusty.cts_compats.common.block.BushBlockOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class ThornsBlockOnTop extends BushBlockOnTop {
    public ThornsBlockOnTop( Block originalBlock ) {
        super( originalBlock, AssignUtil.FULL_BLOCK_ON_SLAB );
    }

    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.makeStuckInBlock(state, new Vec3( 0.8, 0.75, 0.8 ) );
            if (!level.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
                double d0 = Math.abs(entity.getX() - entity.xOld);
                double d1 = Math.abs(entity.getZ() - entity.zOld);
                if (d0 >= 0.003 || d1 >= 0.003) {
                    entity.hurt(level.damageSources().sweetBerryBush(), 1.0F);
                }
            }
        }

    }
}
