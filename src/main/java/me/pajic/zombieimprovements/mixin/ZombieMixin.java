package me.pajic.zombieimprovements.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.zombieimprovements.ZombieImprovements;
import me.pajic.zombieimprovements.util.AttachmentUtil;
import me.pajic.zombieimprovements.util.ModUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.entity.EntitySpawnReason;
//? if 1.21.1 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Cancellable;
*///?}

@Mixin(Zombie.class)
public abstract class ZombieMixin extends Mob {
    protected ZombieMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Unique private int soundTimer = 0;
    @Unique private Zombie soundSource = null;
    @Unique private EntitySpawnReason spawnType = null;

    @Inject(
            method = /*? if 1.21.1 {*//*"hurt"*//*?} else {*/"hurtServer"/*?}*/,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V"
            )
    )
    private void playSoundOnReinforcementsSpawn(
            //? if > 1.21.1
            ServerLevel level,
            DamageSource source,
            float amount,
            CallbackInfoReturnable<Boolean> cir,
            @Local Zombie zombie
    ) {
        if (ZombieImprovements.CONFIG.reinforcementSpawnSounds.get()) {
            soundTimer = 40;
            soundSource = zombie;
        }
    }

	//? if neoforge
    //@SuppressWarnings("deprecation")
	@Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/monster/zombie/Zombie;isUnderWaterConverting()Z"
            )
    )
    private void tickReinforcementSound(CallbackInfo ci) {
        if (soundTimer > 0 && soundSource != null) {
            if (soundTimer % 8 == 0) playSound(
					soundSource.getBlockStateOn().getSoundType().getBreakSound(),
					ZombieImprovements.CONFIG.spawnSoundVolume.get(), 1
			);
            soundTimer--;
        }
    }

    @Inject(
            method = "handleAttributes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/monster/zombie/Zombie;setCanBreakDoors(Z)V"
            )
    )
    private void markLeader(float difficulty, CallbackInfo ci) {
        heal(getMaxHealth());
		AttachmentUtil.setLeader((Zombie) (Object) this);
		ZombieImprovements.debugLog("Leader {} spawned at {} {} {}", getDisplayName().getString(), getX(), getY(), getZ());
    }

    @Inject(
            method = "finalizeSpawn",
            at = @At("HEAD")
    )
    private void getSpawnReason(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        this.spawnType = spawnType;
    }

    @ModifyExpressionValue(
            method = "handleAttributes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextFloat()F"
            )
    )
    private float noLeaderIfFromSpawner(float original) {
        return ZombieImprovements.CONFIG.noLeaderFromSpawners.get() && ModUtil.isFromSpawner(spawnType) ? 1 : original;
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
        return !ModUtil.isFromSpawner(spawnType) || !ZombieImprovements.CONFIG.noReinforcementsFromSpawners.get();
    }

    @WrapWithCondition(
            method = "handleAttributes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/monster/zombie/Zombie;randomizeReinforcementsChance()V"
            )
    )
    private boolean noReinforcementsIfFromSpawner(Zombie instance) {
        return !ModUtil.isFromSpawner(spawnType) || !ZombieImprovements.CONFIG.noReinforcementsFromSpawners.get();
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
            method = /*? if 1.21.1 {*//*"hurt"*//*?} else {*/"hurtServer"/*?}*/,
            at = @At(
                    value = "INVOKE",
                    //? if 1.21.1
                    //target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"
                    //? if > 1.21.1
                    target = "Lnet/minecraft/server/level/ServerLevel;isSpawningMonsters()Z"
            )
    )
    private boolean onlyLeaderSpawnsReinforcements(boolean original) {
        return ZombieImprovements.CONFIG.onlyLeaderSpawnsReinforcements.get() ?
				AttachmentUtil.isLeader((Zombie) (Object) this) : original;
    }

    //? if 1.21.1 {
    /*@SuppressWarnings("unchecked")
    @WrapOperation(
            method = "hurt",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/entity/monster/Zombie;"
            )
    )
    private Zombie fixIncorrectReinforcementSpawn(Level level, Operation<Zombie> original, @Cancellable CallbackInfoReturnable<Boolean> cir) {
        EntityType<? extends Zombie> entityType = (EntityType<? extends Zombie>) getType();
        Zombie zombie = entityType.create(level);
        if (zombie == null) {
            cir.setReturnValue(true);
            return null;
        }
        else return zombie;
    }
    *///?}
}
