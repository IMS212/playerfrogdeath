package net.ims.playerfrogdeath.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Frog.class)
public abstract class FrogMixin extends Animal {
    protected FrogMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }


    /**
     * @author IMS
     * @reason I have no clue anymore.
     */
    @Inject(method = "canEat", at = @At("HEAD"), cancellable = true)
    private static void canEatPlayer(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (livingEntity instanceof Player) {
            cir.setReturnValue(true);
        }
    }
}
