package net.dusty_dusty.cts_compats.mods.vanillaBackport.block;

import com.blackgear.vanillabackport.client.registries.ModParticles;
import com.blackgear.vanillabackport.client.registries.ModSoundEvents;
import com.blackgear.vanillabackport.core.util.LevelUtils;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.BushBlockOnTop;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;

public class FireflyBushOnTop extends BushBlockOnTop {
    public FireflyBushOnTop( Block originalBlock ) {
        super( originalBlock, AssignUtil.FULL_BLOCK_ON_SLAB );
    }

    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, RandomSource random) {
        if (random.nextInt(30) == 0 && LevelUtils.isMoonVisible( level ) 
                && level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) <= pos.getY()
        ) {
            level.playLocalSound(pos, ModSoundEvents.FIREFLY_BUSH_IDLE.get(), SoundSource.AMBIENT, 1.0F, 1.0F, false);
        }

        if ((LevelUtils.isMoonVisible(level) || level.getMaxLocalRawBrightness(pos) <= 13) && random.nextDouble() <= 0.7) {
            double x = pos.getX() + random.nextDouble() * 10.0 - 5.0;
            double y = pos.getY() + random.nextDouble() * 5.0 - 0.5;
            double z = pos.getZ() + random.nextDouble() * 10.0 - 5.0;
            level.addParticle( ModParticles.FIREFLY.get(), x, y, z, 0.0, 0.0, 0.0);
        }

    }
}
