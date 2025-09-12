package me.pajic.zombieimprovements.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.zombieimprovements.Main;
import me.pajic.zombieimprovements.util.ZombieExtension;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//? if < 1.21.8 {
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.nbt.CompoundTag;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Cancellable;
//?}
//? if >= 1.21.8 {
/*import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
*///?}

@Mixin(Zombie.class)
public abstract class ZombieMixin extends Mob implements ZombieExtension {
    protected ZombieMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Unique private static final EntityDataAccessor<Boolean> LEADER = SynchedEntityData.defineId(
            Zombie.class, EntityDataSerializers.BOOLEAN
    );
    @Unique private int soundTimer = 0;
    @Unique private Zombie soundSource = null;
    @Unique private /*? if < 1.21.8 {*/MobSpawnType/*?}*//*? if >= 1.21.8 {*//*EntitySpawnReason*//*?}*/ spawnType = null;

    @Inject(
            method = /*? if < 1.21.8 {*/"hurt"/*?}*//*? if >= 1.21.8 {*//*"hurtServer"*//*?}*/,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V"
            )
    )
    private void playSoundOnReinforcementsSpawn(
            //? if >= 1.21.8
            /*ServerLevel level,*/
            DamageSource source,
            float amount,
            CallbackInfoReturnable<Boolean> cir,
            @Local Zombie zombie
    ) {
        if (Main.CONFIG.reinforcementSpawnSounds.get()) {
            soundTimer = 40;
            soundSource = zombie;
        }
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/monster/Zombie;isUnderWaterConverting()Z"
            )
    )
    private void tickReinforcementSound(CallbackInfo ci) {
        if (soundTimer > 0 && soundSource != null) {
            if (soundTimer % 8 == 0) playSound(soundSource.getBlockStateOn().getSoundType().getBreakSound());
            soundTimer--;
        }
    }

    @Inject(
            method = "handleAttributes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/monster/Zombie;setCanBreakDoors(Z)V"
            )
    )
    private void markLeader(float difficulty, CallbackInfo ci) {
        heal(getMaxHealth());
        entityData.set(LEADER, true);
        Main.debugLog("Leader {} spawned at {} {} {}", getDisplayName().getString(), getX(), getY(), getZ());
    }

    @Inject(
            method = "finalizeSpawn",
            at = @At("HEAD")
    )
    private void getSpawnReason(ServerLevelAccessor level, DifficultyInstance difficulty, /*? if < 1.21.8 {*/MobSpawnType/*?}*//*? if >= 1.21.8 {*//*EntitySpawnReason*//*?}*/ spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
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
        return Main.CONFIG.noLeaderFromSpawners.get() && Main.isFromSpawner(spawnType) ? 1 : original;
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
        return !Main.isFromSpawner(spawnType) || !Main.CONFIG.noReinforcementsFromSpawners.get();
    }

    @WrapWithCondition(
            method = "handleAttributes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/monster/Zombie;randomizeReinforcementsChance()V"
            )
    )
    private boolean noReinforcementsIfFromSpawner(Zombie instance) {
        return !Main.isFromSpawner(spawnType) || !Main.CONFIG.noReinforcementsFromSpawners.get();
    }

    @ModifyExpressionValue(
            method = /*? if < 1.21.8 {*/"hurt"/*?}*//*? if >= 1.21.8 {*//*"hurtServer"*//*?}*/,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"
            )
    )
    private boolean onlyLeaderSpawnsReinforcements(boolean original) {
        return Main.CONFIG.onlyLeaderSpawnsReinforcements.get() ? entityData.get(LEADER) : original;
    }

    //? if < 1.21.8 {
    @SuppressWarnings("unchecked")
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
    //?}

    @Inject(
            method = "defineSynchedData",
            at = @At("TAIL")
    )
    private void defineLeaderData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(LEADER, false);
    }

    //? if < 1.21.8 {
    @Inject(
            method = "addAdditionalSaveData",
            at = @At("TAIL")
    )
    private void saveLeaderData(CompoundTag compound, CallbackInfo ci) {
        compound.putBoolean("Leader", entityData.get(LEADER));
    }

    @Inject(
            method = "readAdditionalSaveData",
            at = @At("TAIL")
    )
    private void readLeaderData(CompoundTag compound, CallbackInfo ci) {
        entityData.set(LEADER, compound.getBoolean("Leader"));
    }
    //?}
    //? if >= 1.21.8 {
    /*@Inject(
            method = "addAdditionalSaveData",
            at = @At("TAIL")
    )
    private void saveLeaderData(ValueOutput output, CallbackInfo ci) {
        output.putBoolean("Leader", entityData.get(LEADER));
    }

    @Inject(
            method = "readAdditionalSaveData",
            at = @At("TAIL")
    )
    private void readLeaderData(ValueInput input, CallbackInfo ci) {
        entityData.set(LEADER, input.getBooleanOr("Leader", false));
    }
    *///?}

    @Override
    public boolean zi$isLeader() {
        return entityData.get(LEADER);
    }
}
