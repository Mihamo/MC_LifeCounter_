package com.mihamo.livescounter.data;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import com.mihamo.livescounter.MCLifeCounterMod;
import com.mihamo.livescounter.config.LivesConfig;

public class PlayerLivesData {
    private static final Identifier LIVES_DATA_ID = new Identifier(MCLifeCounterMod.MOD_ID, "lives_data");
    
    public static class LivesAttachment {
        public int lives;
        public int maxLives;
        public boolean isHardcoreMode = false;
        
        public LivesAttachment(int maxLives) {
            this.maxLives = maxLives;
            this.lives = maxLives;
        }
        
        public void readFromNbt(NbtCompound nbt) {
            if (nbt.contains("Lives")) {
                this.lives = nbt.getInt("Lives");
                this.maxLives = nbt.getInt("MaxLives");
                this.isHardcoreMode = nbt.getBoolean("HardcoreMode");
            }
        }
        
        public void writeToNbt(NbtCompound nbt) {
            nbt.putInt("Lives", this.lives);
            nbt.putInt("MaxLives", this.maxLives);
            nbt.putBoolean("HardcoreMode", this.isHardcoreMode);
        }
    }
    
    public static LivesAttachment getOrCreate(PlayerEntity player) {
        try {
            var attachment = player.getAttachedOrCreate(LIVES_DATA_ID, () -> new LivesAttachment(LivesConfig.getDefaultLives()));
            return attachment;
        } catch (Exception e) {
            MCLifeCounterMod.LOGGER.warn("Could not get lives data for player {}, creating new", player.getName().getString());
            return new LivesAttachment(LivesConfig.getDefaultLives());
        }
    }
    
    public static int getLives(PlayerEntity player) {
        return getOrCreate(player).lives;
    }
    
    public static int getMaxLives(PlayerEntity player) {
        return getOrCreate(player).maxLives;
    }
    
    public static void decreaseLives(PlayerEntity player) {
        LivesAttachment data = getOrCreate(player);
        if (data.lives > 0) {
            data.lives--;
        }
    }
    
    public static void setHardcoreMode(PlayerEntity player, boolean enabled) {
        getOrCreate(player).isHardcoreMode = enabled;
    }
    
    public static boolean isHardcoreMode(PlayerEntity player) {
        return getOrCreate(player).isHardcoreMode;
    }
    
    public static void resetLives(PlayerEntity player) {
        LivesAttachment data = getOrCreate(player);
        data.lives = data.maxLives;
        data.isHardcoreMode = false;
    }
}
