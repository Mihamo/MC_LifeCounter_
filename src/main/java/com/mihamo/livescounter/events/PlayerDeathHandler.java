package com.mihamo.livescounter.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.game.GameMode;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import com.mihamo.livescounter.data.PlayerLivesData;
import com.mihamo.livescounter.MCLifeCounterMod;

public class PlayerDeathHandler implements ServerLivingEntityEvents.AllowDeath {
    
    public static boolean onPlayerDeath(LivingEntity entity, DamageSource damageSource) {
        if (!(entity instanceof ServerPlayerEntity player)) {
            return true;
        }
        
        PlayerLivesData.LivesAttachment data = PlayerLivesData.getOrCreate(player);
        int currentLives = data.lives;
        int maxLives = data.maxLives;
        
        // Decrease lives
        PlayerLivesData.decreaseLives(player);
        int livesLeft = PlayerLivesData.getLives(player);
        
        // Send death message with remaining lives
        String deathMessage = String.format("%s died! Lives remaining: %d/%d", 
            player.getName().getString(), livesLeft, maxLives);
        
        broadcastMessage(player.getServerWorld(), Text.of(deathMessage));
        MCLifeCounterMod.LOGGER.info(deathMessage);
        
        // Check if this is the last life
        if (livesLeft == 1) {
            PlayerLivesData.setHardcoreMode(player, true);
            broadcastMessage(player.getServerWorld(), 
                Text.of("⚠️ " + player.getName().getString() + " LAST LIFE! Game is now in HARDCORE mode!"));
            MCLifeCounterMod.LOGGER.warn("{} is on their last life! Hardcore mode activated.", player.getName().getString());
        } 
        // Check if player is out of lives
        else if (livesLeft <= 0) {
            // Switch to spectator mode
            player.changeGameMode(GameMode.SPECTATOR);
            broadcastMessage(player.getServerWorld(), 
                Text.of("Game Over! " + player.getName().getString() + " has been switched to Spectator mode."));
            MCLifeCounterMod.LOGGER.info("{} ran out of lives and switched to Spectator mode.", player.getName().getString());
            
            // Don't allow normal death (player stays in world as spectator)
            return false;
        }
        
        // Allow normal death behavior for first 3 lives
        return true;
    }
    
    private static void broadcastMessage(ServerWorld world, Text message) {
        world.getPlayers(p -> true).forEach(player -> 
            player.sendMessage(message, false)
        );
    }
}
