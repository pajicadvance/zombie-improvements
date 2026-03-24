package me.pajic.zombieimprovements.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.zombieimprovements.util.HumanoidRenderStateExtension;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
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
