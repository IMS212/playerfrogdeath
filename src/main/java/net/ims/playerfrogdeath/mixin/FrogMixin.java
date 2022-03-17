package net.ims.playerfrogdeath.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.frog.Frog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Frog.class)
public class FrogMixin {
    /**
     * @author IMS
     * @reason I have no clue anymore.
     */
    @Overwrite
    public static boolean canEat(LivingEntity livingEntity) {
        return true;
    }
}
