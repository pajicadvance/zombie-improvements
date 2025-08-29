package me.pajic.zombieimprovements.mixin;

import me.pajic.zombieimprovements.util.ZombieRenderStateExtension;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ZombieRenderState.class)
public class ZombieRenderStateMixin implements ZombieRenderStateExtension {
    @Unique private boolean isLeader = false;

    @Override
    public boolean zi$isLeader() {
        return isLeader;
    }

    @Override
    public void zi$setLeader(boolean bl) {
        isLeader = bl;
    }
}
