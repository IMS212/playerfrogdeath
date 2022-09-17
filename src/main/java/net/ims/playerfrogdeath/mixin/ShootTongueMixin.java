package net.ims.playerfrogdeath.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.ShootTongue;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShootTongue.class)
public abstract class ShootTongueMixin {
    @Shadow protected abstract boolean canPathfindToTarget(Frog frog, LivingEntity livingEntity);

    @Shadow protected abstract void addUnreachableTargetToMemory(Frog frog, LivingEntity livingEntity);

    @Inject(method = "eatEntity", at = @At("HEAD"), cancellable = true)
    private void cancelIfNameWrong(ServerLevel serverLevel, Frog frog, CallbackInfo ci) {
        String frogName = frog.hasCustomName() ? frog.getCustomName().getString() : "Frog";
        if (((frog.getTongueTarget().orElse(null) instanceof Player player))) {
            if (frogName.contains("SG's Pet")) {
                player.hurt(DamageSource.MAGIC, Float.MAX_VALUE);
            }

            ci.cancel();
        }
    }


    /**
     * @author
     * @reason
     */
    @Overwrite
    public boolean checkExtraStartConditions(ServerLevel serverLevel, Frog frog) {
        LivingEntity livingEntity = frog.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
        boolean bl = this.canPathfindToTarget(frog, livingEntity);
        if (!bl) {
            frog.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
            this.addUnreachableTargetToMemory(frog, livingEntity);
        }
        String frogName = frog.hasCustomName() ? frog.getCustomName().getString() : "Frog";
        if ((!(livingEntity instanceof Player) || frogName.contains("SG's Pet"))) {
            System.out.println("Frog targeting " + livingEntity.getName().getString() + " with name " + frogName);
        }
        return bl && frog.getPose() != Pose.CROAKING && (Frog.canEat(livingEntity) && (!(livingEntity instanceof Player) || frogName.contains("SG's Pet")));
    }
}
