package net.dusty_dusty.cts_compats.mods.vanillaBackport.block;

import com.blackgear.vanillabackport.client.registries.ModSoundEvents;
import com.blackgear.vanillabackport.common.level.blocks.CreakingHeartBlock;
import com.blackgear.vanillabackport.common.level.blocks.EyeblossomBlock;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import net.dusty_dusty.cts_compats.common.block.onTopBlocks.FlowerBlockOnTop;
import net.dusty_dusty.cts_compats.mods.vanillaBackport.VanillaBackportRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class EyeblossomOnTop extends FlowerBlockOnTop {
    private final EyeblossomBlock.Type type;

    public EyeblossomOnTop( EyeblossomBlock.Type type, Block originalBlock ) {
        super( originalBlock );
        this.type = type;
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (this.type.emitSounds() && random.nextInt(700) == 0) {
            BlockState floorState = level.getBlockState(pos.below());
            if (floorState.is( ModBlocks.PALE_MOSS_BLOCK.get())) {
                level.playLocalSound( pos.getX(), pos.getY(), pos.getZ(),
                        ModSoundEvents.EYEBLOSSOM_IDLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
        }

    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if ( this.tryChangingState(state, level, pos, random ) ) {
            level.playSound( null, pos, this.type.transform().longSwitchSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        super.randomTick(state, level, pos, random);
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if ( this.tryChangingState(state, level, pos, random ) ) {
            level.playSound( null, pos, this.getShortSwitchSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        super.tick(state, level, pos, random);
    }

    private SoundEvent getShortSwitchSound() {
        return this.type == EyeblossomBlock.Type.OPEN
                ? ModSoundEvents.EYEBLOSSOM_OPEN.get() : ModSoundEvents.EYEBLOSSOM_CLOSE.get();
    }

    private static BlockState getTypeState( EyeblossomBlock.Type type ) {
        Block block = type == EyeblossomBlock.Type.OPEN ? VanillaBackportRegistry.OPEN_EYEBLOSSOM_ON_TOP.get()
                : VanillaBackportRegistry.CLOSED_EYEBLOSSOM_ON_TOP.get();
        return block.defaultBlockState();
    }

    private boolean tryChangingState(BlockState state, ServerLevel level, BlockPos origin, RandomSource random) {
        if ( !level.dimensionType().natural() ) {
            return false;
        } else if ( CreakingHeartBlock.isNaturalNight(level) == this.type.emitSounds() ) {
            return false;
        } else {
            EyeblossomBlock.Type type = this.type.transform();
            level.setBlock( origin, getTypeState( type ), 3 );
            level.gameEvent(GameEvent.BLOCK_CHANGE, origin, GameEvent.Context.of(state));
            type.spawnTransformParticle(level, origin, random);
            BlockPos.betweenClosed(origin.offset(-3, -2, -3), origin.offset(3, 2, 3)).forEach((pos) -> {
                BlockState closeState = level.getBlockState(pos);
                if (closeState == state || closeState.is( this.getOriginBlock() ) ) {
                    double distance = Math.sqrt(origin.distSqr(pos));
                    int ticks = random.nextIntBetweenInclusive((int)(distance * 5.0F), (int)(distance * 10.0F));
                    level.scheduleTick( pos, closeState.getBlock(), ticks );
                }
            });
            return true;
        }
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide() && level.getDifficulty() != Difficulty.PEACEFUL && entity instanceof Bee bee) {
            if (!bee.hasEffect(MobEffects.POISON)) {
                bee.addEffect(new MobEffectInstance(MobEffects.POISON, 25));
            }
        }

    }
}
