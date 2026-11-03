# Straight track model pipeline

## First prototype

- Minecraft target: 1.21.1
- Loader: NeoForge 21.1.265
- Blockbench source name: `metro_track_straight.bbmodel`
- Segment dimensions: 48 units wide (3 blocks), 4 units high for the concrete bed, 16 units long (1 block).
- Visual parts: one continuous concrete bed and two steel rails.
- Intended gameplay collision: one compact central block, not the full three-block visual width.

## Save the Blockbench source

1. In Blockbench, use **File → Save**.
2. Save the project as `metro_track_straight.bbmodel`.
3. Keep the editable source file. Do not convert the entire 48-unit-wide model into a vanilla Minecraft Block/Item model.
4. Add the source file to:
   `src/main/resources/assets/metrotrains/models/blockbench/metro_track_straight.bbmodel`

The repository's Gradle resource configuration excludes `.bbmodel` files from the built mod JAR, so this is a development source asset.

## Why this is not a normal block model

A vanilla Minecraft block model is designed around a single 16×16×16 block. A single 48-unit-wide model cannot safely be treated as an ordinary block model and expected to render across neighboring block positions.

The in-game implementation must deliberately separate:
- the block position and collision shape used by gameplay;
- the visual geometry that spans three blocks.

The renderer/placement implementation will be selected after inspecting the actual Blockbench source file and its element coordinates. This avoids writing an importer for a model format that does not match the saved project.

## Current status

The first geometry prototype has been modeled in Blockbench. The screenshot is useful for visual review, but it is not a substitute for the editable `.bbmodel` file. The model source must be added before its exact geometry and pivots can be integrated into Java rendering.
