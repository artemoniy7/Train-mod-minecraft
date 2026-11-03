package com.artemoniy7.metrotrains.registry;

import com.artemoniy7.metrotrains.MetroTrains;
import com.artemoniy7.metrotrains.block.entity.TrackBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MetroTrains.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TrackBlockEntity>> METRO_TRACK =
            BLOCK_ENTITY_TYPES.register("metro_track",
                    () -> BlockEntityType.Builder.of(TrackBlockEntity::new, ModBlocks.METRO_TRACK.get()).build(null));

    private ModBlockEntities() {}
}
