package me.pajic.zombieimprovements.mixin.client;

//? >=26.1 {

import me.pajic.zombieimprovements.util.HumanoidRenderStateExtension;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(HumanoidRenderState.class)
public class HumanoidRenderStateMixin implements HumanoidRenderStateExtension {

    @Unique private boolean zi$leader = false;

    @Override
    public boolean zi$isLeader() {
        return zi$leader;
    }

    @Override
    public void zi$setLeader(boolean bl) {
        zi$leader = bl;
    }
}
//?}
