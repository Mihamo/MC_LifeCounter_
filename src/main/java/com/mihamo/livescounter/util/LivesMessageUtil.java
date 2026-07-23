package com.mihamo.livescounter.util;

import net.minecraft.text.Text;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class LivesMessageUtil {
    
    public static Text formatDeathMessage(String playerName, int livesLeft, int maxLives) {
        return Text.literal(playerName)
            .append(Text.literal(" died! Lives remaining: ").withColor(0xFF0000))
            .append(Text.literal(livesLeft + "/" + maxLives).withColor(0xFFFF00));
    }
    
    public static Text formatLastLifeWarning(String playerName) {
        return Text.literal("⚠️ ")
            .append(Text.literal(playerName).withColor(0xFF0000))
            .append(Text.literal(" LAST LIFE! Game is now in HARDCORE mode!").withColor(0xFF0000).withUnderline(true));
    }
    
    public static Text formatGameOver(String playerName) {
        return Text.literal("Game Over! ")
            .append(Text.literal(playerName).withColor(0x000000))
            .append(Text.literal(" has been switched to Spectator mode.").withColor(0x808080));
    }
    
    public static Text formatLivesHUD(int lives, int maxLives) {
        Formatting color = lives == 1 ? Formatting.DARK_RED : (lives <= maxLives / 2 ? Formatting.YELLOW : Formatting.GREEN);
        return Text.literal("Lives: ").withColor(0xFFFFFF)
            .append(Text.literal(lives + "/" + maxLives).withColor(color == Formatting.DARK_RED ? 0xFF0000 : (color == Formatting.YELLOW ? 0xFFFF00 : 0x00FF00)));
    }
}
