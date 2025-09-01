package me.pajic.zombieimprovements.mixin;

import me.pajic.zombieimprovements.util.HumanoidRenderStateExtension;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(HumanoidRenderState.class)
public class HumanoidRenderStateMixin implements HumanoidRenderStateExtension {
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
