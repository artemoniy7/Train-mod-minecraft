# Development roadmap

## Milestone 1: Foundation
- [x] Add the official NeoForge 1.21.1 ModDevGradle build setup and Gradle Wrapper
- [ ] Launch the development client
- [ ] Confirm that `metrotrains` appears in the Mods list

## Milestone 2: First content
- [ ] Register a test item and block
- [ ] Add item models, blockstates, and textures
- [ ] Add creative tab entries

## Milestone 3: Railway prototype
- [ ] Design track placement and connections
- [ ] Create the first rail vehicle entity
- [ ] Implement basic acceleration, braking, and steering

## Milestone 4: Metro systems
- [ ] Add multi-car trains and coupling
- [ ] Add doors and passenger interaction
- [ ] Add stations, signals, and route logic

## Engineering principles
- Keep gameplay logic server-authoritative.
- Separate registration, content, entities, and client rendering.
- Test on both a development client and a dedicated server.
