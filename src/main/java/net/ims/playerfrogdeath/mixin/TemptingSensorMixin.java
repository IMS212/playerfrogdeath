package net.ims.playerfrogdeath.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.frog.FrogAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(TemptingSensor.class)
public class TemptingSensorMixin {
    @Shadow @Final private static TargetingConditions TEMPT_TARGETING;
    private boolean frog;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void checkFrog(Ingredient ingredient, CallbackInfo ci) {
        this.frog = ingredient == FrogAi.getTemptations();
    }

    @Inject(method = "playerHoldingTemptation", at = @At("HEAD"), cancellable = true)
    private void killPlayers(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (frog) {
            cir.setReturnValue(true);
        }
    }
}
