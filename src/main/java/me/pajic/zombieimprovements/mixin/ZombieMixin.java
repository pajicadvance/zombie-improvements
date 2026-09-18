package me.pajic.zombieimprovements.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.zombieimprovements.ZombieImprovements;
import me.pajic.zombieimprovements.util.AttachmentUtil;
import me.pajic.zombieimprovements.util.ModSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(Zombie.class)
public abstract class ZombieMixin extends Mob {

    protected ZombieMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Unique private int soundTimer = 0;
    @Unique private Zombie soundSource = null;
    @Unique private EntitySpawnReason spawnType = null;

    @Inject(
            //~ if <26.1 'hurtServer' -> 'hurt'
            method = "hurtServer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V"
            )
    )
    private void playSoundOnReinforcementsSpawn(
            /*? >=26.1 {*/ServerLevel level,/*?}*/
            DamageSource source,
            float damage,
            CallbackInfoReturnable<Boolean> cir,
            @Local Zombie reinforcement
    ) {
        if (ZombieImprovements.CONFIG.reinforcementSpawnSounds.get()) {
            soundTimer = 40;
            soundSource = reinforcement;
        }
    }

	//? neoforge
    //@SuppressWarnings("deprecation")
	@Inject(
            method = "tick",
            at = @At(value = "TAIL")
    )
    private void tickReinforcementSound(CallbackInfo ci) {
        if (level() instanceof ServerLevel && isAlive() && !isNoAi() && soundTimer > 0 && soundSource != null) {
            if (soundTimer % 8 == 0) playSound(
					soundSource.getBlockStateOn().getSoundType().getBreakSound(),
					ZombieImprovements.CONFIG.spawnSoundVolume.get(), 1
			);
            soundTimer--;
        }
    }

    @SuppressWarnings("DataFlowIssue")
	@Inject(
            method = "handleAttributes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/monster/zombie/Zombie;setCanBreakDoors(Z)V"
            )
    )
    private void onLeaderAttributeAssignment(float difficultyModifier,/*? >=26.1 {*/ EntitySpawnReason spawnReason,/*?}*/ CallbackInfo ci) {
		Zombie zombie = (Zombie) (Object) this;
        heal(getMaxHealth());
		AttachmentUtil.setLeader(zombie);
		zombie.getAttribute(Attributes.SCALE).addOrReplacePermanentModifier(new AttributeModifier(
				ZombieImprovements.id("leader_scale"),
				ZombieImprovements.CONFIG.leaderSizeIncrease.get(),
				AttributeModifier.Operation.ADD_VALUE
		));
		if (ZombieImprovements.CONFIG.leaderIncreasedDamage.get()) {
			double r = random.nextDouble();
			zombie.getAttribute(Attributes.ATTACK_DAMAGE).addOrReplacePermanentModifier(new AttributeModifier(
					ZombieImprovements.id("leader_attack_damage"),
					ZombieImprovements.CONFIG.damageIncreaseFormula.evalSafe(Map.of('r', r), r * 1.5 + 3),
					AttributeModifier.Operation.ADD_VALUE
			));
		}
		ZombieImprovements.debugLog("Leader {} spawned at {} {} {}", getDisplayName().getString(), getX(), getY(), getZ());
    }

    @Inject(
            method = "finalizeSpawn",
            at = @At("HEAD")
    )
    private void getSpawnReason(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData groupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        this.spawnType = spawnReason;
    }

    @ModifyExpressionValue(
            method = "handleAttributes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextFloat()F"
            )
    )
    private float noLeaderIfFromSpawner(float original) {
        return ZombieImprovements.CONFIG.noLeaderFromSpawners.get() && EntitySpawnReason.isSpawner(spawnType) ? 1 : original;
    }

    @WrapWithCondition(
            method = "handleAttributes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;addOrReplacePermanentModifier(Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V",
                    ordinal = 2
            )
    )
    private boolean noReinforcementsIfFromSpawner(AttributeInstance instance, AttributeModifier modifier) {
        return !EntitySpawnReason.isSpawner(spawnType) || !ZombieImprovements.CONFIG.noReinforcementsFromSpawners.get();
    }

    @WrapWithCondition(
            method = "handleAttributes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/monster/zombie/Zombie;randomizeReinforcementsChance()V"
            )
    )
    private boolean noReinforcementsIfFromSpawner(Zombie instance) {
        return !EntitySpawnReason.isSpawner(spawnType) || !ZombieImprovements.CONFIG.noReinforcementsFromSpawners.get();
    }

	@ModifyExpressionValue(
			method = "handleAttributes",
			at = @At(
					value = "CONSTANT",
					args = "floatValue=0.05"
			)
	)
	private float modifyMaxSpawnChance(float original) {
		return ZombieImprovements.CONFIG.leaderMaxSpawnChance.get();
	}

    @ModifyExpressionValue(
            //~ if <26.1 'hurtServer' -> 'hurt'
            method = "hurtServer",
            at = @At(
                    value = "INVOKE",
                    //~ if <26.1 'server/level/ServerLevel;isSpawningMonsters()Z' -> 'world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z'
                    target = "Lnet/minecraft/server/level/ServerLevel;isSpawningMonsters()Z"
            )
    )
    private boolean onlyLeaderSpawnsReinforcements(boolean original) {
        return ZombieImprovements.CONFIG.onlyLeaderSpawnsReinforcements.get() ?
				AttachmentUtil.isLeader((Zombie) (Object) this) : original;
    }

	@ModifyReturnValue(
			method = "getAmbientSound",
			at = @At("RETURN")
	)
	private SoundEvent modifyAmbientSound(SoundEvent original) {
		return ZombieImprovements.CONFIG.leaderUniqueSounds.get() && AttachmentUtil.isLeader(this) ?
				ModSoundEvents.AMBIENT : original;
	}

	@ModifyReturnValue(
			method = "getHurtSound",
			at = @At("RETURN")
	)
	private SoundEvent modifyHurtSound(SoundEvent original) {
		return ZombieImprovements.CONFIG.leaderUniqueSounds.get() && AttachmentUtil.isLeader(this) ?
				ModSoundEvents.HURT : original;
	}

	@ModifyReturnValue(
			method = "getDeathSound",
			at = @At("RETURN")
	)
	private SoundEvent modifyDeathSound(SoundEvent original) {
		return ZombieImprovements.CONFIG.leaderUniqueSounds.get() && AttachmentUtil.isLeader(this) ?
				ModSoundEvents.DEATH : original;
	}
}
