package mihamo.lifecount.mixin;

import mihamo.lifecount.LifeCounterManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to intercept player death events
 */
@Mixin(ServerPlayerEntity.class)
public class PlayerDeathMixin {
    @Inject(
        method = "onDeath",
        at = @At("HEAD")
    )
    private void onPlayerDeath(CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        LifeCounterManager.handlePlayerDeath(player);
    }
}
