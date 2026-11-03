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
- `src/main/resources/assets/metrotrains/models/blockbench` - editable Blockbench source projects (`.bbmodel`)
- `docs` - design notes and development roadmap

## Current milestone

- [x] NeoForge 1.21.1 development environment
- [x] Mod loads in the development client
- [x] Metro Trains creative tab and first placeholder item
- [x] First straight track prototype modeled in Blockbench
- [x] Add the Blockbench source model to the repository
- [x] Add the first in-game track block and temporary 3-block-wide renderer
- [ ] Build and test the track in the development client
- [ ] Verify placement, collision, and rendering in the development client

## Track model workflow

The first track prototype is a one-block-long segment with a three-block-wide concrete bed and two steel rails. The intended gameplay collision is compact (one central block), while the visual extends across the full three-block width.

The Blockbench project is kept as an editable `.bbmodel` source file in `src/main/resources/assets/metrotrains/models/blockbench/`. It is not a Minecraft block model JSON and must not be copied directly into `models/block/`. The wide visual requires a custom in-game rendering/placement implementation; the exact implementation will be added after the source model is committed and inspected.

See [docs/track-model-pipeline.md](docs/track-model-pipeline.md) for the current modeling and integration steps.

## Design direction

- Train visuals: a mix of realistic proportions/details and Minecraft-friendly readability.
- Metro inspiration: Russian and Soviet metro architecture, rolling stock, signage, and station design.
