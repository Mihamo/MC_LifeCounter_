package mihamo.lifecount;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameMode;
import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages player lives and applies gamemode changes based on death count
 */
public class LifeCounterManager {
    private static final Logger LOGGER = LoggerFactory.getLogger("mc_lifecounter_evolution");
    private static final int INITIAL_LIVES = 3;
    private static final String DEATH_COUNT_NBT_KEY = "LifeCounter_Deaths";
    
    private static final Map<UUID, Integer> playerDeaths = new HashMap<>();
    private static MinecraftServer currentServer;
    
    public LifeCounterManager() {
        // Server lifecycle event to sync data on server start
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            currentServer = server;
            LOGGER.info("LifeCounterManager initialized");
        });
    }
    
    /**
     * Get the number of deaths for a player
     */
    public static int getDeathCount(PlayerEntity player) {
        return playerDeaths.getOrDefault(player.getUuid(), 0);
    }
    
    /**
     * Get the remaining lives for a player
     */
    public static int getRemainingLives(PlayerEntity player) {
        int deaths = getDeathCount(player);
        return Math.max(0, INITIAL_LIVES - deaths);
    }
    
    /**
     * Handle player death - increment death counter and apply gamemode changes
     */
    public static void handlePlayerDeath(PlayerEntity player) {
        UUID playerUuid = player.getUuid();
        int currentDeaths = getDeathCount(player);
        int newDeathCount = currentDeaths + 1;
        int remainingLives = getRemainingLives(player);
        
        playerDeaths.put(playerUuid, newDeathCount);
        
        LOGGER.info("Player {} died. Death count: {} / Remaining lives: {}", 
            player.getName().getString(), newDeathCount, Math.max(0, remainingLives - 1));
        
        if (!player.getWorld().isClient) {
            // Save death count to NBT for persistence
            NbtCompound nbt = player.getPersistentData();
            nbt.putInt(DEATH_COUNT_NBT_KEY, newDeathCount);
            
            // Determine new gamemode based on death count
            if (remainingLives == 1) {
                // Last life - switch to Hardcore
                changeGameMode(player, GameMode.HARDCORE);
                player.sendMessage(
                    net.minecraft.text.Text.of("§c⚠ DERNIÈRE VIE ! Vous êtes passé en mode HARDCORE !"),
                    false
                );
                LOGGER.info("Player {} is now in HARDCORE mode (last life)", player.getName().getString());
            } else if (remainingLives <= 0) {
                // No lives left - switch to Spectator
                changeGameMode(player, GameMode.SPECTATOR);
                player.sendMessage(
                    net.minecraft.text.Text.of("§4☠ GAME OVER ! Vous êtes passé en mode SPECTATEUR !"),
                    false
                );
                LOGGER.info("Player {} is now in SPECTATOR mode (no lives left)", player.getName().getString());
            } else {
                // Still has lives - keep in Survival with message
                changeGameMode(player, GameMode.SURVIVAL);
                player.sendMessage(
                    net.minecraft.text.Text.of("§6Vies restantes: " + (remainingLives - 1)),
                    false
                );
            }
        }
    }
    
    /**
     * Change a player's gamemode
     */
    private static void changeGameMode(PlayerEntity player, GameMode gameMode) {
        if (currentServer != null && !player.getWorld().isClient) {
            player.changeGameMode(gameMode);
        }
    }
    
    /**
     * Load player death data from NBT (called when player joins)
     */
    public static void loadPlayerData(PlayerEntity player) {
        NbtCompound nbt = player.getPersistentData();
        if (nbt.contains(DEATH_COUNT_NBT_KEY)) {
            int deathCount = nbt.getInt(DEATH_COUNT_NBT_KEY);
            playerDeaths.put(player.getUuid(), deathCount);
            LOGGER.info("Loaded death count for {}: {}", player.getName().getString(), deathCount);
        }
    }
    
    /**
     * Reset player data (optional - for testing or new games)
     */
    public static void resetPlayerData(PlayerEntity player) {
        playerDeaths.remove(player.getUuid());
        NbtCompound nbt = player.getPersistentData();
        nbt.remove(DEATH_COUNT_NBT_KEY);
        changeGameMode(player, GameMode.SURVIVAL);
        LOGGER.info("Reset data for player {}", player.getName().getString());
    }
}
