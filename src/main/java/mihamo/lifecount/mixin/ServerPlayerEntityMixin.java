package mihamo.lifecount.mixin;

import mihamo.lifecount.LifeCounterManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to load player data when they join
 */
@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(
        method = "<init>",
        at = @At("TAIL")
    )
    private void onPlayerInit(CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        LifeCounterManager.loadPlayerData(player);
    }
}
