package com.mihamo.livescounter.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.NbtCompound;

import com.mihamo.livescounter.data.PlayerLivesData;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    
    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void onReadNbt(NbtCompound nbt, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (nbt.contains("LivesData")) {
            PlayerLivesData.getOrCreate(player).readFromNbt(nbt.getCompound("LivesData"));
        }
    }
    
    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void onWriteNbt(NbtCompound nbt, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        NbtCompound livesData = new NbtCompound();
        PlayerLivesData.getOrCreate(player).writeToNbt(livesData);
        nbt.put("LivesData", livesData);
    }
}
