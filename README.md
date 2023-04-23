# SeedCrackerX-Forge
a port of 19MisterX98's SeedcrackerX for forge

## Installation

 ### Vanilla Launcher

  Download and install the [forge mod loader](https://files.minecraftforge.net/).

 ### MultiMC
 
  Add a new minecraft instance and press "Install Forge" in the instance options.

 ### Mod Installation
 
  Download the lastest [release](https://github.com/someElseIsHere/SeedCrackerX-Forge/releases) of SeedCrackerX Forge    
  Download [Cloth Config](https://www.curseforge.com/minecraft/mc-mods/cloth-config)
  
  put the .jar files in your mods directory, either %appdata%/.minecraft/mods/ folder for the vanilla launcher or your own MultiMC instance folder.
  
## Usage

  Run around in the world until the mod finds a dungeon. After the mod found one the cracking process starts automaticly. If it doesnt get you a world seed you may want to find another dungeon. This mod also supports cracking the seed via [structures and endpillars](https://youtu.be/aUuPSZVPH8E?t=462) and [warped fungus](https://www.youtube.com/watch?v=HKjwgofhKs4)
  
  ### Supported Structures
    - Ocean Monument
    - End City
    - Buried Treasure
    - Desert Pyramid
    - Jungle Temple
    - Swamp Hut
    - Shipwreck
  
  ### Supported Decorators
    - Dungeon
    - End Gateway
    - Desert Well
    - Emerald Ore
    - Warped Fungus

## Commands(Deprecated, use the GUI instead)

  The command prefix for this mod is /seed.
  
  ### Render Command (Currently Broken)
  -`/seed render outlines <ON/OFF/XRAY>`
    
  This command only affects the renderer feedback. The default value is 'XRAY' and highlights data through blocks. You can set    the render mod to 'ON' for more standard rendering. 
  
  ### Finder Command
  -`/seed finder type <FEATURE_TYPE> (ON/OFF)`
  
  -`/seed finder category (BIOMES/ORES/OTHERS/STRUCTURES) (ON/OFF)`
  
  This command is used to disable finders in case you are aware the data is wrong. For example, a map generated in 1.14 has different decorators and would require you to disable them while going through those chunks.
  
  -`/seed finder reload`
  
  Searches the loaded area again

  ### Data Command
  - `/seed data clear`
  
  Clears all the collected data without requiring a relog. This is useful for multi-world servers.
  
  - `/seed data bits`
  
  Display how many bits of information have been collected. Even though this is an approximate, it serves as a good basis to guess when the brute-forcing should start.
  
  ### Cracker Command
  - `/seed cracker <ON/OFF>`
 
  Enables or disables the mod completely. Unlike the other commands, this one is persistent across reloads.
  
  - `/seed cracker debug`

  Additional info is shown
  
## Video Tutorials

https://youtu.be/1ChmLi9og8Q

https://youtu.be/8ytfZ2MXosY

## Upcoming Features

A list of features I have on my mind... they won't necessarily be implemented in this order if at all.

    - Stronghold portal room cracker. (alternative to dungeon floor?)
    - Tree data collection (probably only oak and birch. Puts info into a file that can be compiled to run on GPU)

## Setting up the Workspace

-Clone the repository.

-Run `gradlew genSources <idea|eclipse>`.

## Building the Mod

-Update the version in `build.gradle` and `mod.toml`.

-Run `gradlew build`.
 
## Contributors

[KaptainWutax](https://github.com/KaptainWutax) - Author

[neil](https://www.youtube.com/watch?v=aUuPSZVPH8E) - Video Tutorial

[Nekzuris](https://github.com/Nekzuris) - README

[19MisterX98](https://www.youtube.com/channel/UCby9ZxEjJCqmccQGF3GSYlA) - Author of SeedCrackerX

[someElseIsHere](https://github.com/someElseIsHere/) Author of this Fork
