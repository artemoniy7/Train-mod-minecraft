# Metro Trains Mod

A Minecraft mod focused on metro trains, railway infrastructure, stations, and transport systems.

## Target platform

- Minecraft: 1.21.1
- Mod loader: NeoForge 21.1.x
- Java: 21
- Mod ID: `metrotrains`
- Base package: `com.artemoniy7.metrotrains`

## Development setup

1. Install a 64-bit JDK 21 and IntelliJ IDEA.
2. Generate/download the official NeoForge 1.21.1 MDK project.
3. Copy its Gradle build files and Gradle Wrapper into this repository root. Keep the MDK's compatible Gradle and NeoForge plugin versions.
4. Open the project in IntelliJ IDEA and import it as a Gradle project.
5. Run the generated `runClient` configuration.
6. Build with `./gradlew build` (Windows: `gradlew.bat build`).

## Project layout

- `src/main/java` - Java source code
- `src/main/resources/META-INF` - NeoForge mod metadata
- `src/main/resources/assets/metrotrains` - models, textures, blockstates, and language files
- `docs` - design notes and development roadmap

## Status

Initial project skeleton. The first milestone is a mod that loads successfully in a 1.21.1 NeoForge development client.
