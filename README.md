# MC Life Counter Mod

A Minecraft Fabric mod (1.20.2) that adds a lives system with progressive hardcore mode.

## Features

✅ **Lives Counter** - Each player starts with a configurable number of lives (default: 3)

✅ **Death Tracking** - Count deaths and display remaining lives when a player dies

✅ **Last Life Warning** - When a player reaches their last life, the game switches to HARDCORE mode for that player

✅ **Game Over Spectator Mode** - When a player runs out of lives, they are switched to Spectator mode

## Configuration

Default configuration:
- **Default Lives**: 3 lives per player
- **Min Lives**: 1
- **Max Lives**: 10

Edit `LivesConfig.java` to change default values.

## Installation

1. Install [Fabric Loader](https://fabricmc.net/)
2. Install [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
3. Download the latest mod JAR from [Releases](https://github.com/Mihamo/MC_LifeCounter_/releases)
4. Place it in your `mods` folder
5. Launch Minecraft

## How It Works

### Game Flow
1. Player starts with configured number of lives
2. When a player dies:
   - Lives counter decreases by 1
   - A message displays: "Player died! Lives remaining: X/Y"
   - Player respawns normally

3. When a player reaches their **last life**:
   - Game switches to HARDCORE mode for that player
   - A warning message displays in red
   - If they die again, they switch to Spectator mode

4. When a player is **out of lives**:
   - They are automatically switched to Spectator mode
   - Game Over message is displayed
   - They can still view the world but cannot interact

## Translation Support

The mod includes translations for:
- 🇺🇸 English (en_us.json)
- 🇫🇷 French (fr_fr.json)

Add more translations by creating new JSON files in `src/main/resources/assets/livescounter/lang/`

## Building

```bash
./gradlew build
```

The compiled JAR will be in `build/libs/`

## License

MIT License - See LICENSE file for details

## Author

Mihamo

---

**Compatible with**: Minecraft 1.20.2 + Fabric
