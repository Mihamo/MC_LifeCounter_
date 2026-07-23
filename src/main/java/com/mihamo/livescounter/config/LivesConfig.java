package com.mihamo.livescounter.config;

public class LivesConfig {
    public static final int DEFAULT_LIVES = 3;
    public static final int MIN_LIVES = 1;
    public static final int MAX_LIVES = 10;
    
    private static int defaultLives = DEFAULT_LIVES;
    
    public LivesConfig() {
    }
    
    public static void load() {
        // Configuration loading logic here
        // For now, using default values
        defaultLives = DEFAULT_LIVES;
    }
    
    public static int getDefaultLives() {
        return defaultLives;
    }
    
    public static void setDefaultLives(int lives) {
        if (lives >= MIN_LIVES && lives <= MAX_LIVES) {
            defaultLives = lives;
        }
    }
}
