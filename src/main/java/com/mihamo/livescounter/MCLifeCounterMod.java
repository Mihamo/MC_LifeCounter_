package com.mihamo.livescounter;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mihamo.livescounter.config.LivesConfig;
import com.mihamo.livescounter.events.PlayerDeathHandler;
import com.mihamo.livescounter.data.PlayerLivesData;

public class MCLifeCounterMod implements ModInitializer {
	public static final String MOD_ID = "mc_lifecounter";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("MC Life Counter Mod initialized!");
		
		// Load configuration
		LivesConfig.load();
		
		// Register event handlers
		ServerLivingEntityEvents.ALLOW_DEATH.register(PlayerDeathHandler::onPlayerDeath);
		
		LOGGER.info("Starting with {} lives per player", LivesConfig.DEFAULT_LIVES);
	}
}
