package me.pajic.zombieimprovements.mixin.client;

//? >=26.1 {

import me.pajic.zombieimprovements.util.LeaderAuraModelProvider;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.monster.zombie.BabyZombieVillagerModel;
import net.minecraft.client.model.monster.zombie.ZombieVillagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieVillagerRenderer;
import net.minecraft.client.renderer.entity.state.ZombieVillagerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieVillagerRenderer.class)
public class ZombieVillagerRendererMixin implements LeaderAuraModelProvider {

    @Unique private ZombieVillagerModel<ZombieVillagerRenderState> auraModel;
    @Unique private ZombieVillagerModel<ZombieVillagerRenderState> babyAuraModel;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void createAuraModels(EntityRendererProvider.Context context, CallbackInfo ci) {
        auraModel = new ZombieVillagerModel<>(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER));
        babyAuraModel = new BabyZombieVillagerModel<>(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_BABY));
        zi$hideHat(auraModel);
        zi$hideHat(babyAuraModel);
    }

    @Unique
    private static void zi$hideHat(ZombieVillagerModel<?> model) {
        ModelPart head = model.getHead();
        head.getChild("hat").visible = false;
        if (head.hasChild("hat_rim")) head.getChild("hat_rim").visible = false;
    }

    @Override
    public EntityModel<?> zi$getAuraModel(boolean baby) {
        return baby ? babyAuraModel : auraModel;
    }
}
//?}
