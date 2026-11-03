# Metro Trains

A Minecraft mod about metro trains, stations, tunnels, and transport infrastructure, inspired primarily by Russian and Soviet metro systems.

## Target platform

- Minecraft: 1.21.1
- Mod loader: NeoForge 21.1.x
- NeoForge version: 21.1.265
- Java: 21
- Mod ID: `metrotrains`
- Base package: `com.artemoniy7.metrotrains`

## Development setup

1. Install a 64-bit JDK 21.
2. Install IntelliJ IDEA.
3. Clone this repository.
4. Open the repository folder in IntelliJ IDEA and import it as a Gradle project.
5. Wait for Gradle to download Minecraft and NeoForge development dependencies.
6. Run the `runClient` configuration, or use `gradlew.bat runClient` on Windows.
7. Build the mod with `gradlew.bat build` on Windows or `./gradlew build` on macOS/Linux.

The Gradle Wrapper is included, so a separate Gradle installation is not required.

## Project layout

- `src/main/java` - Java source code
- `src/main/templates/META-INF/neoforge.mods.toml` - generated NeoForge mod metadata
- `src/main/resources/assets/metrotrains` - models, textures, blockstates, and language files
- `docs` - design notes and development roadmap

## Current milestone

The project now contains the official NeoForge 1.21.1 ModDevGradle build setup. The next milestone is to verify a clean build and launch the development client, then add the first metro-themed blocks and items.

## Design direction

- Train visuals: a mix of realistic proportions/details and Minecraft-friendly readability.
- Metro inspiration: Russian and Soviet metro architecture, rolling stock, signage, and station design.
