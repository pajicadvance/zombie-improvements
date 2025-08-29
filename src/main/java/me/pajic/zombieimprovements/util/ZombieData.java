package me.pajic.zombieimprovements.util;

import me.pajic.zombieimprovements.Main;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;
//? if < 1.21.8
import net.minecraft.world.entity.MobSpawnType;
//? if >= 1.21.8
/*import net.minecraft.world.entity.EntitySpawnReason;*/

public class ZombieData {
    public static EntityDataAccessor<Boolean> LEADER = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.BOOLEAN);
    public static ResourceLocation ZOMBIE_POWER_LAYER = Main.withModNamespace("textures/entity/zombie/zombie_leader.png");

    public static boolean isFromSpawner(/*? if < 1.21.8 {*/MobSpawnType/*?}*//*? if >= 1.21.8 {*//*EntitySpawnReason*//*?}*/ spawnType) {
        return /*? if < 1.21.8 {*/MobSpawnType/*?}*//*? if >= 1.21.8 {*//*EntitySpawnReason*//*?}*/.isSpawner(spawnType);
    }

    public static void init() {}
}
